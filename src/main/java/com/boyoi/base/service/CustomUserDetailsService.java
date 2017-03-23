package com.boyoi.base.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * spring security 自定义类
 * 数据库里面取得的用户权限和Spring Security的配置进行桥接
 * 实现UserDetailsService接口
 * @author Chenj
 */
public interface CustomUserDetailsService extends UserDetailsService {
}
