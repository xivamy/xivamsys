package com.xiva.action.gather;

import java.io.IOException;

import com.xiva.action.common.BasicAction;
import com.xiva.test.httpclient.CrawlerUtil;

/**
 * 
 * 数据采集Action
 * 
 * @author xiva
 * @version [版本号, 2013-5-25]
 * @see [相关类/方法]
 * @since [产品、模块版本]
 */
public class CrawlerAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7994415375153241465L;

    private String globleUrl;

    private String listSlabel;

    private String listElabel;

    private String titleSlabel;

    private String titleElabel;

    private String articleSlabel;

    private String articleElabel;

    private String urlList;

    
    public void getURLHtmlContent() throws IOException
    {
        String content = CrawlerUtil.getFirstHtmlContent(globleUrl);
        
        response.setContentType("text/json");
        super.response.getWriter().write(content);
    }
    
    public String getGlobleUrl()
    {
        return globleUrl;
    }

    public void setGlobleUrl(String globleUrl)
    {
        this.globleUrl = globleUrl;
    }

    public String getListSlabel()
    {
        return listSlabel;
    }

    public void setListSlabel(String listSlabel)
    {
        this.listSlabel = listSlabel;
    }

    public String getListElabel()
    {
        return listElabel;
    }

    public void setListElabel(String listElabel)
    {
        this.listElabel = listElabel;
    }

    public String getTitleSlabel()
    {
        return titleSlabel;
    }

    public void setTitleSlabel(String titleSlabel)
    {
        this.titleSlabel = titleSlabel;
    }

    public String getTitleElabel()
    {
        return titleElabel;
    }

    public void setTitleElabel(String titleElabel)
    {
        this.titleElabel = titleElabel;
    }

    public String getArticleSlabel()
    {
        return articleSlabel;
    }

    public void setArticleSlabel(String articleSlabel)
    {
        this.articleSlabel = articleSlabel;
    }

    public String getArticleElabel()
    {
        return articleElabel;
    }

    public void setArticleElabel(String articleElabel)
    {
        this.articleElabel = articleElabel;
    }

    public String getUrlList()
    {
        return urlList;
    }

    public void setUrlList(String urlList)
    {
        this.urlList = urlList;
    }

}
