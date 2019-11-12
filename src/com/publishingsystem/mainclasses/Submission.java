package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Submission extends Article{
	private ArrayList<Review> reviews;
	private int submissionId;
	private int mainAuthorId;
	private Decision decision;
	private SubmissionStatus status;
	
	public Submission(ArrayList<Author> authors, String title, String summary, int submissionId, String pdf, int journalId) {
		super(authors, title, summary, submissionId, journalId, pdf);
		this.submissionId = submissionId;
		this.status = SubmissionStatus.SUBMITTED;
		this.reviews = new ArrayList<Review>();
	}
	
	public void setMainAuthorId(int id) {
		this.mainAuthorId = id;
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
	
	public int getMainAuthorId() {
		return this.mainAuthorId;
	}
	
	public ArrayList<Review> getReviews() {
		return this.reviews;
	}

	public int getSubmissionId() {
		return this.submissionId;
	}
	
	public void setVerdict(int reviewerId, Verdict v) {
		int numVerdicts = 0;
		for(Review r : this.reviews) {
			if(r.getReviewerId() == reviewerId) {
				r.setVerdict(v);
			}
			if(r.getVerdict() != null)
				numVerdicts++;
		}
		
		if(numVerdicts == 3) {
			if(this.status.equals(SubmissionStatus.REVIEWSRECEIVED))
				this.status = SubmissionStatus.INITIALVERDICT;
			else if(this.status.equals(SubmissionStatus.RESPONSESRECEIVED))
				this.status = SubmissionStatus.FINALVERDICT;
		}
	}

	public void addReview(Review r) {
		if(this.reviews.size() < 3)
			this.reviews.add(r);
		if(this.reviews.size() == 3) {
			this.status = SubmissionStatus.REVIEWSRECEIVED;
		}
	}
	
	public void addResponse(int reviewerId, Response response) {
		int numResponses = 0;
		for(Review r : this.reviews) {
			if(r.getReviewerId() == reviewerId) {
				r.addResponse(response);
			}
			if(r.getResponse() != null)
				numResponses++;
		}
		if(numResponses == 3) {
			this.status = SubmissionStatus.RESPONSESRECEIVED;
		}
	}
}
