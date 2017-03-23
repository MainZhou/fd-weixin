package com.boyoi.base.domain;

import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色管理 实体对象
 *
 * @author Chenj
 */
public class Role extends BaseDomain {
    
	private static final long serialVersionUID = 4468258274090344501L;

	/**
     * 角色名称
     */
    @NotBlank(message = "{Role.validator.role_name.required}")@Size(max=20, message = "{Role.validator.role_name.max}")
    private String roleName;

    /**
     * 角色对应的模块
     */
    private List<Module> modules;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}