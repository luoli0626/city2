package com.wan.sys.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import org.hibernate.annotations.Where;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.entity.organization.Organization;

/**
 * 
 * 文件名称： 角色实体
 * 内容摘要： 
 * 创建人： 唐君左
 * 创建日期： 2017-6-26
 * 版本号： v1.0.0
 * 公  司：金科物业服务有限公司
 * 版权所有： (C)2016-2017     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 *
 */
@Entity
@Table(name = "sys_role")
@Where(clause="recordStatus='"+GlobalConstant.FLAG_Y+"'")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Role extends BaseEntity {

	/***/
	private static final long serialVersionUID = 3761288219059616551L;

	
	private String text;
	private BigDecimal seq;
	private Integer taskNumber;
	private Double taskRate;
	private Double taskRate1;
	private Double highestScore;//最高得分
	private String descript;
	private List<Menu> menus;//角色拥有菜单
	private List<User> users;//角色拥有用户
	private Organization organization;//角色所属组织
	// Constructors

	/** default constructor */
	public Role() {
	}
	// Property accessors
	@Column(length=100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}
	@Column(length=100)
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_to_menu", joinColumns = {@JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "symenu_id") })
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinTable(name = "sys_user_to_role", joinColumns = {@JoinColumn(name = "SYROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYUSER_ID") })
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	@OneToOne(cascade=CascadeType.REFRESH)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="organizationId")
	public Organization getOrganization() {
		return organization;
	}
	
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Column(name="TASK_NUMBER",length=20)
	public Integer getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}
	@Column(name="TASK_RATE",length=20)
	public Double getTaskRate() {
		return taskRate;
	}
	public void setTaskRate(Double taskRate) {
		this.taskRate = taskRate;
	}
	@Column(name="TASK_RATE1",length=20)
	public Double getTaskRate1() {
		return taskRate1;
	}
	public void setTaskRate1(Double taskRate1) {
		this.taskRate1 = taskRate1;
	}
	@Column(name="HIGHEST_SCORE",length=20)
	public Double getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(Double highestScore) {
		this.highestScore = highestScore;
	}
	
	
}