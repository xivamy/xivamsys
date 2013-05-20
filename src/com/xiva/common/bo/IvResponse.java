package com.xiva.common.bo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class IvResponse
{
    private String contentType;

    private String responseCode;

    private Object httpContent;
    
    private HttpEntity entity;
    
    private HttpResponse httpRes;

    public String getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }

    public Object getHttpContent()
    {
        return httpContent;
    }

    public void setHttpContent(Object httpContent)
    {
        this.httpContent = httpContent;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public HttpEntity getEntity()
    {
        return entity;
    }

    public void setEntity(HttpEntity entity)
    {
        this.entity = entity;
    }

    public HttpResponse getHttpRes()
    {
        return httpRes;
    }

    public void setHttpRes(HttpResponse httpRes)
    {
        this.httpRes = httpRes;
        HttpEntity entity = httpRes.getEntity();
        String contentType = entity.getContentType().getValue();
        this.setEntity(entity);
        this.setContentType(contentType);
    }
    
}
