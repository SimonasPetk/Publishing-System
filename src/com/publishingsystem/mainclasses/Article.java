package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	protected ArrayList<Author> authors;
	protected String title;
	protected String summary;
	protected int articleId;
	protected int journalId;
	protected String pdf;
	protected String pageRange;
	protected int editionNumber;
	
	public Article(ArrayList<Author> authors, String title, String summary, int articleId, int journalId, String pdf) {
		this.title = title;
		this.summary = summary;
		this.authors = authors;
		this.articleId = articleId;
		this.journalId = journalId;
		this.pdf = pdf;
	}
	
	

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public void setEditionNumber(int en) {
		this.editionNumber = en;
	}
	
	public void setPageRange(String pr) {
		this.pageRange = pr;
	}
	

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public int getArticleId() {
		return articleId;
	}
	
	public int getJournalId() {
		return this.journalId;
	}
	
	public int getEditionNumber() {
		return this.editionNumber;
	}
	
	public String getPDF() {
		return this.pdf;
	}
	
	public String getPageRange() {
		return this.pageRange;
	}
	
	
}
