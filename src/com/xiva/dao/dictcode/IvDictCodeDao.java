package com.xiva.dao.dictcode;

import java.util.List;

import com.xiva.dao.base.BaseDao;
import com.xiva.domain.IvDictCode;

public interface IvDictCodeDao extends BaseDao<IvDictCode, Integer>
{
    public List<IvDictCode> findDictCodeByKey(String statusKey);
}
