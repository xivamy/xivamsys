package com.xiva.service.org;

import com.xiva.common.BusinessResponse;
import com.xiva.domain.Org;
import com.xiva.service.bo.Page;

public interface OrgService {
    
    public BusinessResponse getAllOrgByPage(Page page);
    
    public BusinessResponse getAllOrgByParentId(Integer parentId);
    
    public BusinessResponse getOrgCountByParentId(Integer parentId);
    
    public BusinessResponse findOrgByPIdPage(Page page, Integer parentId);
    
    public BusinessResponse getOrgByOrgId(Integer orgId);
    
    public void saveOrg(Org org);
    
    public void delOrg(Integer[] orgIds);
    
    public void updateOrg(Org org);
}
