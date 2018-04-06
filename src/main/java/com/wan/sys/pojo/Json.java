package com.wan.sys.pojo;

/**
 * JSON模型
 * 
 * @author  
 * 
 */
@SuppressWarnings("serial")
public class Json implements java.io.Serializable {

	private boolean success = false;// 是否成功
	private String msg = "";// 提示信息
	private Object obj = null;// 其他信息
	private Object obj_other=null;
	
	public Object getObj_other() {
		return obj_other;
	}

	public void setObj_other(Object obj_other) {
		this.obj_other = obj_other;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
