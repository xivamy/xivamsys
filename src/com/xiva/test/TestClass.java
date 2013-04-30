package com.xiva.test;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xiva.common.CommonConstant;
import com.xiva.common.util.DateUtil;
import com.xiva.common.util.ParameterUtil;
import com.xiva.domain.UserBasic;
import com.xiva.domain.UserLog;
import com.xiva.service.user.UserService;

public class TestClass {

    private static Log log = LogFactory.getFactory().getInstance(TestClass.class);
    
    @Test public void log() {
        UserLog userLog = new UserLog();
        userLog.setLoginTime("20120813004012");
        Date logoutDate = DateUtil.getCurrentTime();
        String loginTime = userLog.getLoginTime();
        Date loginDate = DateUtil.parse(loginTime);
        long logLong = (logoutDate.getTime() - loginDate.getTime())/1000;
        String logoutDateStr = DateUtil.format(logoutDate, DateUtil.DATE_FORMAT_5, Locale.CHINA);
        userLog.setLogoutTime(logoutDateStr);
        String accuracy = ParameterUtil.getPropByKey(ParameterUtil.LOG_TIME_ACCURACY);
        
        if (CommonConstant.SECOND.equalsIgnoreCase(accuracy)) {
            log.info(String.valueOf(logLong));
            userLog.setLoginLong(String.valueOf(logLong));
        }
        
    }
    
    @Test public void testRandom() {
        Random random = new Random();
        log.info(random.nextInt(10));
    }
    
    @Test public void testCount()
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = (UserService)ac.getBean("userService");
        log.info(userService.getAllUserByPage(null).getAttribute(CommonConstant.TOTAL_COUNT));
    }
    public static void main(String[] args)
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = (UserService)ac.getBean("userService");
        UserBasic userBasic = new UserBasic();
        userBasic.setPassword("1232111");
        userBasic.setUserName("xivamy55");
        userService.login(userBasic);
        log.info(ParameterUtil.getPropByKey(ParameterUtil.UPLOAD_FILEPATH));
    }
}
