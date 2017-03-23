package com.boyoi.base.security;

import com.boyoi.core.utils.EncryptUtil;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

/**
 * spring security
 * 自定义加密,sha1加密两次
 *
 * @author Chenj
 */
public class CustomPasswordEncoder extends MessageDigestPasswordEncoder {


    public CustomPasswordEncoder() {
        super("SHA1");//不起作用
    }

    /**
     * 覆盖加密方法,与本系统一致sha1加密两次
     * @param rawPass 密码原始字符
     * @param salt (盐巴),让密码更复杂
     * @return 加密后的字符
     */
    @Override
    public String encodePassword(String rawPass, Object salt) {
       return EncryptUtil.current(rawPass);
    }
}
