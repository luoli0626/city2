package com.wan.sys.pojo;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.Menu;
import com.wan.sys.entity.User;
import com.wan.sys.entity.organization.Organization;

/**
 * 角色模型
 * 
 * @author  
 * 
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class RoleBean extends BaseEntity implements java.io.Serializable {

	
	private String text;
	private BigDecimal seq;
	private String descript;
	private Integer taskNumber;
	private Double taskRate;
	private Double taskRate1;
	private String parentId;
	private String parentText;
	private String state;
	private String checked;
	private String resourcesId;
	private String resourcesText;
	private String flag="";
	private List<Menu> menus;//角色拥有菜单
	private List<User> users;//角色拥有用户
	private Organization organization;//角色所属机构
	private Double highestScore;
	public String getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

	public String getResourcesText() {
		return resourcesText;
	}

	public void setResourcesText(String resourcesText) {
		this.resourcesText = resourcesText;
	}



	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Integer getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Double getTaskRate() {
		return taskRate;
	}

	public void setTaskRate(Double taskRate) {
		this.taskRate = taskRate;
	}

	public Double getTaskRate1() {
		return taskRate1;
	}

	public void setTaskRate1(Double taskRate1) {
		this.taskRate1 = taskRate1;
	}

	public Double getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(Double highestScore) {
		this.highestScore = highestScore;
	}

}
