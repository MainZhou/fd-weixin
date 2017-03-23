package com.boyoi.core.service.impl;

import com.boyoi.base.domain.Menu;
import com.boyoi.base.domain.Module;
import com.boyoi.base.service.MenuService;
import com.boyoi.base.service.ModuleService;
import com.boyoi.core.creator.Creator;
import com.boyoi.core.creator.impl.*;
import com.boyoi.core.dao.CreatorDao;
import com.boyoi.core.domain.ColumnDb;
import com.boyoi.core.domain.ColumnRequest;
import com.boyoi.core.service.CreatorService;
import com.boyoi.core.utils.PdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 代码自动生成工具 service 层实现类
 *
 * @author Chenj
 */
@Service
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    private CreatorDao creatorDao;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private MenuService menuService;

    // 生成的模块统一后缀名
    private static final String moduleNameSuffix = "管理-（所有模块）";
    // 生成的菜单统一后缀名
    private static final String menuNameSuffix = "管理";


    @Override
    public Collection<String> getTables() {
        return creatorDao.getTables();
    }

    @Override
    public Collection<ColumnDb> getColumnByTable(String tableName) {
        //从数据库中取得数据
        Collection<ColumnDb> dbColumns = creatorDao.getColumnByTable(tableName);
        //获得classpath下的pd文件
        File pdFile = PdUtil.pdFileInCreatorProperties;
        if( null != pdFile){
            //迭代pdFile.并查找table下的column
            Map<String, String> pdColumns = PdUtil.readColumnsByTableName(pdFile, tableName);
            //如查找的数据不为空.迭代.并设置到columns中
            Set<String> pdKeys = pdColumns.keySet();
            for (ColumnDb columnDb :dbColumns){
                for (String pdKey : pdKeys){
                    // 先去除pd里的_, 在判断pd 与数据库有对应的字段 不区分大小写
                    if (pdKey.replaceAll("_", "").equalsIgnoreCase(columnDb.getJavaName())){
//                    if (pdKey.equalsIgnoreCase(columnDb.getJavaName())){
                        columnDb.setCnName(pdColumns.get(pdKey));
                        break;
                    } else {
                         //没有对应的设为列名
                        columnDb.setCnName(columnDb.getJavaName());
                    }
                }
            }
        }
        return dbColumns;
    }

    @Override
    public Map<Boolean,Collection<String>> execColumnRequest(ColumnRequest columnRequest, HttpServletRequest servletRequest) {

        //准备返回的信息
        Map<Boolean,Collection<String>> msg = new LinkedHashMap<>();
        Collection<String> success = new ArrayList<>();
        Collection<String> failed = new ArrayList<>();
        msg.put(true,success);
        msg.put(false,failed);

        //判断是否生成javaBean controller层 service层 dao层 jsp
        Map<String, Boolean> genType = columnRequest.getGenType();
        Set<Map.Entry<String, Boolean>> entries = genType.entrySet();

        for (Map.Entry<String, Boolean> entry : entries){
            Creator creator = null;
            Creator creator2 = null;
            Creator creator3 = null;
            Creator creator4 = null;
            Creator creator5 = null;

            //用户提交了要生成此类型
            if (entry.getValue()){
                /**
                 * 迭代,根据不同类型,创建不同的实现类
                 */
                switch (entry.getKey()){

                    /**
                     * 生成javaBean
                     */
                    case "javaBean":{
                        creator  = new JavaBeanCreator(columnRequest, servletRequest);       // javaBean
                        creator2 = new I18N_zh_cn_Creator(columnRequest,servletRequest);     // 中文国际化
                        creator3 = new I18N_en_Creator(columnRequest,servletRequest);        // 英文国际化
                        break;
                    }

                    /**
                     * 生成controller
                     */
                    case "controller":{
                        creator  = new ControllerCreator(columnRequest, servletRequest);          // controller层
                        break;
                    }

                    /**
                     * 生成service接口及实现类
                     */
                    case "service":{
                        creator  = new ServiceCreator(columnRequest, servletRequest);           // service接口
                        creator2 = new ServiceImplCreator(columnRequest,servletRequest);        // service实现类
                        break;
                    }

                    case "dao":{
                        creator  = new DaoCreator(columnRequest, servletRequest);           // dao接口
                        creator2 = new DaoImplCreator(columnRequest,servletRequest);        // dao实现类
                        break;
                    }

                    case "jsp":{
                        creator  = new JspAdd(columnRequest, servletRequest);           // jsp页面 添加
                        creator2 = new JspUpdate(columnRequest,servletRequest);         // jsp页面 编辑
                        creator3 = new JspList(columnRequest,servletRequest);           // jsp页面 列表
                        creator4 = new JspListJs(columnRequest,servletRequest);           // jsp页面 列表JS
                        creator5 = new JspDetail(columnRequest,servletRequest);           // jsp页面 详情JS
                        break;
                    }

                    default:{
                        creator = null;
                        creator2 = null;
                    }

                }
            }

            if(null != creator)
                genFile(success, failed, entry, creator);
            if(null != creator2)
                genFile(success, failed, entry, creator2);
            if(null != creator3)
                genFile(success, failed, entry, creator3);
            if(null != creator4)
                genFile(success, failed, entry, creator4);
            if(null != creator5)
                genFile(success, failed, entry, creator5);

        }

        // 如用户提交了生成模块和菜单
        if (null != columnRequest.getGen_module_menu() && columnRequest.getGen_module_menu())
            genModuleMenu(columnRequest, success, failed);

        return msg;
    }


    /**
     * 处理生成模块和菜单
     * @param columnRequest 请求对象
     * @param success 生成成功的集合
     * @param failed 生成失败的集合
     */
    private void genModuleMenu(ColumnRequest columnRequest, Collection<String> success, Collection<String> failed) {

        // 通过"功能名称"+管理-（所有模块） 查找是否有已经存在的
        Module module = new Module();
        module.setModuleName(columnRequest.getFuncName() + moduleNameSuffix);
        List<Module> existModules = moduleService.findByDomain(module);

        // 获得或生成菜单
        Menu menu = genMenu(columnRequest, success,failed);
        module.setMenu(menu);

        // 设置 url
        String urlPrefix = columnRequest.getPrefixFirstCharLow();
        module.setUserPostUrl("/" + urlPrefix + "/add\n" +
                            "/" + urlPrefix + "/update\n" +
                            "/" + urlPrefix + "/list\n" +
                            "/" + urlPrefix + "/detail\n" +
                            "/" + urlPrefix + "/check\n" +
                            "/" + urlPrefix + "/del\n" );

        int result;

        // 大于0个说明有相同的，进行覆盖
        if (existModules.size() > 0){
            module.setGuid(existModules.get(0).getGuid());
            // 保存在数据库中
            result = moduleService.update(module);
            if (result == 1)
                success.add("成功覆盖模块: " + columnRequest.getFuncName() + moduleNameSuffix );
            else
                failed.add("覆盖模块失败: " + columnRequest.getFuncName() + moduleNameSuffix );
        }else {
            // 不存在, 先新增
            module.setGuid(UUID.randomUUID().toString());
            result = moduleService.add(module);
            if (result == 1)
                success.add("成功新增模块: " + columnRequest.getFuncName() + moduleNameSuffix );
            else
                failed.add("新增模块失败: " + columnRequest.getFuncName() + moduleNameSuffix );
        }

    }

    /**
     * 生成菜单
     * @return 返回菜单ID
     */
    private Menu genMenu(ColumnRequest columnRequest, Collection<String> success, Collection<String> failed) {
        Menu menu = new Menu();
        // “功能名称” + 管理
        menu.setMenuName(columnRequest.getFuncName() + menuNameSuffix);
        // 是否有父菜单
        Menu parentMenu = new Menu();
        parentMenu.setGuid("".equals(columnRequest.getParentMenuId()) ? null : columnRequest.getParentMenuId());
        menu.setParentMenu(parentMenu);

        // 查找是否存在
        List<Menu> exitsMenus = menuService.findByDomain(menu);
        int result;

        // 已经存在
        if (exitsMenus.size() > 0){
            // 设置Guid
            menu.setGuid(exitsMenus.get(0).getGuid());
            // 设置入口
            menu.setEntryUrl("/" + columnRequest.getPrefixFirstCharLow() + "/list");
            // 覆盖性的更新
            result = menuService.update(menu);
            if (result == 1)
                success.add("成功更新菜单: " + columnRequest.getFuncName() + menuNameSuffix );
            else
                failed.add("更新菜单失败: " + columnRequest.getFuncName() + menuNameSuffix );
        }else {
            // 不存在, 生成一个
            menu.setGuid(UUID.randomUUID().toString());
            menu.setEntryUrl("/" + columnRequest.getPrefixFirstCharLow() + "/list");
            // 添加到数据库中
            result = menuService.add(menu);
            if (result == 1)
                success.add("成功新增菜单: " + columnRequest.getFuncName() + menuNameSuffix );
            else
                failed.add("新增菜单失败: " + columnRequest.getFuncName() + menuNameSuffix );
        }

        return menu;
    }

    /**
     * 根据不同的creator生成文件,并把生成后的消息加入到集合中
     * @param success 生成成功的集合
     * @param failed 生成失败的集合
     * @param entry 用户提交的要生成类型的entry
     * @param creator 生成文件的实现方法
     */
    private void genFile(Collection<String> success, Collection<String> failed, Map.Entry<String, Boolean> entry, Creator creator) {
        String genFileName = creator.creatorFile();
        if(null != genFileName)
            success.add("生成" + entry.getKey() + "成功！ 文件地址：" + genFileName);
        else
            failed.add("生成"+entry.getKey()+"失败！");
    }

    @Override
    public String getDatabaseName() {
        return creatorDao.getDatabaseName().toLowerCase();
    }

}
