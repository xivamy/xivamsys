package com.xiva.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BusinessResponse implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5692867747423968336L;

    private boolean hasError = false;

    private Map<String, Object> attrs = new HashMap<String, Object>();

    public Object getAttribute(String key) {
        return this.attrs.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attrs.put(key, value);
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

}
