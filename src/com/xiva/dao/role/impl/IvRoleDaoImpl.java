package com.xiva.dao.role.impl;

import org.springframework.stereotype.Repository;

import com.xiva.dao.base.impl.BaseDaoImpl;
import com.xiva.dao.role.IvRoleDao;
import com.xiva.domain.IvRole;

@Repository(value="roleDao")
public class IvRoleDaoImpl extends BaseDaoImpl<IvRole, Integer>   implements IvRoleDao
{
    
}
