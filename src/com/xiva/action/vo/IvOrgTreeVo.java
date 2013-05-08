package com.xiva.action.vo;

import java.util.List;

public class IvOrgTreeVo
{
    private int id;

    private String text;

//    private String iconCls;
//
//    private boolean checked;
//
    private String state;

    private List<IvOrgTreeVo> children;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public List<IvOrgTreeVo> getChildren()
    {
        return children;
    }

    public void setChildren(List<IvOrgTreeVo> children)
    {
        this.children = children;
    }


}
