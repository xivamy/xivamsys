package com.xiva.dao.dictcode.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiva.dao.base.impl.BaseDaoImpl;
import com.xiva.dao.dictcode.IvDictCodeDao;
import com.xiva.domain.IvDictCode;

@Repository(value="ivDictCodeDao")
public class IvDictCodeDaoImpl extends BaseDaoImpl<IvDictCode, Integer>  implements IvDictCodeDao
{

    @Override
    public List<IvDictCode> findDictCodeByKey(String statusKey)
    {
        
        IvDictCode dictCode = this.getByProperty("statuskey", statusKey);
        
        List<IvDictCode> codeList = this.findByProperty("parentId", dictCode.getId());
        
        return codeList;
    }
    
}
