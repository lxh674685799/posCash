package com.soft.laboratory.model.system;
/**
 * 此类为 TODO:类描述
 * 
 */
public class TipContent{
	String id;
	String title;
	String content;
	public String getId() {
		return id;
	}
	public TipContent addId(String id) {
		this.id = id;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public TipContent addTitle(String title) {
		this.title = title;
		return this;
	}
	public String getContent() {
		return content;
	}
	public TipContent addContent(String content) {
		this.content = content;
		return this;
	}
	
}
