package com.boyoi.base.security.filter;

import com.boyoi.base.enums.LoginFlag;
import com.boyoi.base.security.LoginFailHelper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * spring security 登录验证码过滤器
 * @author Chenj
 */
public class ValidateCodeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //是否允许校验验证码
    private boolean allowValidate = false;
    //session中保存的验证码,可通过spring注入,修改此属性
    private String validateCode_Session = DEFAULT_VALIDATE_CODE_SESSION;
    //post提交的name名称,可通过spring注入,修改此属性
    private String validateCode_Post = DEFAULT_VALIDATE_CODE_POST;
    //保存在session中的最后一次提交的用户名,可通过spring注入,修改此属性
    private String lastUsername = LAST_USERNAME;
    // session中保存的最后一次登录的用户名标签
    private static final String LAST_USERNAME = "lastUsername";
    // session中保存的验证码
    private static final String DEFAULT_VALIDATE_CODE_SESSION = LoginFlag.VALIDATE_CODE.name();
    // 输入的验证码
    private static final String DEFAULT_VALIDATE_CODE_POST = "code";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String login_name = obtainUsername(request);
        String password = obtainPassword(request);

        if (login_name == null)
            login_name = "";

        if (password == null)
            password = "";

        login_name = login_name.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                login_name, password);

        // Place the last username attempted into HttpSession for views
        HttpSession session = request.getSession(false);

        //保存最后一次用户输入的用户名, 并校验验证码
        if (session != null || getAllowSessionCreation()) {
            session =  request.getSession();
            session.setAttribute(
                    lastUsername,
                    TextEscapeUtils.escapeEntities(login_name));

            // 校验验证码
            Integer login_error_time = LoginFailHelper.getTimes(request.getRemoteAddr());
            //判断是否允许校验验证码,并且登录失败次数超过3次,才校验验证码
            if (isAllowValidate() && login_error_time > 3)
                checkValidateCode(request);
        }

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);


        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * <li>比较session中的验证码和用户输入的验证码是否相等</li>
     */
    protected void checkValidateCode(HttpServletRequest request) {
        String sessionValidateCode = obtainSessionValidateCode(request);
        String validateCodeParameter = obtainValidateCodeParameter(request);
        if (StringUtils.isEmpty(validateCodeParameter)
                || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
            throw new AuthenticationServiceException(messages.getMessage("LoginAuthentication.captchaNotEquals", request.getLocale()));
        }
    }

    /**
     * 从request请求中获得用户填写的验证码
     * @param request request请求
     * @return String
     */
    private String obtainValidateCodeParameter(HttpServletRequest request) {
        return request.getParameter(validateCode_Post);
    }

    /**
     * 从session中获得保存在session中的验证码
     * @param request request请求
     * @return String
     */
    protected String obtainSessionValidateCode(HttpServletRequest request) {
        Object obj = request.getSession()
                .getAttribute(validateCode_Session);
        return null == obj ? "" : obj.toString();
    }

    public String getLastUsername() {
        return lastUsername;
    }

    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

    public String getValidateCode_Session() {
        return validateCode_Session;
    }

    public void setValidateCode_Session(String validateCode_Session) {
        this.validateCode_Session = validateCode_Session;
    }

    public String getValidateCode_Post() {
        return validateCode_Post;
    }

    public void setValidateCode_Post(String validateCode_Post) {
        this.validateCode_Post = validateCode_Post;
    }

    public boolean isAllowValidate() {
        return allowValidate;
    }

    public void setAllowValidate(boolean allowValidate) {
        this.allowValidate = allowValidate;
    }
}
