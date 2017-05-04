package com.mystudy.auth.entity;

import com.mystudy.auth.common.BaseEntity;

import java.io.Serializable;


public class Role extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 646905746787206654L;

	public Role() {
    }

    private Long id;
    private String name;
    private String functionsIds;

    public String getFunctionsIds() {
		return functionsIds;
	}

	public void setFunctionsIds(String functionsIds) {
		this.functionsIds = functionsIds;
	}

	public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
