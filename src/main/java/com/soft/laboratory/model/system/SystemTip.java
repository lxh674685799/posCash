package com.soft.laboratory.model.system;

import java.util.List;

public class SystemTip {
	int count;
	List<TipContent> list;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<TipContent> getList() {
		return list;
	}
	public void setList(List<TipContent> list) {
		this.list = list;
	}
	public static TipContent createContent(){
		return new TipContent();
	}
}