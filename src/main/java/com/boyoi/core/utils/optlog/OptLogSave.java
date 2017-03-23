package com.boyoi.core.utils.optlog;

import com.alibaba.fastjson.JSON;
import com.boyoi.base.domain.LogLogin;
import com.boyoi.base.domain.LogOpt;
import com.boyoi.base.enums.LogTypeEnum;
import com.boyoi.base.service.LogOptService;
import com.boyoi.core.utils.JdbcUtil;
import com.boyoi.core.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 保存操作日志的bean
 * @author Chenj
 */
public class OptLogSave {

    private static final Logger LOG = LoggerFactory.getLogger(OptLogSave.class);

    private LogOptService logOptService;

    /**
     * 每隔30秒, 执行保存日志
     */
    public void init(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start();
            }
        }, 30000, 30000);
    }

    public void start(){
        Map<String, LogOpt> cache = OptLogCache.getOptLogCache();
        Set<Map.Entry<String, LogOpt>> entries = cache.entrySet();
        for (Map.Entry<String, LogOpt> entry : entries){
            LogOpt logOpt = entry.getValue();
            logOpt.setGuid(WebUtils.generateID());
            // 获得sql, 取出操作表名
            String sql = logOpt.getDescription();
            if (null == sql || "".equals(sql)){
                continue;
            }
            String[] split = sql.split(" ");
            String table;

            // 更新取第二个, 删除和添加为第三个
            if (sql.startsWith("update")){
                table = split[1].replace("\n", "");
            }else {
                table = split[2].replace("\n", "");
            }
            logOpt.setOptObject(table);

            // 删除, 获得数据是否存在, 存在就说明操作失败
            if (logOpt.getOptType() == LogTypeEnum.D){
                logOpt.setDescription(null);  // 清空描述
                Connection conn = null;
                try {
                    conn = JdbcUtil.getConn();
                    if (null != conn){
                        String sqlSuccess = "select count(1) from " + table + " where guid =?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sqlSuccess);
                        preparedStatement.setString(1, logOpt.getOptGuid());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){
                            logOpt.setOptSuccess(resultSet.getInt(1)==0?"1":"0");
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    LOG.debug("保存操作日志发生异常!");
                }finally {
                    JdbcUtil.close(null,null,conn);
                }

            }else if (logOpt.getOptType() == LogTypeEnum.A || logOpt.getOptType() == LogTypeEnum.U){  // 添加语句 或 更新语句
                // 解析添加的对象
                Object baseDomain = logOpt.getBaseDomain();
                // 登录日志, 不记录
                if (baseDomain instanceof LogLogin)
                    continue;

                logOpt.setDescription(JSON.toJSONString(baseDomain));

            }

            // 保存在数据库中
            logOptService.add(logOpt);

        }

        // 清除缓存
        cache.clear();
    }

    /**
     * 销毁时, 保存没有保存的日志
     */
    public void destory(){
        start();
    }

    public void setLogOptService(LogOptService logOptService) {
        this.logOptService = logOptService;
    }
}
