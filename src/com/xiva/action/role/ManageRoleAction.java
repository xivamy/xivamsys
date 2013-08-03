package com.xiva.action.role;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.action.common.BasicAction;
import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.domain.IvRole;
import com.xiva.service.bo.Page;
import com.xiva.service.role.RoleService;

/**
 * 
 * 角色相关Action
 * @author xiva
 * @version [版本号, 2013-6-4]
 * @see [相关类/方法]
 * @since [产品、模块版本]
 */
public class ManageRoleAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3061187933243100826L;
    
    @Autowired
    private transient RoleService roleService;
    
    private String roleId;
    
    @SuppressWarnings("unchecked")
    public void roleInfoList() throws IOException
    {
        int start = Integer.valueOf(String.valueOf(super.request.getAttribute("start")));
        int limit = Integer.valueOf(String.valueOf(super.request.getAttribute("limit")));
        log.info(super.getStart());
        log.info(super.getLimit());
        
        Page page = new Page();
        page.setStart(start);
        page.setPageSize(limit);
        
        Integer roleIdInt = Integer.valueOf(0);
        if (roleId != null)
        {
            roleIdInt = Integer.valueOf(roleId);
        }
        
        BusinessResponse bResponse = roleService.findRolesByPIdPage(page, roleIdInt);
        
        List<IvRole> result = (List<IvRole>) bResponse.getAttribute(CommonConstant.RESULT);
        Object total = bResponse.getAttribute(CommonConstant.TOTAL_COUNT);
        JSONArray jsonArray = JSONArray.fromObject(result);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(CommonConstant.JSON_ITEMS, jsonArray.toString());
        jsonObj.put(CommonConstant.JSON_TOTAL, total);
        super.response.getWriter().write(jsonObj.toString());
        
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

}
