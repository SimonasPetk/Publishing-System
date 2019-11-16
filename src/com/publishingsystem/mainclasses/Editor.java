package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class Editor extends Academic{
	protected ArrayList<Journal> journals;
	private int editorId;
	
	public Editor(String title, String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.journals = new ArrayList<Journal>();
	}
	
	public void setEditorId(int id) {
		this.editorId = id;
	}
	
	public int getEditorId() {
		return this.editorId;
	}
	
	public void viewArticlesInConsideration() {
		
	}
	
	public void acceptArticle(Submission s) {
		s.setDecision(Decision.ACCEPTED);
	}
	
	public void rejectArticle(Submission s) {
		s.setDecision(Decision.REJECTED);
	}
	
	public void retire() {}
	
	public ArrayList<Journal> getJournals(){
		return this.journals;
	}
}
