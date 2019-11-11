package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Author extends Academic{
	protected ArrayList<Submission> submissions;
	
	public Author(int academicId, String title ,String forename, String surname, String emailId, String university) {
		super(academicId, title, forename, surname, emailId, university);
		submissions = new ArrayList<Submission>();
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
	public void submitArticle(Submission submission) {
		this.submissions.add(submission);
	}
	
	public void respond(Submission submission, int reviewerId, Response response) {
		submission.addResponse(reviewerId, response);
	}

}
