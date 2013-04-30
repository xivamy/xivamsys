package com.xiva.common.util;

import com.xiva.common.util.SearchContent.QueryJoinEnum;
import com.xiva.common.util.SearchContent.QueryWhereEnum;

/**
 * 
 * @author xiva
 *
 */
public class SearchItem {
    
    private String attributeName;
    
    private Object value;
    
    private QueryWhereEnum queryWhere;
    
    private QueryJoinEnum queryJoinEnum;
    
    public SearchItem()
    {
        this.queryJoinEnum = QueryJoinEnum.JOIN_AND;
    }
    
    public SearchItem(String attributeName, Object value)
    {
        this.attributeName = attributeName;
        this.value = value;
    }
    
    public SearchItem(String attributeName, Object value,
            QueryJoinEnum queryJoinEnum) {
        super();
        this.attributeName = attributeName;
        this.value = value;
        this.queryJoinEnum = queryJoinEnum;
    }

    public SearchItem(String attributeName, Object value,
            QueryWhereEnum queryWhere) {
        super();
        this.attributeName = attributeName;
        this.value = value;
        this.queryWhere = queryWhere;
    }

    public SearchItem(String attributeName, Object value,
            QueryWhereEnum queryWhere, QueryJoinEnum queryJoinEnum) {
        super();
        this.attributeName = attributeName;
        this.value = value;
        this.queryWhere = queryWhere;
        this.queryJoinEnum = queryJoinEnum;
    }

    public String getAttributeName() {
        return attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    
    public QueryWhereEnum getQueryWhere() {
        return queryWhere;
    }
    public void setQueryWhere(QueryWhereEnum queryWhere) {
        this.queryWhere = queryWhere;
    }
    
    public QueryJoinEnum getQueryJoinEnum() {
        return queryJoinEnum;
    }
    public void setQueryJoinEnum(QueryJoinEnum queryJoinEnum) {
        this.queryJoinEnum = queryJoinEnum;
    }
    
    
}
