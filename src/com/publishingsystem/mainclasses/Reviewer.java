package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer extends Author{
	private ArrayList<Review> reviews;
	private ArrayList<ReviewerOfSubmission> reviewerOfSubmissions;
	private int reviewerId;
	
	public Reviewer(int authorId, int reviewerId, String title ,String forename, String surname, String emailId, String university, Hash hash) {
		super(authorId, title, forename, surname, emailId, university, hash);
		this.reviews = new ArrayList<Review>();
		this.reviewerId = reviewerId;
	}
	
	//---POSSIBLY DELETE LATER---
	public Reviewer(Author a) {
		super(a.getAuthorId(), a.getTitle(), a.getForename(), a.getSurname(), a.getEmailId(), a.getUniversity(), a.getHash());
		setAcademicId(a.getAcademicId());
		this.reviews = new ArrayList<Review>();
		this.reviewerOfSubmissions = new ArrayList<ReviewerOfSubmission>();
	}
	
	public void addSubmissionsToReview(ArrayList<Submission> submissionsToReview) {
		for(Submission s : submissionsToReview) {
			this.reviewerOfSubmissions.add(new ReviewerOfSubmission(this, s));
		}
	}
	
	public ArrayList<ReviewerOfSubmission> getReviewerOfSubmissions() {
		return reviewerOfSubmissions;
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
