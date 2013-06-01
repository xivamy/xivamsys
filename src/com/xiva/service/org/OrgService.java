package com.xiva.service.org;

import com.xiva.common.BusinessResponse;
import com.xiva.domain.Org;
import com.xiva.service.bo.Page;

public interface OrgService {
    
    BusinessResponse getAllOrgByPage(Page page);
    
    BusinessResponse getAllOrgByParentId(Integer parentId);
    
    BusinessResponse getOrgCountByParentId(Integer parentId);
    
    BusinessResponse findOrgByPIdPage(Page page, Integer parentId);
    
    BusinessResponse getOrgByOrgId(Integer orgId);
    
    void saveOrg(Org org);
    
    void delOrg(Integer[] orgIds);
    
    void updateOrg(Org org);
}
