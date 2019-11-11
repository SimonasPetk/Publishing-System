package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	protected ArrayList<Author> authors;
	protected String title;
	protected String summary;
	protected int articleId;
	protected int journalId;
	
	public Article(ArrayList<Author> authors, String title, String summary, int articleId, int journalId) {
		this.title = title;
		this.summary = summary;
		this.authors = authors;
		this.articleId = articleId;
		this.journalId = journalId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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
	
	
}
