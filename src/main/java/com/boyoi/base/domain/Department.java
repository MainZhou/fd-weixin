package com.boyoi.base.domain;

import com.boyoi.base.enums.DepartmentStatusEnum;
import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 部门管理 实体对象
 *
 * @author Chenj
 */
public class Department extends BaseDomain {

	private static final long serialVersionUID = 1299208219919212814L;

	/**
     * 部门名称
     */
    @NotBlank(message = "{Department.validator.deptName.required}")@Size(max=20, message = "{Department.validator.deptName.max}")
    private String deptName;
    
    /**
     * 部门编码
     */
    @Size(max=10, message = "{Department.validator.deptCode.max}")
    private String deptCode;
    
    /**
     * 部门状态
     */
    private DepartmentStatusEnum deptStatus;
    
    /**
     * 上级部门
     */
    private Department parentDept;

    /**
     * 部门上级加下级string
     */
    private String deptString;
    
    /**
     * 部门地址
     */
    @Size(max=100, message = "{Department.validator.deptAddr.max}")
    private String deptAddr;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public DepartmentStatusEnum getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(DepartmentStatusEnum deptStatus) {
        this.deptStatus = deptStatus;
    }

    public Department getParentDept() {
        return parentDept;
    }

    public void setParentDept(Department parentDept) {
        this.parentDept = parentDept;
    }

    public String getDeptAddr() {
        return deptAddr;
    }

    public void setDeptAddr(String deptAddr) {
        this.deptAddr = deptAddr;
    }

    public String getDeptString() {
        return deptString;
    }

    public void setDeptString(String deptString) {
        this.deptString = deptString;
    }
}