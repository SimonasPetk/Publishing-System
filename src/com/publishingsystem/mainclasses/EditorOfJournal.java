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
	
	public void reInitiate() {
		this.tempRetired = false;
	}
	
	public void setTempRetired(boolean t) {
		this.tempRetired = t;
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
	
	public void setChiefEditor(boolean chiefEditor) {
		this.chiefEditor = chiefEditor;
	}
}
