package com.publishingsystem.mainclasses;
import java.sql.Date;

public class PDF {
	private Submission submission;
	private Date date;
	private int pdfId;

	public PDF(int pdfId, Date date, Submission s) {
		this.pdfId = pdfId;
		this.date = date;
		this.submission = s;
	}
	
	public int getPdfId() {
		return pdfId;
	}
	
	public void setPdfId(int pdfId) {
		this.pdfId = pdfId;
	}

	public void setSubmission(Submission s) {
		this.submission = s;
	}

	public Submission getSubmission() {
		return this.submission;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
