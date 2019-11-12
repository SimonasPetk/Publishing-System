package com.publishingsystem.mainclasses;
import java.util.ArrayList;
import java.sql.Date;

public class Journal {
	private String journalName;
	private int iSSN;
	private Date dateOfPublication;
	private ArrayList<Volume> volumes;
	private ArrayList<Editor> boardOfEditors;
	private int chiefEditorId;
	
	public Journal(int iSSN, String journalName, Date dateOfPublication) {
		this.journalName = journalName;
		this.iSSN = iSSN;
		this.dateOfPublication = dateOfPublication;
		this.volumes = new ArrayList<Volume>();
		this.boardOfEditors = new ArrayList<Editor>();
	}
	
	public void setChiefEditorId(int id) {
		this.chiefEditorId = id;
	}
	
	public int getCheifEditorId() {
		return this.chiefEditorId;
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
		this.volumes.add(v);
	}


	public ArrayList<Editor> getBoardOfEditors() {
		return this.boardOfEditors;
	}
	
	public void addEditorToBoard(Editor e) {
		this.boardOfEditors.add(e);
	}
	
}
