package com.xiva.common.util;

import java.io.IOException;
import java.util.Properties;

public class ParameterUtil {
    

    private static Properties propes  = new Properties();
    
    /**
     * 上传文件路径
     */
    public static String UPLOAD_FILEPATH = "uploadFilePath";
    
    /**
     * 日志记录精确度
     */
    public final static String LOG_TIME_ACCURACY = "logTimeAccuracy";
    
    static
    {
        try {
            propes.load(ParameterUtil.class.getClassLoader().getResourceAsStream("sys.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private ParameterUtil()
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
