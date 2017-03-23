package com.boyoi.base.security;

import com.boyoi.base.web.controller.OauthController;
import com.boyoi.core.utils.RedisUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * spring security
 * 访问决策器
 * 判断认证后的用户权限是否与访问该资源所需的权限相同
 *
 * @author Chenj
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * 做决策
     * @param authentication 认证信息
     * @param object url
     * @param configAttributes CustomInvocationSecurityMetadataSource带来的
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
                                                    throws AccessDeniedException, InsufficientAuthenticationException {

        return;
//        //为空,不需要权限,pass
//        if(configAttributes == null){
//            return ;
//        }
//        //迭代资源需要的权限,并与认证后的用户权限比较
//        for (ConfigAttribute ca : configAttributes){
//            //取得访问该资源,需要的角色
//            String needRole = ca.getAttribute();
//            //迭代用户认证信息里的权限,如用户认证信息里的权限等于访问该资源需要的权限,pass
//            for(GrantedAuthority ga : authentication.getAuthorities()){
//                if(needRole.equals(ga.getAuthority())){
//                    return;
//                }
//            }
//        }
//
//        // 进行token验证
//        FilterInvocation fi = (FilterInvocation) object;
//        String token = fi.getHttpRequest().getHeader("token");
//        if (null == token){
//            token = fi.getHttpRequest().getParameter("token");
//        }
//        if (null != token && !"".equals(token)){
//            Authentication redisAuth = RedisUtil.get(token, OauthController.DB_NUM);
//            if (null == redisAuth){
//                throw new AccessDeniedException("Token不存在或已失效!");
//            }else {
//                //迭代资源需要的权限,并与认证后的用户权限比较
//                for (ConfigAttribute ca : configAttributes){
//                    //取得访问该资源,需要的角色
//                    String needRole = ca.getAttribute();
//                    //迭代用户认证信息里的权限,如用户认证信息里的权限等于访问该资源需要的权限,pass
//                    for(GrantedAuthority ga : redisAuth.getAuthorities()){
//                        if(needRole.equals(ga.getAuthority())){
//                            return;
//                        }
//                    }
//                }
//            }
//        }
//
//        //没有访问权限,抛出异常
//        throw new AccessDeniedException("没有权限!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
