package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Author extends Academic{
	private ArrayList<Article> articles;
	
	public Author(ArrayList<Article> articles, int academicId, String title ,String forename, String surname, String emailId, String university) {
		super(academicId, title, forename, surname, emailId, university);
		this.articles = articles;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}
	
	public void getArticleStatus(Article article) {
		
	}
	
	public void getResponses() {}

}
