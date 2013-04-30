package com.xiva.action.vo;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.util.ServerLocator;
import com.xiva.domain.Org;
import com.xiva.domain.UserBasic;
import com.xiva.service.org.OrgService;

public class IvUserBasicVo implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String orgName;

    private Integer userId;

    private String userName;

    private String password;

    private String email;

    private String nickName;

    private Integer orgId;

    private String registerTime;
    
    private String validateTime;
    
    private String phoneNum;
    
    private String userCode;
    
    private String status;
    
    private String statusCode;
    
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getRegisterTime() {
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

    public String getUserCode() {
        return userCode;
    }
    
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void convertFromBo(UserBasic basic) 
    {
        OrgService orgService = (OrgService)ServerLocator.getService("orgService");
        try 
        {
            BeanUtils.copyProperties(this, basic);
            BusinessResponse response = orgService.getOrgByOrgId(this.orgId);
            Org org = (Org)response.getAttribute(CommonConstant.RESULT);
            
            this.setOrgName(org.getOrgName());
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
