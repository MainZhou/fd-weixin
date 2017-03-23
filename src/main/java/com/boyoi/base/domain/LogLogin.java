package com.boyoi.base.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.boyoi.core.domain.BaseDomain;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;

/**
 * 登录日志 实体对象
 *
 * @author Chenj
 */
public class LogLogin extends BaseDomain {
    
	private static final long serialVersionUID = 1656895196094361494L;

	/**
     * 用户
     */
    private User user;
    
    /**
     * 登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date loginDate;
    
    /**
     * 浏览器类型
     */
    @Size(max=100, message = "{LogLogin.validator.browser.max}")
    private String browser;
    
    /**
     * IP地址
     */
    @Size(max=15, message = "{LogLogin.validator.ip.max}")
    private String ip;
    
    /**
     * 退出时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date logoutDate;
    
    /**
     * 停留时间(秒)
     */
    
    private Long leaveTime;
    

    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public java.util.Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(java.util.Date loginDate) {
        this.loginDate = loginDate;
    }
    
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public java.util.Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(java.util.Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public Long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Long leaveTime) {
        this.leaveTime = leaveTime;
    }
}