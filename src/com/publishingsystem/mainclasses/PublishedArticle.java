package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class PublishedArticle extends Article{
	private String pageRange;
	private String journalName;
	private int editionNumber;
	
	public PublishedArticle(Article a, String pageRange, String journalName, int editionNumber) {
		super(a.getArticleId(), a.getTitle(), a.getSummary(), a.getJournal());
		this.pageRange = pageRange;
		this.editionNumber = editionNumber;
	}
	
	public String getJournalName() {
		return journalName;
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
