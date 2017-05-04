package com.mystudy.auth.entity;

import java.io.Serializable;

import com.mystudy.auth.common.BaseEntity;


public class RoleFunctions extends BaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1869445886256489041L;
	private Long Id;
    private Long roleId;
    private Long functionId;
    private int status;

    public RoleFunctions() {
    }

    public RoleFunctions(Long id, Long roleId, Long functionId, int status) {
        Id = id;
        this.roleId = roleId;
        this.functionId = functionId;
        this.status = status;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
