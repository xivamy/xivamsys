package com.xiva.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.xiva.dao.base.impl.BaseDaoImpl;
import com.xiva.dao.system.UserLogDao;
import com.xiva.domain.UserLog;

@Repository(value="userLogDao")
public class UserLogDaoImpl extends BaseDaoImpl<UserLog, Integer> implements UserLogDao{

}
