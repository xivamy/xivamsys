package com.xiva.common;

/**
 * 
 * 异常码定义类
 * 100a00001
 * a代表业务关联性，后五位为序列号
 * 1为系统相关业务
 * 2为组织相关业务
 * @author xiva
 */
public interface IvExceptionCode
{
    Integer SYS_ERROR = 100100001;
    
    Integer RECORD_NOT_EXSIT = 100100002;
    
    Integer OUT_DATE = 100100003;
    
    Integer SELECT_NO_RECORD = 100100004;
    
    Integer UPDATE_DB_FAILURE = 100100006;
    
    Integer ORG_CONTAIN_USER = 100200001;
    
    Integer ORG_CONTAIN_CHILD = 100200002;
}
