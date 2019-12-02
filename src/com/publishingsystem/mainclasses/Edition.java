package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Edition {
	private ArrayList<PublishedArticle> articles;
	private String editionDate;
	private int editionNumber;
	private Volume volume;
	private boolean published;
	private final static int maxArticles = 8;
	private final static int minArticles = 3; 
	
	
	public Edition(String editionDate, int editionNumber, ArrayList<PublishedArticle> articles, Volume volume) {
		this.editionDate = editionDate;
		this.editionNumber = editionNumber;
		this.articles = articles;
		this.volume = volume;
		this.published = false;
	}

	public void setEditionDate(String editionDate) {
		this.editionDate = editionDate;
	}

	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}
	
	public void setVolume(Volume vol) {
		this.volume = vol;
	}
	
	public void setPublished(boolean set) {
		this.published = set;
	}

	public ArrayList<PublishedArticle> getArticles() {
		return articles;
	}

	public String getEditionDate() {
		return editionDate;
	}

	public int getEditionNumber() {
		return editionNumber;
	}
	
	public Volume getVolume() {
		return volume;
	}
	
	public boolean getPublished() {
		
		return published;
	}
	
	public boolean maxArticlesReached() {
		return maxArticles == articles.size();
	}
	
	public boolean minArticlesReached() {
		return minArticles <= articles.size();
	}
	
}
