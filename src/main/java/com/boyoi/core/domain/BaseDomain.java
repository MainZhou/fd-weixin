package com.boyoi.core.domain;

import java.io.Serializable;

/**
 * 实体的基类.继承此类.子类自动获得属性id,一般为数据库中的guid.
 * @author Chenj
 */
public abstract class BaseDomain implements Serializable {

	private static final long serialVersionUID = -8520902863856007586L;
	//	private String id;
	private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

}
