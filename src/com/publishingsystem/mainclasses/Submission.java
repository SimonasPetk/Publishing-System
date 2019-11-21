package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Submission{
	private ArrayList<Review> reviews;
	private Decision decision;
	private SubmissionStatus status;
	private int submissionId;
	private Article article;
	
	public Submission(Article a) {
		this.article = a;
		this.status = SubmissionStatus.SUBMITTED;
		this.reviews = new ArrayList<Review>();
	}
	
	public Submission(Article a, int submissionId) {
		this.submissionId = submissionId;
		this.article = a;
		this.status = SubmissionStatus.SUBMITTED;
		this.reviews = new ArrayList<Review>();
	}
	
	public Article getArticle() {
		return this.article;
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

	public void addReview(Review r) {
		this.reviews.add(r);
	}
}
