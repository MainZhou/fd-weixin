package com.boyoi.work.domain;

import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 区域 实体对象
 *
 * @author zhoujl
 */
public class Area extends BaseDomain {

    /**
     * @Function:
     * @Time:2016年4月8日下午4:00:36
     */
    private static final long serialVersionUID = -1640459646348338037L;

    /**
     * 区域编码
     */
    @Size(max = 6, message = "{Area.validator.areaid.max}")
    private String areaid;

    /**
     * 区域名称
     */
    @NotBlank(message = "{Area.validator.areaname.required}")
    @Size(max = 32, message = "{Area.validator.areaname.max}")
    private String areaname;

    /**
     * 上级区域
     */
    @NotBlank(message = "{Area.validator.parentid.required}")
    @Size(max = 6, message = "{Area.validator.parentid.max}")
    private String parentid;

    /**
     * 区域等级
     */
    @NotBlank(message = "{Area.validator.levels.required}")
    @Size(max = 1, message = "{Area.validator.levels.max}")
    private String levels;


    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }


}