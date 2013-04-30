package com.xiva.common;

import java.io.Serializable;
import java.util.Map;

public class BusinessRequest implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7497147515454003025L;

    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params;
    }
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
    
}
