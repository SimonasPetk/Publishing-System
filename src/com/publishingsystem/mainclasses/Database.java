package com.publishingsystem.mainclasses;
import java.sql.*;
import java.util.*;
import javax.sql.*; 

public class Database {
	public static void main(String[] args) {
		System.out.println("\nDrivers loaded as properties:");
		System.out.println(System.getProperty("jdbc.drivers"));
		System.out.println("\nDrivers loaded by DriverManager:");
		Enumeration<Driver> list = DriverManager.getDrivers();
		while (list.hasMoreElements())
			System.out.println(list.nextElement());
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")){
			
			// use the open connection
			// for several queries
		}
		catch (SQLException ex) {
		 ex.printStackTrace();
		}
	}
}