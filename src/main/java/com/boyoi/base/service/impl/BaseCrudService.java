package com.boyoi.base.service.impl;

import com.boyoi.base.dao.ModuleDao;
import com.boyoi.base.domain.Module;
import com.boyoi.base.domain.ModuleUrl;
import com.boyoi.base.security.CustomInvocationSecurityMetadataSource;
import com.boyoi.base.service.ModuleService;
import com.boyoi.base.service.UserService;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块 service层实现类
 *
 * @author Chenj
 */
@SuppressWarnings("unused")
@Service
public class BaseCrudService extends BaseRedisServiceImpl<ModuleDao> implements ModuleService {

    @Autowired
    private CustomInvocationSecurityMetadataSource securityMetadataSource;

    /**
     * 添加后
     * @param t 实体
     */
    @Override
    protected <T extends BaseDomain> void addAfter(T t, int result) {
        Module module = (Module)t;
        //删除
        dao.delModuleUrls(module);

        //添加
        String userPostUrl = module.getUserPostUrl();
        if(null != userPostUrl){
            String[] userPostUrls = module.getUserPostUrl().split("\\n");
            List<ModuleUrl> moduleUrlList = new ArrayList<>();
            //放入实体中
            for (String url : userPostUrls){
                if (!url.trim().equals("")){
                    ModuleUrl moduleUrl = new ModuleUrl();
                    moduleUrl.setUrl(url);
                    moduleUrlList.add(moduleUrl);
                }
            }
            if (moduleUrlList.size() > 0){
                module.setUrls(moduleUrlList);
                dao.addModuleUrls(module);
            }
        }
        // 重新加载spring security 资源源数据定义
        securityMetadataSource.loadResourceDefine();
        // 删除缓存
        delRedisByKeys(t.getGuid());
        super.delRedisByEasyGrid();
    }

    /**
     * 修复后要执行的
     * @param t 实体
     */
    @Override
    protected <T extends BaseDomain> void updateAfter(T t, int result) {
        addAfter(t,result);
    }

}