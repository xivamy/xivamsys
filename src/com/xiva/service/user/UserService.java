package com.xiva.service.user;

import com.xiva.common.BusinessResponse;
import com.xiva.domain.UserBasic;
import com.xiva.service.bo.Page;

/**
 * 
 */
public interface UserService {
    
    /**
     * 
     * @param userBasic
     * @return
     */
    public BusinessResponse login(UserBasic userBasic);
    
    /**
     * 
     * @param page
     * @return
     */
    public BusinessResponse getAllUserByPage(Page page);
    
    /**
     * 
     * @param userBasic
     */
    public void saveUser(UserBasic userBasic);
    
    /**
     * 
     * @param page
     * @param orgId
     * @return
     */
    public BusinessResponse findUserByOrgId(Page page, UserBasic userParam);
    
    
    /**
     * 
     * @param userIds
     */
    public void delUser(Integer[] userIds);
    
    
    /**
     * 
     * @param userId
     * @return
     */
    public UserBasic getUserByPK(Integer userId);
    
    /**
     * 
     * 
     * @param userBasic
     */
    public void modifyUserInfo(UserBasic userBasic);
    
}
