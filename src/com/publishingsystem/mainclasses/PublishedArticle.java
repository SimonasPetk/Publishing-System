package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class PublishedArticle extends Article{
	private String pageRange;
	private int editionNumber;
	
	public PublishedArticle(String title, String summary, int journalId, ArrayList<Author> authors) {
		super(title, summary, journalId, authors);
	}
	
	public PublishedArticle(Article a) {
		super(a.getTitle(), a.getSummary(), a.getJournalId(), a.getAuthors());
	}
	
	public void setEditionNumber(int en) {
		this.editionNumber = en;
	}
	
	public void setPageRange(String pr) {
		this.pageRange = pr;
	}
	
	public int getEditionNumber() {
		return this.editionNumber;
	}
	
	public String getPageRange() {
		return this.pageRange;
	}
	
	

}
