package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class Editor extends Academic{
	private ArrayList<Journal> journals;
	
	public Editor(int academicId, String title, String forename, String surname, String emailId, String university, Hash hash) {
		super(academicId, title, forename, surname, emailId, university, hash);
		this.journals = new ArrayList<Journal>();
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
	public void createJournal(Journal j) {
		j.setChiefEditorId(this.academicId);
		this.journals.add(j);
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
