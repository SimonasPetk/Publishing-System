package com.publishingsystem.mainclasses;

public class AuthorOfArticle {
	private Article article;
	private Author author;
	private boolean mainAuthor;
	private int numReviews;
	
	public AuthorOfArticle(Article article, Author author,boolean mainAuthor, int numReviews) {
		this.article = article;
		this.author = author;
		this.mainAuthor = mainAuthor;
		this.numReviews = numReviews;
	}

	public int getNumReviews() {
		return numReviews;
	}

	public Article getArticle() {
		return article;
	}

	public Author getAuthor() {
		return author;
	}

	public boolean isMainAuthor() {
		return mainAuthor;
	}
	
	public String toString() {
	    return "AuthorOfArticle: (" + this.article + "), " + this.author + ", " + this.mainAuthor + ", " + this.numReviews;
	}
}
