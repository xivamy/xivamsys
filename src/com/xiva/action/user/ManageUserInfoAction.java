package com.xiva.action.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.action.common.BasicAction;
import com.xiva.action.vo.IvUserBasicVo;
import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.util.DateUtil;
import com.xiva.domain.UserBasic;
import com.xiva.service.bo.Page;
import com.xiva.service.user.UserService;
import com.xiva.service.util.DictCodeUtil;

public class ManageUserInfoAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7766958120705210593L;

    private static final Log log = LogFactory.getFactory().getInstance(ManageUserInfoAction.class);

    private String orgId;

    private String userName;

    private String userIds;

    private Integer userId;
    
    private IvUserBasicVo userBasicVo;
    
    @Autowired
    private UserService userService;

    @SuppressWarnings("unchecked")
    public void loadUserList() throws IOException
    {
        int start = Integer.valueOf(String.valueOf(super.request.getAttribute("start")));
        int limit = Integer.valueOf(String.valueOf(super.request.getAttribute("limit")));
        Integer orgIdInt = null;
        UserBasic userParam = new UserBasic();
        log.info(super.getStart());
        log.info(super.getLimit());
        Page page = new Page();
        page.setStart(start);
        page.setPageSize(limit);
        
        
        if (orgId != null)
        {
            orgIdInt = Integer.valueOf(orgId);
        }
        else
        {
            orgIdInt = Integer.valueOf(1);
        }

        userParam.setOrgId(orgIdInt);
        userParam.setUserName(userName);

        BusinessResponse bResponse = userService.findUserByOrgId(page, userParam);

        List<UserBasic> result = (List<UserBasic>) bResponse.getAttribute(CommonConstant.RESULT);
        List<IvUserBasicVo> viewList = new ArrayList<IvUserBasicVo>();

        for (UserBasic userBasic : result)
        {
            IvUserBasicVo userVo = new IvUserBasicVo();
            userVo.convertFromBo(userBasic);
            String codeKey = userBasic.getStatus() == null ? "0" : String.valueOf(userBasic.getStatus());
            String codeDes = DictCodeUtil.getDictDes(codeKey);
            userVo.setStatusCode(codeDes);
            userVo.setRegisterTime(DateUtil.format(DateUtil.parse(userBasic.getRegisterTime()), DateUtil.DATE_FORMAT_2, Locale.CHINA));
            viewList.add(userVo);
        }

        Object total = bResponse.getAttribute(CommonConstant.TOTAL_COUNT);
        JSONArray jsonArray = JSONArray.fromObject(viewList);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("items", jsonArray.toString());
        jsonObj.put("total", total);
        response.setContentType("text/json");
        super.response.getWriter().write(jsonObj.toString());
    }

    public void addUser() throws IOException
    {
        JSONObject jsonObj = new JSONObject();
        Map<String, Object> bdo = new HashMap<String, Object>();
        String[] fields = { "userName", "nickName", "companyId", "userCode", "status", "phoneNum", "email", "validDate" };
        for (int i = 0; i < fields.length; i++)
        {
            bdo.put(fields[i], request.getParameter(fields[i]));
        }
        UserBasic userBasic = new UserBasic();
        userBasic.setUserName(String.valueOf(bdo.get("userName")));
        userBasic.setNickName(String.valueOf(bdo.get("nickName")));
        userBasic.setEmail(String.valueOf(bdo.get("email")));
        userBasic.setRegisterTime(DateUtil.format(new Date(), DateUtil.DATE_FORMAT_5, Locale.CHINA));
        userBasic.setOrgId(Integer.valueOf(bdo.get("companyId").toString()));
        userBasic.setValidateTime(String.valueOf(bdo.get("validDate")));
        userBasic.setUserCode(String.valueOf(bdo.get("userCode")));
        userBasic.setPhoneNum(String.valueOf(bdo.get("phoneNum")));
        userBasic.setStatus(CommonConstant.USER_STATUS_CREATE);

        try
        {
            userService.saveUser(userBasic);
            jsonObj.put("success", true);
        }
        catch (Exception e)
        {
            jsonObj.put("success", false);
        }
        super.response.getWriter().write(jsonObj.toString());
    }

    public void delUser() throws IOException
    {
        JSONObject jsonObj = new JSONObject();
        Integer[] idsInt = null;
        try
        {
            if (userIds != null)
            {
                String[] idStrArray = userIds.split(",");
                int len = idStrArray.length;
                idsInt = new Integer[len];
                for (int i = 0; i < len; i++)
                {
                    idsInt[i] = Integer.valueOf(idStrArray[i]);
                }
            }
            userService.delUser(idsInt);
            jsonObj.put("success", true);
        }
        catch (Exception e)
        {
            jsonObj.put("success", false);
        }
        super.response.getWriter().write(jsonObj.toString());
    }

    public void getUserInfo() throws IOException
    {
        UserBasic user = userService.getUserByPK(userId);
        JSONObject jsonObj = JSONObject.fromObject(user);
        super.response.getWriter().write(jsonObj.toString());
    }

    
    public void modifyUserInfo() throws IOException
    {
        UserBasic user = userService.getUserByPK(userBasicVo.getUserId());
        user.setEmail(userBasicVo.getEmail());
        user.setNickName(userBasicVo.getNickName());
        user.setOrgId(userBasicVo.getOrgId());
        user.setUserCode(userBasicVo.getUserCode());
        user.setPhoneNum(userBasicVo.getPhoneNum());
        user.setValidateTime(userBasicVo.getValidateTime());
        user.setStatus(userBasicVo.getStatus());
        JSONObject jsonObj = new JSONObject();
        try
        {
            userService.modifyUserInfo(user);
            jsonObj.put("success", true);
        }
        catch (Exception e)
        {
            jsonObj.put("success", false);
        }
        super.response.getWriter().write(jsonObj.toString());
    }
    
    public String getOrgId()
    {
        return orgId;
    }

    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserIds()
    {
        return userIds;
    }

    public void setUserIds(String userIds)
    {
        this.userIds = userIds;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public IvUserBasicVo getUserBasicVo()
    {
        return userBasicVo;
    }

    public void setUserBasicVo(IvUserBasicVo userBasicVo)
    {
        this.userBasicVo = userBasicVo;
    }

    public static void main(String[] args)
    {
        String ids = "1,2,5,7,";
        String[] idStrArray = ids.split(",");
        System.out.println(Arrays.toString(idStrArray));
    }
}
