package com.xiva.dao.permission.impl;

import org.springframework.stereotype.Repository;

import com.xiva.dao.base.impl.BaseDaoImpl;
import com.xiva.dao.permission.ResourceDao;
import com.xiva.domain.IvResource;

@Repository(value="resDao")
public class ResourceDaoImpl extends BaseDaoImpl<IvResource, Integer>  implements ResourceDao
{

}
