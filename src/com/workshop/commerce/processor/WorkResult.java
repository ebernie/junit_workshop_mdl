package com.workshop.commerce.processor;

public class WorkResult {
	
	enum ErrCode {
		UNKNOWN_ERROR, NO_ERROR;
	}

	private boolean successful;

	private String errMsg;

	private ErrCode errCode;

	public WorkResult(boolean successful, String errMsg, ErrCode errCode) {
		super();
		this.successful = successful;
		this.errMsg = errMsg;
		this.errCode = errCode;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public ErrCode getErrCode() {
		return errCode;
	}

	public void setErrCode(ErrCode errCode) {
		this.errCode = errCode;
	}

	@Override
	public String toString() {
		return "WorkResult [successful=" + successful + ", errMsg=" + errMsg
				+ ", errCode=" + errCode + "]";
	}

}
