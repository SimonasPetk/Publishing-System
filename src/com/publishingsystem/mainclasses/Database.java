package com.publishingsystem.mainclasses;
import java.sql.*;
import java.util.*;
import javax.sql.*;

public class Database {
//	private static final String connection = "jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f";
//	private static final String database = "team022";
	
	//localhost
	private static final String connection = "jdbc:mysql://localhost:3306/publishing_system?user=root&password=password";
	private static final String database = "publishing_system";
	
	public static String getConnectionName() {
		return connection;
	}
	
	public static String getDatabaseName() {
		return database;
	}
	
	public static String createTableAcademic() { 
		return "CREATE TABLE Academic ("
				+ "academicID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "title TEXT, "
				+ "forenames TEXT, "
				+ "surname TEXT, "
				+ "university TEXT, "
				+ "emailAddress TEXT, "
				+ "passwordHash TEXT, "
				+ "salt VARBINARY(256))";
	}

	public static String createTableJournal() {
		return "CREATE TABLE Journal ("
				+ "ISSN INT PRIMARY KEY, "
				+ "name TEXT, "
				+ "numberOfPages INT)";
	}

	public static String createTableVolume() {
		return "CREATE TABLE Volume ("
				+ "volNum INT PRIMARY KEY,"
				+ "year DATE, "
				+ "ISSN INT references Journal(ISSN))";
	}

	public static String createTableEdition() {
		return "CREATE TABLE Edition ("
				+ "volNum INT references Volume(volNum), "
				+ "month DATE, "
				+ "edNum INT PRIMARY KEY)";
	}

	public static String createTablePDF() {
		return "CREATE TABLE PDF ("
				+ "pdfID INT PRIMARY KEY, "
				+ "pdfLink TEXT)";
	}

	public static String createTableSubmission() {
		return "CREATE TABLE Submission ("
				+ "submissionID INT PRIMARY KEY AUTO_INCREMENT, "
				+ "mainAuthorID INT references Author(authorID), "
				+ "status TEXT)";
	}

	public static String createTableEditor(){
		return "CREATE TABLE Editor ("
				+ "editorID INT PRIMARY KEY references Academic(academicID),"
				+ "ISSN INT references Journal(ISSN))";
	}

	public static String createTableArticle(){
		return "CREATE TABLE Article ("
				+ "articleID INT PRIMARY KEY references Submission(submissionID),"
				+ "title TEXT,"
				+ "summary TEXT,"
				+ "pdf TEXT,"
				+ "pageRange INT,"
				+ "edNum INT references Edition(edNum))";
	}

	public static String createTableAuthor(){
		return "CREATE TABLE Author ("
				+ "authorID INT PRIMARY KEY NOT NULL references Academic(academicID),"
				+ "articleID INT references Article(articleID))";
	}

	public static String createTableReviewer() {
		return "CREATE TABLE Reviewer ("
				+ "reviewerID INT PRIMARY KEY references Academic(academicID),"
				+ "submissionID INT references Submission(submissionID))";
	}

	public static String createTableReview() {
		return "CREATE TABLE Review ("
				+ "reviewerID INT references Academic(academicID),"
				+ "submissionID INT references Submission(submissionID),"
				+ "summary TEXT,"
				+ "typingErrors TEXT,"
				+ "verdict TEXT,"
				+ "PRIMARY KEY (submissionID, reviewerID))";
	}

	public static String createTableResponse() {
		return "CREATE TABLE Response ("
				+ "submissionID INT references Submission(submissionID), "
				+ "reviewerID INT references Reviewer(reviewerID), "
				+ "criticismID INT references Criticism(criticismID), "
				+ "PRIMARY KEY (submissionID, reviewerID))";
	}

	public static String createTableCriticism() {
		return "CREATE TABLE Criticism ("
				+ "reviewerID INT references Academic(academicID), "
				+ "submissionID INT references Submission(submissionID), "
				+ "criticism TEXT,"
				+ "answer TEXT,"
				+ "PRIMARY KEY (SubmissionID, reviewerID))";
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
		try (Connection con = DriverManager.getConnection(connection)) {
			Statement statement = con.createStatement();
			ResultSet res = statement.executeQuery("SHOW TABLES LIKE 'Academic';");
			if(res.next())
				statement.execute("DROP TABLE ACADEMIC");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Journal';");
			if(res.next())
				statement.execute("DROP TABLE JOURNAL");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Volume';");
			if(res.next())
				statement.execute("DROP TABLE VOLUME");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Edition';");
			if(res.next())
				statement.execute("DROP TABLE EDITION");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'PDF';");
			if(res.next())
				statement.execute("DROP TABLE PDF");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Submission';");
			if(res.next())
				statement.execute("DROP TABLE SUBMISSION");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Editor';");
			if(res.next())
				statement.execute("DROP TABLE EDITOR");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Article';");
			if(res.next())
				statement.execute("DROP TABLE ARTICLE");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Author';");
			if(res.next())
				statement.execute("DROP TABLE AUTHOR");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Reviewer';");
			if(res.next())
				statement.execute("DROP TABLE REVIEWER");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Review';");
			if(res.next())
				statement.execute("DROP TABLE REVIEW");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Response';");
			if(res.next())
				statement.execute("DROP TABLE RESPONSE");
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Criticism';");
			if(res.next())
				statement.execute("DROP TABLE CRITICISM");
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createTables() {
		try (Connection con = DriverManager.getConnection(connection)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+database+";");
			ResultSet res = statement.executeQuery("SHOW TABLES LIKE 'Academic';");
			if(!res.next())
				statement.execute(createTableAcademic());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Journal';");
			if(!res.next())
				statement.execute(createTableJournal());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Volume';");
			if(!res.next())
				statement.execute(createTableVolume());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Edition';");
			if(!res.next())
				statement.execute(createTableEdition());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'PDF';");
			if(!res.next())
				statement.execute(createTablePDF());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Submission';");
			if(!res.next())
				statement.execute(createTableSubmission());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Editor';");
			if(!res.next())
				statement.execute(createTableEditor());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Article';");
			if(!res.next())
				statement.execute(createTableArticle());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Author';");
			if(!res.next())
				statement.execute(createTableAuthor());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Reviewer';");
			if(!res.next())
				statement.execute(createTableReviewer());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Review';");
			if(!res.next())
				statement.execute(createTableReview());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Response';");
			if(!res.next())
				statement.execute(createTableResponse());
			
			res = statement.executeQuery("SHOW TABLES LIKE 'Criticism';");
			if(!res.next())
				statement.execute(createTableCriticism());
			
			
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
		//createTableAcademic();
//		printAllRecords("Academic");
		
	}

}

