package com.publishingsystem.mainclasses;

public enum Verdict{
	STRONGACCEPT, WEAKACCEPT, WEAKREJECT, STRONGREJECT, NOVERDICT;
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
			case NOVERDICT:
			    return "NOVERDICT";
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
			case NOVERDICT:
			    return "No Verdict";
			default:
				return "";
		}
	}

};
