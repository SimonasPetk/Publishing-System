package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer extends Academic{
	private ArrayList<Review> reviews;
	private int reviewerId;
	private AuthorOfArticle authorOfArticle;
	
	public Reviewer(int reviewerId, String title ,String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.reviews = new ArrayList<Review>();
		this.reviewerId = reviewerId;
	}
	
	//---POSSIBLY DELETE LATER---
	public Reviewer(AuthorOfArticle a) {
		super(a.getAuthor().getTitle(), a.getAuthor().getForename(), a.getAuthor().getSurname(), a.getAuthor().getEmailId(), a.getAuthor().getUniversity(), a.getAuthor().getHash());
		setAcademicId(a.getAuthor().getAcademicId());
		this.authorOfArticle = a;
		this.reviews = new ArrayList<Review>();
	}
	
	public AuthorOfArticle getAuthorOfArticle() {
		return authorOfArticle;
	}

	public void setReviewerId(int id) {
		this.reviewerId = id;
	}
	
	public int getReviewerId() {
		return this.reviewerId;
	}

	public void addReview(Submission s, Review r) {
		this.reviews.add(r);
		s.addReview(r);
	}
	
	public void setVerdict(Review r, Verdict v) {
		r.setVerdict(v);
	}
	
	public ArrayList<Review> getReviews(){
		return this.reviews;
	}
	
}
