package com.xiva.domain.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author XIVA
 * 
 */
@MappedSuperclass
public abstract class NeedLogEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Column(name = "updated_by")
    private String updatedBy;
    
    @Column(name = "created_by")
    private String createdBy;
    
    private Timestamp updated;
    
    private Timestamp created;
    
    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdated()
    {
        return updated;
    }

    public void setUpdated(Timestamp updated)
    {
        this.updated = updated;
    }

    public Timestamp getCreated()
    {
        return created;
    }

    public void setCreated(Timestamp created)
    {
        this.created = created;
    }
}
