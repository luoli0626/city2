package com.wan.sys.pojo;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import com.wan.sys.common.BaseEntity;
import com.wan.sys.entity.Menu;
import com.wan.sys.entity.Role;
import com.wan.sys.entity.organization.Organization;


/**
 * 用户模型
 * 
 * @author  
 * 
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class UserBean extends BaseEntity implements java.io.Serializable {
  
	public String[] checkStatusStr={"-全部","Y-通过","N-未通过"};
	
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
	private List<Organization> organization;//用户所属结构
	private String organId; //机构ID
	private String flag;//状态
	private List<Role> roles;
	private Long rolesID; //角色ID
	
	private Date createdatetimeStart;
	private Date createdatetimeEnd;
	private Date modifydatetimeStart;
	private Date modifydatetimeEnd;
	private String oldPassword;
	
	private List<Menu> menus;
	
	private String staff;//用户对应的人员
	private String isFirstLog;//是否第一次登陆 1或空表示第一次登陆
	
	private String tempSession;//在第一次登陆是用到的中间变量
	private Long scenesID; //场景ID
	private String  openId;
	private String  orgIdid;
	private Integer taskNumber;//任务量
	private String checkStatus;//审核状态
	private String seal;//解封状态
	private String isAdmin;
	private String code;//	验证码
	private String token;
	private String imgAdress;
	private String newPwd;
	private String ifManage;

	public String getIfManage() {
		return ifManage;
	}

	public void setIfManage(String ifManage) {
		this.ifManage = ifManage;
	}

	public String getImgAdress() {
		return imgAdress;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setImgAdress(String imgAdress) {
		this.imgAdress = imgAdress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeal() {
		return seal;
	}

	public void setSeal(String seal) {
		this.seal = seal;
	}

	public String getTempSession() {
		return tempSession;
	}

	public void setTempSession(String tempSession) {
		this.tempSession = tempSession;
	}

	public String getIsFirstLog() {
		return isFirstLog;
	}

	public void setIsFirstLog(String isFirstLog) {
		this.isFirstLog = isFirstLog;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	@JsonIgnore
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(List<Organization> organization) {
		this.organization = organization;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public Date getModifydatetimeStart() {
		return modifydatetimeStart;
	}

	public void setModifydatetimeStart(Date modifydatetimeStart) {
		this.modifydatetimeStart = modifydatetimeStart;
	}

	public Date getModifydatetimeEnd() {
		return modifydatetimeEnd;
	}

	public void setModifydatetimeEnd(Date modifydatetimeEnd) {
		this.modifydatetimeEnd = modifydatetimeEnd;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(Integer taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getOrgIdid() {
		return orgIdid;
	}

	public void setOrgIdid(String orgIdid) {
		this.orgIdid = orgIdid;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Long getRolesID() {
		return rolesID;
	}

	public void setRolesID(Long rolesID) {
		this.rolesID = rolesID;
	}

	public Long getScenesID() {
		return scenesID;
	}

	public void setScenesID(Long scenesID) {
		this.scenesID = scenesID;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
