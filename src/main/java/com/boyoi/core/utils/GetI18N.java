package com.boyoi.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 获得国际化资源对应的国际化信息
 * @author Chenj
 */
public class GetI18N {
    public static String get(HttpServletRequest request, String key){
            ApplicationContext ac = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(request.getSession()
                            .getServletContext());
            return ac.getMessage(key, null, getSessionLocale(request));
    }

    private static Locale getSessionLocale(HttpServletRequest request) {
        return (Locale) request.getSession().getAttribute(
                SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    }
}
