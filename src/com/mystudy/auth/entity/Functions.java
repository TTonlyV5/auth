package com.mystudy.auth.entity;

import com.mystudy.auth.common.BaseEntity;

import java.io.Serializable;


public class Functions extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6499621070591180818L;
	private Long id;
    private String name;
    private Long parentId;
    private String url;
    private int serialNum;
    private int accordion;

    public Functions() {
    }

    public Functions(Long id, String name, Long parentId, String url, int serialNum, int accordion) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.serialNum = serialNum;
        this.accordion = accordion;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getAccordion() {
        return accordion;
    }

    public void setAccordion(int accordion) {
        this.accordion = accordion;
    }
}
