package com.xiva.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity
@Table(name = "userbasic")
@NamedQueries(value = { @NamedQuery(name = "UserBasic.queryAll", query = "from UserBasic") })
public class UserBasic extends BaseEntity
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6674613321262497781L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String userName;

    private String password;

    private String email;

    private String nickName;

    private Integer orgId;

    private String userCode;

    @Column(name = "phone_num")
    private String phoneNum;
    
    @Column(name = "active_code")
    private String activeCode;
    
    private String status;

    @Column(name = "validate_time")
    private String validateTime;

    @Column(name = "register_time")
    private String registerTime;

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getPhoneNum()
    {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }

    public String getValidateTime()
    {
        return validateTime;
    }

    public void setValidateTime(String validateTime)
    {
        this.validateTime = validateTime;
    }

    public Integer getOrgId()
    {
        return orgId;
    }

    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getActiveCode()
    {
        return activeCode;
    }

    public void setActiveCode(String activeCode)
    {
        this.activeCode = activeCode;
    }

    public String toString()
    {
        return userName + email;
    }
}
