package com.xiva.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author XIVA
 * equal，notEqual， gt， ge，lt， le，between，like
 */
public class SearchContent {

    public final static String WHERE_IN = "IN";
    
    public final static String WHERE_NOT_IN = "NOT IN";
    
    public final static String WHERE_LIKE = "LIKE";
    
    public final static String WHERE_EQUAL = "=";
    
    public final static String WHERE_DIFFER = "<>";
    
    public final static String WHERE_MORE = ">";
    
    public final static String WHERE_LESS = "<";
    
    public final static String WHERE_NOT_LESS = ">=";
    
    public final static String WHERE_NOT_MORE = "<=";
    
    public final static String SORT_DESC = "1";
    
    public final static String SORT_ASC = "0";
    
    public final static String PARAM_PREFIX = "p";
    
    public final static String COND_JOIN_OR = "OR";
    
    public final static String COND_JOIN_AND = "AND";
    
    public final static int PAGE_SIZE = 20;
    
    private String orderBy;
    
    private String whereCondition;
    
    private List<SearchItem> queryParams;
    
    private int pageSize = PAGE_SIZE;
    
    private int pageStart = 0;
    
    private boolean userPage = false;

    public enum QueryWhereEnum {  
        IN, NOT_IN, LIKE, EQUAL, DIFFER, MORE, LESS, NOT_LESS, NOT_MORE, BETWEEN_AND
    }
    
    public static String getWhereValue(QueryWhereEnum whereEnum)
    {
        String whereValue = "";
        switch (whereEnum) {
        case IN:
            whereValue = WHERE_IN;
            break;
        case NOT_IN:
            whereValue = WHERE_NOT_IN;
            break;
        case LIKE:
            whereValue = WHERE_LIKE;
            break;
        case EQUAL:
            whereValue = WHERE_EQUAL;
            break;
        case DIFFER:
            whereValue = WHERE_DIFFER;
            break;
         default:whereValue = WHERE_EQUAL;
        }
        return whereValue;
    }
    
    public enum QueryJoinEnum {  
        JOIN_OR, JOIN_AND
    }
    
    public static String getJoinValue(QueryJoinEnum joinEnum)
    {
        String whereValue = "";
        switch (joinEnum) {
        case JOIN_OR:
            whereValue = COND_JOIN_OR;
            break;
        case JOIN_AND:
            whereValue = COND_JOIN_AND;
            break;
         default:whereValue = COND_JOIN_AND;
        }
        return whereValue;
    }
    
    public SearchContent() {
        queryParams = new ArrayList<SearchItem>();
    }
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }
    
    public List<SearchItem> getQueryParams() {
        return queryParams;
    }
    public void setQueryParams(List<SearchItem> queryParams) {
        this.queryParams = queryParams;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageStart() {
        return pageStart;
    }
    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }
    
    public boolean isUserPage() {
        return userPage;
    }
    public void setUserPage(boolean userPage) {
        this.userPage = userPage;
    }
    
    public void addCondition(SearchItem searchItem) {
        queryParams.add(searchItem);
    }
    
}
