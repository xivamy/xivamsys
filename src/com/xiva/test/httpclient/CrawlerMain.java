package com.xiva.test.httpclient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

public class CrawlerMain
{

    public static String url = "http://news.sina.com.cn/china/";

    public static void startGrab(String url)
    {
        String content = CrawlerUtil.getFirstHtmlContent(url);
        if (StringUtils.isEmpty(content))
        {
            return;
        }
        String startParse = "<ul class=\"list01 fblue\">";
        String endParse = "</ul>";
        List<String> segList = CrawlerUtil.parseContent(content, startParse, endParse);
        
        List<String> urlList = CrawlerUtil.parseSegments(segList);
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(128);
        
        for (String grabUrl:urlList)
        {
            queue.add(grabUrl);
            if (queue.size() > 20)
            {
                break;
            }
        }
        
        CrawlerThread thread = new CrawlerThread();
        thread.setQueue(queue);
        thread.setContentStart("<!-- 正文内容 begin -->");
        thread.setContentEnd("<!-- publish_helper_end -->");
        thread.setTitleStart("<title>");
        thread.setTitleEnd("</title>");
        
        int threadNum = urlList.size() / 5;
        Thread[] threads = new Thread[threadNum];
        
        System.out.println(queue.size());
        for (int i=0; i<threadNum; i++)
        {
            Thread thd = new Thread(thread);
            threads[i] = thd;
            thd.start();
        }
        System.out.println("=========");
        
        while(true)
        {
            if (queue.size() == 0)
            {
                thread.stopRequest();
                break;
            }
            else
            {
                try
                {
                    TimeUnit.SECONDS.sleep(5);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
    }

    public static void main(String[] args) throws IOException
    {
        String url = "http://news.sina.com.cn/china/";
        startGrab(url);
    }
}
