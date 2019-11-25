package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Submission{
	private ArrayList<Review> reviews;
	private Decision decision;
	private SubmissionStatus status;
	private int submissionId;
	private Article article;
	private ArrayList<PDF> versions;
	
	public Submission(int submissionId, Article a, SubmissionStatus status, PDF pdf) {
		this.submissionId = submissionId;
		this.article = a;
		this.status = status;
		this.reviews = new ArrayList<Review>();
		this.versions = new ArrayList<PDF>();
		this.versions.add(pdf);
	}
	
	public ArrayList<PDF> getVersions() {
		return versions;
	}
	
	public void addVersion(PDF pdf) {
		this.versions.add(pdf);
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
