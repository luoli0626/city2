package com.wan.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "sys_house_client")
public class HouseClient {
	
	//Fields
	private Long id;
	private String userId;
	private String lineNo;
	private String isTakeHouse;
	private String token;
	private String imgIdCard;
	private String imgFace;
	private String houseState;
	private String projectId;
	private String houseInfo;
	private String unit;
	private String building;
	private String room;
	private String userName;
	private String userPhone;
	
	public HouseClient() {
	}

	
	public HouseClient(Long id) {
		this.id = id;
	}

	



	public HouseClient(Long id, String userId, String lineNo,
			String isTakeHouse, String token, String imgIdCard, String imgFace,
			String houseState) {
		this.id = id;
		this.userId = userId;
		this.lineNo = lineNo;
		this.isTakeHouse = isTakeHouse;
		this.token = token;
		this.imgIdCard = imgIdCard;
		this.imgFace = imgFace;
		this.houseState = houseState;
	}


	@Id
	@GeneratedValue(generator = "pk")
	@GenericGenerator(name = "pk", strategy = "com.wan.sys.util.IdGenerator")
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "userId")
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "lineNo")
	public String getLineNo() {
		return lineNo;
	}


	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	@Column(name = "isTakeHouse")
	public String getIsTakeHouse() {
		return isTakeHouse;
	}


	public void setIsTakeHouse(String isTakeHouse) {
		this.isTakeHouse = isTakeHouse;
	}

	@Column(name = "token")
	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "img_idCard")
	public String getImgIdCard() {
		return imgIdCard;
	}


	public void setImgIdCard(String imgIdCard) {
		this.imgIdCard = imgIdCard;
	}

	@Column(name = "img_face")
	public String getImgFace() {
		return imgFace;
	}


	public void setImgFace(String imgFace) {
		this.imgFace = imgFace;
	}

	@Column(name = "house_state")
	public String getHouseState() {
		return houseState;
	}


	public void setHouseState(String houseState) {
		this.houseState = houseState;
	}

	@Column(name= "projectId" )
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name="houseInfo")
	public String getHouseInfo() {
		return houseInfo;
	}


	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}

	@Column(name="unit")
	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="building")
	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}

	@Column(name="room")
	public String getRoom() {
		return room;
	}


	public void setRoom(String room) {
		this.room = room;
	}

	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="user_phone")
	public String getUserPhone() {
		return userPhone;
	}


	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	
}
