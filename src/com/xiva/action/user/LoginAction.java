package com.xiva.action.user;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.action.common.BasicAction;
import com.xiva.common.BusinessResponse;
import com.xiva.common.CommonConstant;
import com.xiva.common.util.CommonUtil;
import com.xiva.common.util.DateUtil;
import com.xiva.common.util.GlobalMessageUtil;
import com.xiva.domain.UserBasic;
import com.xiva.domain.UserLog;
import com.xiva.service.system.UserLogService;
import com.xiva.service.user.UserService;

public class LoginAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5874659849371115057L;

    private String userName;

    private String password;

    private boolean success;

    @Autowired
    private transient UserService userService;

    @Autowired
    private transient UserLogService userLogService;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String login() throws IOException
    {
        log.info("login start");
        UserBasic userBasic = new UserBasic();
        userBasic.setPassword(this.password);
        userBasic.setUserName(this.userName);
        BusinessResponse response = userService.login(userBasic);
        UserBasic user = (UserBasic) response.getAttribute(CommonConstant.RESULT);
        if (response.isHasError())
        {
            return FAILURE;
        }
        else
        {
            if (this.password != null)
            {
                String realPassword = CommonUtil.encryptPassword(this.password);
                HttpSession session = super.request.getSession();
                if (user.getPassword().equals(realPassword))
                {
                    session.setAttribute(CommonConstant.USER_LOGIN, user);
                    session.setAttribute(CommonConstant.USER_LOGIN_TIME, DateUtil.getCurrentTime());
                    UserLog userLog = CommonUtil.createUserLog(super.request);
                    session.setAttribute(CommonConstant.USER_LOG, userLog);
                    success = true;
                    return SUCCESS;
                }
                else
                {
                    return FAILURE;
                }
            }
            else
            {
                return FAILURE;
            }
        }
    }

    public String logout()
    {
        HttpSession session = super.request.getSession();
        UserLog userLog = (UserLog) session.getAttribute(CommonConstant.USER_LOG);
        userLog.setReason(GlobalMessageUtil.getPropByKey(GlobalMessageUtil.LOG_REASON_NORMAL));
        userLog.setLogoutWay(CommonConstant.LOGOUT_NORMAL);
        userLogService.recordLog(userLog);
        session.setAttribute(CommonConstant.NORMAL_LOGOUT, Boolean.TRUE);
        session.invalidate();
        return SUCCESS;
    }
}
