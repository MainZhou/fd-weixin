package com.boyoi.base.service;
 
import com.boyoi.base.domain.Module;
import com.boyoi.base.domain.Role;
import com.boyoi.core.service.BaseCrudService;

import java.util.List;

/**
 * 角色 service层
 *
 * @author Chenj
 */
public interface RoleService extends BaseCrudService {

    /**
     * 查找所有的角色，并带有模块对象
     * @return 角色集合
     */
    List<Role> findAllRoleWithModule();

    /**
     * 通过角色ID查找角色对应的模块
     *
     * @param roleId 角色ID
     * @return 角色对应的模块集合
     */
    List<Module> findModuleByRoleId(String roleId);


    /**
     * 通过角色ID查找角色没有授权的模块
     *
     * @param roleId 角色ID
     * @return 角色对应的模块集合
     */
    List<Module> findNoGranModuleByRoleId(String roleId);

    /**
     * 授权，添加模块到中间表
     * @param roleId 角色ID
     * @param moduleIds 模块ID数组
     * @return 成功个数
     */
    Integer addModule(String roleId, String[] moduleIds);

    /**
     * 取消授权，添加模块到中间表
     * @param roleId 角色ID
     * @param moduleIds 模块ID数组
     * @return 成功个数
     */
    Integer delModule(String roleId, String[] moduleIds);

}