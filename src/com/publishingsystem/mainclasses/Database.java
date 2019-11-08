package com.publishingsystem.mainclasses;
import java.sql.*;
import java.util.*;
import javax.sql.*;

public class Database {

	public static void createTableAcademic () { 
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")){
			Statement statement = con.createStatement();
			statement.executeUpdate("USE team022");
			statement.executeUpdate("DROP TABLE Academic");
            statement.executeUpdate("CREATE TABLE Academic ("
            		+ "academicID INT PRIMARY KEY, "
            		+ "title TEXT, "
            		+ "forenames TEXT, "
            		+ "surname TEXT, "
            		+ "university TEXT, "
            		+ "emailAddress TEXT, "
            		+ "passwordHash TEXT, "
            		+ "salt VARBINARY(256))");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createTableJournal () {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			Statement statement = con.createStatement();
			statement.executeUpdate("USE team022");
			statement.executeUpdate("DROP TABLE Journal");
            statement.executeUpdate("CREATE TABLE Journal ("
            		+ "ISSN INT PRIMARY KEY, "
            		+ "name TEXT, "
            		+ "numberOfPages INT)");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createTableVolume () {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			Statement statement = con.createStatement();
			statement.executeUpdate("USE team022");
			statement.executeUpdate("DROP TABLE Volume");
            statement.executeUpdate("CREATE TABLE Volume ("
            		+ "volNum INT PRIMARY KEY,"
            		+ "year DATE, "
            		+ "ISSN INT references Journal(ISSN))");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createTableEdition () {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			Statement statement = con.createStatement();
			statement.executeUpdate("USE team022");
			statement.executeUpdate("DROP TABLE Edition");
            statement.executeUpdate("CREATE TABLE Edition ("
            		+ "volNum INT references Volume(volNum), "
            		+ "month DATE, "
            		+ "edNum INT PRIMARY KEY)");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createTablePDF () {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			Statement statement = con.createStatement();
			statement.executeUpdate("USE team022");
			statement.executeUpdate("DROP TABLE PDF");
            statement.executeUpdate("CREATE TABLE PDF ("
            		+ "pdfID INT PRIMARY KEY, "
            		+ "pdfLink TEXT)");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createTableSubmission () {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			Statement statement = con.createStatement();
			statement.executeUpdate("USE team022");
			//statement.executeUpdate("DROP TABLE Submission");
            statement.executeUpdate("CREATE TABLE Submission ("
            		+ "submissionID INT PRIMARY KEY, "
            		+ "title TEXT, "
            		+ "abstract Text,"
            		+ "pdfID INT references PDF(pdfID))");
			statement.close();
		}
		catch (SQLException ex) {
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
		
		//try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")){
		createTableAcademic();
		createTableJournal();
		createTableVolume();
		createTableEdition();
		createTablePDF();
		createTableSubmission();
		//}
		//catch (SQLException ex) {
		 //ex.printStackTrace();
		//}
	}
}