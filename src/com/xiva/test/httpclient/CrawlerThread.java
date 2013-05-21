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

public class CrawlerThread implements Runnable
{

    private BlockingQueue<String> queue;

    private String contentStart;

    private String contentEnd;

    private String titleStart;

    private String titleEnd;

    private volatile boolean stopRequested;

    private Thread runThread;

    public CrawlerThread()
    {
        stopRequested = false;
    }

    public CrawlerThread(BlockingQueue<String> queue)
    {
        this.queue = queue;
        stopRequested = false;
    }

    @Override
    public void run()
    {
        runThread = Thread.currentThread();
        while (true && !stopRequested)
        {
            
            String url = null;
            try
            {
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
            List<String> titleList = CrawlerUtil.parseContent(htmlContent, this.titleStart, this.titleEnd);
            String title = "";
            if (titleList != null && titleList.size() > 0)
            {
                title = "<h1>" + titleList.get(0) + "</h1>";
            }

            for (String content : contentList)
            {
                int startIdx = content.indexOf("src");
                if (startIdx > 0)
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
                    }
                    String scrUrl = content.substring(srcIdx + 1, srcEndIdx);
                    String srcPath = CrawlerUtil.downLoadPicture(scrUrl);
                    content = content.replaceAll(scrUrl, srcPath);
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

    public void stopRequest()
    {
        stopRequested = true;
        if (runThread != null)
        {
            runThread.interrupt();
        }
    }
}
