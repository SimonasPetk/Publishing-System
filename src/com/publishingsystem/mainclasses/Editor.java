package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class Editor extends Academic{
	private int editorId;
	private ArrayList<Journal> journals;
	
	public Editor(int academicId, String title, String forename, String surname, String emailId, String university, int editorId) {
		super(academicId, title, forename, surname, emailId, university);
		this.editorId = editorId;
		this.journals = new ArrayList<Journal>();
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
	
	//Chief Editor functions
	public void assignEditor(Editor e, Journal j) {
		j.addEditorToBoard(e);
	}
	
	public void publishArticle(Article a) {
		
	}
	
	public void publishVolume(Volume v) {

	}
	
	public void publishEdition(Edition e) {
		
	}
	
	public void addToNextAvailableEdition(Article a) {
		
	}
	
	public void changeRole(){
		
	}
}
