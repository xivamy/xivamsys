package com.xiva.dao.user;

import com.xiva.dao.base.BaseDao;
import com.xiva.domain.UserBasic;

public interface UserDao extends BaseDao<UserBasic, Integer>{

    public UserBasic getUser(Integer userId);
}
