package com.boyoi.base.domain;

import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 图标 实体对象
 *
 * @author Chenj
 */
public class Icon extends BaseDomain {

	private static final long serialVersionUID = -7015191757523585628L;
	/**
     * CSS样式名称
     */
    @NotBlank(message = "{Icon.validator.css_name.required}")@Size(max=100, message = "{Icon.validator.css_name.max}")
    private String cssName;

    public String getCssName() {
        return cssName;
    }

    public void setCssName(String cssName) {
        this.cssName = cssName;
    }
}