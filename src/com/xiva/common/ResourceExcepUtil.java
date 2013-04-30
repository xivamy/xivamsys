package com.xiva.common;

import java.io.IOException;
import java.util.Properties;

public class ResourceExcepUtil
{
    private static Properties exceProp;
    
    static
    {
        exceProp = new Properties();
        try
        {
            exceProp.load(ResourceExcepUtil.class.getClassLoader().getResourceAsStream("global.a.exception"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static String getDescByKey(Integer key)
    {
        return exceProp.getProperty(String.valueOf(key));
    }
}
