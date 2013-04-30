package com.xiva.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity  
@Table(name = "userlog")
public class UserLog extends BaseEntity{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3226384199019222807L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer logId;
    
    private Integer userId;
    
    @Column(name = "login_time")
    private String loginTime;
    
    @Column(name = "logout_time")
    private String logoutTime;
    
    @Column(name = "login_long")
    private String loginLong;
    
    @Column(name = "login_ip")
    private String loginIp;
    
    @Column(name = "logout_way")
    private Integer logoutWay;

    private String reason;
    
    public Integer getLogId() {
        return logId;
    }
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }
    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLoginLong() {
        return loginLong;
    }
    public void setLoginLong(String loginLong) {
        this.loginLong = loginLong;
    }

    public String getLoginIp() {
        return loginIp;
    }
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getLogoutWay() {
        return logoutWay;
    }
    public void setLogoutWay(Integer logoutWay) {
        this.logoutWay = logoutWay;
    }
    
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
