package com.boyoi.core.common;

import org.springframework.validation.ObjectError;

import java.util.Collection;

/**
 * 提示信息，JSON对象
 *
 */
public class MessageJson {

    /**
     * 信息代码
     */
    private Integer id;

    /**
     * 提示信息
     */
    private String message;

    /**
     * spring mvc返回的错误对象
     */
    private Collection<ObjectError> objectErrors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public Collection<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public void setObjectErrors(Collection<ObjectError> objectErrors) {
        this.objectErrors = objectErrors;
    }
}
