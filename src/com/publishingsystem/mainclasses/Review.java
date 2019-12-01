package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Review {
	private ReviewerOfSubmission reviewerOfSubmission;
	private String summary;
	private String typingErrors;
	private ArrayList<Criticism> criticisms;
	private Verdict initialVerdict;
	private Verdict finalVerdict;
	
	public Review(ReviewerOfSubmission ros, String summary, String typingErrors, ArrayList<Criticism> criticisms, Verdict v) {
		this.reviewerOfSubmission = ros;
		this.summary = summary;
		this.typingErrors = typingErrors;
		this.criticisms = new ArrayList<Criticism>();
		this.criticisms = criticisms;
		this.initialVerdict = v;
	}

	public void setFinalVerdict(Verdict v) {
		this.finalVerdict = v;
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
	
	public Verdict getInitialVerdict() {
		return this.initialVerdict;
	}
	
	public Verdict getFinalVerdict() {
		return this.finalVerdict;
	}

	public ArrayList<Criticism> getCriticisms() {
		return this.criticisms;
	}
	
	public void answer(ArrayList<String> answers) {
		for(int i = 0; i < answers.size(); i++) {
			this.criticisms.get(i).answer(answers.get(i));
		}
	}
	
	public String toString() {
		String criticismString = "";
		for(Criticism c : this.criticisms) {
			criticismString += "Q: "+c.getCriticism()+"\n"+"A: "+c.getAnswer()+"\n";
		}
		return "Summary: "+this.summary+"\n"+"Typing Errors: "+this.typingErrors+" \n"+criticismString;
	}
	
	public boolean responsesRecieved() {
		boolean recieved = true;
		for(Criticism c : this.criticisms) {
			if(c.getAnswer() == null)
				recieved = false;
		}
		return recieved;
	}
	
}
