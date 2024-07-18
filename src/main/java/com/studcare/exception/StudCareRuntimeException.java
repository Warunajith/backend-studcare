package com.studcare.exception;

public class StudCareRuntimeException extends RuntimeException {
	public static final int ERROR_CODE_DEFAULT = 0;
	private final int code;

	public StudCareRuntimeException(String message) {
		super(message);
		this.code = ERROR_CODE_DEFAULT;
	}

	public StudCareRuntimeException(int code, String message) {
		super(message);
		this.code = code;
	}

	public StudCareRuntimeException(Throwable cause) {
		super(cause);
		this.code = ERROR_CODE_DEFAULT;
	}

	public StudCareRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.code = ERROR_CODE_DEFAULT;
	}

	public int getCode() {
		return code;
	}
}

