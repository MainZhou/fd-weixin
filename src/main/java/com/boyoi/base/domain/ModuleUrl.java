package com.boyoi.base.domain;

import java.io.Serializable;

/**
 * 模块对应的URL实体
 * @author Chenj
 */
public class ModuleUrl implements Serializable {

	private static final long serialVersionUID = -4186068572174360142L;
	private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}