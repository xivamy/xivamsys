package com.xiva.service.system.impl;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiva.common.CommonConstant;
import com.xiva.common.util.DateUtil;
import com.xiva.common.util.ParameterUtil;
import com.xiva.dao.system.UserLogDao;
import com.xiva.domain.UserLog;
import com.xiva.service.system.UserLogService;

@Service("userLogService")
@Transactional
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogDao userLogDao;
    
    @Override
    public void recordLog(UserLog userLog) {
        
        Date logoutDate = DateUtil.getCurrentTime();
        String loginTime = userLog.getLoginTime();
        Date loginDate = DateUtil.parse(loginTime);
        long logLong = (logoutDate.getTime() - loginDate.getTime())/1000;
        String logoutDateStr = DateUtil.format(logoutDate, DateUtil.DATE_FORMAT_5, Locale.CHINA);
        userLog.setLogoutTime(logoutDateStr);
        String accuracy = ParameterUtil.getPropByKey(ParameterUtil.LOG_TIME_ACCURACY);
        
        if (CommonConstant.SECOND.equalsIgnoreCase(accuracy)) {
            userLog.setLoginLong(String.valueOf(logLong));
        }
        userLogDao.add(userLog);
    }
    
}
