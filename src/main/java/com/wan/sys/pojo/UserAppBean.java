package com.wan.sys.pojo;

import java.util.ArrayList;
import java.util.List;
/**
 * app用户信息接口实体
 * 
 * @author  tanxinwang
 * 
 */
public class UserAppBean {
	private String NickName;
	private String power;//0:项目管理人员，1：项目人员,2：项目经理
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public UserAppBean(){
		this.power="1";//初始化为项目人员
	}
	private List<String> roles=new ArrayList<String>();
	private List<String> ors=new ArrayList<String>();
	public String return_String(String p){
		return p==null ? "": p;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = return_String(nickName);
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getOrs() {
		return ors;
	}
	public void setOrs(List<String> ors) {
		this.ors = ors;
	}
	
}
