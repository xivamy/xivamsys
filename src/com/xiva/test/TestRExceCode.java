package com.xiva.test;

import com.xiva.common.IvExceptionCode;
import com.xiva.exception.IvMsgException;

public class TestRExceCode
{

    public static void main(String[] args)
    {
        IvMsgException ivmsg = new IvMsgException(IvExceptionCode.OUT_DATE, 30);
        int i = 10;
        int j = 4;
        if (i > j)
        {
            System.out.println(ivmsg.getBusMsg());
        }
    }
}
