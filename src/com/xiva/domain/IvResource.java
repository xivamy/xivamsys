package com.xiva.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity
@Table(name = "iv_resource")
public class IvResource extends BaseEntity
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 931364381933383669L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Resource_Id")
    private Integer resourceId;

    @Column(name = "Resource_Type")
    private Integer resourceType;

    @Column(name = "resource_name")
    private String resourceName;
    
    @Column(name = "resource_code")
    private String resourceCode;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "resource_desc")
    private String resourceDesc;

    @Column(name = "resource_pid")
    private Integer resourcePid;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    public Integer getResourceId()
    {
        return resourceId;
    }

    public void setResourceId(Integer resourceId)
    {
        this.resourceId = resourceId;
    }

    public Integer getResourceType()
    {
        return resourceType;
    }

    public void setResourceType(Integer resourceType)
    {
        this.resourceType = resourceType;
    }

    public String getResourceName()
    {
        return resourceName;
    }

    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }

    public String getResourceCode()
    {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode)
    {
        this.resourceCode = resourceCode;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceDesc()
    {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc)
    {
        this.resourceDesc = resourceDesc;
    }

    public Integer getResourcePid()
    {
        return resourcePid;
    }

    public void setResourcePid(Integer resourcePid)
    {
        this.resourcePid = resourcePid;
    }

    public String getReserve1()
    {
        return reserve1;
    }

    public void setReserve1(String reserve1)
    {
        this.reserve1 = reserve1;
    }

    public String getReserve2()
    {
        return reserve2;
    }

    public void setReserve2(String reserve2)
    {
        this.reserve2 = reserve2;
    }

    public String getReserve3()
    {
        return reserve3;
    }

    public void setReserve3(String reserve3)
    {
        this.reserve3 = reserve3;
    }
    
}