package com.xiva.action.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.action.common.BasicAction;
import com.xiva.action.common.DateJsonValueProcessor;
import com.xiva.action.common.ExtTreeVO;
import com.xiva.action.user.ManageUserInfoAction;
import com.xiva.action.util.RequestParaUtil;
import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.IvExceptionCode;
import com.xiva.common.util.DateUtil;
import com.xiva.domain.Org;
import com.xiva.exception.IvMsgException;
import com.xiva.service.bo.Page;
import com.xiva.service.org.OrgService;

/**
 * 
 * @author xiva
 * 
 */
public class ManageOrgAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1590070718164355947L;

    private static final Log log = LogFactory.getFactory().getInstance(ManageUserInfoAction.class);

    private String node;

    private Integer nodeId;

    private String porgId;

    private String treePath;

    private String orgIds;
    
    private Integer orgId;

    @Autowired
    private OrgService orgService;

    @SuppressWarnings("unchecked")
    public void getOrgTree() throws IOException
    {

        log.info("getOrgTree start");
        Integer parentId = Integer.valueOf(0);
        if (StringUtils.isNotEmpty(node))
        {
            parentId = Integer.valueOf(node);
        }

        String expandPath = String.valueOf(super.request.getSession().getAttribute(CommonConstant.ORG_TREE_EXPANDED_PATH));
        String[] paths = expandPath.split("/");

        BusinessResponse businessResponse = orgService.getAllOrgByParentId(parentId);
        List<Org> orgList = (List<Org>) businessResponse.getAttribute(CommonConstant.RESULT);
        List<ExtTreeVO> optionList = new ArrayList<ExtTreeVO>();
        for (Org org : orgList)
        {
            ExtTreeVO option = new ExtTreeVO();
            String orgId = String.valueOf(org.getSysId());
            option.setId(orgId);
            option.setText(org.getOrgName());
            BusinessResponse countResponse = orgService.getOrgCountByParentId(org.getSysId());
            Long total = (Long) countResponse.getAttribute(CommonConstant.TOTAL_COUNT);
            if (total > 0)
            {
                option.setLeaf(false);
            }
            else
            {
                option.setLeaf(true);
            }
            int pathLen = paths.length;
            for (int i = 0; i < pathLen; i++)
            {
                String treeNodeId = paths[i];

                if (orgId.equals(treeNodeId))
                {
                    option.setExpanded(true);
                    if (i == pathLen - 1)
                    {
                        removeTreePath();
                    }
                }
            }
            optionList.add(option);
        }
        JSONArray jsonArray = JSONArray.fromObject(optionList);
        super.response.getWriter().write(jsonArray.toString());
    }

    @SuppressWarnings("unchecked")
    public void getChildOrg() throws IOException
    {

        log.info("getChildOrg start");
        JsonConfig cfg = new JsonConfig();
        cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor());

        Integer parentId = Integer.valueOf(1);
        Org org = null;
        if (StringUtils.isNotEmpty(porgId))
        {
            parentId = Integer.valueOf(porgId);
            BusinessResponse orgResponse = orgService.getOrgByOrgId(parentId);
            org = (Org) orgResponse.getAttribute(CommonConstant.RESULT);
        }

        BusinessResponse businessResponse = orgService.getAllOrgByParentId(parentId);
        List<Org> orgList = (List<Org>) businessResponse.getAttribute(CommonConstant.RESULT);

        if (null != org)
        {
            orgList.add(org);
        }
        Collections.sort(orgList);
        JSONArray jsonArray = JSONArray.fromObject(orgList, cfg);
        super.response.getWriter().write(jsonArray.toString());
    }

    @SuppressWarnings("unchecked")
    public void loadOrgList() throws IOException
    {

        JsonConfig cfg = new JsonConfig();
        cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor());
        Integer orgId = Integer.valueOf(0);
        Page page = new Page();
        page.setStart(start);
        page.setPageSize(limit);
        if (nodeId != null)
        {
            orgId = Integer.valueOf(nodeId);
        }

        BusinessResponse bResponse = orgService.findOrgByPIdPage(page, orgId);
        List<Org> result = (List<Org>) bResponse.getAttribute(CommonConstant.RESULT);
        Object total = bResponse.getAttribute(CommonConstant.TOTAL_COUNT);
        JSONArray jsonArray = JSONArray.fromObject(result, cfg);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(CommonConstant.JSON_ITEMS, jsonArray.toString());
        jsonObj.put(CommonConstant.JSON_TOTAL, total);

        super.response.getWriter().write(jsonObj.toString());
    }

    public void addOrg() throws IOException
    {
        Map<String, Object> bdo = new HashMap<String, Object>();
        String[] fields = { "parentId", "orgName", "orgType", "orgCode", "unicode", "createTime", "invalidDate" };
        for (int i = 0; i < fields.length; i++)
        {
            bdo.put(fields[i], request.getParameter(fields[i]));
        }
        Org org = new Org();
        org.setOrgName(String.valueOf(bdo.get("orgName") == null ? "" : bdo.get("orgName")));
        org.setParentId(Integer.valueOf(String.valueOf(bdo.get("parentId"))));
        org.setOrgType(Integer.valueOf(String.valueOf(bdo.get("orgType"))));
        org.setOrgCode(String.valueOf(bdo.get("orgCode")));
        org.setUnicode(String.valueOf(bdo.get("unicode")));

        org.setCreateTime(DateUtil.parseToSqlTimestamp(String.valueOf(bdo.get("createTime"))));
        org.setInvalidDate(DateUtil.parseToSqlTimestamp(String.valueOf(bdo.get("invalidDate"))));
        org.setStauts(Integer.valueOf(1));
        orgService.saveOrg(org);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put(SUCCESS, true);
        super.response.getWriter().write(jsonObj.toString());
    }

    public void delOrg() throws Exception
    {
        JSONObject jsonObj = new JSONObject();
        Integer[] idsInt = null;
        if (orgIds != null)
        {
            String[] idStrArray = orgIds.split(CommonConstant.COMMA);
            int len = idStrArray.length;
            idsInt = new Integer[len];
            for (int i = 0; i < len; i++)
            {
                idsInt[i] = Integer.valueOf(idStrArray[i]);
            }
        }
        orgService.delOrg(idsInt);
        jsonObj.put(SUCCESS, true);
        super.response.getWriter().write(jsonObj.toString());
    }

    public void getOrgInfo() throws IOException
    {
        JsonConfig cfg = new JsonConfig();
        cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor());
        
        JSONObject jsonObj = new JSONObject();
        Integer ordInt = Integer.valueOf(orgId);
        
        if (orgId != null)
        {
            BusinessResponse response = orgService.getOrgByOrgId(ordInt);
            Org org = (Org)response.getAttribute(CommonConstant.RESULT);
            
            if(org == null)
            {
                throw new IvMsgException(IvExceptionCode.RECORD_NOT_EXSIT);
            }
            else
            {
                jsonObj.put(SUCCESS, true);
                jsonObj.put(CommonConstant.JSON_DATA, JSONObject.fromObject(org, cfg).toString());
            }
        }
        else
        {
            jsonObj.put(SUCCESS, false);
        }
        
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public void modifyOrg() throws IOException
    {
        JSONObject jsonObj = new JSONObject();
        
        Org org = new Org();
        RequestParaUtil.getObjParameter(request, org);
        org.setSysId(orgId);
        try
        {
            orgService.updateOrg(org);
            jsonObj.put(SUCCESS, true);
        }
        catch(Exception e)
        {
            jsonObj.put(SUCCESS, false);
        }
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public void saveTreePath()
    {
        super.request.getSession().setAttribute(CommonConstant.ORG_TREE_EXPANDED_PATH, treePath);
    }

    public void removeTreePath()
    {
        super.request.getSession().removeAttribute(CommonConstant.ORG_TREE_EXPANDED_PATH);
    }

    // get set 方法
    public String getNode()
    {
        return node;
    }

    public void setNode(String node)
    {
        this.node = node;
    }

    public Integer getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(Integer nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getPorgId()
    {
        return porgId;
    }

    public void setPorgId(String porgId)
    {
        this.porgId = porgId;
    }

    public String getTreePath()
    {
        return treePath;
    }

    public void setTreePath(String treePath)
    {
        this.treePath = treePath;
    }

    public String getOrgIds()
    {
        return orgIds;
    }

    public void setOrgIds(String orgIds)
    {
        this.orgIds = orgIds;
    }

    public Integer getOrgId()
    {
        return orgId;
    }
    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }
    
}
