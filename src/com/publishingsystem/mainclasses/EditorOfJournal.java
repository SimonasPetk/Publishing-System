package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class EditorOfJournal {
	private Journal journal;
	private Editor editor;
	private boolean chiefEditor;
	private boolean tempRetired = false;
	
	public EditorOfJournal(Journal j, Editor e, boolean chiefEditor) {
		this.journal = j;
		this.editor = e;
		this.chiefEditor = chiefEditor;
	}
	
	//constructor for chief editors adding new editors
	public EditorOfJournal(Journal j) {
		this.journal = j;
		this.chiefEditor = false;
	}
	
	public void temporaryRetire() {
		this.tempRetired = true;
	}
	
	public boolean isTempRetired() {
		return tempRetired;
	}

	public Journal getJournal() {
		return journal;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor e) {
		editor = e;
	}
	
	public boolean isChiefEditor() {
		return chiefEditor;
	}
	
	public void addEditorAsChiefEditor() {
		System.out.println(journal);
		System.out.println(editor);
		System.out.println("Hello this is both the editor and the journal");
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(editor);
		Database.registerEditors(editors);
		Database.updateEditorOfJournal(editors, journal);
	}
}
