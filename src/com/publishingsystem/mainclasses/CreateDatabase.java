package com.publishingsystem.mainclasses;

import java.sql.*;
import java.util.*;

public class CreateDatabase extends Database{
	private static String[] roles = {"Author", "Reviewer", "Editor"};

	public static String createTableAcademics() { 
		return "CREATE TABLE Academics ("
				+ "academicID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "title TEXT, "
				+ "forename TEXT, "
				+ "surname TEXT, "
				+ "university TEXT, "
				+ "emailAddress TEXT, "
				+ "hash BINARY(64), "
				+ "salt BINARY(32))";
	}

	public static String createTableJournals() {
		return "CREATE TABLE Journals ("
				+ "ISSN INT PRIMARY KEY, "
				+ "name TEXT, "
				+ "dateOfPublication date)";
	}

	public static String createTableVolumes() {
		return "CREATE TABLE Volumes ("
				+ "volNum INT PRIMARY KEY,"
				+ "year DATE, "
				+ "ISSN INT REFERENCES JOURNALS(ISSN))";
	}

	public static String createTableEditions() {
		return "CREATE TABLE Editions ("
				+ "volNum INT REFERENCES VOLUMES(volNum), "
				+ "month DATE, "
				+ "edNum INT PRIMARY KEY)";
	}

	public static String createTablePDF() {
		return "CREATE TABLE PDF ("
				+ "pdfID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "submissionID INT REFERENCES SUBMISSION(submissionID),"
				+ "pdfLink TEXT, "
				+ "uploadDate DATE)";
	}
	
	public static String createTableChiefEditors(){
		return "CREATE TABLE ChiefEditors ("
				+ "editorID INT references EDITORS(editorID),"
				+ "ISSN INT references JOURNALS(ISSN),"
				+ "PRIMARY KEY (editorID, ISSN))";
	}

	public static String createTableEditors(){
		return "CREATE TABLE Editors ("
				+ "editorID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT references ACADEMICS(academicID))";
	}
	
	public static String createTableEditorOf(){
		return "CREATE TABLE EditorOf ("
				+ "editorID INT REFERENCES EDITORS(editorID),"
				+ "ISSN INT REFERENCES JOURNALS(ISSN),"
				+ "PRIMARY KEY (editorID, ISSN))";
	}
	
	public static String createTablePublishedArticles() {
		return "CREATE TABLE PublishedArticles ("
				+ "articleID INT PRIMARY KEY REFERENCES ARTICLES(articleID),"
				+ "pageRange INT,"
				+ "edNum INT REFERENCES EDITION(edNum))";
	}

	public static String createTableArticles(){
		return "CREATE TABLE Articles ("
				+ "articleID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "mainAuthorID INT REFERENCES AUTHORS(authorID),"
				+ "title TEXT,"
				+ "summary TEXT,"
				+ "pdfId INT references PDF(pdfId))";
	}
	
	public static String createTableSubmissions() {
		return "CREATE TABLE Submissions ("
				+ "submissionID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "articleID INT REFERENCES ARTICLE(articleID),"
				+ "status TEXT)";
	}

	public static String createTableAuthors(){
		return "CREATE TABLE Authors ("
				+ "authorID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT REFERENCES ACADEMICS(academicID))";
	}
	
	public static String createTableAuthorOf(){
		return "CREATE TABLE AuthorOf ("
				+ "authorID INT REFERENCES AUTHORS(authorID),"
				+ "articleID INT REFERENCES ARTICLES(articleID))";
	}

	public static String createTableReviewers() {
		return "CREATE TABLE Reviewers ("
				+ "reviewerID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT REFERENCES ACADEMIC(academicID))";
	}
	
	public static String createTableReviewerOf() {
		return "CREATE TABLE ReviewerOf ("
				+ "reviewerID INT REFERENCES REVIEWERS(reviewerID),"
				+ "submissionID INT REFERENCES SUBMISSIONS(submissionID))";
	}

