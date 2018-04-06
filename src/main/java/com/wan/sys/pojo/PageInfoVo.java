package com.wan.sys.pojo;

import java.io.Serializable;
import java.util.List;

public class PageInfoVo implements Serializable{

	private static final long serialVersionUID = 6642394637030880473L;
	/** 默认的分页大小 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/** 默认显示几页 */
	public static final int DEFAULT_PAGE_TOTAL = 6;
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
	@SuppressWarnings("rawtypes")
	private List rows;// 每行记录
	
	private String pageHtml;//页面显示分页信息
	
	private boolean success;
	
	
	
	public void create(int total,int page,String methodName) {
		this.total = total;
		count();
		if(totalPage-page>=DEFAULT_PAGE_TOTAL){
			if(page>1){
				pageHtml="<div class='mod-page'>&nbsp;<a class='page-item page-first' href='javascript:"+methodName+"(1);'><<</a>&nbsp;<a class='page-item' href='javascript:"+methodName+"("+(page-1)+");'><</a>&nbsp;";
			}else{
				pageHtml="<div class='mod-page'>&nbsp;<a class='page-item page-first' href='javascript:"+methodName+"(1);'><<</a>&nbsp;<a class='page-item' href='javascript:"+methodName+"(1);'><</a>&nbsp;";
				
			}		
				for(int i=page;i<DEFAULT_PAGE_TOTAL+page;i++){
					if(page==i){
						pageHtml+="<span class='page-item page-cur'>"+i+"</span>&nbsp";
					}else{
				       pageHtml+="<a class='page-item' href='javascript:"+methodName+"("+i+");'>"+i+"</a>&nbsp;";
					}
				}
			if(page<totalPage){
				pageHtml+="<a class='page-item'  href='javascript:"+methodName+"("+page+1+");'>></a>&nbsp;<a class='page-item page-last'  href='javascript:"+methodName+"("+totalPage+");'>>></a></div>"; 
			}else{
				pageHtml+="<a class='page-item'  href='javascript:"+methodName+"("+totalPage+");'>></a>&nbsp;<a class='page-item page-last'  href='javascript:"+methodName+"("+totalPage+");'>>></a></div>"; 
					
			}
		}else{
			if(page>1){
				pageHtml="<div class='mod-page'>&nbsp;<a class='page-item page-first' href='javascript:"+methodName+"(1);'><<</a>&nbsp;<a class='page-item' href='javascript:"+methodName+"("+(page-1)+");'><</a>&nbsp;";
			}else{
				pageHtml="<div class='mod-page'>&nbsp;<a class='page-item page-first' href='javascript:"+methodName+"(1);'><<</a>&nbsp;<a class='page-item' href='javascript:"+methodName+"(1);'><</a>&nbsp;";
				
			}		
				for(int i=totalPage-DEFAULT_PAGE_TOTAL+1;i<=totalPage;i++){
					if(i<=0){
						continue;
					}
					if(page==i){
						pageHtml+="<span class='page-item page-cur'>"+i+"</span>&nbsp";
					}else{
				        pageHtml+="<a class='page-item' href='javascript:"+methodName+"("+i+");'>"+i+"</a>&nbsp;";
					}
				}
			if(page<totalPage){
				pageHtml+="<a class='page-item'  href='javascript:"+methodName+"("+(page+1)+");'>></a>&nbsp;<a class='page-item page-last'  href='javascript:"+methodName+"("+totalPage+");'>>></a></div>"; 
			}else{
				pageHtml+="<a class='page-item'  href='javascript:"+methodName+"("+totalPage+");'>></a>&nbsp;<a class='page-item page-last'  href='javascript:"+methodName+"("+totalPage+");'>>></a></div>"; 
					
			}
		}
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

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public String getPageHtml() {
		return pageHtml;
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
}
