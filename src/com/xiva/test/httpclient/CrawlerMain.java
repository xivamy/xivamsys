package com.xiva.test.httpclient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
        
        int threadNum = urlList.size() / 5;
        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        int crawlerNum = queue.size();
        System.out.println();
        for (int i=0; i<threadNum; i++)
        {
            CrawlerThread thread = new CrawlerThread();
            thread.setQueue(queue);
            thread.setContentStart("<!-- 正文内容 begin -->");
            thread.setContentEnd("<!-- publish_helper_end -->");
            thread.setTitleStart("<title>");
            thread.setTitleEnd("</title>");
            
            pool.execute(thread);
        }
        System.out.println("=========");
        
        while(true)
        {
            int restNum = queue.size();
            
            BigDecimal restPercent = new BigDecimal((double)restNum/(double)crawlerNum);
            restPercent.setScale(2, BigDecimal.ROUND_HALF_UP);
            
            System.out.println("剩余百分比:" + String.format("%.2f", restPercent.doubleValue()));
            if ( restNum == 0)
            {
                pool.shutdown();
                while(!pool.isTerminated())
                {
                    System.out.println("====Wait pool Terminated=====");
                    try
                    {
                        TimeUnit.SECONDS.sleep(6);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                
                System.out.println("====Shutdown=====");
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
        System.out.println("====End=====");
    }

    public static void main(String[] args) throws IOException
    {
        String url = "http://news.sina.com.cn/china/";
        startGrab(url);
    }
}
