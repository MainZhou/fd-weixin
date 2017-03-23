package com.boyoi.base.service.impl;

import com.boyoi.base.dao.RoleDao;
import com.boyoi.base.domain.Module;
import com.boyoi.base.domain.Role;
import com.boyoi.base.security.CustomInvocationSecurityMetadataSource;
import com.boyoi.base.service.RoleService;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 角色 service层实现类
 *
 * @author Chenj
 */
@Service
public class RoleServiceImpl extends BaseRedisServiceImpl<RoleDao> implements RoleService {

//    @Autowired
//    private CustomInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public List<Role> findAllRoleWithModule() {
        return dao.findAllRoleWithModule();
    }

    /**
     * 通过角色ID查找角色对应的模块
     * @param roleId 角色ID
     * @return 角色对应的模块集合
     */
    @Override
    public List<Module> findModuleByRoleId(String roleId) {
        return dao.findModuleByRoleId(roleId);
    }

    @Override
    public List<Module> findNoGranModuleByRoleId(String roleId) {
        return dao.findNoGranModuleByRoleId(roleId);
    }

    @Override
    public Integer addModule(String roleId, String[] moduleIds) {

        //迭代，一条一条的加
        int success = 0;

        if (moduleIds.length > 0)
            for (String moduleId : moduleIds)
                if (1 == dao.addModule(UUID.randomUUID().toString(), roleId, moduleId))
                    success ++ ;
        // 重新加载spring security 资源源数据定义
//        securityMetadataSource.loadResourceDefine();
        CustomInvocationSecurityMetadataSource customInvocationSecurityMetadataSource = new CustomInvocationSecurityMetadataSource(this);
        customInvocationSecurityMetadataSource.loadResourceDefine();
        // 删除缓存
        delRedisByKeys(roleId);

        return success;
    }

    @Override
    public Integer delModule(String roleId, String[] moduleIds) {
        //迭代，一条一条的删
        int success = 0;

        if (moduleIds.length > 0)
            for (String moduleId : moduleIds)
                if (1 == dao.delModuleByRoleId(roleId, moduleId))
                    success ++ ;

        // 删除缓存
        delRedisByKeys(roleId);

        // 重新加载spring security 资源源数据定义
//        securityMetadataSource.loadResourceDefine();
        CustomInvocationSecurityMetadataSource customInvocationSecurityMetadataSource = new CustomInvocationSecurityMetadataSource(this);
        customInvocationSecurityMetadataSource.loadResourceDefine();

        return success;
    }
}