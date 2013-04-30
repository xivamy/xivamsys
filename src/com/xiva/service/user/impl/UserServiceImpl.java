package com.xiva.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.util.CommonUtil;
import com.xiva.common.util.SearchContent;
import com.xiva.common.util.SearchItem;
import com.xiva.common.util.SearchContent.QueryJoinEnum;
import com.xiva.common.util.SearchContent.QueryWhereEnum;
import com.xiva.dao.user.UserDao;
import com.xiva.domain.UserBasic;
import com.xiva.service.bo.Page;
import com.xiva.service.user.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService
{

    private static Log log = LogFactory.getFactory().getInstance(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public BusinessResponse login(UserBasic userBasic)
    {
        log.info("login#start");
        BusinessResponse response = new BusinessResponse();
        SearchContent searchContent = new SearchContent();
        SearchItem searchItem = new SearchItem();
        searchItem.setAttributeName("userName");
        searchItem.setValue(userBasic.getUserName());
        searchItem.setQueryWhere(QueryWhereEnum.EQUAL);
        searchItem.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
        searchContent.addCondition(searchItem);
        UserBasic user = userDao.getByProperties(searchContent);
        if (user != null)
        {
            log.info(user + "login#start");
            response.setAttribute(CommonConstant.RESULT, user);
            return response;
        }
        else
        {
            log.info("login#start");
            response.setHasError(true);
            return response;
        }
    }

    @Override
    public BusinessResponse getAllUserByPage(Page page)
    {

        BusinessResponse response = new BusinessResponse();
        List<UserBasic> userList = null;
        SearchContent searchContent = new SearchContent();
        searchContent.setUserPage(true);
        searchContent.setPageStart(page.getStart());
        searchContent.setPageSize(page.getPageSize());
        Long totalCount = userDao.getTotalCount(searchContent);
        if (totalCount > 0)
        {
            userList = userDao.findByProperties(searchContent);
        }

        response.setAttribute(CommonConstant.RESULT, userList);
        response.setAttribute(CommonConstant.TOTAL_COUNT, totalCount);
        return response;
    }

    @Override
    public void saveUser(UserBasic userBasic)
    {

        // 此处需要增加发送邮件或者短信到客户的信息（主要内容是密码）
        userBasic.setPassword(CommonUtil.encryptPassword("123456"));
        userDao.add(userBasic);
    }

    @Override
    public BusinessResponse findUserByOrgId(Page page, UserBasic userParam)
    {

        BusinessResponse response = new BusinessResponse();
        List<UserBasic> userList = null;

        SearchContent searchContent = new SearchContent();
        searchContent.setUserPage(true);
        searchContent.setPageStart(page.getStart());
        searchContent.setPageSize(page.getPageSize());

        SearchItem searchItem = new SearchItem();
        searchItem.setAttributeName("orgId");
        searchItem.setValue(userParam.getOrgId());
        searchItem.setQueryWhere(QueryWhereEnum.EQUAL);
        searchItem.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
        searchContent.addCondition(searchItem);

        String userName = userParam.getUserName();

        if (StringUtils.isNotEmpty(userName))
        {
            SearchItem searchName = new SearchItem();
            searchName.setAttributeName("userName");
            searchName.setValue(userName);
            searchName.setQueryWhere(QueryWhereEnum.EQUAL);
            searchName.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
            searchContent.addCondition(searchName);

            SearchItem searchNickName = new SearchItem();
            searchNickName.setAttributeName("nickName");
            searchNickName.setValue(userName);
            searchNickName.setQueryWhere(QueryWhereEnum.EQUAL);
            searchNickName.setQueryJoinEnum(QueryJoinEnum.JOIN_OR);
            searchContent.addCondition(searchNickName);
        }

        Long totalCount = userDao.getTotalCount(searchContent);
        if (totalCount > 0)
        {
            userList = userDao.findByProperties(searchContent);
        }
        else
        {
            userList = new ArrayList<UserBasic>();
        }

        response.setAttribute(CommonConstant.RESULT, userList);
        response.setAttribute(CommonConstant.TOTAL_COUNT, totalCount);
        return response;
    }

    @Override
    public void delUser(Integer[] userIds)
    {

        for (Integer userId : userIds)
        {
            UserBasic userBasic = userDao.getByPK(userId);
            if (userBasic != null)
            {
                userDao.delete(userBasic);
            }
        }
    }

    @Override
    public UserBasic getUserByPK(Integer userId)
    {

        UserBasic user = userDao.getByPK(userId);
        return user;
    }

    @Override
    public void modifyUserInfo(UserBasic userBasic)
    {
        userDao.update(userBasic);
    }

}
