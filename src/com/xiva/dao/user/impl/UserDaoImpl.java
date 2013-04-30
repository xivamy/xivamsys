package com.xiva.dao.user.impl;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import com.xiva.dao.base.impl.BaseDaoImpl;
import com.xiva.dao.user.UserDao;
import com.xiva.domain.UserBasic;

@Repository(value="userDao")
public class UserDaoImpl extends BaseDaoImpl<UserBasic, Integer> implements UserDao{

    
    @Override
    public UserBasic getUser(Integer userId) {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        return jpaTemplate.find(UserBasic.class, userId); 
    }

}
