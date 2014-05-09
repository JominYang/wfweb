package com.wfcsu.wfweb.vo;

public class JsonVo {
	private boolean success = false;
	private String msg = "";
	private Object obj = null;
	public final boolean isSuccess() {
		return success;
	}
	public final void setSuccess(boolean success) {
		this.success = success;
	}
	public final String getMsg() {
		return msg;
	}
	public final void setMsg(String msg) {
		this.msg = msg;
	}
	public final Object getObj() {
		return obj;
	}
	public final void setObj(Object obj) {
		this.obj = obj;
	}
	
}
