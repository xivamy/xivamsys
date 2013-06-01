package com.xiva.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;

import com.xiva.common.CommonConstant;
import com.xiva.common.IvExceptionCode;
import com.xiva.common.bo.IvResponse;
import com.xiva.exception.IvMsgException;

/**
 * 
 * HttpClient 服务类 主要负责发送http请求
 * 
 * @author xiva
 * @version [版本号, 2013-4-30]
 * @see [相关类/方法]
 * @since [产品、模块版本]
 */
public class HttpClientService
{

    private HttpClient httpClient;
    
    private boolean isForge;

    private HttpClientService()
    {
        httpClient = new DefaultHttpClient();

        
        ((DefaultHttpClient) httpClient).setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()
        {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context)
            {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1)
                {
                    keepAlive = 5000;// 会话保持时长
                }
                return keepAlive;
            }

        });
    }

    public static HttpClientService getInstance(boolean useHttps)
    {
        HttpClientService service = new HttpClientService();
        
        if (useHttps)
        {
            service.useHttpsRequest();
        }
        service.setTimeout();
        
        if ("true".equalsIgnoreCase(ParameterUtil.getPropByKey(CommonConstant.IS_FORGE_IP)))
        {
            service.isForge = true;
        }
        
        return service;
    }

    private void useHttpsRequest()
    {

    }

    private void setTimeout()
    {
        int timeout = 3000; // 单位毫秒
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
    }

    public void useProxy(String hostName, int port)
    {
        HttpHost proxy = new HttpHost(hostName, port, "http");
        httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
    }

    public IvResponse httpGetRequest(String url, Map<String, String> params)
    {
        IvResponse ivResponse = new IvResponse();

        URIBuilder builder = new URIBuilder();
        builder.setPath(url);
        URI uri = null;
        try
        {
            if (params != null && params.size() > 0)
            {
                for (Entry<String, String> entry : params.entrySet())
                {
                    builder.setParameter(entry.getKey(), entry.getValue());
                }
            }

            uri = builder.build();

        }
        catch (URISyntaxException e1)
        {
            System.out.println(url);
            throw new IvMsgException(IvExceptionCode.SYS_ERROR);
        }

        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpRes = null;
        try
        {
            if (isForge)
            {
                httpGet.addHeader("x-forwarded-for", this.genarateIp());
            }
            httpRes = httpClient.execute(httpGet);
            ivResponse.setHttpRes(httpRes);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            throw new IvMsgException(IvExceptionCode.SYS_ERROR);
        }

        return ivResponse;
    }

    public IvResponse httpPostRequest(String url, Map<String, String> params)
    {
        IvResponse ivResponse = new IvResponse();

        HttpPost httpPost = new HttpPost(url);
        if (params != null && params.size() > 0)
        {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : params.entrySet())
            {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            try
            {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httpPost.setEntity(entity);
            }
            catch (UnsupportedEncodingException e)
            {
                throw new IvMsgException(IvExceptionCode.SYS_ERROR);
            }
        }

        HttpResponse httpRes = null;
        try
        {
            if (isForge)
            {
                httpPost.addHeader("x-forwarded-for", this.genarateIp());
            }
            
            httpRes = httpClient.execute(httpPost);
            ivResponse.setHttpRes(httpRes);
        }
        catch (Exception e)
        {
            throw new IvMsgException(IvExceptionCode.SYS_ERROR);
        }

        return ivResponse;
    }

    public void setForge(boolean isForge)
    {
        this.isForge = isForge;
    }
    
    private String genarateIp()
    {
        String ipStr = "";
        
        for (int i=0; i<4; i++)
        {
            Random rand = new Random(System.nanoTime());
            int ipSeg = rand.nextInt(253)+1;
            ipStr = ipStr + String.valueOf(ipSeg) + ".";
        }
        
        ipStr = ipStr.substring(0, ipStr.length() - 1);
        return ipStr;
    }
    
}
