package com.publishingsystem.mainclasses;

public class AuthorOfArticle {
	private Article article;
	private Author author;
	private boolean mainAuthor;
	
	public AuthorOfArticle(Article article, Author author) {
		this.article = article;
		this.author = author;
	}
	
	public AuthorOfArticle(Article article, Author author, boolean mainAuthor) {
		this.article = article;
		this.author = author;
		this.mainAuthor = true;
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
	
}
