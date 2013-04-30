package com.xiva.action.common;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import com.xiva.domain.IvDictCode;
import com.xiva.service.util.DictCodeUtil;

public class LoadDataAction extends BasicAction
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3928738455652155826L;

    private String statuskey;

    public void loadDictCode() throws IOException
    {
        List<IvDictCode> dictList = DictCodeUtil.getUserStatusDict(statuskey);
        JSONArray jsonArray = JSONArray.fromObject(dictList);
        super.response.getWriter().write(jsonArray.toString());
    }

    public String getStatuskey()
    {
        return statuskey;
    }

    public void setStatuskey(String statuskey)
    {
        this.statuskey = statuskey;
    }

}
