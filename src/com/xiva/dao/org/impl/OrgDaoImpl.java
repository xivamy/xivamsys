package com.xiva.dao.org.impl;

import org.springframework.stereotype.Repository;

import com.xiva.dao.base.impl.BaseDaoImpl;
import com.xiva.dao.org.OrgDao;
import com.xiva.domain.Org;

@Repository(value="orgDao")
public class OrgDaoImpl extends BaseDaoImpl<Org, Integer> implements OrgDao{


}
