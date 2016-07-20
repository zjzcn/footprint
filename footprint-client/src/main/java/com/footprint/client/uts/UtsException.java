package com.footprint.client.uts;

public class UtsException extends Exception {
	private static final long serialVersionUID = 1L;

	public UtsException() {
        super();
    }

    public UtsException(String message) {
        super(message);
    }

    public UtsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtsException(Throwable cause) {
        super(cause);
    }
}
