package com.soft.core.page;

public class Page {

	/** 每页显示的条数 */
	private int pageSize = 15;
	/** 当前页码*/
	private int pageIndex;
	/** 总记录数*/
	private int totalCount;
	/** 总页数*/
	private int pageCount;
	/** 每页【第一条记录】的位置*/
	private int resultIndex;

	public Page() {
	}

	public Page(int pageIndex, int totalCount) {
		this.setPageSize(pageSize);
		this.setPageIndex(pageIndex);
		this.setTotalCount(totalCount);
	}

	public int getResultIndex() {
		return resultIndex;
	}

	public void setResultIndex(int resultIndex) {
		this.resultIndex = resultIndex;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		this.setResultIndex(pageSize * (pageIndex - 1));
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		int pageCount = (totalCount + pageSize - 1) / pageSize;
		this.setPageCount(pageCount);
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
