package com.wan.sys.pojo;

import com.wan.sys.common.BaseEntity;


/**
 * 模型
 * 
 * @author  
 * 
 */
public class BirthdayBean extends BaseEntity implements java.io.Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchName;
	private String areaName;
	private String buildName;
	private String cellName;
	private String houseName;
	private String owerName;
	private String birthdayDay;
	private String countDay;
	private String phone;
	private String relationship;
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getOwerName() {
		return owerName;
	}
	public void setOwerName(String owerName) {
		this.owerName = owerName;
	}
	public String getBirthdayDay() {
		return birthdayDay;
	}
	public void setBirthdayDay(String birthdayDay) {
		this.birthdayDay = birthdayDay;
	}
	public String getCountDay() {
		return countDay;
	}
	public void setCountDay(String countDay) {
		this.countDay = countDay;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	
	
}
