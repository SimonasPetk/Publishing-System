package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Article {
	private ArrayList<AuthorOfArticle> authorsOfArticle;
	private String title;
	private String summary;
	private int articleId;
	private Journal journal;
	private Submission submission;
	private PDF pdf;
	private int numReviews;

	public Article(int articleId, String title, String summary, Journal journal) {
		this.articleId = articleId;
		this.title = title;
		this.summary = summary;
		this.journal = journal;
		this.authorsOfArticle = new ArrayList<AuthorOfArticle>();
	}
	
	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}

	public PDF getPdf() {
		return pdf;
	}

	public int getNumReviews() {
		return numReviews;
	}
	
	public void submit(Submission s) {
		this.submission = s;
	}
	
	public Submission getSubmission() {
		return this.submission;
	}
	
	
	public void setPDF(PDF pdf) {
		this.pdf = pdf;
	}
	
	public PDF getPDF() {
		return this.pdf;
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
	
}
