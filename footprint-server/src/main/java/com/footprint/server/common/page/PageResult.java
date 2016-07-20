package com.footprint.server.common.page;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {
	public static final int DEFLAUT_PAGE_SIZE = 20;
	
	// -- 分页参数 --//
	private int pageNo = 1;// 页数

	private int pageSize = DEFLAUT_PAGE_SIZE;// 显示条数

	private long totalCount = 0;// 总条数
	
	private String sortField;

	private String ascOrDesc = "desc";

	private List<T> resultList = new ArrayList<T>();// 取得页内的记录列表
	
	public PageResult() {

	}

	public PageResult(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public PageResult(List<T> resultList) {
		this.resultList = resultList;
	}

	public PageResult(int pageNo, int pageSize, long totalCount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	public PageResult(int pageNo, int pageSize, long totalCount, List<T> resultList) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.resultList = resultList;
	}

	// -- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号如果大于总条数，则当前页定位到总页数
	 */
	public int getPageNo() {
		if (this.getPageCount() > -1 && pageNo > this.getPageCount()) {
			pageNo = (int) this.getPageCount();
		}
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 查询结果list
	 */
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getPageCount() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count = count + 1;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean hasNextPage() {
		return (pageNo + 1 <= getPageCount());
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (hasNextPage()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean hasPrevPage() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrevPage() {
		if (hasPrevPage()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	public int getStartRow() {
		return (pageNo - 1) * pageSize;
	}

	public int getEndRow() {
		return pageNo * pageSize - 1;
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
	
}
