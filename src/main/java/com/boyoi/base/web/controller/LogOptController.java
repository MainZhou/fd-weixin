package com.boyoi.base.web.controller;

import com.alibaba.fastjson.JSON;
import com.boyoi.base.domain.LogOpt;
import com.boyoi.base.enums.LogTypeEnum;
import com.boyoi.base.service.LogOptService;
import com.boyoi.core.annotation.LogOptDisplayForSubObject;
import com.boyoi.core.annotation.LogOptNoDisplay;
import com.boyoi.core.common.SpringTool;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.utils.GetI18N;
import com.boyoi.core.utils.StringUtil;
import com.boyoi.core.web.controller.BaseCrudController;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 controller层
 *
 * @author Chenj
 */
@Controller
@RequestMapping(value = {"logopt", "logOpt" })
public class LogOptController extends BaseCrudController<LogOpt,LogOptService> {

    /**
    * 进入添加对象前,需要初始化的一些数据
    * @param logopt 操作日志实体对象
    * @param model 模型
    */
    @Override
    protected void addUiInit(LogOpt logopt, Model model) {

    }

    /**
    * 进入编辑对象前,需要初始化的一些数据
    * @param model 模型
    */
    @Override
    protected void updateUiInit(Model model) {

    }

    @Override
    protected void beforeListAll(Model model) {
        model.addAttribute("optType", LogTypeEnum.values());
    }

    @Override
    public String detail(String guid, Model model) {
        // 查找当前对应的所有操作日志
        List<LogOpt> logOpts = service.findOptLogByGuid(guid);
        execResult(model, logOpts);
        return "/LogOpt/detail";
    }

    @RequestMapping(value = "detailOptGuid")
    public String detailByOptGuid(String optGuid, Model model) {
        // 查找当前对应的所有操作日志
        List<LogOpt> logOpts =  service.findOptLogByOptGuid(optGuid);
        execResult(model, logOpts);
        return "/LogOpt/detail";
    }

    private void execResult(Model model, List<LogOpt> logOpts) {
        if (null != logOpts){
            if (logOpts.size() > 1){
                LogOpt logOpt0 = logOpts.get(logOpts.size()-1);
                for(int i=logOpts.size()-1; i>=1;i--){
                    // 解析
                    compare(logOpt0, logOpts.get(i-1));
                    logOpt0 = logOpts.get(i-1);
                }
            }else if (logOpts.size() ==1){
                decodeOptLog(null, logOpts.get(0));
                LogOpt logOpt = logOpts.get(0);
                for (Map.Entry<String, Object> entry0 : logOpt.getMiddleMap().entrySet()) {
                    logOpt.setCompareDesc(logOpt.getCompareDesc() + "\n" +  entry0.getKey() + " : " + entry0.getValue());
                    logOpt.setCompareSize(logOpt.getCompareSize() + 1);
                }

            }else {
                model.addAttribute("noLog", "没有日志!");
                return;
            }

            String optObject = logOpts.get(0).getOptObject();
            String s = StringUtil.tableName2Prefix(optObject);
            logOpts.get(0).setOptObject(GetI18N.get(request, s + ".func.name"));

            model.addAttribute("detail", logOpts.get(0));
            model.addAttribute("currAllOptLog", logOpts);
        }
    }


    private void compare(LogOpt logOpt0, LogOpt logOpt1) {

        // 解析
        decodeOptLog(null, logOpt0);
        decodeOptLog(logOpt0, logOpt1);

        Map<String, Object> middleMap0 = logOpt0.getMiddleMap();
        Map<String, Object> middleMap1 = logOpt1.getMiddleMap();
        // 删除操作时间
        middleMap0.remove("操作时间");
        middleMap1.remove("操作时间");

        // 对比描述。只对比0并且为更新
        if (logOpt0.getOptType() == LogTypeEnum.A && logOpt1.getOptType() == LogTypeEnum.U ){

            for (Map.Entry<String, Object> entry0 : middleMap0.entrySet()){
                // 两个相同
                if (null != entry0.getValue()){
                    if (!entry0.getValue().equals(middleMap1.get(entry0.getKey()))){
                        // 不为空。添加到下一个
                        if (null != middleMap1.get(entry0.getKey())) {
                            logOpt1.setCompareDesc(logOpt1.getCompareDesc() + "\n" + entry0.getKey() + " : " + middleMap1.get(entry0.getKey()));
                            logOpt1.setCompareSize(logOpt1.getCompareSize() + 1);
                        }else{
                            // 为空。设置初始值
                            logOpt0.setCompareDesc(logOpt0.getCompareDesc() + "\n" + entry0.getKey() + " : " + entry0.getValue());
                            logOpt0.setCompareSize(logOpt0.getCompareSize() + 1);
                        }
                    }else {
                        // 相同
                        logOpt0.setCompareDesc(logOpt0.getCompareDesc() + "\n" + entry0.getKey() + " : " + entry0.getValue());
                        logOpt0.setCompareSize(logOpt0.getCompareSize() + 1);
                    }
                }else if (entry0.getValue() != middleMap1.get(entry0.getKey())){
                    // 不相同设置到下一个
                    if (null != middleMap1.get(entry0.getKey())) {
                        logOpt1.setCompareDesc(logOpt1.getCompareDesc() + "\n" + entry0.getKey() + " : " + middleMap1.get(entry0.getKey()));
                        logOpt1.setCompareSize(logOpt1.getCompareSize() + 1);
                    }
                }else{
                    // 相同
                    if (null != middleMap0.get(entry0.getKey())) {
                        logOpt0.setCompareDesc(logOpt0.getCompareDesc() + "\n" + entry0.getKey() + " : " + entry0.getValue());
                        logOpt0.setCompareSize(logOpt0.getCompareSize() + 1);
                    }
                }
            }

        }else if (logOpt0.getOptType() == LogTypeEnum.U && logOpt1.getOptType() == LogTypeEnum.U){

            for (Map.Entry<String, Object> entry0 : middleMap0.entrySet()){
                // 两个相同
                if (null != entry0.getValue()){
                    if (!entry0.getValue().equals(middleMap1.get(entry0.getKey()))){
                        // 不相同设置到下一个，且不为空
                        if (null != middleMap1.get(entry0.getKey())){
                            logOpt1.setCompareDesc(logOpt1.getCompareDesc() + "\n" + entry0.getKey() + " : " + middleMap1.get(entry0.getKey()));
                            logOpt1.setCompareSize(logOpt1.getCompareSize() + 1);
                        }
                    }
                }else if (entry0.getValue() != middleMap1.get(entry0.getKey())){
                    // 不相同设置到下一个
                    if (null != middleMap1.get(entry0.getKey())) {
                        logOpt1.setCompareDesc(logOpt1.getCompareDesc() + "\n" + entry0.getKey() + " : " + middleMap1.get(entry0.getKey()));
                        logOpt1.setCompareSize(logOpt1.getCompareSize() + 1);
                    }
                }
            }
        }

    }

