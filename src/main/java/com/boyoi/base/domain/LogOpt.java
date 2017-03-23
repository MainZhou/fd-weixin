package com.boyoi.base.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.boyoi.base.enums.LogTypeEnum;
import com.boyoi.core.domain.BaseDomain;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 操作日志 实体对象
 *
 * @author Chenj
 */
public class LogOpt extends BaseDomain {
    
	private static final long serialVersionUID = 4059062446563133852L;

	/**
     * 操作对象
     */
    private String optObject;
    
    /**
     * 操作的guid
     */
    private String optGuid;
    
    /**
     * 操作描述
     */
    private String description;

    /**
     * 比较后的描述
     */
    private String compareDesc = "";

    /**
     * 比较后的大小。共多个个
     */
    private Integer compareSize = 0;
    
    /**
     * 操作结果
     */
    @Size(max=1, message = "{LogOpt.validator.optSuccess.max}")
    private String optSuccess;
    
    /**
     * 操作类型
     */
    private LogTypeEnum optType;
    
    /**
     * 操作人
     */
    private String optPerson;
    
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date optDate;

    /**
     * 缓存添加对象
     */
    private Object baseDomain;

    /**
     * 解析对象的中间结果
     */
    private Map<String, Object> middleMap = new LinkedHashMap<>();


    public String getOptObject() {
        return optObject;
    }

    public void setOptObject(String optObject) {
        this.optObject = optObject;
    }

    public String getOptGuid() {
        return optGuid;
    }

    public void setOptGuid(String optGuid) {
        this.optGuid = optGuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompareDesc() {
        return compareDesc;
    }

    public void setCompareDesc(String compareDesc) {
        this.compareDesc = compareDesc;
    }

    public Integer getCompareSize() {
        return compareSize;
    }

    public void setCompareSize(Integer compareSize) {
        this.compareSize = compareSize;
    }

    public String getOptSuccess() {
        return optSuccess;
    }

    public void setOptSuccess(String optSuccess) {
        this.optSuccess = optSuccess;
    }

    public LogTypeEnum getOptType() {
        return optType;
    }

    public void setOptType(LogTypeEnum optType) {
        this.optType = optType;
    }

    public String getOptPerson() {
        return optPerson;
    }

    public void setOptPerson(String optPerson) {
        this.optPerson = optPerson;
    }

    public Date getOptDate() {
        return optDate;
    }

    public void setOptDate(Date optDate) {
        this.optDate = optDate;
    }

    public Object getBaseDomain() {
        return baseDomain;
    }

    public void setBaseDomain(Object baseDomain) {
        this.baseDomain = baseDomain;
    }

    public Map<String, Object> getMiddleMap() {
        return middleMap;
    }
}