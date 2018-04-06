package com.wan.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysGb6275 entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_gb_6275")
public class SysGb6275 implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String code;
	private String bz;
	private String type;

	// Constructors

	/** default constructor */
	public SysGb6275() {
		
	}

	/** minimal constructor */
	public SysGb6275(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SysGb6275(Long id, String name, String code, String bz, String type) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.bz = bz;
		this.type = type;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 36, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE", length = 25)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "BZ", length = 100)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "type", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}