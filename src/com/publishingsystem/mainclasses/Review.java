package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Review {
	private Reviewer reviewer;
	private Submission submission;
	private String summary;
	private String typingErrors;
	private ArrayList<Criticism> criticisms;
	private Verdict verdict;
	
	public Review(Reviewer reviewer, Submission submission, String summary, String typingErrors, ArrayList<String> criticisms) {
		this.reviewer = reviewer;
		this.submission = submission;
		this.summary = summary;
		this.typingErrors = typingErrors;
		this.criticisms = new ArrayList<Criticism>();
		for(String c : criticisms) {
			this.criticisms.add(new Criticism(c));
		}
	}
	
	public void setVerdict(Verdict v) {
		this.verdict = v;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public Submission getSubmission() {
		return submission;
	}

	public String getSummary() {
		return summary;
	}

	public String getTypingErrors() {
		return typingErrors;
	}
	
	public Verdict getVerdict() {
		return this.verdict;
	}

	public ArrayList<Criticism> getCriticisms() {
		return this.criticisms;
	}
	
}
