package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer extends Academic{
	private ArrayList<Review> reviews;
	private int reviewerId;
	
	public Reviewer(String title ,String forename, String surname, String emailId, String university, int reviewerId, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.reviews = new ArrayList<Review>();
	}
	
	public Reviewer(Author a) {
		super(a.getTitle(), a.getForename(), a.getSurname(), a.getEmailId(), a.getUniversity(), a.getHash());
		setAcademicId(a.getAcademicId());
		this.reviews = new ArrayList<Review>();
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
