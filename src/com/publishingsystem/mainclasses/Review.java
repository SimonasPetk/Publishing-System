package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Review {
	private Reviewer reviewer;
	private Submission submission;
	private Article article;
	private String summary;
	private String typingErrors;
	private ArrayList<Criticism> criticisms;
	private Verdict verdict;
	
	public Review(Reviewer reviewer, Submission submission, String summary, String typingErrors, ArrayList<Criticism> criticisms, Article article) {
		this.reviewer = reviewer;
		this.submission = submission;
		this.summary = summary;
		this.typingErrors = typingErrors;
		this.criticisms = new ArrayList<Criticism>();
		this.criticisms = criticisms;
		this.article = article;
	}
	
	public Article getArticle() {
		return article;
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
	
	public String toString() {
		String criticismString = "";
		for(Criticism c : this.criticisms) {
			criticismString += "Q: "+c.getCriticism()+"\n"+"A: "+c.getAnswer()+"\n";
		}
		return "Summary: "+this.summary+"\n"+"Typing Errors: "+this.typingErrors+" \n"+criticismString;
	}
	
}
