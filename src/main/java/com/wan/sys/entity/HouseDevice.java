package com.wan.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * THourseDemo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_house_device")
public class HouseDevice implements java.io.Serializable {

	// Fields

	private Long id;
	private String deviceSerial;  //设备序列号
	private String deviceName;    //设备名称
	private String projectId;     //项目id
	private String projectName;   //项目名字
	private String token;
	private String isALive; // 设备是否激活
	private Long vailTime;

	// Constructors

	/** default constructor */
	public HouseDevice() {
	}

	/** minimal constructor */
	public HouseDevice(Long id) {
		this.id = id;
	}

	/** full constructor */
	public HouseDevice(Long id, String deviceSerial, String projectId,
			String projectName, String token, String isALive) {
		this.id = id;
		this.deviceSerial = deviceSerial;
		this.projectId = projectId;
		this.projectName = projectName;
		this.token = token;
		this.isALive = isALive;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "com.wan.sys.util.IdGenerator")
	public Long getId() {
		return this.id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "deviceSerial")
	public String getDeviceSerial() {
		return this.deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	@Column(name = "project_id", length = 36)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "project_name")
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "token")
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "isAlive")
	public String getIsALive() {
		return isALive;
	}

	public void setIsALive(String isALive) {
		this.isALive = isALive;
	}
	@Column(name = "vailTime")
	public Long getVailTime() {
		return vailTime;
	}

	public void setVailTime(Long vailTime) {
		this.vailTime = vailTime;
	}

	
	@Column(name = "deviceName")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}


}