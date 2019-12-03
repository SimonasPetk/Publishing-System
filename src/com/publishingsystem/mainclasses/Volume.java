package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Volume {
	private ArrayList<Edition> editions;
	private int year;
	private int volumeNumber;
	private int volumeId;
	private Journal journal;
	private final static int maxEditions = 6;
	private final static int minEditions = 4;
	
	public Volume(int year, int volumeId) {
	    this.editions = RetrieveDatabase.getEditions(volumeId);
		this.year = year;
		this.volumeNumber = -1;
		this.volumeId = volumeId;
		this.journal = null;
	}
	public Volume(int year, int volumeNumber, int volumeId) {
	    this.editions = RetrieveDatabase.getEditions(volumeId);
		this.year = year;
		this.volumeNumber = volumeNumber;
		this.volumeId = volumeId;
		this.journal = null;
	}
	
    public void addEdition(Edition e) {
        e.setVolume(this);
        this.editions.add(e);
    }
	
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }
    
    public void setJournal(Journal j) {
        j.addVolume(this);
        this.journal = j;
    }
    
    
	public ArrayList<Edition> getEditions() {
	    return editions;
    }
	
	public int getYear() {
		return year;
	}
	
	public int getVolumeNumber() {
		return volumeNumber;
	}
	
	public int getVolumeId() {
	    return volumeId;
	}

	public Journal getJournal() {
		return journal;
	}
	
	
	public boolean maxEditionsReached() {
		return maxEditions == editions.size();
	}
	
	public boolean minEditionsReached() {
		return minEditions <= editions.size();
	}
}
