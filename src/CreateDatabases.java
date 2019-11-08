import java.sql.*;
import java.util.*;
import javax.sql.*; 

public class CreateDatabases {
	
	public void create () { 
		try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")){
			Statement statement = con.createStatement();
			statement.executeUpdate("CREATE TABLE Academic (academicId INT PRIMARY KEY, title TEXT, forenames TEXT, surname TEXT, "
					+ "university TEXT, emailAddress TEXT, passwordHash TEXT, BYTE[256]");
			statement.close();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main () {
		this.create();
	}
}

