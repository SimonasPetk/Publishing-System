package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer extends Academic{
	private ArrayList<Submission> submissionsInReview;
	private int reviewerId;
	
	public Reviewer(String title ,String forename, String surname, String emailId, String university, int reviewerId, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		this.submissionsInReview = new ArrayList<Submission>();
	}
	
	public Reviewer(Author a) {
		super(a.getTitle(), a.getForename(), a.getSurname(), a.getEmailId(), a.getUniversity(), a.getHash());
		setAcademicId(a.getAcademicId());
		this.submissionsInReview = new ArrayList<Submission>();
	}
	
	public void setReviewerId(int id) {
		this.reviewerId = id;
	}
	
	public int getReviewerId() {
		return this.reviewerId;
	}

	public void addSubmissionToReview(Submission s) {
		this.submissionsInReview.add(s);
	}
	
	public void setVerdict(Submission s, Verdict v) {
		s.setVerdict(this.reviewerId, v);
	}
	
	public void addReview(Submission s, Review r) {
		s.addReview(r);
	}
	
}
