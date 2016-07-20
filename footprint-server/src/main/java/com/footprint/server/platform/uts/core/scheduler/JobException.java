package com.footprint.server.platform.uts.core.scheduler;

public class JobException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public JobException() {
        super();
    }

    public JobException(String msg) {
        super(msg);
    }

    public JobException(Throwable cause) {
        super(cause);
    }

    public JobException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
