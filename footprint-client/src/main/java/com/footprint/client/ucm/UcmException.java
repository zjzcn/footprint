package com.footprint.client.ucm;

public class UcmException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UcmException() {
		super();
	}

	public UcmException(String msg) {
		super(msg);
	}

	public UcmException(Throwable cause) {
		super(cause);
	}

	public UcmException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
