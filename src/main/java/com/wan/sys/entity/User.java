
package com.wan.sys.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.common.GlobalConstant;
import com.wan.sys.entity.organization.Organization;

/**
 * 
 * 文件名称： 用户表实体
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
@Table(name="sys_user2")
@Where(clause="recordStatus='"+GlobalConstant.FLAG_Y+"'")
public class User extends BaseEntity{
	/***/
	private static final long serialVersionUID = 8453704950404240438L;
	
	private String loginAcct;
	private String password;
	
	private String nickName;//姓名
	private String sex;//性别编码
	private Date birthday; //出生日期
	

	private String officePhone;
	private String mobilePhone;
	private String email;
	private String fax;
	private String qq;
	private String type;
	private List<Role> roles;//用户拥有的角色
	private List<Organization> organization;//所属机构
	private String isFirstLog;//是否第一次登陆 1或空表示第一次登陆
	private String openId;//openID
	private String token;//登陆token
	private Long expireTime;//登陆时长
	private String position;//职位
	private String unionId;
	private String ifManage;
	private List<String> deptId;//现阶段的部门id
	
	@Transient
	public List<String> getDeptId() {
		return deptId;
	}

	public void setDeptId(List<String> deptId) {
		this.deptId = deptId;
	}

	@Column(name="union_id")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	
	

	@Column(name="position")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name="token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Column(name="expire_time")
	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	
	
	@Column(length=32)
	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	@Column(length=32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(length=20)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Column(length=10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Column(length=20)
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	@Column(length=20)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	@Column(length=40)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=20)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(length=13)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	//@JoinColumn(name="orgId")
	@JoinTable(name = "sys_user_project", joinColumns = {@JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "PROJECT_ID") })
	public List<Organization> getOrganization() {
		return organization;
	}
	
	public void setOrganization(List<Organization> organization) {
		this.organization = organization;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinTable(name = "sys_user_to_role", joinColumns = {@JoinColumn(name = "SYUSER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYROLE_ID") })
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@Column(length=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Column(name="IS_FIRSTLOG")
	public String getIsFirstLog() {
		return isFirstLog;
	}

	public void setIsFirstLog(String isFirstLog) {
		this.isFirstLog = isFirstLog;
	}
	
	@Column(name="OPEN_ID")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name= "if_manage")
	public String getIfManage() {
		return ifManage;
	}

	public void setIfManage(String ifManage) {
		this.ifManage = ifManage;
	}

	
	
	
}