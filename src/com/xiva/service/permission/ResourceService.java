package com.xiva.service.permission;

import com.xiva.common.BusinessResponse;
import com.xiva.domain.IvResource;
import com.xiva.service.bo.Page;

public interface ResourceService
{
    /**
     * 根据父ID，分页获取列表
     * @param page
     * @param parentId
     * @return
     */
    BusinessResponse findResByPIdPage(Page page, Integer parentId);
    
    /**
     * 根据父ID获取列表
     * @param parentId
     * @return
     */
    BusinessResponse getAllOrgByParentId(Integer parentId);
    
    /**
     * 根据父ID，查询子节点个数
     * @param parentId
     * @return
     */
    BusinessResponse getResCountByParentId(Integer parentId);

    /**
     * 保存资源对象
     * @param res
     * @return
     */
    boolean saveResource(IvResource res);
    
    /**
     * 删除资源
     * 
     * @param ids
     */
    void delResources(Integer[] ids);
    
    /**
     * 根据主键获取对象
     * @param resId
     * @return
     */
    IvResource getResByPK(Integer resId);

    /**
     * 修改资源
     * @param res
     */
    void modifyResInfo(IvResource res);
}
