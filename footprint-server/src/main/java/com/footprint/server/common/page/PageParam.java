package com.footprint.server.common.page;

public class PageParam {
	public static final int DEFLAUT_PAGE_SIZE = 20;
	
	// -- 分页参数 --//
	private int pageNo = 1;// 页数

	private int pageSize = DEFLAUT_PAGE_SIZE;// 显示条数

	private String sortField;

	private String ascOrDesc = "desc";

	public PageParam() {

	}

	public PageParam(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}
	
	public int getStartRow() {
		return (pageNo - 1) * pageSize;
	}

	public int getEndRow() {
		return pageNo * pageSize-1;
	}
}
