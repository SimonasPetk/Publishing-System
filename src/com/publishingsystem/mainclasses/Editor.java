package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class Editor extends Academic{
	private int editorId;
	private ArrayList<EditorOfJournal> editorOfJournals;

	public Editor(String title, String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.editorOfJournals = new ArrayList<EditorOfJournal>();
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
	
	public void addEditorOfJournal(EditorOfJournal eoj){
		this.editorOfJournals.add(eoj);
	}

	public ArrayList<EditorOfJournal> getEditorOfJournals(){
		return this.editorOfJournals;
	}

	//Chief Editor functions
	public void createJournal(Journal j) {
		EditorOfJournal editorOfJournal = new EditorOfJournal(j, this, true);
		this.editorOfJournals.add(editorOfJournal);
		j.addEditorToBoard(editorOfJournal);
	}
	
	public void registerEditors(Journal j, ArrayList<Editor> editors) {
		for(Editor e : editors) {
			EditorOfJournal editorOfJournal = new EditorOfJournal(j, e);
			e.addEditorOfJournal(editorOfJournal);
			j.addEditorToBoard(editorOfJournal);
		}
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
