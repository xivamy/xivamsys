package com.xiva.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity
@Table(name = "iv_function")
public class IvFunction extends BaseEntity
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3604365401843737187L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "func_id")
    private String funcId;

    @Column(name = "func_name")
    private String funcName;

    @Column(name = "func_desc")
    private String funcDesc;

    public String getFuncId()
    {
        return funcId;
    }

    public void setFuncId(String funcId)
    {
        this.funcId = funcId;
    }

    public String getFuncName()
    {
        return funcName;
    }

    public void setFuncName(String funcName)
    {
        this.funcName = funcName;
    }

    public String getFuncDesc()
    {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc)
    {
        this.funcDesc = funcDesc;
    }

}