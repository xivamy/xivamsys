package com.xiva.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity
@Table(name = "org")
public class Org extends BaseEntity implements Comparable<Org>
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7966114720385101920L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sysId;

    @Column(name = "orgname")
    private String orgName;

    @Column(name = "orgcode")
    private String orgCode;

    @Column(name = "unicode")
    private String unicode;

    @Column(name = "org_type")
    private Integer orgType;

    @Column(name = "parentid")
    private Integer parentId;

    private Integer stauts;

    private Integer area;

    @Column(name = "setup_date")
    private Timestamp setUpDate;

    @Column(name = "invalid_date")
    private Timestamp invalidDate;

    @Column(name = "createuser")
    private Integer createUser;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "updateuser")
    private Integer updateUser;

    @Column(name = "update_time")
    private Timestamp updateTime;

    public Integer getSysId()
    {
        return sysId;
    }

    public void setSysId(Integer sysId)
    {
        this.sysId = sysId;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getUnicode()
    {
        return unicode;
    }

    public void setUnicode(String unicode)
    {
        this.unicode = unicode;
    }

    public Integer getOrgType()
    {
        return orgType;
    }

    public void setOrgType(Integer orgType)
    {
        this.orgType = orgType;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public Integer getStauts()
    {
        return stauts;
    }

    public void setStauts(Integer stauts)
    {
        this.stauts = stauts;
    }

    public Integer getArea()
    {
        return area;
    }

    public void setArea(Integer area)
    {
        this.area = area;
    }

    public Timestamp getSetUpDate()
    {
        return setUpDate;
    }

    public void setSetUpDate(Timestamp setUpDate)
    {
        this.setUpDate = setUpDate;
    }

    public Timestamp getInvalidDate()
    {
        return invalidDate;
    }

    public void setInvalidDate(Timestamp invalidDate)
    {
        this.invalidDate = invalidDate;
    }

    public Integer getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(Integer createUser)
    {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public Integer getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser)
    {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public int compareTo(Org o)
    {

        if (null == o)
        {
            return 1;
        }
        else if (this.sysId.intValue() > o.getSysId().intValue())
        {
            return 1;
        }
        else if (this.sysId.intValue() < o.getSysId().intValue())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public String toString()
    {
        return "sysId:" + sysId + ",orgName:" + orgName;
    }

}
