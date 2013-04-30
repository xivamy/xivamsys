package com.xiva.common.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiva.common.CommonConstant;
import com.xiva.common.util.GlobalMessageUtil;
import com.xiva.domain.UserLog;
import com.xiva.service.system.UserLogService;

public class UserLoginListener implements HttpSessionListener {

    @Autowired
    private UserLogService userLogService;
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Boolean logout = (Boolean) session.getAttribute(CommonConstant.NORMAL_LOGOUT);
        //只有正常登陆的用户才会有用户日志
        UserLog userLog = (UserLog)session.getAttribute(CommonConstant.USER_LOG);
        if ( userLog != null ) {
            if (logout != null && logout.equals(Boolean.TRUE)) {
                userLog.setLogoutWay(CommonConstant.LOGOUT_SESSION_OUT);
                userLog.setReason(GlobalMessageUtil.getPropByKey(GlobalMessageUtil.LOG_REASON_OUT));
                userLogService.recordLog(userLog);
            }
        }
        
    }

}
