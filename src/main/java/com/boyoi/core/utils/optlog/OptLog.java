package com.boyoi.core.utils.optlog;

import com.boyoi.base.domain.LogOpt;
import com.boyoi.base.domain.User;
import com.boyoi.base.enums.LogTypeEnum;
import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.web.controller.OauthController;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.utils.RedisUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis - 操作日志拦截器
 *
 * 拦截insert , update 语句
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }))
public class OptLog implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(OptLog.class);

    private boolean enable;

    /**
     * Mybatis拦截器方法
     *
     * @param invocation 拦截器入参
     * @return 返回执行结果
     * @throws Throwable 抛出异常
     */
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        //RowBounds rowBounds = (RowBounds) args[2];
        //判断参数带不带EasyGridRequest,不带不拦截
        if(enable){
            MappedStatement mappedStatement = (MappedStatement) args[0];
            SqlCommandType sqlType = mappedStatement.getSqlCommandType();
            SqlSource sqlSource = ((MappedStatement) args[0]).getSqlSource();
            BoundSql boundSql = sqlSource.getBoundSql(args[1]);
            // 如是删除语句
            if (sqlType == SqlCommandType.DELETE){
                // 保存删除日志
                saveDelLog(args[1], boundSql.getSql());
                // 返回结果
                return invocation.proceed();
            }else if (sqlType == SqlCommandType.INSERT){
                // 添加语句
                // 保存添加日志
                Object result = invocation.proceed();
                saveInsertLog(args[1], boundSql.getSql(), result);
                return result;
            }else if (sqlType == SqlCommandType.UPDATE){
                // 更新语句
                Object result = invocation.proceed();
                saveUpdateLog(args[1], boundSql.getSql(), result);

            }
        }

        //执行下一个拦截器
        return invocation.proceed();
    }

    /**
     * 保存更新日志
     * @param arg 保存对象
     * @param sql sql
     * @param result 结果
     */
    private void saveUpdateLog(Object arg, String sql, Object result) {
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (NullPointerException n){
            // 获得oauth2登录的用户

            // 未登录。ignore
            return;
        }
        String optPerson = getOptPerson(request);
        if (null == optPerson){
            return;
        }

        Map<String, LogOpt> cache = OptLogCache.getOptLogCache();
        if (arg instanceof  BaseDomain){
            BaseDomain baseDomain = (BaseDomain) arg;
            LogOpt logOpt = new LogOpt();
            logOpt.setOptDate(new Date());
            logOpt.setOptType(LogTypeEnum.U);
            if (baseDomain.getGuid() == null){
                LOG.warn(baseDomain.getClass().getName() + "中GUID不能为空！！");
                LOG.warn("---  请在保存数据库之前查看为什么为空。否则不能保存日志 ---");
                return;
            }
            logOpt.setOptGuid(baseDomain.getGuid());
            logOpt.setDescription(sql);
            logOpt.setOptPerson(optPerson);
            logOpt.setBaseDomain(arg);
            if (result instanceof Integer)
                logOpt.setOptSuccess(String.valueOf(result));

            // 放入缓存中,等待添加日志
            cache.put(baseDomain.getGuid(), logOpt);
        }
    }


    /**
     * 保存添加日志
     * @param arg 请求参数
     * @param sql sql
     * @param result 添加结果
     */
    private void saveInsertLog(Object arg, String sql, Object result) {
        // 如是自身, 不操作
        if (sql.startsWith("insert into tsys_log_opt")){
            return;
        }
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (NullPointerException n){
            // 未登录。ignore
            return;
        }
        String optPerson = getOptPerson(request);
        if (null == optPerson){
            return;
        }

        Map<String, LogOpt> cache = OptLogCache.getOptLogCache();

        if(arg instanceof BaseDomain){
            BaseDomain baseDomain = (BaseDomain) arg;
            LogOpt logOpt = new LogOpt();
            logOpt.setOptDate(new Date());
            logOpt.setOptType(LogTypeEnum.A);
            if (baseDomain.getGuid() == null){
                LOG.warn(baseDomain.getClass().getName() + "中GUID不能为空！！");
                LOG.warn("---  请在保存数据库之前为guid赋值。否则不能保存日志 ---");
                return;
            }
            logOpt.setOptGuid(baseDomain.getGuid());
            logOpt.setDescription(sql);
            logOpt.setOptPerson(optPerson);
            logOpt.setBaseDomain(arg);
            if (result instanceof Integer)
                logOpt.setOptSuccess(String.valueOf(result));

            // 放入缓存中,等待添加日志
            cache.put(baseDomain.getGuid(), logOpt);
        }
    }

    /**
     * 保存删除日志
     * @param obj 插入对象
     * @param sql sql
     */
    private void saveDelLog(Object obj, String sql) {

        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (NullPointerException n){
            // 未登录。ignore
            return;
        }
        String optPerson = getOptPerson(request);
        if (null == optPerson){
            return;
        }

        Map<String, LogOpt> cache = OptLogCache.getOptLogCache();

        if (obj instanceof Map){ // 批量删除
            if (((Map) obj).containsKey("list")){
                List<String> guids = (List<String>) ((Map) obj).get("list");
                LogOpt logOpt;
                for (String guid : guids){
                    logOpt = new LogOpt();
                    logOpt.setOptDate(new Date());
                    logOpt.setOptType(LogTypeEnum.D);
                    logOpt.setOptGuid(guid);
                    logOpt.setOptPerson(optPerson);
                    logOpt.setDescription(sql);
                    cache.put(guid, logOpt);
                }
            }

        }else if (obj instanceof BaseDomain){ // 实体删除
            LogOpt logOpt;
            logOpt = new LogOpt();
            logOpt.setOptDate(new Date());
            logOpt.setOptType(LogTypeEnum.D);
            logOpt.setOptGuid(((BaseDomain) obj).getGuid());
            logOpt.setOptPerson(optPerson);
            logOpt.setDescription(sql);
            cache.put(((BaseDomain) obj).getGuid(), logOpt);
        }

    }

    /**
     * 获得操作人
     */
    private String getOptPerson(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(LoginFlag.LOGIN_SUCCESS.name());
        if (null == user){
            String token = request.getHeader("token");
            if (null == token){
                token = request.getParameter("token");
            }
            if (null != token && !"".equals(token)){
                Authentication redisAuth = RedisUtil.get(token, OauthController.DB_NUM);
                if (null != redisAuth){
                    return  redisAuth.getCredentials().toString();
                }
            }
        }else {
            return user.getRealName();
        }
        return null;
    }


    /**
     * 只拦截Executor
     *
     * @param target 目标
     * @return object
     */
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 设置属性值
     *
     * @param p 属性值
     */
    public void setProperties(Properties p) {
        // 是否开启
        String enableString = p.getProperty("enable");
        enable = "yes".equalsIgnoreCase(enableString);
    }
}
