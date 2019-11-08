package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	private ArrayList<Author> authors;
	private String title;
	private String summary;
	private int articleId;
	
	public Article(ArrayList<Author> authors, String title, String summary, int articleId) {
		this.title = title;
		this.summary = summary;
		this.authors = authors;
		this.articleId = articleId;
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
	
	
}
