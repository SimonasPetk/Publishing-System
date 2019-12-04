package com.publishingsystem.mainclasses;

public enum Verdict{
	STRONGACCEPT, WEAKACCEPT, WEAKREJECT, STRONGREJECT;
		public String asString() {
			switch(this) {
			case STRONGACCEPT:
				return "STRONGACCEPT";
			case  WEAKACCEPT:
				return "WEAKACCEPT";
			case WEAKREJECT:
				return "WEAKREJECT";
			case STRONGREJECT:
				return "STRONGREJECT";
			default:
				return "";
		}
	}
	
	public String toString() {
		switch(this) {
			case STRONGACCEPT:
				return "Strong Accept";
			case  WEAKACCEPT:
				return "Weak Accept";
			case WEAKREJECT:
				return "Weak Reject";
			case STRONGREJECT:
				return "Strong Reject";
			default:
				return "";
		}
	}
};
