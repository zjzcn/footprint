package com.footprint.server.platform.uts.core.invocation;

import java.io.Serializable;
import java.util.Map;

/**
 * 调用类型基类
 */

public abstract class Invocation implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /**
     * http方式调用的url
     */
    private String invocationUrl;

    public String getInvocationUrl() {
        return invocationUrl;
    }

    public void setInvocationUrl(String invocationUrl) {
        this.invocationUrl = invocationUrl;
    }

    public abstract Map<String, Object> toMap();
}
