package com.boyoi.base.dao;
 
import com.boyoi.base.domain.Module;
import com.boyoi.base.domain.Role;
import com.boyoi.core.dao.BaseCrudDao;

import java.util.List;

/**
 * 角色 dao层
 *
 * @author Chenj
 */
public interface RoleDao extends BaseCrudDao {

    /**
     * 查找所有的角色，并带有模块对象
     * @return 角色集合
     */
    List<Role> findAllRoleWithModule();

    /**
     * 通过角色ID查找角色对应的模块
     * @param role_id 角色ID
     * @return 角色对应的模块集合
     */
    List<Module> findModuleByRoleId(String role_id);

    /**
     * 通过角色ID查找角色没有授权的模块
     *
     * @param role_id 角色ID
     * @return 角色对应的模块集合
     */
    List<Module> findNoGranModuleByRoleId(String role_id);

    /**
     * 授权，添加模块到中间表
     * @param id 中间表id
     * @param roleId 角色ID
     * @param moduleId 模块ID
     * @return 影响的条数
     */
    Integer addModule(String id, String roleId, String moduleId);

    /**
     * 删除角色对应的模块
     * @param roleId 角色ID
     * @param moduleId 模块ID
     * @return 成功个数
     */
    Integer delModuleByRoleId(String roleId, String moduleId);

}