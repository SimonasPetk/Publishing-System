package com.publishingsystem.mainclasses;

public class ReviewerOfSubmission {
	private Reviewer reviewer;
	private Submission submission;
	
	public ReviewerOfSubmission(Reviewer r, Submission s) {
		this.reviewer = r;
		this.submission = s;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public Submission getSubmission() {
		return submission;
	}
}
