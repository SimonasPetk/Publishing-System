package com.publishingsystem.mainclasses;

public class Remark {
	private int remarkId;
	private String criticism;
	
	public Remark(String criticism) {
		this.criticism = criticism;
	}
	
	public int getRemarkId() {
		return remarkId;
	}
	public void setRemarkId(int remarkId) {
		this.remarkId = remarkId;
	}
	public String getCriticism() {
		return criticism;
	}
	public void setCriticism(String criticism) {
		this.criticism = criticism;
	}
}
