package com.wan.sys.pojo;

import java.io.Serializable;

public class ResponseHead implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -127623688320708323L;
	private String errcode;
	private String errmsg;
	private Object data;
	//private int total;
	//private String sessionId;
	private String pageInfo;
	//public String  userId;
	
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
//	public int getTotal() {
//		return total;
//	}
//	public void setTotal(int total) {
//		this.total = total;
//	}
//	public String getSessionId() {
//		return sessionId;
//	}
//	public void setSessionId(String sessionId) {
//		this.sessionId = sessionId;
//	}
	public String getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}
//	
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
}
