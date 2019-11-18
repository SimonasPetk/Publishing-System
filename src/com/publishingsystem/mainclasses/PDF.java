package com.publishingsystem.mainclasses;
import java.sql.Date;

public class PDF {
	private String pdfLink;
	private String content;
	private Date date;
	
	public PDF(String pdfLink, Date date) {
		this.pdfLink = pdfLink;
		this.date = date;
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