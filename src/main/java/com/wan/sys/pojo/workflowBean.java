package com.wan.sys.pojo;

public class workflowBean {
	public String Id;
	public String startTime;
	public String endTime;
	public String userName;
	public String definitionKey;
	public String taskId;
	public String backtaskId;
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBacktaskId() {
		return backtaskId;
	}
	public void setBacktaskId(String backtaskId) {
		this.backtaskId = backtaskId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDefinitionKey() {
		return definitionKey;
	}
	public void setDefinitionKey(String definitionKey) {
		this.definitionKey = definitionKey;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}

}
