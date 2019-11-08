package com.publishingsystem.mainclasses;
import java.sql.*;
import java.util.*;
import javax.sql.*; 

public class Database {
	
	public static void create () { 
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")){
			System.out.println(con);
			Statement statement = con.createStatement();
			
			statement.executeUpdate("USE team022");
			//statement.executeUpdate("DROP TABLE Academic");
            statement.executeUpdate("CREATE TABLE Academic ("
            		+ "academicId INT PRIMARY KEY, "
            		+ "title TEXT, "
            		+ "forenames TEXT, "
            		+ "surname TEXT, "
            		+ "university TEXT, "
            		+ "emailAddress TEXT, "
            		+ "passwordHash TEXT, "
            		+ "salt VARBINARY(256))");
			//statement.executeUpdate("CREATE DATABASE team022");
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
		create();
		//}
		//catch (SQLException ex) {
		 //ex.printStackTrace();
		//}
	}
}