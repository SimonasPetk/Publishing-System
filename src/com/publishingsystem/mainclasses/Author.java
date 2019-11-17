package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Author extends Academic{
	private ArrayList<Submission> submissions;
	private int authorId;
	
	public Author(String title ,String forename, String surname, String emailId, String university, Hash hash) {
		super(title, forename, surname, emailId, university, hash);
		submissions = new ArrayList<Submission>();
	}
	
	public void setAuthorId(int id) {
		this.authorId = id;
	}
	
	public int getAuthorId() {
		return this.authorId;
	}

	public ArrayList<Submission> getSubmissions() {
		return this.submissions;
	}
	
	public SubmissionStatus getSubmissionStatus(Submission submission) {
		return submission.getStatus();
	}
	
	public ArrayList<Review> getReviews(Submission submission) {
		return submission.getReviews();
	}
	
	//Main Author
	public void submit(Submission submission) {
		submission.setMainAuthorId(this.authorId);
		this.submissions.add(submission);
	}
	
	public void submitRevisedVersion(Submission s, PDF pdf) {
		s.addVersion(pdf);
	}
	
	public void respond(Submission s, Reviewer reviewer, Response response) {
		s.addResponse(reviewer.getReviewerId(), response);
	}

}
