package com.publishingsystem.mainclasses;

public class ReviewerOfSubmission {
	private Reviewer reviewer;
	private Submission submission;
	private Review review;
	
	public ReviewerOfSubmission(Reviewer r, Submission s) {
		this.reviewer = r;
		this.submission = s;
	}
	
	public void addReview(Review r) {
		this.review = review;
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
