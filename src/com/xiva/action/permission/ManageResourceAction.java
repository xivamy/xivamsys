package com.xiva.action.permission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.action.common.BasicAction;
import com.xiva.action.common.ExtTreeVO;
import com.xiva.action.util.RequestParaUtil;
import com.xiva.action.vo.IvOrgTreeVo;
import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.domain.IvResource;
import com.xiva.service.bo.Page;
import com.xiva.service.permission.ResourceService;

/**
 * 
 * 权限管理系统中的资源管理相关Action
 * @author XIVA
 * @version [版本号, 2013-5-25]
 * @see [相关类/方法]
 * @since [产品、模块版本]
 */
@SuppressWarnings("unchecked")
public class ManageResourceAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2583526432276651133L;

    @Autowired
    private transient ResourceService resService;
    
    // 资源ID
    private String resId;
    
    private String node;
    
    // 需要删除资源的ID
    private String resIds;
    
    private IvResource resource;
    
    public void loadResourceList() throws IOException
    {
        int start = Integer.valueOf(String.valueOf(super.request.getAttribute("start")));
        int limit = Integer.valueOf(String.valueOf(super.request.getAttribute("limit")));
        log.info(super.getStart());
        log.info(super.getLimit());
        
        Page page = new Page();
        page.setStart(start);
        page.setPageSize(limit);
        
        Integer resIdInt = Integer.valueOf(1);
        if (resId != null)
        {
            resIdInt = Integer.valueOf(resId);
        }
        
        BusinessResponse bResponse = resService.findResByPIdPage(page, resIdInt);
        List<IvResource> result = (List<IvResource>) bResponse.getAttribute(CommonConstant.RESULT);
        Object total = bResponse.getAttribute(CommonConstant.TOTAL_COUNT);
        JSONArray jsonArray = JSONArray.fromObject(result);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(CommonConstant.JSON_ITEMS, jsonArray.toString());
        jsonObj.put(CommonConstant.JSON_TOTAL, total);
        super.response.getWriter().write(jsonObj.toString());
        
    }
    
    public void getResTree() throws IOException
    {
        List<ExtTreeVO> optionList = new ArrayList<ExtTreeVO>();
        Integer resIdInt = Integer.valueOf(0);
        
        if (StringUtils.isNotEmpty(node))
        {
            resIdInt = Integer.valueOf(node);
        }
        
        BusinessResponse businessResponse = resService.getAllOrgByParentId(resIdInt);
        List<IvResource> orgList = (List<IvResource>) businessResponse.getAttribute(CommonConstant.RESULT);
        
        for (IvResource ivRes:orgList)
        {
            Integer resId = ivRes.getResourceId();
            ExtTreeVO option = new ExtTreeVO();
            option.setId(resId);
            option.setText(ivRes.getResourceName());
            BusinessResponse countResponse = resService.getResCountByParentId(resId);
            Long total = (Long) countResponse.getAttribute(CommonConstant.TOTAL_COUNT);
            if (total > 0)
            {
                option.setLeaf(false);
            }
            else
            {
                option.setLeaf(true);
            }
            
            optionList.add(option);
        }
        
        JSONArray jsonArray = JSONArray.fromObject(optionList);
        super.response.getWriter().write(jsonArray.toString());
    }

    /**
     * 获取所有书 Combox用
     * @throws IOException
     */
    public void getAllResTree() throws IOException
    {
        log.info("getAllOrgTree start");
        Integer parentId = Integer.valueOf(0);
        if (StringUtils.isNotEmpty(node))
        {
            parentId = Integer.valueOf(node);
        }
        List<IvOrgTreeVo> optionList = new ArrayList<IvOrgTreeVo>();

        this.getResTreeByOrgId(optionList, parentId);
        JSONArray jsonArray = JSONArray.fromObject(optionList);
        super.response.getWriter().write(jsonArray.toString());
        
    }
    
    private void getResTreeByOrgId(List<IvOrgTreeVo> treeList, Integer orgId)
    {
        BusinessResponse businessResponse = resService.getAllOrgByParentId(orgId);
        List<IvResource> orgList = (List<IvResource>) businessResponse.getAttribute(CommonConstant.RESULT);

        for (IvResource ivRes : orgList)
        {
            IvOrgTreeVo treeVo = new IvOrgTreeVo();
            treeVo.setId(ivRes.getResourceId());
            treeVo.setText(ivRes.getResourceName());

            BusinessResponse countResponse = resService.getResCountByParentId(ivRes.getResourceId());
            Long total = (Long) countResponse.getAttribute(CommonConstant.TOTAL_COUNT);
            if (total > 0)
            {
                List<IvOrgTreeVo> optionList = new ArrayList<IvOrgTreeVo>();
                getResTreeByOrgId(optionList, ivRes.getResourceId());
                treeVo.setChildren(optionList);
                treeVo.setState("closed");
                treeList.add(treeVo);
            }
            else
            {
                treeList.add(treeVo);
            }
        }
    }
    
    public void addResInfo() throws IOException
    {
        IvResource res = new IvResource();
        RequestParaUtil.getObjParameter(super.request, res);
        boolean isSave = resService.saveResource(res);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(SUCCESS, isSave);
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public void delSelRes() throws IOException
    {
        JSONObject jsonObj = new JSONObject();
        Integer[] idsInt = null;
        try
        {
            if (resIds != null)
            {
                String[] idStrArray = resIds.split(",");
                int len = idStrArray.length;
                idsInt = new Integer[len];
                for (int i = 0; i < len; i++)
                {
                    idsInt[i] = Integer.valueOf(idStrArray[i]);
                }
            }
            resService.delResources(idsInt);
            jsonObj.put("success", true);
        }
        catch (Exception e)
        {
            jsonObj.put("success", false);
        }
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public void getResInfo() throws IOException
    {
        Integer resIdInt = null;
        if (resId != null)
        {
            resIdInt = Integer.valueOf(resId);
        }
        IvResource res = resService.getResByPK(resIdInt);
        JSONObject jsonObj = JSONObject.fromObject(res);
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public void modifyResInfo() throws IOException
    {
        JSONObject jsonObj = new JSONObject();
        
        IvResource res = resService.getResByPK(resource.getResourceId());
        res.setResourceName(resource.getResourceName());
        res.setResourceType(resource.getResourceType());
        res.setResourceUrl(resource.getResourceUrl());
        res.setResourcePid(resource.getResourcePid());
        
        try
        {
            resService.modifyResInfo(res);
            jsonObj.put("success", true);
        }
        catch (Exception e)
        {
            jsonObj.put("success", false);
        }
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public String getResId()
    {
        return resId;
    }

    public void setResId(String resId)
    {
        this.resId = resId;
    }

    public String getNode()
    {
        return node;
    }

    public void setNode(String node)
    {
        this.node = node;
    }

    public String getResIds()
    {
        return resIds;
    }

    public void setResIds(String resIds)
    {
        this.resIds = resIds;
    }

    public IvResource getResource()
    {
        return resource;
    }

    public void setResource(IvResource resource)
    {
        this.resource = resource;
    }
    
}
