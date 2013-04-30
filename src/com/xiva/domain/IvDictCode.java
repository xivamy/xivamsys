package com.xiva.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity
@Table(name = "iv_dict_code")
@NamedQueries(value = {@NamedQuery(name = "IvDictCode.queryAll", query = "from IvDictCode")})
public class IvDictCode extends BaseEntity
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String statuskey;

    private Integer parentId;

    private String statusdes;

    private String languge;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getStatuskey()
    {
        return statuskey;
    }

    public void setStatuskey(String statuskey)
    {
        this.statuskey = statuskey;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public String getStatusdes()
    {
        return statusdes;
    }

    public void setStatusdes(String statusdes)
    {
        this.statusdes = statusdes;
    }

    public String getLanguge()
    {
        return languge;
    }

    public void setLanguge(String languge)
    {
        this.languge = languge;
    }

}
