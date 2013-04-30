package com.xiva.exception;

import java.text.MessageFormat;

import com.xiva.common.ResourceExcepUtil;

public class IvMsgException extends CommonException
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3736339427858038330L;
    
    private String busMsg;
    
    public IvMsgException(Integer key)
    {
        String msg = ResourceExcepUtil.getDescByKey(key);
        this.setBusMsg(msg);
    }
    
    public IvMsgException(Integer key, Object ... arguments)
    {
        String msg = ResourceExcepUtil.getDescByKey(key);
        String msgfmt = MessageFormat.format(msg, arguments);
        this.setBusMsg(msgfmt);
    }

    public String getBusMsg()
    {
        return busMsg;
    }

    public void setBusMsg(String busMsg)
    {
        this.busMsg = busMsg;
    }
    
    
}
