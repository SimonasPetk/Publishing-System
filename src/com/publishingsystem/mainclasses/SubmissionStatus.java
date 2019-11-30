package com.publishingsystem.mainclasses;

public enum SubmissionStatus {
	SUBMITTED, REVIEWSRECEIVED,
	INITIALVERDICT, RESPONSESRECEIVED, FINALVERDICT, COMPLETED;
	
	public String asString() {
		switch(this) {
		case SUBMITTED:
			return "SUBMITTED";
		case REVIEWSRECEIVED:
			return "REVIEWSRECEIVED";
		case INITIALVERDICT:
			return "INITIALVERDICT";
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
			return "Submitted -> Reviews received";
		case INITIALVERDICT:
			return "Submitted -> Reviews received -> Initial verdict";
		case RESPONSESRECEIVED:
			return "Submitted -> Reviews received -> Initial verdict -> Responses received";
		case FINALVERDICT:
			return "Submitted -> Reviews received -> Initial verdict -> Responses received -> Final verdict";
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
			case INITIALVERDICT:
				return "Initial verdict";
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
