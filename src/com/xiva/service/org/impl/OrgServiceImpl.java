package com.xiva.service.org.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.IvExceptionCode;
import com.xiva.common.util.DateUtil;
import com.xiva.common.util.SearchContent;
import com.xiva.common.util.SearchItem;
import com.xiva.common.util.SearchContent.QueryJoinEnum;
import com.xiva.common.util.SearchContent.QueryWhereEnum;
import com.xiva.dao.org.OrgDao;
import com.xiva.dao.user.UserDao;
import com.xiva.domain.Org;
import com.xiva.domain.UserBasic;
import com.xiva.exception.IvMsgException;
import com.xiva.service.bo.Page;
import com.xiva.service.org.OrgService;

@Service("orgService")
@Transactional
public class OrgServiceImpl implements OrgService
{

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;
    
    @Override
    public BusinessResponse getAllOrgByPage(Page page)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResponse getAllOrgByParentId(Integer parentId)
    {

        BusinessResponse response = new BusinessResponse();
        List<Org> orgList = orgDao.findByProperty("parentId", parentId);
        response.setAttribute(CommonConstant.RESULT, orgList);

        return response;
    }

    @Override
    public BusinessResponse getOrgCountByParentId(Integer parentId)
    {

        BusinessResponse response = new BusinessResponse();
        SearchContent searchContent = new SearchContent();
        SearchItem searchItem = new SearchItem();
        searchItem.setAttributeName("parentId");
        searchItem.setValue(parentId);
        searchItem.setQueryWhere(QueryWhereEnum.EQUAL);
        searchItem.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
        searchContent.addCondition(searchItem);
        Long totalCount = orgDao.getTotalCount(searchContent);
        response.setAttribute(CommonConstant.TOTAL_COUNT, totalCount);

        return response;
    }

    @Override
    public BusinessResponse findOrgByPIdPage(Page page, Integer parentId)
    {
        BusinessResponse response = new BusinessResponse();
        List<Org> orgList = null;
        SearchContent searchContent = new SearchContent();
        searchContent.setUserPage(true);
        searchContent.setPageStart(page.getStart());
        searchContent.setPageSize(page.getPageSize());

        SearchItem searchItem = new SearchItem();
        searchItem.setAttributeName("parentId");
        searchItem.setValue(parentId);
        searchItem.setQueryWhere(QueryWhereEnum.EQUAL);
        searchItem.setQueryJoinEnum(QueryJoinEnum.JOIN_AND);
        searchContent.addCondition(searchItem);
        Long totalCount = orgDao.getTotalCount(searchContent);

        if (totalCount > 0)
        {
            orgList = orgDao.findByProperties(searchContent);
        }
        response.setAttribute(CommonConstant.RESULT, orgList);
        response.setAttribute(CommonConstant.TOTAL_COUNT, totalCount);

        return response;
    }

    @Override
    public BusinessResponse getOrgByOrgId(Integer orgId)
    {
        BusinessResponse response = new BusinessResponse();
        Org org = orgDao.getByPK(orgId);
        response.setAttribute(CommonConstant.RESULT, org);
        return response;
    }

    @Override
    public void saveOrg(Org org)
    {
        orgDao.add(org);
    }

    @Override
    public void delOrg(Integer[] orgIds)
    {
        for (Integer orgId : orgIds)
        {
            Org org = orgDao.getByPK(orgId);
            List<UserBasic> userList = userDao.findByProperty("orgId", orgId);
            if (userList != null && userList.size() > 0)
            {
                throw new IvMsgException(IvExceptionCode.ORG_CONTAIN_USER);
            }
            else
            {
                List<Org> orgList = orgDao.findByProperty("parentId", orgId);
                if (orgList != null && orgList.size() > 0)
                {
                    throw new IvMsgException(IvExceptionCode.ORG_CONTAIN_CHILD, org.getOrgName());
                }
                else
                {
                    orgDao.delete(org); 
                }
            }
        }
    }

    @Override
    public void updateOrg(Org org)
    {
        Org orgDb = orgDao.getByPK(org.getSysId());
        
        orgDb.setUpdateTime(DateUtil.getNowTimeStamp());
        orgDb.setStauts(Integer.valueOf(1));
        
        orgDb.setCreateTime(org.getCreateTime());
        orgDb.setParentId(org.getParentId());
        orgDb.setInvalidDate(org.getInvalidDate());
        orgDb.setOrgCode(org.getOrgCode());
        orgDb.setOrgType(org.getOrgType());
        orgDb.setSetUpDate(org.getSetUpDate());
        orgDb.setUnicode(org.getUnicode());
        orgDb.setOrgName(org.getOrgName());
        orgDao.update(orgDb);
    }

}
