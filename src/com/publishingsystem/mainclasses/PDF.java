package com.publishingsystem.mainclasses;
import java.sql.Date;

public class PDF {
	private Submission submission;
	private String pdfLink;
	private String content;
	private Date date;
	private int pdfId;

	public PDF(int pdfId, String pdfLink, Date date, Submission s) {
		this.pdfId = pdfId;
		this.pdfLink = pdfLink;
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

	public String getPdfLink() {
		return pdfLink;
	}

	public void setPdfLink(String pdfLink) {
		this.pdfLink = pdfLink;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
