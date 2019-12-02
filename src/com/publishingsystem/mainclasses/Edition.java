package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Edition {
	private ArrayList<PublishedArticle> articles;
	private String editionDate;
	private int editionNumber;
	private Volume volume;
	private final static int maxArticles = 8;
	private final static int minArticles = 3; 
	
	public Edition(String editionDate, int editionNumber, ArrayList<PublishedArticle> articles, Volume volume) {
		this.editionDate = editionDate;
		this.editionNumber = editionNumber;
		this.articles = articles;
		this.volume = volume;
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
	
	public boolean maxArticlesReached() {
		return maxArticles == articles.size();
	}
	
	public boolean minArticlesReached() {
		return minArticles <= articles.size();
	}
}
