package com.xiva.common.util;

import java.io.IOException;
import java.util.Properties;

public class GlobalMessageUtil {

    private static Properties propes  = new Properties();
    
    public final static String LOG_REASON_NORMAL = "logReasonNormal";
    
    public final static String LOG_REASON_OUT = "logReasonOut";
    
    static
    {
        try {
            propes.load(GlobalMessageUtil.class.getClassLoader().getResourceAsStream("global.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private GlobalMessageUtil()
    {
    }
    
    public static String getPropByKey(String key)
    {
        String value =  propes.getProperty(key);
        if (value == null)
        {
            value = "";
        }
        return value;
    }
}