	public static String createTableReviews() {
		return "CREATE TABLE Reviews ("
				+ "reviewerID INT REFERENCES REVIEWERS(academicID),"
				+ "submissionID INT REFERENCES SUBMISSIONS(submissionID),"
				+ "summary TEXT,"
				+ "typingErrors TEXT,"
				+ "verdict TEXT,"
				+ "PRIMARY KEY (submissionID, reviewerID))";
	}

	public static String createTableResponses() {
		return "CREATE TABLE Responses ("
				+ "reviewerID INT REFERENCES REVIEWERS(reviewerID), "
				+ "submissionID INT REFERENCES SUBMISSIONS(submissionID), "
				+ "PRIMARY KEY (submissionID, reviewerID))";
	}

	public static String createTableRemarks() {
		return "CREATE TABLE Remarks ("
				+ "remarkID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "reviewerID INT REFERENCES REVIEWER(reviewerID), "
				+ "articleID INT REFERENCES SUBMISSION(submissionID), "
				+ "criticism TEXT,"
				+ "answer TEXT)";
	}
	
	public static String createTableAcademicRoles() {
		return "CREATE TABLE AcademicRoles ("
				+ "academicID INT REFERENCES ACADEMICS(academicID), "
				+ "roleID INT REFERENCES ROLES(roleID))";
	}
	
