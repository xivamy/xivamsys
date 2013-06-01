package com.xiva.service.permission.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.util.SearchContent;
import com.xiva.common.util.SearchItem;
import com.xiva.common.util.SearchContent.QueryJoinEnum;
import com.xiva.common.util.SearchContent.QueryWhereEnum;
import com.xiva.dao.permission.ResourceDao;
import com.xiva.domain.IvResource;
import com.xiva.service.bo.Page;
import com.xiva.service.permission.ResourceService;

@Service("resService")
@Transactional
public class ResourceServiceImpl implements ResourceService
{

    private static Log log = LogFactory.getFactory().getInstance(ResourceServiceImpl.class);
    
    @Autowired
    private ResourceDao resDao;

    @Override
    public BusinessResponse findResByPIdPage(Page page, Integer parentId)
    {
        BusinessResponse response = new BusinessResponse();
        List<IvResource> orgList = null;
        SearchContent searchContent = new SearchContent();
        searchContent.setUserPage(true);
        searchContent.setPageStart(page.getStart());
        searchContent.setPageSize(page.getPageSize());

        SearchItem searchItem = new SearchItem();
        searchItem.setAttributeName("resourcePid");
        searchItem.setValue(parentId);
        searchItem.setQueryWhere(QueryWhereEnum.EQUAL);
        searchItem.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
        searchContent.addCondition(searchItem);
        Long totalCount = resDao.getTotalCount(searchContent);

        if (totalCount > 0)
        {
            orgList = resDao.findByProperties(searchContent);
        }
        response.setAttribute(CommonConstant.RESULT, orgList);
        response.setAttribute(CommonConstant.TOTAL_COUNT, totalCount);

        return response;
    }

    @Override
    public BusinessResponse getAllOrgByParentId(Integer parentId)
    {
        BusinessResponse response = new BusinessResponse();
        List<IvResource> resList = resDao.findByProperty("resourcePid", parentId);
        response.setAttribute(CommonConstant.RESULT, resList);
        return response;
    }

    @Override
    public BusinessResponse getResCountByParentId(Integer parentId)
    {
        BusinessResponse response = new BusinessResponse();
        SearchContent searchContent = new SearchContent();
        SearchItem searchItem = new SearchItem();
        searchItem.setAttributeName("resourcePid");
        searchItem.setValue(parentId);
        searchItem.setQueryWhere(QueryWhereEnum.EQUAL);
        searchItem.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
        searchContent.addCondition(searchItem);
        Long totalCount = resDao.getTotalCount(searchContent);
        response.setAttribute(CommonConstant.TOTAL_COUNT, totalCount);
        
        return response;
    }

    @Override
    public boolean saveResource(IvResource res)
    {
        boolean isSave = true;
        try
        {
            resDao.add(res);
        }
        catch (Exception e){
            log.error(e.toString());
            isSave = false;
        }
        
        return isSave;
    }

    @Override
    public void delResources(Integer[] ids)
    {
        for (Integer resId : ids)
        {
            IvResource res = resDao.getByPK(resId);
            if (res != null)
            {
                resDao.delete(res);
            }
        }
    }

    @Override
    public IvResource getResByPK(Integer resId)
    {
        IvResource res = resDao.getByPK(resId);
        return res;
    }

    @Override
    public void modifyResInfo(IvResource res)
    {
        resDao.update(res);
    }
    
}
