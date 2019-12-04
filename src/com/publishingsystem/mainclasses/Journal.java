package com.publishingsystem.mainclasses;
import java.util.ArrayList;
import java.sql.Date;

public class Journal {
	private String journalName;
	private int iSSN;
	private Date dateOfPublication;
	private ArrayList<EditorOfJournal> boardOfEditors;
	private ArrayList<Volume> volumes;
	
	public Journal(int iSSN, String journalName, Date dateOfPublication) {
		this.journalName = journalName;
		this.iSSN = iSSN;
		this.dateOfPublication = dateOfPublication;
		this.boardOfEditors = new ArrayList<EditorOfJournal>();
		this.volumes = RetrieveDatabase.getVolumes(iSSN);
	}
	

	public String getJournalName() {
		return this.journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public int getISSN() {
		return this.iSSN;
	}

	public void setISSN(int iSSN) {
		this.iSSN = iSSN;
	}

	public Date getDateOfPublication() {
		return this.dateOfPublication;
	}

	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	
	public ArrayList<Volume> getVolumes() {
		return this.volumes;
	}
	
	public void addVolume(Volume v) {
	    v.setJournal(this);
		this.volumes.add(v);
	}

	public ArrayList<EditorOfJournal> getBoardOfEditors() {
		return this.boardOfEditors;
	}
	
	public void setBoardOfEditors(ArrayList<EditorOfJournal> e) {
		this.boardOfEditors = e;
	}
	
	public void addEditorToBoard(EditorOfJournal e) {
		this.boardOfEditors.add(e);
	}
	
	public String toString() {
	    return this.iSSN + ", " + this.journalName + ", " + this.dateOfPublication + ", " + this.boardOfEditors + ", " + this.volumes;
	}
}
