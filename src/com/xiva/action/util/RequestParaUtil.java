package com.xiva.action.util;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

import com.xiva.common.util.DateUtil;

public class RequestParaUtil
{
    public static void getObjParameter(HttpServletRequest request, Object obj)
    {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            String name = field.getName();
            Object value = request.getParameter(name);
            if (name.equalsIgnoreCase("serialVersionUID"))
            {
                continue;
            }
            if (value != null)
            {
                System.out.println(field.getGenericType());
                if (ComUtilConstant.TYPE_STRING.equalsIgnoreCase(field.getType().getName()))
                {
                    try
                    {
                        PropertyUtils.setProperty(obj, name, value.toString());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if (ComUtilConstant.TYPE_INTEGER.equalsIgnoreCase(field.getType().getName()))
                {
                    try
                    {
                        PropertyUtils.setProperty(obj, name, Integer.valueOf(value.toString()));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if (ComUtilConstant.TYPE_TIMESTAMP.equalsIgnoreCase(field.getType().getName()))
                {
                    try
                    {
                        PropertyUtils.setProperty(obj, name, DateUtil.parseToSqlTimestamp(value.toString()));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
            else
            {
                try
                {
                    PropertyUtils.setProperty(obj, name, null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
    }
}
