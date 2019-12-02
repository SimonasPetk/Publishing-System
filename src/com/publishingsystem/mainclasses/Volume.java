package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Volume {
	private ArrayList<Edition> editions;
	private String dateOfPublication;
	private int volumeNumber;
	private Journal journal;
	private final static int maxEditions = 6;
	private final static int minEditions = 4;
	
	public Volume(String dateOfPublication, int volumeNumber, ArrayList<Edition> editions, Journal journal) {
		this.dateOfPublication = dateOfPublication;
		this.volumeNumber = volumeNumber;
		this.editions = editions;
		this.journal = journal;
	}
	
	public void setDateOfPublication(String dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	
	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
	}
	
	public ArrayList<Edition> getEditions() {
		return editions;
	}
	
	public String getDateOfPublication() {
		return dateOfPublication;
	}
	
	public int getVolumeNumber() {
		return volumeNumber;
	}
	
	public boolean maxEditionsReached() {
		return maxEditions == editions.size();
	}
	
	public boolean minEditionsReached() {
		return minEditions <= editions.size();
	}
	
}
