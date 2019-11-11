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
		//try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/publishing_system?user=root&password=password")){
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/publishing_system", "root", "password")) {
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
			
			ResultSet res = statement.executeQuery("SELECT passwordHash, salt FROM Academic WHERE academicId = 1;");
			while(res.next()) {
				String hash = res.getString("passwordHash");
				String salt = res.getString("salt");
				Hash newH = new Hash("generic", salt);
				System.out.println(newH.getHash()+" "+hash+" "+ (newH.getHash().equals(hash)));
			}
			
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}