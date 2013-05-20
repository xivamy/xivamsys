package com.xiva.common.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * 初始化系统配置
 * @author xiva
 * @version [版本号, 2013-5-20]
 * @see [相关类/方法]
 * @since [产品、模块版本]
 */
public class InitSystemServlet  extends GenericServlet
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 887499507367001418L;

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
    {
        // 初始化配置文件夹
        
    }

}
