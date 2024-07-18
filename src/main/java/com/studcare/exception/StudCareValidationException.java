package com.studcare.exception;

public class StudCareValidationException extends RuntimeException{
	public static final int ERROR_CODE_DEFAULT = 0;
	public static final int ERROR_CODE_INVALID_PROCESS = 1;
	private final int code;

	public StudCareValidationException(String message) {
		super(message);
		this.code = ERROR_CODE_DEFAULT;
	}

	public StudCareValidationException(int code, String message) {
		super(message);
		this.code = code;
	}

	public StudCareValidationException(Throwable cause) {
		super(cause);
		this.code = ERROR_CODE_DEFAULT;
	}

	public StudCareValidationException(String message, Throwable cause) {
		super(message, cause);
		this.code = ERROR_CODE_DEFAULT;
	}

	public int getCode() {
		return code;
	}
}
