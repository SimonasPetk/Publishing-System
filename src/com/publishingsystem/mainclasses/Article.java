package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	protected ArrayList<Author> authors;
	protected String title;
	protected String summary;
	protected int articleId;
	protected int journalId;
	protected int mainAuthorId;
	protected String pdf;
	
	public Article(String title, String summary, int journalId, ArrayList<Author> authors) {
		this.title = title;
		this.summary = summary;
		this.authors = authors;
		this.journalId = journalId;
	}
	
	public void setMainAuthorId(int id) {
		this.mainAuthorId = id;
	}
	
	public int getMainAuthorId() {
		return this.mainAuthorId;
	}

	
	public void setArticleId(int id) {
		this.articleId = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public void setPDF(String pdf) {
		this.pdf = pdf;
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
	
	public String getPDF() {
		return this.pdf;
	}
	
	
}
