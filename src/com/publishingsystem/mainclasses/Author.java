package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Author extends Academic{
	private ArrayList<AuthorOfArticle> authorOfArticles;
	private ArrayList<Submission> submissions;
	private int authorId;
	
	public Author(String title ,String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		submissions = new ArrayList<Submission>();
		authorOfArticles = new ArrayList<AuthorOfArticle>();
	}
	
	public void setAuthorId(int id) {
		this.authorId = id;
	}
	
	public int getAuthorId() {
		return this.authorId;
	}

	public ArrayList<Submission> getSubmissions() {
		return this.submissions;
	}
	
	public SubmissionStatus getSubmissionStatus(Submission submission) {
		return submission.getStatus();
	}
	
	public ArrayList<Review> getReviews(Submission submission) {
		return submission.getReviews();
	}
	
	public void addAuthorOfArticle(AuthorOfArticle a) {
		this.authorOfArticles.add(a);
	}
	
	//Main Author
	public void submit(Submission s) {
		AuthorOfArticle authorOfArticle = new AuthorOfArticle(s, this, true);
		this.authorOfArticles.add(authorOfArticle);
		s.addAuthorOfArticle(authorOfArticle);
		
		this.submissions.add(s);
	}
	
	public void submitRevisedVersion(Submission s, PDF pdf) {
		s.addVersion(pdf);
		pdf.setArticle(s);
	}
	
	public void respond(Review review, ArrayList<String> answers) {
		ArrayList<Criticism> criticisms = review.getCriticisms();
		for(int i = 0; i < criticisms.size(); i++){
			criticisms.get(i).answer(answers.get(i));
		}
	}
	
	public void registerCoAuthors(Article article, ArrayList<Author> coauthors) {
		for(Author author : coauthors) {
			AuthorOfArticle authorOfArticle = new AuthorOfArticle(article, author);
			author.addAuthorOfArticle(authorOfArticle);
			article.addAuthorOfArticle(authorOfArticle);
		}
	}

}
