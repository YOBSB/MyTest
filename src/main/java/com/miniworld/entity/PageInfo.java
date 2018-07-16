package com.miniworld.entity;

import java.util.ArrayList;
import java.util.List;

public class PageInfo<E> {
	private int indexPage;
	private int pageRecordsNumber;
	private int totalCounts;
	private List<E> pageItems=new ArrayList<E>();
	public int getIndexPage() {
		return indexPage;
	}
	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}
	public int getPageRecordsNumber() {
		return pageRecordsNumber;
	}
	public void setPageRecordsNumber(int pageRecordsNumber) {
		this.pageRecordsNumber = pageRecordsNumber;
	}
	public List<E> getPageItems() {
		return pageItems;
	}
	public void setPageItems(List<E> pageItems) {
		this.pageItems = pageItems;
	}
	public int getTotalCounts() {
		return totalCounts;
	}
	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
	}
	
	
}
