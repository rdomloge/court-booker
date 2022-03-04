package com.domloge.courtbooker;

public class CommitResult {

	private String message;
	private int code;
	private boolean success;
	
	public CommitResult(String message, int code, boolean success) {
		super();
		this.message = message;
		this.code = code;
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getCode() {
		return code;
	}

	public boolean isSuccess() {
		return success;
	}
}
