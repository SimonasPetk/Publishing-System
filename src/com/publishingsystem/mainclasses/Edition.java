package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Edition {
	private ArrayList<Article> articles;
	private String editionDate;
	private int editionNumber;
	
	public Edition(String editionDate, int editionNumber) {
		this.editionDate = editionDate;
		this.editionNumber = editionNumber;
		this.articles = new ArrayList<Article>();
	}

	public void setEditionDate(String editionDate) {
		this.editionDate = editionDate;
	}

	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public String getEditionDate() {
		return editionDate;
	}

	public int getEditionNumber() {
		return editionNumber;
	}
	
}
