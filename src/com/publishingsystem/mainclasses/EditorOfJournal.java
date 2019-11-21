package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class EditorOfJournal {
	private Journal journal;
	private Editor editor;
	private boolean chiefEditor;
	
	public EditorOfJournal(Journal j, Editor e, boolean chiefEditor) {
		this.journal = j;
		this.editor = e;
		this.chiefEditor = chiefEditor;
	}
	
	public Journal getJournal() {
		return journal;
	}

	public Editor getEditor() {
		return editor;
	}

	public boolean isChiefEditor() {
		return chiefEditor;
	}
}
