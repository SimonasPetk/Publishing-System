package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Submission{
	private ArrayList<ReviewerOfSubmission> reviewersOfSubmission;
	private SubmissionStatus status;
	private int submissionId;
	private Article article;
	private ArrayList<PDF> versions;
	
	public Submission(int submissionId, Article a, SubmissionStatus status, PDF pdf) {
		this.submissionId = submissionId;
		this.article = a;
		this.status = status;
		this.reviewersOfSubmission = new ArrayList<ReviewerOfSubmission>();
		this.versions = new ArrayList<PDF>();
		this.versions.add(pdf);
	}
	
	public void setReviewersOfSubmission(ArrayList<ReviewerOfSubmission> ros) {
		this.reviewersOfSubmission = ros;
	}
	
	public ArrayList<ReviewerOfSubmission> getReviewersOfSubmission() {
		return reviewersOfSubmission;
	}
	
	public void addReviewerOfSubmission(ReviewerOfSubmission ros) {
		this.reviewersOfSubmission.add(ros);
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
	
	public String toString() {
		return "Submission: "+this.article.getTitle()+" "+this.article.getSummary()+" "+this.getArticle().getJournal().getJournalName();
	}
}
