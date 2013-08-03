package com.xiva.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xiva.domain.base.BaseEntity;

@Entity
@Table(name = "iv_role")
public class IvRole extends BaseEntity
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6499647667331673997L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;
    
    @Column(name = "role_parent_id")
    private Integer rolePId;
    
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Integer roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleDesc()
    {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc)
    {
        this.roleDesc = roleDesc;
    }

    public Integer getRolePId()
    {
        return rolePId;
    }

    public void setRolePId(Integer rolePId)
    {
        this.rolePId = rolePId;
    }

}