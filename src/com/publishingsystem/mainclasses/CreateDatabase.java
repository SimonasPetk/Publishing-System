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
				+ "volNum INT PRIMARY KEY AUTO_INCREMENT,"
				+ "year DATE, "
				+ "ISSN INT REFERENCES JOURNALS(ISSN))";
	}

	public static String createTableEditions() {
		return "CREATE TABLE EDITIONS ("
				+ "edNum INT PRIMARY KEY AUTO_INCREMENT, "
				+ "volNum INT REFERENCES VOLUMES(volNum), "
				+ "month DATE)";
	}

	public static String createTablePDF() {
		return "CREATE TABLE PDF ("
				+ "pdfID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "articleID INT REFERENCES ARTICLE(articleID),"
				+ "pdfLink TEXT, "
				+ "uploadDate DATE)";
	}

	public static String createTableEditors(){
		return "CREATE TABLE EDITORS ("
				+ "editorID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT references ACADEMICS(academicID))";
	}

	public static String createTableEditorOfJournal(){
		return "CREATE TABLE EDITOROFJOURNAL ("
				+ "editorID INT REFERENCES EDITORS(editorID),"
				+ "ISSN INT REFERENCES JOURNALS(ISSN),"
				+ "ChiefEditor BOOLEAN, "
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
				+ "ISSN INT REFERENCES JOURNAL(ISSN), "
				+ "title TEXT,"
				+ "summary TEXT)";
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

	public static String createTableAuthorOfArticle(){
		return "CREATE TABLE AUTHOROFARTICLE ("
				+ "authorID INT REFERENCES AUTHORS(authorID),"
				+ "articleID INT REFERENCES ARTICLES(articleID), "
				+ "mainAuthor BOOLEAN, "
				+ "PRIMARY KEY (authorID, articleID))";
	}

	public static String createTableReviewers() {
		return "CREATE TABLE REVIEWERS ("
				+ "reviewerID INT PRIMARY KEY AUTO_INCREMENT,"
				+ "academicID INT REFERENCES ACADEMIC(academicID))";
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

	public static String createTableCriticisms() {
		return "CREATE TABLE CRITICISMS ("
				+ "criticismID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "reviewerID INT REFERENCES REVIEWER(reviewerID), "
				+ "submissionID INT REFERENCES SUBMISSION(submissionID), "
				+ "criticism TEXT,"
				+ "answer TEXT)";
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

			res = statement.executeQuery("SHOW TABLES LIKE 'EDITORS';");
			if(!res.next())
				statement.execute(createTableEditors());

			res = statement.executeQuery("SHOW TABLES LIKE 'EDITOROFJOURNAL';");
			if(!res.next())
				statement.execute(createTableEditorOfJournal());

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

			res = statement.executeQuery("SHOW TABLES LIKE 'AUTHOROFARTICLE';");
			if(!res.next())
				statement.execute(createTableAuthorOfArticle());

			res = statement.executeQuery("SHOW TABLES LIKE 'REVIEWERS';");
			if(!res.next())
				statement.execute(createTableReviewers());

			res = statement.executeQuery("SHOW TABLES LIKE 'REVIEWS';");
			if(!res.next())
				statement.execute(createTableReviews());

			res = statement.executeQuery("SHOW TABLES LIKE 'CRITICISMS';");
			if(!res.next())
				statement.execute(createTableCriticisms());

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

        printAllRecords("JOURNALS");

	}

}
