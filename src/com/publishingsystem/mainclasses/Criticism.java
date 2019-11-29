package com.publishingsystem.mainclasses;

public class Criticism {
	private int criticismId;
	private String criticism;
	private String answer;
	
	public Criticism(String criticism) {
		this.criticism = criticism;
	}
	
	public Criticism(String criticism, String answer) {
		this.criticism = criticism;
		this.answer = answer;
	}
	public Criticism(int criticismId, String criticism, String answer) {
		this.criticismId = criticismId;
		this.criticism = criticism;
		this.answer = answer;
	}
	
	public int getCriticismId() {
		return criticismId;
	}
	
	public void setCriticismId(int criticismId) {
		this.criticismId = criticismId;
	}
	
	public void answer(String answer) {
		this.answer = answer;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public String getCriticism() {
		return this.criticism;
	}
}
