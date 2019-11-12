package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer extends Author{
	private int reviewerId;
	private ArrayList<Submission> submissionsInReview;
	
	public Reviewer(int academicId, String title ,String forename, String surname, String emailId, String university, int reviewerId, Hash hash) {
		super(academicId, title, forename, surname, emailId, university, hash);
		this.reviewerId = reviewerId;
		this.submissionsInReview = new ArrayList<Submission>();
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
