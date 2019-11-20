package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Submission extends Article{
	private ArrayList<Review> reviews;
	private Decision decision;
	private SubmissionStatus status;
	private int submissionId;
	
	public Submission(String title, String summary, String journalName, PDF pdf) {
		super(title, summary, journalName, pdf);
		this.status = SubmissionStatus.SUBMITTED;
		this.reviews = new ArrayList<Review>();
	}
	
	public void setSubmissionId(int id) {
		this.submissionId = id;
	}
	
	public int getSubmissionId() {
		return this.submissionId;
	}
	
	public SubmissionStatus getStatus() {
		return this.status;
	}
	
	public Decision getDecision() {
		return this.decision;
	}
	
	public void setDecision(Decision d) {
		this.decision = d;
		this.status = SubmissionStatus.COMPLETED;
	}
	
	public ArrayList<Review> getReviews() {
		return this.reviews;
	}
	
	public Review getReview(int reviewerId) {
		for(Review r : this.reviews) {
			if(r.getReviewer().getReviewerId() == reviewerId) {
				return r;
			}
		}
		return null;
	}

	public void setVerdict(int reviewerId, Verdict v) {
		
		Review r = getReview(reviewerId);
		r.setVerdict(v);
		
//		if(numVerdicts == 3) {
//			if(this.status.equals(SubmissionStatus.REVIEWSRECEIVED))
//				this.status = SubmissionStatus.INITIALVERDICT;
//			else if(this.status.equals(SubmissionStatus.RESPONSESRECEIVED))
//				this.status = SubmissionStatus.FINALVERDICT;
//		}
	}

	public void addReview(Review r) {
		this.reviews.add(r);
	}
}
