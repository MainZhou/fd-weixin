package com.boyoi.base.web.controller;

import com.boyoi.base.domain.Role;
import com.boyoi.base.domain.Token;
import com.boyoi.base.domain.User;
import com.boyoi.base.enums.TokenClientId;
import com.boyoi.base.service.UserService;
import com.boyoi.core.common.ResponseResult;
import com.boyoi.core.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 自定义的oauth2 控制层
 *
 * @author Chenj
 */
@Controller
@RequestMapping("oauth")
public class OauthController {

	private static final Logger LOG = LoggerFactory.getLogger(OauthController.class);

    // 默认token过期时间（120秒）
    private static final Long TOKEN_EXPIRES_IN = 120l;
    // 更新令牌的过期时间2小时
    private static final Long REFRESH_TOKEN_EXPIRES_IN = 7200l;
    //Redis db 编号
    public static final int DB_NUM = 10;

    @Autowired
    private UserService userService;

    @RequestMapping("token")
    @ResponseBody
    public Object token(String client_id, String grant_type, String refresh_token, User user){
        ResponseResult responseResult = new ResponseResult();
        // 处理类型为密码
        if (null != grant_type){
            switch (grant_type){
                // 获得token
                case "password":
                    try {
                        TokenClientId.valueOf(client_id);
                        User userInDb = userService.findUserByUsernamePwd(user);
                        // 用户认证成功, 返回token对象,并保存redis中
                        if (null != userInDb)
                            return makeToken(userInDb);
                        else
                            responseResult.setMsg("帐号或密码出错!");
                    }catch (IllegalArgumentException | NullPointerException e){
                        responseResult.setMsg("客户端ID出错!");
                    }
                    break;

                // 更新令牌
                case "refresh_token":
                    if (null != refresh_token && !"".equals(refresh_token)){
                        Authentication authentication = RedisUtil.get(refresh_token, DB_NUM);
                        if (null != authentication)
                            return refreshToken((String) authentication.getPrincipal());
                        else
                            responseResult.setMsg("refresh_token不正确!");
                    }
                    else
                        responseResult.setMsg("refresh_token不为空!");
                    break;

                //默认
                default:
                    responseResult.setMsg("授权类型不正确!");
                    break;

            }
        }
        else{
            responseResult.setMsg("授权类型不能为空!");
        }

        return responseResult;
    }

    /**
     * 生成一个token
     * @param user 用户
     * @return Token
     */
    private Token makeToken(User user) {
        Token token = new Token();
        // token
        token.setAccess_token(UUID.randomUUID().toString());
        // refresh token
        token.setRefresh_token(UUID.randomUUID().toString());
        // 过期时间
        token.setExpires_in(TOKEN_EXPIRES_IN);

        // 获得用户的权限信息,并保存到redis中
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Role> granRoleByUserId = userService.findGranRoleByUserId(user.getGuid());
        for (Role role : granRoleByUserId)
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getGuid(), user.getRealName(), authorities);

        // 添加到redis
        RedisUtil.add(token.getAccess_token(), authentication, TOKEN_EXPIRES_IN, false, DB_NUM);
        RedisUtil.add(token.getRefresh_token(), authentication, REFRESH_TOKEN_EXPIRES_IN, false, DB_NUM);
        LOG.info("生成Token成功! 用户真实名称: {}", user.getRealName());
        return token;
    }

    /**
     * 刷新一个token
     * @return Token
     */
    private Token refreshToken(String userId) {
        Token token = new Token();
        // token
        token.setAccess_token(UUID.randomUUID().toString());
        // 重新生成refresh token ，并保存
        token.setRefresh_token(UUID.randomUUID().toString());
        // 过期时间
        token.setExpires_in(TOKEN_EXPIRES_IN);

        // 获得用户的权限信息,并保存到redis中
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Role> granRoleByUserId = userService.findGranRoleByUserId(userId);
        for (Role role : granRoleByUserId)
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);

        // 添加到redis
        RedisUtil.add(token.getAccess_token(), authentication, TOKEN_EXPIRES_IN, false, DB_NUM);
        RedisUtil.add(token.getRefresh_token(), authentication, REFRESH_TOKEN_EXPIRES_IN, false, DB_NUM);
        LOG.debug("刷新Token成功！UserId:{}", userId);
        return token;
    }


}
