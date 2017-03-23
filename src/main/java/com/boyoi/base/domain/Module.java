package com.boyoi.base.domain;

import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 模块 实体对象
 *
 * @author Chenj
 */
public class Module extends BaseDomain {
    
	private static final long serialVersionUID = -1913685342900919769L;

	/**
     * 模块名称
     */
    @NotBlank(message = "{Module.validator.moduleName.required}")@Size(max=20, message = "{Module.validator.moduleName.max}")
    private String moduleName;
    
    /**
     * 功能简述
     */
    @Size(max=500, message = "{Module.validator.intro.max}")
    private String intro;
    
    /**
     * 菜单ID
     */
    private Menu menu;

    /**
     * 功能地址
     */
    private List<ModuleUrl> urls;

    /**
     * spring mvc接收的url
     */
    private String userPostUrl;

    
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<ModuleUrl> getUrls() {
        return urls;
    }

    public void setUrls(List<ModuleUrl> urls) {
        this.urls = urls;
    }

    public String getUserPostUrl() {
        if (null == userPostUrl) userPostUrl = "";
        if (null != urls && urls.size() >0)
            for (ModuleUrl moduleUrl : urls)
                userPostUrl += moduleUrl.getUrl() + "\n";

        return userPostUrl;
    }

    public void setUserPostUrl(String userPostUrl) {
        this.userPostUrl = userPostUrl;
    }
}