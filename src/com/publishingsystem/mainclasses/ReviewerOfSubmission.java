package com.publishingsystem.mainclasses;

public class ReviewerOfSubmission {
	private Reviewer reviewer;
	private Submission submission;
	private Review review;
	private boolean complete;

	public ReviewerOfSubmission(Reviewer r, Submission s) {
		this.reviewer = r;
		this.submission = s;
	}
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void addReview(Review r) {
		this.review = r;
	}

	public Review getReview() {
		return review;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public Submission getSubmission() {
		return submission;
	}
}
