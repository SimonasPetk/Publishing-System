package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Volume {
	private ArrayList<Edition> editions;
	private String dateOfPublication;
	private int volumeNumber;
	
	public Volume(String dateOfPublication, int volumeNumber) {
		this.dateOfPublication = dateOfPublication;
		this.volumeNumber = volumeNumber;
		this.editions = new ArrayList<Edition>();
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
	
}