	public static String createTableRoles() {
		return "CREATE TABLE Roles ("
				+ "roleID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "role TEXT)";
	}
	
	public static String fillTableRoles() {
		String insert = "INSERT INTO ROLES VALUES";
		for(int i = 0; i < roles.length; i++) {
			insert+= "(null,'"+ roles[i] + "')";
			if(i < roles.length-1)
				insert+=",";
		}
		insert+=";";
		return insert;
	}

	public static void printAllRecords(String tblName) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			Statement statement = con.createStatement();
			statement.execute("USE team022;");
			ResultSet rs = statement.executeQuery("SELECT * FROM " + tblName);
			//printResultSet(rs);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			for (int i = 1; i <= columnsNumber; i++) System.out.print(rsmd.getColumnName(i) + "   ");
			System.out.println();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
				}
				System.out.println("");
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


	public static void dropTables() {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			ResultSet res = statement.executeQuery("SHOW TABLES LIKE 'Academics';");
			if(res.next())
				statement.execute("DROP TABLE ACADEMICS");

			res = statement.executeQuery("SHOW TABLES LIKE 'Journals';");
			if(res.next())
				statement.execute("DROP TABLE JOURNALS");

			res = statement.executeQuery("SHOW TABLES LIKE 'Volumes';");
			if(res.next())
				statement.execute("DROP TABLE VOLUMES");

			res = statement.executeQuery("SHOW TABLES LIKE 'Editions';");
			if(res.next())
				statement.execute("DROP TABLE EDITIONS");

			res = statement.executeQuery("SHOW TABLES LIKE 'PDF';");
			if(res.next())
				statement.execute("DROP TABLE PDF");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ChiefEditors';");
			if(res.next())
				statement.execute("DROP TABLE CHIEFEDITORS");

			res = statement.executeQuery("SHOW TABLES LIKE 'Editors';");
			if(res.next())
				statement.execute("DROP TABLE EDITORS");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'EditorOf';");
			if(res.next())
				statement.execute("DROP TABLE EDITOROF");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'PublishedArticles';");
			if(res.next())
				statement.execute("DROP TABLE PUBLISHEDARTICLES");

			res = statement.executeQuery("SHOW TABLES LIKE 'Articles';");
			if(res.next())
				statement.execute("DROP TABLE ARTICLES");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Submissions';");
			if(res.next())
				statement.execute("DROP TABLE SUBMISSIONS");

			res = statement.executeQuery("SHOW TABLES LIKE 'Authors';");
			if(res.next())
				statement.execute("DROP TABLE AUTHORS");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'AuthorOf';");
			if(res.next())
				statement.execute("DROP TABLE AUTHOROF");

			res = statement.executeQuery("SHOW TABLES LIKE 'Reviewers';");
			if(res.next())
				statement.execute("DROP TABLE REVIEWERS");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ReviewerOf';");
			if(res.next())
				statement.execute("DROP TABLE REVIEWEROF");

			res = statement.executeQuery("SHOW TABLES LIKE 'Reviews';");
			if(res.next())
				statement.execute("DROP TABLE REVIEWS");

			res = statement.executeQuery("SHOW TABLES LIKE 'Responses';");
			if(res.next())
				statement.execute("DROP TABLE RESPONSES");

			res = statement.executeQuery("SHOW TABLES LIKE 'Remarks';");
			if(res.next())
				statement.execute("DROP TABLE REMARKS");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Roles';");
			if(res.next())
				statement.execute("DROP TABLE ROLES");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'AcademicRoles';");
			if(res.next())
				statement.execute("DROP TABLE ACADEMICROLES");
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void createTables() {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			ResultSet res = statement.executeQuery("SHOW TABLES LIKE 'Academics';");
			if(!res.next())
				statement.execute(createTableAcademics());

			res = statement.executeQuery("SHOW TABLES LIKE 'Journals';");
			if(!res.next())
				statement.execute(createTableJournals());

			res = statement.executeQuery("SHOW TABLES LIKE 'Volumes';");
			if(!res.next())
				statement.execute(createTableVolumes());

			res = statement.executeQuery("SHOW TABLES LIKE 'Editions';");
			if(!res.next())
				statement.execute(createTableEditions());

			res = statement.executeQuery("SHOW TABLES LIKE 'PDF';");
			if(!res.next())
				statement.execute(createTablePDF());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ChiefEditors';");
			if(!res.next())
				statement.execute(createTableChiefEditors());

			res = statement.executeQuery("SHOW TABLES LIKE 'Editors';");
			if(!res.next())
				statement.execute(createTableEditors());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'EditorOf';");
			if(!res.next())
				statement.execute(createTableEditorOf());

			res = statement.executeQuery("SHOW TABLES LIKE 'PublishedArticles';");
			if(!res.next())
				statement.execute(createTablePublishedArticles());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Articles';");
			if(!res.next())
				statement.execute(createTableArticles());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Submissions';");
			if(!res.next())
				statement.execute(createTableSubmissions());


			res = statement.executeQuery("SHOW TABLES LIKE 'Authors';");
			if(!res.next())
				statement.execute(createTableAuthors());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'AuthorOf';");
			if(!res.next())
				statement.execute(createTableAuthorOf());

			res = statement.executeQuery("SHOW TABLES LIKE 'Reviewers';");
			if(!res.next())
				statement.execute(createTableReviewers());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ReviewerOf';");
			if(!res.next())
				statement.execute(createTableReviewerOf());

			res = statement.executeQuery("SHOW TABLES LIKE 'Reviews';");
			if(!res.next())
				statement.execute(createTableReviews());

			res = statement.executeQuery("SHOW TABLES LIKE 'Responses';");
			if(!res.next())
				statement.execute(createTableResponses());

			res = statement.executeQuery("SHOW TABLES LIKE 'Remarks';");
			if(!res.next())
				statement.execute(createTableRemarks());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Roles';");
			if(!res.next()) {
				statement.execute(createTableRoles());
				statement.execute(fillTableRoles());
			}
			
			res = statement.executeQuery("SHOW TABLES LIKE 'AcademicRoles';");
			if(!res.next())
				statement.execute(createTableAcademicRoles());


		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("\nDrivers loaded as properties:");
		System.out.println(System.getProperty("jdbc.drivers"));
		System.out.println("\nDrivers loaded by DriverManager:");
		Enumeration<Driver> list = DriverManager.getDrivers();
		while (list.hasMoreElements())
			System.out.println(list.nextElement());
		System.out.println();

		dropTables();
		createTables();

	}
	
}