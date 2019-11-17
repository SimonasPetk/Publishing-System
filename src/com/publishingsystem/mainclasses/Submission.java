package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Submission extends Article{
	private ArrayList<Review> reviews;
	private Decision decision;
	private SubmissionStatus status;
	private int submissionId;
	private ArrayList<PDF> versions;
	
	public Submission(String title, String summary, int journalId, ArrayList<Author> authors, PDF pdf) {
		super(title, summary, journalId, authors);
		this.status = SubmissionStatus.SUBMITTED;
		this.reviews = new ArrayList<Review>();
		this.versions = new ArrayList<PDF>();
		this.versions.add(pdf);
	}
	
	public void addVersion(PDF pdf) {
		this.versions.add(pdf);
	}
	
	public ArrayList<PDF> getVersions() {
		return this.versions;
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
			if(r.getReviewerId() == reviewerId) {
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
		if(this.reviews.size() < 3)
			this.reviews.add(r);
		if(this.reviews.size() == 3) {
			this.status = SubmissionStatus.REVIEWSRECEIVED;
		}
	}
	
	public void addResponse(int reviewerId, Response response) {
		Review r = getReview(reviewerId);
		r.addResponse(response);
//		if(numResponses == 3) {
//			this.status = SubmissionStatus.RESPONSESRECEIVED;
//		}
	}
}
