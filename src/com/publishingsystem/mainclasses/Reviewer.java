package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer extends Academic{
	private ArrayList<ReviewerOfSubmission> reviewerOfSubmissions;
	private int reviewerId;
	
	public Reviewer(int academicId, int reviewerId, String title ,String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		setAcademicId(academicId);
		this.reviewerOfSubmissions = new ArrayList<ReviewerOfSubmission>();
		this.reviewerId = reviewerId;
	}
	
	//---POSSIBLY DELETE LATER---
	public Reviewer(Author a) {
		super(a.getTitle(), a.getForename(), a.getSurname(), a.getEmailId(), a.getUniversity(), a.getHash());
		setAcademicId(a.getAcademicId());
		this.reviewerOfSubmissions = new ArrayList<ReviewerOfSubmission>();
	}
	
	public void addSubmissionsToReview(ArrayList<Submission> submissionsToReview) {
		for(Submission s : submissionsToReview) {
			this.reviewerOfSubmissions.add(new ReviewerOfSubmission(this, s));
		}
	}
	
	public void addReviewerOfSubmission(ReviewerOfSubmission ros) {
		this.reviewerOfSubmissions.add(ros);
	}
	
	public ArrayList<ReviewerOfSubmission> getReviewerOfSubmissions() {
		return reviewerOfSubmissions;
	}

	public void setReviewerId(int id) {
		this.reviewerId = id;
	}
	
	public int getReviewerId() {
		return this.reviewerId;
	}

}