    /**
     * 解析
     */
	private void decodeOptLog(LogOpt lastLogOpt,LogOpt afterLogOpt) {
        decodeOptLog(afterLogOpt);

        if (null != lastLogOpt){
            decodeOptLog(lastLogOpt);
            // 获得上次的结果
            Map<String, Object> lastMap = lastLogOpt.getMiddleMap();
            // 把上次的结果差异的文件放在下一个结果中
            Map<String, Object> afterMap = afterLogOpt.getMiddleMap();

            // 放入差异文件
            for (Map.Entry<String, Object> map : lastMap.entrySet()){
                if (!afterMap.containsKey(map.getKey())){
                    afterMap.put(map.getKey(), map.getValue());
                }
            }

        }

    }

    /**
     * 解析单个
     * @param logOpt opt对象
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void decodeOptLog(LogOpt logOpt) {
        // 不是删除
        if (logOpt.getOptType() != LogTypeEnum.D && logOpt.getMiddleMap().size() ==0){
            // 解析description
            String description = logOpt.getDescription();
            if (null != description && !"".equals(description)){
                try {
                    String tableName = logOpt.getOptObject();
                    String domainName = StringUtil.tableName2Prefix(tableName);

                    // 获得声明的field
                    Class aClass = null;
                    try {
                        aClass = Class.forName("com.boyoi.work.domain." + domainName);
                    }catch (ClassNotFoundException n){
                        try {
                            aClass = Class.forName("com.boyoi.base.domain." + domainName);
                        } catch (ClassNotFoundException e) {
                            //ignore
                        }
                    }

                    Object result = JSON.parseObject(description, aClass);
                    if (null != result){
                        // 获得声明的字段
                        Field[] declaredFields = result.getClass().getDeclaredFields();
                        // 解析对象
                        decodeObj(domainName, result, declaredFields, logOpt.getMiddleMap());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    LOG.warn("使用公用方法查询发生异常。将不会显示详情");
                    super.detailBefore(logOpt);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        }
    }

    /**
     * 解析对象
     */
    private void decodeObj(String domainName, Object result, Field[] declaredFields, Map<String, Object> middleMap) throws IllegalAccessException {
        for (Field field : declaredFields){
            if (!field.getName().equals("serialVersionUID")){
                // 暴力获得
                field.setAccessible(true);
                // 只获得显示的值
                if (null ==  field.getAnnotation(LogOptNoDisplay.class)){

                    Object obj = field.get(result);
                    if (null != obj){
                        Object val;
                        if (obj instanceof BaseDomain){  // 是对象
                            // 获得对象的信息
                            val = getSubObjInfo(obj);
                        }else if (obj instanceof Date){  // 时间
                            DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                            SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormat.pattern());
                            val = dateFormat.format(field.get(result));
                        }else {  // 其它
                            val = obj.toString();
                        }
                        // 不为null. 才放入
                        middleMap.put(GetI18N.get(request, domainName + "." + field.getName() + ".label"), val);
                    }
                }
            }
        }
    }


    /**
     * 获得子对象的信息
     */
    public String getSubObjInfo(Object obj) {
        try {
            String domainName = obj.getClass().getSimpleName();
            String serviceName = domainName.substring(0,1).toLowerCase() + domainName.substring(1);

            Proxy proxy = (Proxy) SpringTool.getBean(serviceName + "ServiceImpl");
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);

            // 获得findByGuid方法
            Method findByGuidMethod;
            try {
                findByGuidMethod = Class.forName("com.boyoi.work.service." + domainName + "Service").getMethod("findByGuid", String.class);
            }catch (ClassNotFoundException c){
                findByGuidMethod = Class.forName("com.boyoi.base.service." + domainName + "Service").getMethod("findByGuid", String.class);
            }
            // 调用findByGuid
            Object result = invocationHandler.invoke(proxy, findByGuidMethod, new Object[]{((BaseDomain) obj).getGuid()});

            if (null != result){
                // 获得声明的字段
                Field[] declaredFields = result.getClass().getDeclaredFields();
                // 反回声明了的 DisplayForOptLog
                for (Field field : declaredFields){
                    LogOptDisplayForSubObject annotation = field.getAnnotation(LogOptDisplayForSubObject.class);
                    if (null != annotation){
                        field.setAccessible(true);  // 暴力可视
                        return field.get(result).toString();
                    }
                }
                // 运行到这里。说明没有匹配的
            }
        }catch (Exception e){
            // ignore
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return "[未获得数据]";
    }
}