package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Edition {
	private ArrayList<PublishedArticle> articles;
	private int editionMonth;
	private int editionNumber; // edition number within volume
	private int editionId; // edition id in database
	private Volume volume;
	private boolean published;
	private final static int maxArticles = 8;
	private final static int minArticles = 3; 
	
	
	public Edition(int editionMonth, int editionId) {
	    this.articles = RetrieveDatabase.getPublishedArticles(editionId);
		this.editionMonth = editionMonth;
		this.editionNumber = -1;
		this.editionId = editionId;
		this.volume = null;
	    this.published = false;
	}
	
	public Edition(ArrayList<PublishedArticle> articles, int editionMonth, int editionId, int edNumber, Volume vol) {
	    this.articles = articles;
		this.editionMonth = editionMonth;
		this.editionNumber = edNumber;
		this.editionId = editionId;
		this.volume = vol;
	    this.published = true;
	}

	
	public void addPublishedArticle(PublishedArticle a) {
	    a.setEdition(this);
	    this.articles.add(a);
	}
	
	public void addPublishedArticles(ArrayList<PublishedArticle> articles) {
	    for (PublishedArticle a : articles) {
	        a.setEdition(this);
	        this.articles.add(a);
	    }
	}
	
	public void setEditionMonth(int editionMonth) {
		this.editionMonth = editionMonth;
	}

	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}
	
	public void setEditionId(int editionId) {
	    this.editionId = editionId;
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

	public int getEditionMonth() {
		return editionMonth;
	}

	public int getEditionNumber() {
		return editionNumber;
	}

	public int getEditionId() {
	    return editionId;
	}
	
	public Volume getVolume() {
		return volume;
	}
	
	public boolean isPublished() {
		return published;
	}
	
	
	public boolean maxArticlesReached() {
		return maxArticles == articles.size();
	}
	
	public boolean minArticlesReached() {
		return minArticles <= articles.size();
	}
}
