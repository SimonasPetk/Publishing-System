package com.publishingsystem.mainclasses;
import java.sql.*;
import java.util.*;
import javax.sql.*; 

public class DatabaseLocalhost {

	public static void main(String[] args) {
		System.out.println("\nDrivers loaded as properties:");
		System.out.println(System.getProperty("jdbc.drivers"));
		System.out.println("\nDrivers loaded by DriverManager:");
		Enumeration<Driver> list = DriverManager.getDrivers();
		while (list.hasMoreElements()) {
			System.out.println(list.nextElement());
		}
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/publishing_system?user=root&password=password")){
			//Academic
			Statement statement = con.createStatement();
			statement.executeUpdate("USE publishing_system");
			statement.executeUpdate("DROP TABLE Academic;");
			statement.executeUpdate("CREATE TABLE Academic ("
					+ "academicId INT PRIMARY KEY, "
					+ "title TEXT, "
					+ "forenames TEXT, "
					+ "surname TEXT, "
					+ "university TEXT, "
					+ "emailAddress TEXT, "
					+ "passwordHash TEXT, "
					+ "salt VARBINARY(256));");
			
			Hash h = new Hash("generic");
			statement.executeUpdate("INSERT INTO ACADEMIC VALUES("
					+ "1,"
					+ "'MR', "
					+ "'J.', "
					+ "'Smith', "
					+ "'University of Doe', "
					+ "'j.smith@doe.ac.uk', '"
					+h.getHash()
					+"', '"
					+ h.getSalt()
					+"'"
					+");");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}