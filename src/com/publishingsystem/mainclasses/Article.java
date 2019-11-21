package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	private ArrayList<AuthorOfArticle> authorsOfArticle;
	private String title;
	private String summary;
	private int articleId;
	private Journal journal;
	private Submission submission;
	private ArrayList<PDF> versions;
	
	public Article(String title, String summary, Journal journal, PDF pdf) {
		this.title = title;
		this.summary = summary;
		this.journal = journal;
		this.authorsOfArticle = new ArrayList<AuthorOfArticle>();
		this.versions = new ArrayList<PDF>();
		this.versions.add(pdf);
	}
	
	public Article(int articleId, String title, String summary, Journal journal) {
		this.articleId = articleId;
		this.title = title;
		this.summary = summary;
		this.journal = journal;
		this.authorsOfArticle = new ArrayList<AuthorOfArticle>();
		this.versions = new ArrayList<PDF>();
	}
	
	public void submit(Submission s) {
		this.submission = s;
	}
	
	public Submission getSubmission() {
		return this.submission;
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
	
	public Journal getJournal() {
		return this.journal;
	}
	
	public ArrayList<PDF> getPDFVersions() {
		return this.versions;
	}
	
	
}
