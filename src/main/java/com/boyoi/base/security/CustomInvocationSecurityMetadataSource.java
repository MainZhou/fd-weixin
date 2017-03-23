package com.boyoi.base.security;

import com.boyoi.base.domain.Module;
import com.boyoi.base.domain.ModuleUrl;
import com.boyoi.base.domain.Role;
import com.boyoi.base.service.RoleService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

/**
 * spring security
 * 资源源数据定义，即定义某一资源可以被哪些角色访问
 * 处理资源与权限对应关系，返回该资源需要的访问权限
 *
 * @author Chenj
 */
public class CustomInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();

    private static final String NO_AUTH_PARAM ="no_auth_param";

    /** 角色service **/
    private RoleService roleService;

    /**
     * 利用构造函数初始化 RoleService
     *                    取到所有资源及其对应角色的集合
     * @param roleService RoleService
     */
    public CustomInvocationSecurityMetadataSource(RoleService roleService) {
        this.roleService = roleService;
        //加载资源
        loadResourceDefine();
    }

    public void loadResourceDefine() {
        // 先清空
        resourceMap.clear();

        // 添加未授权的角色
        ArrayList<ConfigAttribute> noAuth = new ArrayList<>();
        noAuth.add(new SecurityConfig("此URL未授权!"));
        resourceMap.put(NO_AUTH_PARAM, noAuth);

        // 查找出系统中所有的角色
        List<Role> roles = roleService.findAllRoleWithModule();

        // 迭代所有的角色, 并把所有的角色加载到集合中
        String url;
        for (Role role : roles){
            //创建角色对应的ConfigAttribute
            ConfigAttribute ca = new SecurityConfig(role.getRoleName());
            List<Module> modules = role.getModules();
            for (Module module : modules){
                for (ModuleUrl moduleUrl : module.getUrls()){
                    url = moduleUrl.getUrl();
                    if (null != url && !"".equals(url)){

                        //先获得是否有配置，有就添加一个新的角色，没有就创建
                        Collection<ConfigAttribute> configAttributes = resourceMap.get(url);
                        //没有，创建集合,并添加到resourceMap
                        if (null == configAttributes){
                            configAttributes = new ArrayList<>();
                            resourceMap.put(url, configAttributes);
                        }

                        //已经有了，直接添加
                        configAttributes.add(ca);

                    }
                }
            }
        }

    }

    /**
     * 获得访问该资源需要的访问权限
     * @param object url
     * @return 返回该资源需要的访问权限
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        return null;

        FilterInvocation fi = (FilterInvocation) object;

        // 获得请求的url地址
        String requestUrl = fi.getRequestUrl();

        // 去掉?后的请求参数
        char[] requestUrlArray = requestUrl.toCharArray();
        for (int i = 0; i < requestUrlArray.length; i++){
            if (requestUrlArray[i] == '?'){
                requestUrl = requestUrl.substring(0, i);
                break;
            }
        }

        Collection<ConfigAttribute> needConfigAttr = resourceMap.get(requestUrl);
        if (requestUrl.startsWith("/creator/"))
            return null;
        else if (null == needConfigAttr) // 为空,返回未授权
            return resourceMap.get(NO_AUTH_PARAM);
        else
            return needConfigAttr;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
