package com.publishingsystem.mainclasses;

import java.sql.*;
import java.util.*;

public class CreateDatabase extends Database{
	private static String[] roles = {"Author", "Reviewer", "Editor"};

	public static String createTableAcademics() { 
		return "CREATE TABLE ACADEMICS ("
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
		return "CREATE TABLE JOURNALS ("
				+ "ISSN INT PRIMARY KEY, "
				+ "name TEXT, "
				+ "dateOfPublication date)";
	}

	public static String createTableVolumes() {
		return "CREATE TABLE VOLUMES ("
				+ "volNum INT PRIMARY KEY,"
				+ "year DATE, "
				+ "ISSN INT REFERENCES JOURNALS(ISSN))";
	}

	public static String createTableEditions() {
		return "CREATE TABLE EDITIONS ("
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
		return "CREATE TABLE CHIEFEDITORS ("
				+ "editorID INT references EDITORS(editorID),"
				+ "ISSN INT references JOURNALS(ISSN),"
				+ "PRIMARY KEY (editorID, ISSN))";
	}

	public static String createTableEditors(){
		return "CREATE TABLE EDITORS ("
				+ "editorID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT references ACADEMICS(academicID))";
	}
	
	public static String createTableEditorOf(){
		return "CREATE TABLE EDITOROF ("
				+ "editorID INT REFERENCES EDITORS(editorID),"
				+ "ISSN INT REFERENCES JOURNALS(ISSN),"
				+ "PRIMARY KEY (editorID, ISSN))";
	}
	
	public static String createTablePublishedArticles() {
		return "CREATE TABLE PUBLISHEDARTICLES ("
				+ "articleID INT PRIMARY KEY REFERENCES ARTICLES(articleID),"
				+ "pageRange INT,"
				+ "edNum INT REFERENCES EDITION(edNum))";
	}

	public static String createTableArticles(){
		return "CREATE TABLE ARTICLES ("
				+ "articleID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "mainAuthorID INT REFERENCES AUTHORS(authorID),"
				+ "title TEXT,"
				+ "summary TEXT,"
				+ "pdfId INT references PDF(pdfId))";
	}
	
	public static String createTableSubmissions() {
		return "CREATE TABLE SUBMISSIONS ("
				+ "submissionID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "articleID INT REFERENCES ARTICLE(articleID),"
				+ "status TEXT)";
	}

	public static String createTableAuthors(){
		return "CREATE TABLE AUTHORS ("
				+ "authorID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT REFERENCES ACADEMICS(academicID))";
	}
	
	public static String createTableAuthorOf(){
		return "CREATE TABLE AUTHOROF ("
				+ "authorID INT REFERENCES AUTHORS(authorID),"
				+ "articleID INT REFERENCES ARTICLES(articleID))";
	}

	public static String createTableReviewers() {
		return "CREATE TABLE REVIEWERS ("
				+ "reviewerID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT REFERENCES ACADEMIC(academicID))";
	}
	
	public static String createTableReviewerOf() {
		return "CREATE TABLE REVIEWEROF ("
				+ "reviewerID INT REFERENCES REVIEWERS(reviewerID),"
				+ "submissionID INT REFERENCES SUBMISSIONS(submissionID))";
	}

	public static String createTableReviews() {
		return "CREATE TABLE REVIEWS ("
				+ "reviewerID INT REFERENCES REVIEWERS(academicID),"
				+ "submissionID INT REFERENCES SUBMISSIONS(submissionID),"
				+ "summary TEXT,"
				+ "typingErrors TEXT,"
				+ "verdict TEXT,"
				+ "PRIMARY KEY (submissionID, reviewerID))";
	}

	public static String createTableResponses() {
		return "CREATE TABLE RESPONSES ("
				+ "reviewerID INT REFERENCES REVIEWERS(reviewerID), "
				+ "submissionID INT REFERENCES SUBMISSIONS(submissionID), "
				+ "PRIMARY KEY (submissionID, reviewerID))";
	}

	public static String createTableRemarks() {
		return "CREATE TABLE REMARKS ("
				+ "remarkID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "reviewerID INT REFERENCES REVIEWER(reviewerID), "
				+ "articleID INT REFERENCES SUBMISSION(submissionID), "
				+ "criticism TEXT,"
				+ "answer TEXT)";
	}
	
	public static String createTableAcademicRoles() {
		return "CREATE TABLE ACADEMICROLES ("
				+ "academicID INT REFERENCES ACADEMICS(academicID), "
				+ "roleID INT REFERENCES ROLES(roleID))";
	}
	
	public static String createTableRoles() {
		return "CREATE TABLE ROLES("
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
			statement.execute("USE "+DATABASE+";");
			String query = "SHOW TABLES";
			ArrayList<String> tables = new ArrayList<String>();
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				ResultSet res = preparedStmt.executeQuery();
				while(res.next()) {
					tables.add(res.getString(1));
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			for(String table: tables) {
				query = "DROP TABLE "+table;
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void createTables() {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			ResultSet res = statement.executeQuery("SHOW TABLES LIKE 'ACADEMICS';");
			if(!res.next())
				statement.execute(createTableAcademics());

			res = statement.executeQuery("SHOW TABLES LIKE 'JOURNALS';");
			if(!res.next())
				statement.execute(createTableJournals());

			res = statement.executeQuery("SHOW TABLES LIKE 'VOLUMES';");
			if(!res.next())
				statement.execute(createTableVolumes());

			res = statement.executeQuery("SHOW TABLES LIKE 'EDITIONS';");
			if(!res.next())
				statement.execute(createTableEditions());

			res = statement.executeQuery("SHOW TABLES LIKE 'PDF';");
			if(!res.next())
				statement.execute(createTablePDF());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'CHIEFEDITORS';");
			if(!res.next())
				statement.execute(createTableChiefEditors());

			res = statement.executeQuery("SHOW TABLES LIKE 'EDITORS';");
			if(!res.next())
				statement.execute(createTableEditors());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'EDITOROF';");
			if(!res.next())
				statement.execute(createTableEditorOf());

			res = statement.executeQuery("SHOW TABLES LIKE 'PUBLISHEDARTICLES';");
			if(!res.next())
				statement.execute(createTablePublishedArticles());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ARTICLES';");
			if(!res.next())
				statement.execute(createTableArticles());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'SUBMISSIONS';");
			if(!res.next())
				statement.execute(createTableSubmissions());


			res = statement.executeQuery("SHOW TABLES LIKE 'AUTHORS';");
			if(!res.next())
				statement.execute(createTableAuthors());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'AUTHOROF';");
			if(!res.next())
				statement.execute(createTableAuthorOf());

			res = statement.executeQuery("SHOW TABLES LIKE 'REVIEWERS';");
			if(!res.next())
				statement.execute(createTableReviewers());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'REVIEWEROF';");
			if(!res.next())
				statement.execute(createTableReviewerOf());

			res = statement.executeQuery("SHOW TABLES LIKE 'REVIEWS';");
			if(!res.next())
				statement.execute(createTableReviews());

			res = statement.executeQuery("SHOW TABLES LIKE 'RESPONSES';");
			if(!res.next())
				statement.execute(createTableResponses());

			res = statement.executeQuery("SHOW TABLES LIKE 'REMARKS';");
			if(!res.next())
				statement.execute(createTableRemarks());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ROLES';");
			if(!res.next()) {
				statement.execute(createTableRoles());
				statement.execute(fillTableRoles());
			}
			
			res = statement.executeQuery("SHOW TABLES LIKE 'ACADEMICROLES';");
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

		//dropTables();
		//createTables();
	
        printAllRecords("JOURNALS");
        
	}
	
}