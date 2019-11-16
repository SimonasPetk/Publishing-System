package com.publishingsystem.mainclasses;


public class ChiefEditor extends Editor{
	
	public ChiefEditor(String title, String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
	}
	
	//Chief Editor functions
	public void createJournal(Journal j) {
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
