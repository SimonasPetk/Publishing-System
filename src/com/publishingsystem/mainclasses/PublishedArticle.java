package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class PublishedArticle extends Article{
	private String pageRange;
	private Edition edition;
	
	public PublishedArticle(Article a, String pageRange, Edition edition) {
		super(a.getArticleId(), a.getTitle(), a.getSummary(), a.getJournal());
		this.pageRange = pageRange;
		this.edition = edition;
	}
	
	public void setEditionNumber(Edition en) {
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
