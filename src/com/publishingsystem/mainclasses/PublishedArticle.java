package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class PublishedArticle extends Article{
	private String pageRange;
	private Edition edition;
	private int publishedArticleId;

	public PublishedArticle(int publishedArticleId, Article a) {
	    super(a.getArticleId(), a.getTitle(), a.getSummary(), a.getJournal());
	    this.pageRange = null;
	    this.edition = null;
	    this.publishedArticleId = publishedArticleId;
	}
	
	public PublishedArticle(int publishedArticleId, Article a, String pageRange, Edition edition) {
		super(a.getArticleId(), a.getTitle(), a.getSummary(), a.getJournal());
		this.pageRange = pageRange;
		this.edition = edition;
		this.publishedArticleId = publishedArticleId;
	}
	
	public Edition getEdition() {
		return edition;
	}

	public int getPublishedArticleId() {
		return publishedArticleId;
	}

	public void setEdition(Edition en) {
		this.edition = en;
	}
	
	public void setPageRange(String pr) {
		this.pageRange = pr;
	}
	
	public Edition getEditionNumber() {
		return this.edition;
	}
	
	public String getPageRange() {
		return this.pageRange;
	}
	
	

}
