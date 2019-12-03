package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class Editor extends Academic{
	private int editorId;
	private ArrayList<EditorOfJournal> editorOfJournals;
	
	public Editor(int editorId, String title, String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.editorOfJournals = new ArrayList<EditorOfJournal>();
		this.editorId = editorId;
	}

	public void setEditorId(int id) {
		this.editorId = id;
	}

	public int getEditorId() {
		return this.editorId;
	}

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
			if(!e.emailId.equals(this.emailId)) {
				EditorOfJournal editorOfJournal = new EditorOfJournal(j, e, false);
				e.addEditorOfJournal(editorOfJournal);
				j.addEditorToBoard(editorOfJournal);
			}
		}
	}

}
