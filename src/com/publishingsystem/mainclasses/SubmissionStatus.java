package com.publishingsystem.mainclasses;

public enum SubmissionStatus {
	SUBMITTED, REVIEWSRECEIVED,
	INITIALVERDICT, RESPONSESRECEIVED, FINALVERDICT, COMPLETED;
	
	public String toString() {
		switch(this) {
			case SUBMITTED:
				return "Submitted";
			case REVIEWSRECEIVED:
				return "Reviews received";
			case INITIALVERDICT:
				return "Initial verdict";
			case RESPONSESRECEIVED:
				return "Responses recieved";
			case FINALVERDICT:
				return "Final verdict";
			case COMPLETED:
				return "Completed";
			default:
				return "";
		}
	}
}
