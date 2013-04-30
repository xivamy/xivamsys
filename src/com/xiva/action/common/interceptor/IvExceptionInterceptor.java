package com.xiva.action.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONInterceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.xiva.exception.IvMsgException;

public class IvExceptionInterceptor extends JSONInterceptor  
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5090461961591490562L;

    @Override
    public String intercept(ActionInvocation actioninvocation) throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        
        String result = "exception";
        try
        {
            result = actioninvocation.invoke();
        }
        catch(Exception e)
        {
            String busMsg = "";
            if (e instanceof IvMsgException)
            {
                IvMsgException msge = (IvMsgException)e;
                busMsg = msge.getBusMsg();
            }
            else
            {
                busMsg = e.getMessage();
            }
            
            request.setAttribute("exceptionMsg", busMsg);
            throw e;
        }
        
        return result;
    }

}
