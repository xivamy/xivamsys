package com.xiva.test.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.xiva.common.bo.IvResponse;
import com.xiva.common.util.HttpClientService;

public class HttpClientMain
{

    private static int voteNum = 0;
    
    private static HttpClient httpClient = new DefaultHttpClient();

    public static void getMethodDemo() throws IOException
    {
        HttpGet httpget = new HttpGet("http://news.sina.com.cn/china/");
        HttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            String content = EntityUtils.toString(entity);
            System.out.println(content);
        }
    }

    public static void postMethodDemo() throws IOException
    {

    }
    
    public void baiduTest() throws IOException
    {
        HttpClientService service = HttpClientService.getInstance(false);
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", "%E6%9E%AD%E7%9A%87%E8%AE%BA%E6%88%98%E5%8E%9F%E5%A3%B0%E5%B8%A6+%E9%BB%84%E5%A6%83");
        IvResponse ivResponse = service.httpGetRequest("http://music.baidu.com/search", params);
        System.out.println(EntityUtils.toString(ivResponse.getEntity(), "utf-8"));
    }

    public static void main(String[] args) throws IOException
    {
        for(int i=0; i<800; i++)
        {
            new Thread(new Runnable(){
                @Override
                public void run()
                {
                    for(int i=0; i<20000; i++)
                    {
                        try
                        {
                            HttpClientService service = HttpClientService.getInstance(false);
                            IvResponse ivResponse = service.httpPostRequest("http://sports.sina.cn/?sa=t12d76863v18&&sid=101&vt=4&survey=12p12622s2", null);
                            System.out.println(voteNum + ":" + ivResponse.getResponseCode());
                        }
                        catch(Exception e)
                        {
                            System.out.println("Error");
                            continue;
                        }
                        voteNum++;
                    }
                    
                }
            }).start();
        }
        
//        System.out.println(EntityUtils.toString(ivResponse.getEntity(), "GBK"));
    }
}
