package com.publishingsystem.mainclasses;

public class ChiefEditor extends Editor{
	public ChiefEditor(int academicId, String title, String forename, String surname, String emailId, String university) {
		super(academicId, title, forename, surname, emailId, university);
	}
	
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
