package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Review {
	private ReviewerOfSubmission reviewerOfSubmission;
	private String summary;
	private String typingErrors;
	private ArrayList<Criticism> criticisms;
	private Verdict verdict;
	
	public Review(ReviewerOfSubmission ros, String summary, String typingErrors, ArrayList<Criticism> criticisms, Verdict v) {
		this.reviewerOfSubmission = ros;
		this.summary = summary;
		this.typingErrors = typingErrors;
		this.criticisms = new ArrayList<Criticism>();
		this.criticisms = criticisms;
		this.verdict = v;
	}

	public void setVerdict(Verdict v) {
		this.verdict = v;
	}

	public ReviewerOfSubmission getReviewerOfSubmission() {
		return reviewerOfSubmission;
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
	
	public String toString() {
		String criticismString = "";
		for(Criticism c : this.criticisms) {
			criticismString += "Q: "+c.getCriticism()+"\n"+"A: "+c.getAnswer()+"\n";
		}
		return "Summary: "+this.summary+"\n"+"Typing Errors: "+this.typingErrors+" \n"+criticismString;
	}
	
}
