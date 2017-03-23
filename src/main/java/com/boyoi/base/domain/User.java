package com.boyoi.base.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.boyoi.base.enums.UserStatusEnum;
import com.boyoi.core.domain.BaseDomain;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户 实体对象
 *
 * @author Chenj
 */
public class User extends BaseDomain {
 
	private static final long serialVersionUID = 7672628253267488720L;

	/**
     * 登录名称
     */
    @NotBlank(message = "{User.validator.loginName.required}")@Size(max=12, message = "{User.validator.loginName.max}")
    private String loginName;
    
    /**
     * 登录密码
     */
    @JSONField(serialize = false,deserialize = false)
    @Size(max=40, message = "{User.validator.password.max}")
    private String password;
    
    /**
     * 姓名
     */
    @NotBlank(message = "{User.validator.realName.required}")@Size(max=20, message = "{User.validator.realName.max}")
    private String realName;
    
    /**
     * 性别
     */
    @Size(max=1, message = "{User.validator.sex.max}")
    private String sex;

    /**
     * 用户状态
     */
    private UserStatusEnum userStatus;
    
    /**
     * 联系电话
     */
    @Size(max=13, message = "{User.validator.telephone.max}")
    private String telephone;

    /**
     * 电子邮箱
     */
    private String email;
    
    /**
     * 工号
     */
    @Size(max=10, message = "{User.validator.jobNum.max}")
    private String jobNum;
    
    /**
     * 分管区域
     */
    @Size(max=20, message = "{User.validator.manageArea.max}")
    private String manageArea;
    
    /**
     * 入职时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")@JSONField(format = "yyyy-MM-dd")
    private java.util.Date entryDate;
    
    /**
     * 职务
     */
    @Size(max=10, message = "{User.validator.post.max}")
    private String post;
    
    /**
     * 通讯地址
     */
    @Size(max=20, message = "{User.validator.addr.max}")
    private String addr;
    
    /**
     * 岗位名称
     */
    @Size(max=10, message = "{User.validator.postName.max}")
    private String postName;
    
    /**
     * 所属部门
     */
    private Department department;

    /**
     * 自定义风格
     *  1代表风格1
     *  2代表风格2
     *  ...
     */
    private Integer customStyle;

    /**
     * 对应的角色集合
     */
    private List<Role> roles;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public UserStatusEnum getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getManageArea() {
        return manageArea;
    }

    public void setManageArea(String manageArea) {
        this.manageArea = manageArea;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getCustomStyle() {
        return customStyle;
    }

    public void setCustomStyle(Integer customStyle) {
        this.customStyle = customStyle;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}