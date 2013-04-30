package com.xiva.domain.base;


import java.io.Serializable;

import javax.persistence.Transient;

/**
 * @author XIVA
 *
 */
public abstract class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    //used for createdBy updatedBy
    @Transient
    private String currentOperator;
    
    public String getCurrentOperator()
    {
        return currentOperator;
    }

    public void setCurrentOperator(String currentOperator)
    {
        this.currentOperator = currentOperator;
    }
}
