package com.xiva.service.bo;

/**
 * 分页对象
 * @author xiva
 *
 */
public class Page {
    
    private Integer start;
    
    private Integer pageSize;
    
    private Integer pageIndex;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    
    
}
