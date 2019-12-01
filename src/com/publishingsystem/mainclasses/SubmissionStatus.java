package com.publishingsystem.mainclasses;

public enum SubmissionStatus {
	SUBMITTED, REVIEWSRECEIVED, RESPONSESRECEIVED, FINALVERDICT, COMPLETED;
	
	public String asString() {
		switch(this) {
		case SUBMITTED:
			return "SUBMITTED";
		case REVIEWSRECEIVED:
			return "REVIEWSRECEIVED";
		case RESPONSESRECEIVED:
			return "RESPONSESRECEIVED";
		case FINALVERDICT:
			return "FINALVERDICT";
		case COMPLETED:
			return "COMPLETED";
		default:
			return "";
		}
	}
	
	public String getStatus() {
		switch(this) {
		case SUBMITTED:
			return "Submitted";
		case REVIEWSRECEIVED:
			return "Submitted -> Reviews & Initial verdicts recieved";
		case RESPONSESRECEIVED:
			return "Submitted -> Reviews & Initial verdicts recieved -> Responses received";
		case FINALVERDICT:
			return "Submitted -> Reviews & Initial verdicts recieved -> Responses received -> Final verdicts recieved";
		case COMPLETED:
			return "Completed";
		default:
			return "";
		}
	}
	
	public String toString() {
		switch(this) {
			case SUBMITTED:
				return "Submitted";
			case REVIEWSRECEIVED:
				return "Reviews received";
			case RESPONSESRECEIVED:
				return "Responses received";
			case FINALVERDICT:
				return "Final verdict";
			case COMPLETED:
				return "Completed";
			default:
				return "";
		}
	}
}
