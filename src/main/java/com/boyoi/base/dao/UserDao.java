package com.boyoi.base.dao;
 
import com.boyoi.base.domain.Menu;
import com.boyoi.base.domain.Role;
import com.boyoi.base.domain.User;
import com.boyoi.core.dao.BaseCrudDao;

import java.util.List;

/**
 * 用户管理 dao层
 *
 * @author Chenj
 */
public interface UserDao extends BaseCrudDao {
    /**
     * 通过帐号和密码查找用户
     * @return User
     */
    User findUserByUsernamePwd(User user);

    /**
     * 通过用户名查找用户
     *
     * @return User
     */
    User findUserByUsername(String username);

    /**
     * 更改密码
     * @param user user
     * @return 成功个数
     */
    int changePassword(User user);

    /**
     * 通过用户ID查找没有授权的角色
     * @param userId 用户ID
     * @return 没有授权的角色
     */
    List<Role> findNoGranRoleByUserId(String userId);

    /**
     * 通过用户ID查找已经授权的角色
     * @param userId 用户ID
     * @return 已经授权的角色
     */
    List<Role> findGranRoleByUserId(String userId);

    /**
     * 获得用户授权后的菜单
     * @param userId 用户ID
     * @return 菜单集合
     */
    List<Menu> findMenuByUserId(String userId);

    /**
     * 授权角色
     * @param id guid
     * @param userId 用户ID
     * @param roleId 角色
     * @return 成功个数
     */
    Integer addRole(String id, String userId, String roleId);

    /**
     * 取消授权角色
     * @param userId 用户ID
     * @param roleId 角色
     * @return 成功个数
     */
    Integer delRole(String userId, String roleId);

    /**
     * 设置认证密码
     * @param authPassword 认证密码
     * @param userId 用户id
     * @return 是否成功
     */
    int setAuthPassword(String authPassword, String userId);

    /**
     * 获得认证密码是否存在
     * @param authPassword 认证密码
     * @param userId 用户id
     * @return 是否成功
     */
    int findAuthPassword(String authPassword, String userId);

    /**
     * 查询角色为业务员的用户
     * @return 用户集合
     */
    List<User> findUserByRole();
}