package com.wan.sys.entity.organization;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wan.sys.entity.User;


/**
 * 
 * 文件名称： 机构信息实体映射
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
@Table(name = "sys_organization")
public class Organization implements Serializable {
	/***/
	private static final long serialVersionUID = 4498268582277434287L;
	// Fields
	
	
	private String id;//
	private Organization parentOrgan;
	private String orgName;
	private String orgCode;
	private String orgDesc;
	private String legalRep;
	private String phone;
	private String ownShip;
	private Long regFund;
	private String licenseNo;
	private Long staffNum;
	private String isLeaf;
	private Long nodeLevel;
	private String status;
	private Long operatorId;
	private Date opTime;
	private String orgIndexId;
	private Long sortNumber;
	private String orgMark;// 机构标识
	private Integer childsCount;//子组织个数 用于判断是否有子节点
	private Long sort;
	//private List<Area> areas;//机构区域
	private Long repeatNumber;//允许重复条数
	private Long  correctionFactor;//修正系数
	private String orgType;//机构类型
	private String easyId;
	
	private List<User> users;//角色拥有用户

	/** 删除状态 */
	protected String recordStatus;
	
	/** 创建人id */
	protected Long createUserId;
	
	/** 创建时间 */
	protected Date createTime;
	
	/** 最近修改人id */
	protected Long modifyUserId;
	
	/** 最近修改时间 */
	protected Date modifyTime;
	
	private String ifSingle;//是否抢单
	
	@Transient
	public String getIfSingle() {
		return ifSingle;
	}

	public void setIfSingle(String ifSingle) {
		this.ifSingle = ifSingle;
	}
	
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public Organization(){
		
	}
	
	public Organization(String id){
		
		this.id=id;
	}
	
	
	
	

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "PARENT_ID")
	public Organization getParentOrgan() {
		return this.parentOrgan;
	}

	public void setParentOrgan(Organization parentOrgan) {
		this.parentOrgan = parentOrgan;
	}

	@Column(name = "ORG_NAME", nullable = false, length = 64)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Column(name = "ORG_CODE", length = 100)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	@Column(name = "ORG_DESC", length = 100)
	public String getOrgDesc() {
		return this.orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	@Column(name = "LEGAL_REP", length = 32)
	public String getLegalRep() {
		return this.legalRep;
	}

	public void setLegalRep(String legalRep) {
		this.legalRep = legalRep;
	}

	@Column(name = "PHONE", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "OWNSHIP", length = 32)
	public String getOwnShip() {
		return this.ownShip;
	}

	public void setOwnShip(String ownShip) {
		this.ownShip = ownShip;
	}

	@Column(name = "REG_FUND", precision = 20, scale = 0)
	public Long getRegFund() {
		return this.regFund;
	}

	public void setRegFund(Long regFund) {
		this.regFund = regFund;
	}

	@Column(name = "LICENSE_NO", length = 32)
	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	@Column(name = "STAFF_NUM", precision = 20, scale = 0)
	public Long getStaffNum() {
		return this.staffNum;
	}

	public void setStaffNum(Long staffNum) {
		this.staffNum = staffNum;
	}

	@Column(name = "IS_LEAF")
	public String getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "NODE_LEVEL")
	public Long getNodeLevel() {
		return this.nodeLevel;
	}

	public void setNodeLevel(Long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	@Column(name = "STATUS", length = 3)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "OPERATOR_ID", precision = 20, scale = 0)
	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OP_TIME", length = 7)
	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	@Column(name = "ORG_INDEX_ID", length = 32)
	public String getOrgIndexId() {
		return this.orgIndexId;
	}

	public void setOrgIndexId(String orgIndexId) {
		this.orgIndexId = orgIndexId;
	}

	@Column(name = "SORT_NUMBER", precision = 20, scale = 0)
	public Long getSortNumber() {
		return this.sortNumber;
	}

	public void setSortNumber(Long sortNumber) {
		this.sortNumber = sortNumber;
	}

	@Column(name = "CODE_MARK", length = 32)
	public String getOrgMark() {
		return orgMark;
	}

	public void setOrgMark(String orgMark) {
		this.orgMark = orgMark;
	}
	@Formula("(select count(*) from sys_organization t where t.PARENT_ID =id)")
	public Integer getChildsCount() {
		return childsCount;
	}

	public void setChildsCount(Integer childsCount) {
		this.childsCount = childsCount;
	}
	@Column(name = "SORT", precision = 20, scale = 0)
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "T_AREA_TO_ORG", joinColumns = {@JoinColumn(name = "ORG_ID") }, inverseJoinColumns = { @JoinColumn(name = "AREA_ID") })
//	public List<Area> getAreas() {
//		return areas;
//	}
//
//	public void setAreas(List<Area> areas) {
//		this.areas = areas;
//	}
	@Column(name = "REPEAT_NUMBER" , precision = 20, scale = 0)
	public Long getRepeatNumber() {
		return repeatNumber;
	}

	public void setRepeatNumber(Long repeatNumber) {
		this.repeatNumber = repeatNumber;
	}
	@Column(name = "CORRECTION_FACTOR", precision = 20, scale = 0)
	public Long getCorrectionFactor() {
		return correctionFactor;
	}

	public void setCorrectionFactor(Long correctionFactor) {
		this.correctionFactor = correctionFactor;
	}
	@Column(name = "ORG_TYPE_ID", length = 32)
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "RECORDSTATUS")
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	@Column(name = "CREATEUSERID", precision = 20, scale = 0)
	public Long getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "MODIFYUSERID", precision = 20, scale = 0)
	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFYTIME")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinTable(name = "sys_user_project", joinColumns = {@JoinColumn(name = "PROJECT_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	@Column(name="easy_id")
	public String getEasyId() {
		return easyId;
	}

	public void setEasyId(String easyId) {
		this.easyId = easyId;
	}

	
	
	
	
}