package com.publishingsystem.mainclasses;

public enum Verdict{
	STRONGACCEPT, WEAKACCEPT, WEAKREJECT, STRONGREJECT;
	public String toString() {
		switch(this) {
			case STRONGACCEPT:
				return "Strong accept";
			case  WEAKACCEPT:
				return "Weak accept";
			case WEAKREJECT:
				return "Weak reject";
			case STRONGREJECT:
				return "Strong reject";
			default:
				return "";
		}
	}

};
