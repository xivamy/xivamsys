package com.xiva.common.util;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xiva.common.CommonConstant;
import com.xiva.domain.UserBasic;
import com.xiva.domain.UserLog;

public class CommonUtil
{

    public static String encryptPassword(String strPasswd)
    {
        if (strPasswd == null || strPasswd.equals(""))
        {
            return "";
        }
        MessageDigest md;
        byte[] ebytes = { 0 };
        try
        {
            md = MessageDigest.getInstance("MD5", "SUN");
            md.update(strPasswd.getBytes("utf-8"));
            ebytes = md.digest();
        }
        catch (Exception e)
        {
        }
        return hex2Str(ebytes);
    }

    /**
     * [0x11,0x2f,0xd3]--->"112fd3"
     * 
     * @param code
     * @return
     */
    public static String hex2Str(byte[] code)
    {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        int j = code.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++)
        {
            byte byte0 = code[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }

        return new String(str);
    }

    public static UserLog createUserLog(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        UserLog userLog = new UserLog();
        UserBasic user = (UserBasic) session.getAttribute(CommonConstant.USER_LOGIN);
        Date loginTime = (Date) session.getAttribute(CommonConstant.USER_LOGIN_TIME);

        if (user != null)
        {
            userLog.setUserId(user.getUserId());
        }

        if (loginTime != null)
        {
            userLog.setLoginTime(DateUtil.format(loginTime, DateUtil.DATE_FORMAT_5, Locale.CHINA));
        }
        else
        {
            userLog.setLoginTime(DateUtil.format(DateUtil.getCurrentTime(), DateUtil.DATE_FORMAT_5, Locale.CHINA));
        }
        userLog.setLoginIp(CommonUtil.getRealIp(request));

        return userLog;
    }

    public static String getRealIp(HttpServletRequest request)
    {

        String realIp = request.getHeader("x-forwarded-for");

        if (realIp != null)
        {
            if (realIp.indexOf(',') == -1)
            {
                return realIp;
            }
            else
            {
                return realIp.split(",")[0];
            }
            
        }
        else
        {
            realIp = request.getHeader("Proxy-Client-IP");
            
            if (realIp == null || realIp.length() == 0 || "unknown".equalsIgnoreCase(realIp))
            {
                realIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (realIp == null || realIp.length() == 0 || "unknown".equalsIgnoreCase(realIp))
            {
                realIp = request.getRemoteAddr();
            }
        }

        

        return realIp;
    }

    public static void main(String[] args)
    {
        System.out.println(encryptPassword("123456"));
    }
}
