package com.soft.core.web;

import net.sf.json.util.JSONStringer;


public class ResultMessage {

	private int result = 1;
	private String message = "";

	public ResultMessage() {
	}

	public ResultMessage(int result, String message) {
		this.result = result;
		this.message = message;
	}

	public int getResult() {
		return this.result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		JSONStringer stringer = new JSONStringer();
		stringer.object();
		stringer.key("result");
		stringer.value(this.result);
		stringer.key("message");
		stringer.value(this.message);
		stringer.endObject();
		return stringer.toString();
	}
}
