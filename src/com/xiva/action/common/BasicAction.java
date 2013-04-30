package com.xiva.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.xiva.common.CommonConstant;

/**
 * 
 * @author XIVA
 */
public class BasicAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected static final Log log = LogFactory.getFactory().getInstance(BasicAction.class);
    
    protected static final String FAILURE = "failure";
    
    protected HttpServletRequest request;
    
    protected HttpServletResponse response;
    
    protected int start;
    
    protected int limit;
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
        
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        response.setCharacterEncoding(CommonConstant.ENCODE_UTF_8);
        this.response = response;
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

}
