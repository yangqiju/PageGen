package com.joyveb.bizcom.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ListVO<T> {

	int totalCount;
	List<T> items;
	int totalPages;
	int curPage;
	int pageSize;
	String identifier;
	String secondid;
	String label;

	public ListVO(int total, List<T> items, int curPage, int pageSize) {
		super();
		identifier = "";
		init(total, items, curPage, pageSize);
	}

	public ListVO(String identifier, int total, List<T> items, int curPage,
			int pageSize) {
		super();
		this.identifier = identifier;
		this.identifier = "";
		init(total, items, curPage, pageSize);
	}

	public void init(int total, List<T> items, int curPage, int pageSize) {
		this.totalCount = total;
		this.items = items;
		if (items!=null&&items.size() > 0) {
			this.totalPages = total / pageSize
					+ (total % pageSize == 0 ? 0 : 1);
		} else {
			this.totalPages = 0;
		}
		this.curPage = curPage;
		this.pageSize = pageSize;
	}

	public ListVO() {
		super();
		items=new ArrayList<T>();
	}
	
	public T getItem(int index)
	{
		if(index>=0&&index<items.size())
		{
			return items.get(index);
		}
		else
		{
			return null;
		}
	}
	
	public T firstItem()
	{
		if(items.size()>0)
		{
			return items.get(0);
		}
		return null;
	}
	
}
