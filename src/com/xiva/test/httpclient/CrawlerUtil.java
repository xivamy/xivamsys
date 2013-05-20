package com.xiva.test.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.xiva.common.CommonConstant;
import com.xiva.common.bo.IvResponse;
import com.xiva.common.util.HttpClientService;
import com.xiva.common.util.ParameterUtil;

public class CrawlerUtil
{
    public static String getFirstHtmlContent(String url)
    {
        HttpClientService service = HttpClientService.getInstance(false);

        IvResponse ivResponse = service.httpGetRequest(url, null);

        String content = null;
        try
        {
            content = EntityUtils.toString(ivResponse.getEntity(), "GBK");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content;
    }
    
    
    public static List<String> parseContent(String content, String startParse, String endParse)
    {
        List<String> segments = new ArrayList<String>(32);
        int startIdx = content.indexOf(startParse);

        while (startIdx > 0)
        {
            int endIdx = content.indexOf(endParse, startIdx);
            String seg = content.substring(startIdx + startParse.length(), endIdx);
            segments.add(seg);
            startIdx = content.indexOf(startParse, endIdx);
        }

        return segments;
    }
    
    public static String downLoadPicture(String url)
    {
        HttpClientService service = HttpClientService.getInstance(false);
        IvResponse ivResponse = service.httpGetRequest(url, null);
        int index = url.lastIndexOf(".");
        String fileType = url.substring(index);
        String fileName = UUID.randomUUID().toString();
        String path = ParameterUtil.getPropByKey(CommonConstant.CRAWLER_FILE_PATH);
        String fullPath = path + "/img/" + fileName + fileType;
        
        File storeFile = new File(fullPath);
        FileOutputStream output = null;
        try
        {
            output = new FileOutputStream(storeFile);
            InputStream is = ivResponse.getEntity().getContent();
            byte[] bytes = new byte[1024];
            while (is.read(bytes) > 0)
            {
                output.write(bytes);
            }
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return fullPath;
    }
    
    public static List<String> parseSegments(List<String> segments)
    {
        List<String> urlList = new ArrayList<String>(128);
        String beginRegex = "href";
        String endRegex = ">";
        
        for (String content:segments)
        {
            int startIdx = content.indexOf(beginRegex);
            while(startIdx > 0)
            {
                int endIdx = content.indexOf(endRegex, startIdx);
                
                String aLabel = content.substring(startIdx + "href".length(), endIdx);
                int lblStart = aLabel.indexOf("\"");
                int lblEnd = 0;
                if (lblStart < 0)
                {
                    lblStart = aLabel.indexOf("\'");
                    lblEnd = aLabel.indexOf("\'", lblStart+1);
                }
                else
                {
                    lblEnd = aLabel.indexOf("\"", lblStart+1);
                }
                
                if(lblEnd > lblStart)
                {
                    String urlStr = aLabel.substring(lblStart + 1, lblEnd);
                    urlList.add(urlStr); 
                }
                
                startIdx = content.indexOf(beginRegex, endIdx);
            }
        }
        
        return urlList;
    }
    
    public static void main(String[] args)
    {
        String url = "http://i2.sinaimg.cn/dy/c/2013-05-20/U6074P1T1D27168006F21DT20130520014311.jpg";
        downLoadPicture(url);
    }
}
