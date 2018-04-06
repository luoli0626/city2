package com.wan.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 文件名称： access_token生成或刷新实体
 * 内容摘要：  access_token生成或刷新实体
 * 创建人： tangjunzuo
 * 创建日期： 2017年2月15日
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
@Table(name="sys_access_log")

public class AccessLog  implements java.io.Serializable {

	private static final long serialVersionUID = -4186427549331024633L;
	private Long accessLogId;//主键ID
     private Date accessTime;//开始时间
     private Date lastTime;//最后时间
     private String averageTime;//平均时间
     private Long count;//使用次数
     private String appKey;//应用key
     private Long userId;//用户ID
     @Id
     @Column(name="access_log_id")
 	 @GeneratedValue(generator = "pk")
 	 @GenericGenerator(name = "pk", strategy = "com.wan.sys.util.IdGenerator")
    
	public Long getAccessLogId() {
		return accessLogId;
	}
	public void setAccessLogId(Long accessLogId) {
		this.accessLogId = accessLogId;
	}
	 @Column(name="access_Time")
	public Date getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	@Column(name="last_Time")
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	@Column(name="average_Time")
	public String getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}
	@Column(name="count")
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@Column(name="app_Key")
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	@Column(name="user_Id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

 


}