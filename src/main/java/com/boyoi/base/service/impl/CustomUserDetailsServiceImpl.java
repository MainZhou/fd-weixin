package com.boyoi.base.service.impl;

import com.boyoi.base.domain.Role;
import com.boyoi.base.domain.User;
import com.boyoi.base.service.CustomUserDetailsService;
import com.boyoi.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义的CustomUserDetailsService实现类
 * 将spring security与本系统进行桥接
 * @author Chenj
 */
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDb = userService.findUserByUsername(username);
        UserDetails userDetails;
        if (null != userInDb){
            userDetails = new org.springframework.security.core.userdetails.User(userInDb.getLoginName(),
                    userInDb.getPassword(),
                    userInDb.getUserStatus().getItemValue().equals("A"), // 帐号被禁用
                    true,true,true,
                    getAuthorities(userInDb.getRoles()));
        }else
            throw new UsernameNotFoundException(username + "is not found!");
        return userDetails;
    }

    /**
     * 获得访问角色权限列表
     *
     * @param roles 角色集合
     * @return 权限列表
     */
    public Collection<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

        for (Role role : roles)
            authList.add(new SimpleGrantedAuthority(role.getRoleName()));

        return authList;
    }
}
