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
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
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

}