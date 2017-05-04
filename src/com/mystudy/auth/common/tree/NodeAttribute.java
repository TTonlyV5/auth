package com.mystudy.auth.common.tree;

import java.io.Serializable;


public class NodeAttribute implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1271326454201579202L;
	private Long id;
    private String url;

    public NodeAttribute(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
