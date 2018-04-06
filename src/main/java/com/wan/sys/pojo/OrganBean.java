package com.wan.sys.pojo;

/**
 * 
 * 文件名称: 机构模型
 * 内容摘要:机构模型
 * 创 建 人:
 * 创建日期: Dec 6, 2013
 * 公    司: 亚德科技股份有限公司
 * 版权所有: 版权所有(C)2001-2004
 * 
 * 修改记录1: 
 *   修改日期：
 *   版 本 号：
 *   修 改 人：
 *   修改内容：
 * 修改记录2：…
 * 
 */

public class OrganBean{
	private String id;
     private Long gradeId;//机构级别ID
     private String gradeName;//机构级别名称
     private Long typeId;//机构类型ID
     private String typeName;//机构类型名称
     private String parentId;//上级ID
     private String parentName;//上级名称
     private String orgCode;//机构编码健康档案信息编码ID
     private String orgName;//机构名称
     private String orgDesc;//机构描述
     private String legalRep;//法定代表人
     private String phone;//传真电话
     private String ownship;//所有制形式
     private Long regFund;//注册资金
     private String licenseNo;//执业许可证登记号
     private Long staffNum;//职工人数
     private String isLeaf;//是否叶子
     private Long nodeLevel;//节点层级
     private Long sortRank;//排序价值
     private String status;//状态
     private Long operatorId;//操作员
     private String opTime;//操作时间
     private String orgTndexId;//基础平台区域码
     private String orgMark;//机构标识码
     private String state;//前台treegrid 打开状态
     private String _parentId;
     private String orgType;//机构类型
     private Long sort;//
     private Long repeatNumber;//允许重复条数
  	private Long  correctionFactor;//修正系数
  	
     
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getLegalRep() {
		return legalRep;
	}
	public void setLegalRep(String legalRep) {
		this.legalRep = legalRep;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOwnship() {
		return ownship;
	}
	public void setOwnship(String ownship) {
		this.ownship = ownship;
	}
	public Long getRegFund() {
		return regFund;
	}
	public void setRegFund(Long regFund) {
		this.regFund = regFund;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public Long getStaffNum() {
		return staffNum;
	}
	public void setStaffNum(Long staffNum) {
		this.staffNum = staffNum;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Long getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(Long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public Long getSortRank() {
		return sortRank;
	}
	public void setSortRank(Long sortRank) {
		this.sortRank = sortRank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOpTime() {
		return opTime;
	}
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	public String getOrgTndexId() {
		return orgTndexId;
	}
	public void setOrgTndexId(String orgTndexId) {
		this.orgTndexId = orgTndexId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getOrgMark() {
		return orgMark;
	}
	public void setOrgMark(String orgMark) {
		this.orgMark = orgMark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String id) {
		_parentId = id;
	}
	
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public Long getRepeatNumber() {
 		return repeatNumber;
 	}
 	public void setRepeatNumber(Long repeatNumber) {
 		this.repeatNumber = repeatNumber;
 	}
 	public Long getCorrectionFactor() {
 		return correctionFactor;
 	}
 	public void setCorrectionFactor(Long correctionFactor) {
 		this.correctionFactor = correctionFactor;
 	}
    
}
