package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	protected ArrayList<AuthorOfArticle> authorsOfArticle;
	protected String title;
	protected String summary;
	protected int articleId;
	protected String journalName;
	private ArrayList<PDF> versions;
	
	public Article(String title, String summary, String journalName, PDF pdf) {
		this.title = title;
		this.summary = summary;
		this.journalName = journalName;
		this.authorsOfArticle = new ArrayList<AuthorOfArticle>();
		this.versions = new ArrayList<PDF>();
		this.versions.add(pdf);
	}
	
	public Article(String title, String summary, String journalName) {
		this.title = title;
		this.summary = summary;
		this.journalName = journalName;
		this.authorsOfArticle = new ArrayList<AuthorOfArticle>();
		this.versions = new ArrayList<PDF>();
	}
	
	public void addVersion(PDF pdf) {
		this.versions.add(pdf);
	}
	
	public ArrayList<PDF> getVersions() {
		return this.versions;
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
	
	public void addPDF(PDF pdf) {
		this.versions.add(pdf);
	}
	
	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public ArrayList<AuthorOfArticle> getAuthorsOfArticle() {
		return this.authorsOfArticle;
	}
	
	public void addAuthorOfArticle(AuthorOfArticle a) {
		this.authorsOfArticle.add(a);
	}

	public int getArticleId() {
		return articleId;
	}
	
	public String getJournalName() {
		return this.journalName;
	}
	
	public ArrayList<PDF> getPDFVersions() {
		return this.versions;
	}
	
	
}
