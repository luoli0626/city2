package com.wan.sys.entity.cityManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wan.sys.common.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="city_position")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position extends BaseEntity{
	private String name;
	private String longitude;//经度
	private String latitude;//纬度
	private String type;
	private String createUserName;
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="LONGITUDE")
	public String getLongitude() {
		return longitude;
	}
	@Column(name="CREATEUSERNAME")
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Column(name="LATITUDE")
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
