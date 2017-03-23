package com.boyoi.base.service.impl;

import com.boyoi.base.dao.UserDao;
import com.boyoi.base.domain.Department;
import com.boyoi.base.domain.Menu;
import com.boyoi.base.domain.Role;
import com.boyoi.base.domain.User;
import com.boyoi.base.security.CustomInvocationSecurityMetadataSource;
import com.boyoi.base.service.DepartmentService;
import com.boyoi.base.service.UserService;
import com.boyoi.core.domain.BaseDomain;
import com.boyoi.core.service.impl.BaseRedisServiceImpl;
import com.boyoi.core.utils.EncryptUtil;
import com.boyoi.mybatis.pagehelper.domain.EasyGridRequest;
import com.boyoi.mybatis.pagehelper.domain.EasyUiPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 用户管理 service层实现类
 *
 * @author Chenj
 */
@SuppressWarnings("unused")
@Service
public class UserServiceImpl extends BaseRedisServiceImpl<UserDao> implements UserService {

    @Autowired
    private CustomInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public User findUserByUsernamePwd(User user) {
        //sha1 加密两次
        user.setPassword(EncryptUtil.current(user.getPassword()));
        return dao.findUserByUsernamePwd(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return dao.findUserByUsername(username);
    }

    @Override
    public int changePassword(User user) {
        return dao.changePassword(user);
    }

    @Override
    public List<Role> findNoGranRoleByUserId(String userId) {
        return dao.findNoGranRoleByUserId(userId);
    }

    @Override
    public List<Role> findGranRoleByUserId(String userId) {
        return dao.findGranRoleByUserId(userId);
    }

    @Override
    public List<Menu> findMenuByUserId(String userId) {
        return dao.findMenuByUserId(userId);
    }

    @Override
    public Integer addRole(String userId, String[] roleIds) {
        //迭代，一条一条的加
        int success = 0;

        if (roleIds.length > 0){
            for (String moduleId : roleIds)
                if (1 == dao.addRole(UUID.randomUUID().toString(), userId, moduleId))
                    success ++ ;
        }
        securityMetadataSource.loadResourceDefine();
        return success;
    }

    @Override
    public Integer delRole(String userId, String[] roleIds) {
        //迭代，一条一条的删
        int success = 0;

        if (roleIds.length > 0)
            for (String moduleId : roleIds)
                if (1 == dao.delRole(userId, moduleId))
                    success ++ ;

        securityMetadataSource.loadResourceDefine();
        return success;
    }

    @Override
    public boolean setAuthPassword(String authPassword, String userId) {
        return dao.setAuthPassword(authPassword, userId)==1;
    }

    @Override
    public boolean findAuthPassword(String authPassword, String userId) {
        return dao.findAuthPassword(authPassword, userId)==1;
    }

    @Override
    public List<User> findUserByRole() {
        return dao.findUserByRole();
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected <T extends BaseDomain> void findByEasyGridRequestAfter(EasyGridRequest easyGridRequest, List<T> resultList) {
        List<Department> all = departmentService.findAll();
        for (User result : (List<User>)((EasyUiPage)resultList).getRows()){
            if (null != result.getDepartment()){
                String deptString = departmentService.getDeptString(result.getDepartment(), all);
                result.getDepartment().setDeptString(deptString);
            }
        }
        super.findByEasyGridRequestAfter(easyGridRequest, resultList);
    }

    @Override
    protected <T extends BaseDomain> void findByGuidAfter(T t) {
        List<Department> all = departmentService.findAll();
        if (null != t){
            String deptString = departmentService.getDeptString(((User)t).getDepartment(), all);
            ((User)t).getDepartment().setDeptString(deptString);
        }
        super.findByGuidAfter(t);
    }

}