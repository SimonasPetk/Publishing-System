package com.publishingsystem.mainclasses;

public enum Verdict{
	STRONGACCEPT, WEAKACCEPT, WEAKREJECT, STRONGREJECT;
	public String toString() {
		switch(this) {
			case STRONGACCEPT:
				return "Strong Accept";
			case  WEAKACCEPT:
				return "Weak Accept";
			case WEAKREJECT:
				return "Weak Reject";
			case STRONGREJECT:
				return "Strong Rseject";
			default:
				return "";
		}
	}

};
