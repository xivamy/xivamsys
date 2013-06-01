package com.xiva.test.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.xiva.common.CommonConstant;
import com.xiva.common.util.ParameterUtil;

/**
 * 
 * 采集线程类
 * @author xiva
 * @version [版本号, 2013-5-22]
 * @see [相关类/方法]
 * @since [产品、模块版本]
 */
public class CrawlerThread implements Runnable
{

    private BlockingQueue<String> queue;

    private String contentStart;

    private String contentEnd;

    private String titleStart;

    private String titleEnd;

    public CrawlerThread()
    {
    }

    public CrawlerThread(BlockingQueue<String> queue)
    {
        this.queue = queue;
    }

    @Override
    public void run()
    {
        while (true)
        {
            
            String url = null;
            try
            {
                // 3秒内没有数据，则跳出循环
                url = queue.poll(3, TimeUnit.SECONDS);
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
            
            if (StringUtils.isEmpty(url))
            {
                break;
            }
            String htmlContent = null;
            try
            {
                htmlContent = CrawlerUtil.getFirstHtmlContent(url);
            }
            catch(Exception e)
            {
                continue;
            }

            List<String> contentList = CrawlerUtil.parseContent(htmlContent, this.contentStart, this.contentEnd);
            System.out.println(htmlContent);
            List<String> titleList = CrawlerUtil.parseContent(htmlContent, this.titleStart, this.titleEnd);
            String title = "";
            if (titleList != null && titleList.size() > 0)
            {
                title = "<h1>" + titleList.get(0) + "</h1>";
                
                title = title.split("|")[0];
            }

            for (String content : contentList)
            {
                int startIdx = content.indexOf("src");
                while (startIdx > 0)
                {
                    int srcIdx = content.indexOf("\"", startIdx);
                    int srcEndIdx = 0;
                    if (srcIdx < 0)
                    {
                        srcIdx = content.indexOf("\'", startIdx);
                        srcEndIdx = content.indexOf("\'", srcIdx + 1);
                    }
                    else
                    {
                        srcEndIdx = content.indexOf("\"", srcIdx + 1);
                        startIdx = content.indexOf("src", srcEndIdx);
                    }
                    
                    if (srcEndIdx < srcIdx)
                    {
                        break;
                    }
                    
                    String scrUrl = content.substring(srcIdx + 1, srcEndIdx);
                    if (scrUrl.endsWith("jpg"))
                    {
                        String srcPath = CrawlerUtil.downLoadSCR(scrUrl);
                        content = content.replaceAll(scrUrl, srcPath);
                    }
                    
                }

                String path = ParameterUtil.getPropByKey(CommonConstant.CRAWLER_FILE_PATH);
                String fileName = UUID.randomUUID().toString();
                String filePath = path + "/" + fileName + ".html";
                File file = new File(filePath);
                try
                {
                    FileUtils.writeStringToFile(file, title + content);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setQueue(BlockingQueue<String> queue)
    {
        this.queue = queue;
    }

    public void setContentStart(String contentStart)
    {
        this.contentStart = contentStart;
    }

    public void setContentEnd(String contentEnd)
    {
        this.contentEnd = contentEnd;
    }

    public void setTitleStart(String titleStart)
    {
        this.titleStart = titleStart;
    }

    public void setTitleEnd(String titleEnd)
    {
        this.titleEnd = titleEnd;
    }

}
