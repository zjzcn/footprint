package com.footprint.common.utils;

public class UtilException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UtilException() {
		super();
	}

	public UtilException(String msg) {
		super(msg);
	}

	public UtilException(Throwable cause) {
		super(cause);
	}

	public UtilException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
