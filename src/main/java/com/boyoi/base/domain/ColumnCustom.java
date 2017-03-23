package com.boyoi.base.domain;

import com.boyoi.core.domain.BaseDomain;

import javax.validation.constraints.Size;

/**
 * 列配置 实体对象
 *
 * @author Chenj
 */
public class ColumnCustom extends BaseDomain {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5719521431791634096L;

	/**
     * 关联用户
     */
    @Size(max=36, message = "{ColumnCustom.validator.userId.max}")
    private String userId;
    
    /**
     * 关连实体的DomainUrl
     */
    @Size(max=50, message = "{ColumnCustom.validator.domainUrl.max}")
    private String domainUrl;
    
    /**
     * 列配置
     */
    @Size(max=2000, message = "列配置最大长度不能超过2000")
    private String columnConf;

    /**
     * 列顺序
     */
    @Size(max=2000, message = "列顺序最大长度不能超过2000")
    private String columnOrder;
    


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public String getColumnConf() {
        return columnConf;
    }

    public void setColumnConf(String columnConf) {
        this.columnConf = columnConf;
    }

    public String getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(String columnOrder) {
        this.columnOrder = columnOrder;
    }
}