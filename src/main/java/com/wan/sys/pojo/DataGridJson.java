package com.wan.sys.pojo;

import java.util.List;

/**
 * 后台向前台返回JSON，用于easyui的datagrid
 * 
 * @author  
 * 
 */
@SuppressWarnings("serial")
public class DataGridJson implements java.io.Serializable {

	private Long total;// 总记录数
	@SuppressWarnings("rawtypes")
	private List rows;// 每行记录
	@SuppressWarnings("rawtypes")
	private List footer;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}

	@SuppressWarnings("rawtypes")
	public List getFooter() {
		return footer;
	}

	@SuppressWarnings("rawtypes")
	public void setFooter(List footer) {
		this.footer = footer;
	}

}
