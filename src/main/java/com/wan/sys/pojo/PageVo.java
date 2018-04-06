package com.wan.sys.pojo;

import java.io.Serializable;

public class PageVo implements Serializable{

	private static final long serialVersionUID = 6642394637030880473L;
	/** 默认的分页大小 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/** 总的记录数 */
	private int total;

	/** 总的页数 */
	private int totalPage;

	/** 每页记录数 */
	private int pageSize;

	/** 当前页数 */
	private int currentPage;
	/**
	 * 排序字段名
	 */
	private String sort = null;
	/**
	 * 按什么排序(asc,desc)
	 */
	private String order = "asc";
	
	public PageVo() {
		count();
	}
	
	public void create(int total) {
		this.total = total;
		totalPage = 0;
		count();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage<=0) {
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	/**
	 * 计算总页数,当前页
	 */
	private void count() {
		if (pageSize <= 0)
			pageSize = DEFAULT_PAGE_SIZE;
		if (pageSize > DEFAULT_PAGE_SIZE)//防止恶意分页查询
			pageSize= 1;
		totalPage = total / pageSize;
		if (total % pageSize > 0)
			totalPage++;
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > totalPage)
			currentPage = totalPage;
	}
	
}
