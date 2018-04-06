package com.wan.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wan.sys.common.BaseEntity;

/**
 * 
 * 文件名称： 系统日志实体
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
@Table(name = "sys_log")
public class Log extends BaseEntity {
	/***/
	private static final long serialVersionUID = -2814439899640320960L;
	/**
	 * 
	 */
	
	private String note;//操作
	private String userId;//操作人ID
	private String ip;
	private String userName;//操作人姓名
	private String useTime;//操作时间
	
	
	
	@Column(name ="NOTE",length=255)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Column(name ="USER_ID",length=255)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name ="USER_NAME",length=255)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name ="USE_TIME")
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	
	@Column(name ="IP",length=255)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	

}
