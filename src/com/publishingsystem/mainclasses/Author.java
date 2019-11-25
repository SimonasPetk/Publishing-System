package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Author extends Academic{
	private ArrayList<AuthorOfArticle> authorOfArticles;
	private int authorId;
	
	public Author(int authorId, String title ,String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.authorId = authorId;
		authorOfArticles = new ArrayList<AuthorOfArticle>();
	}
	
	public void setAuthorId(int id) {
		this.authorId = id;
	}
	
	public int getAuthorId() {
		return this.authorId;
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
	
	public ArrayList<AuthorOfArticle> getAuthorOfArticles(){
		return this.authorOfArticles;
	}
	
	//Main Author
	public void submit(Article article, PDF pdf) {
		AuthorOfArticle authorOfArticle = new AuthorOfArticle(article, this, true);
		Submission submission = new Submission(-1, article, SubmissionStatus.SUBMITTED, pdf);
		pdf.setSubmission(submission);
		authorOfArticle.getArticle().submit(submission);
		this.authorOfArticles.add(authorOfArticle);
		article.addAuthorOfArticle(authorOfArticle);
	}
	
	public void submitRevisedVersion(PDF pdf) {
		pdf.getSubmission().addVersion(pdf);
	}
	
	public void respond(Review review, ArrayList<String> answers) {
		ArrayList<Criticism> criticisms = review.getCriticisms();
		for(int i = 0; i < criticisms.size(); i++){
			criticisms.get(i).answer(answers.get(i));
		}
	}
	
	public void registerCoAuthors(Article article, ArrayList<Author> coauthors) {
		for(Author author : coauthors) {
			if(author.authorId != this.authorId) {
				AuthorOfArticle authorOfArticle = new AuthorOfArticle(article, author, false);
				author.addAuthorOfArticle(authorOfArticle);
				article.addAuthorOfArticle(authorOfArticle);
			}
		}
	}

}
