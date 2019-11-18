package com.publishingsystem.mainclasses;
import java.sql.*;
import java.util.*;

public class Database {
	protected static final String CONNECTION = "jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f";
	protected static final String DATABASE = "team022";

//	//localhost
//	protected static final String CONNECTION = "jdbc:mysql://localhost:3306/publishing_system?user=root&password=password";
//	protected static final String DATABASE = "publishing_system";

	public static String getConnectionName() {
		return CONNECTION;
	}

	public static String getDatabaseName() {
		return DATABASE;
	}

	public static void registerEditors(ArrayList<Editor> editors) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			for(Editor e : editors) {
				String query = "INSERT INTO ACADEMICS values (null, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, e.getTitle());
				preparedStmt.setString(2, e.getForename());
				preparedStmt.setString(3, e.getSurname());
				preparedStmt.setString(4, e.getUniversity());
				preparedStmt.setString(5, e.getEmailId());
				preparedStmt.setString(6, e.getHash().getHash());
				preparedStmt.setString(7, e.getHash().getSalt());

				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ACADEMICS");
				while(rs.next())
					e.setAcademicId(Integer.valueOf(rs.getString("last_id")));

				//Add editor to editor table
				query = "INSERT INTO EDITORS values (null, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, e.getAcademicId());
				preparedStmt.execute();

				rs = preparedStmt.executeQuery("select last_insert_id() as last_id from EDITORS");
				while(rs.next())
					e.setEditorId(Integer.valueOf(rs.getString("last_id")));

				int roleId = 0;
				query = "SELECT roleID from ROLES where ROLE = 'Author'";
				preparedStmt = con.prepareStatement(query);
				rs = preparedStmt.executeQuery();
				if(rs.next())
					roleId = rs.getInt("roleID");


				query = "INSERT INTO ACADEMICROLES values (?, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, e.getAcademicId());
				preparedStmt.setInt(2,roleId);
				preparedStmt.execute();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void registerAuthors(ArrayList<Author> authors) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			for(Author a : authors) {
				String query = "INSERT INTO ACADEMICS values (null, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, a.getTitle());
				preparedStmt.setString(2, a.getForename());
				preparedStmt.setString(3, a.getSurname());
				preparedStmt.setString(4, a.getUniversity());
				preparedStmt.setString(5, a.getEmailId());
				preparedStmt.setString(6, a.getHash().getHash());
				preparedStmt.setString(7, a.getHash().getSalt());

				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ACADEMICS");
				while(rs.next())
					a.setAcademicId(Integer.valueOf(rs.getString("last_id")));

				query = "INSERT INTO AUTHORS values (null, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, a.getAcademicId());
				preparedStmt.execute();

				rs = preparedStmt.executeQuery("select last_insert_id() as last_id from AUTHORS");
				while(rs.next())
					a.setAuthorId((Integer.valueOf(rs.getString("last_id"))));

				int roleId = 0;
				query = "SELECT roleID from ROLES where ROLE = 'Editor'";
				preparedStmt = con.prepareStatement(query);
				rs = preparedStmt.executeQuery();
				if(rs.next())
					roleId = rs.getInt("roleID");


				query = "INSERT INTO ACADEMICROLES values (?, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, a.getAcademicId());
				preparedStmt.setInt(2, roleId);
				preparedStmt.execute();

				rs = preparedStmt.executeQuery("select last_insert_id() as last_id from AUTHORS");
				while(rs.next())
					a.setAuthorId((Integer.valueOf(rs.getString("last_id"))));
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addJournal(Journal j) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){

			//Add journal to journal table
			String query = "INSERT INTO JOURNALS values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, j.getISSN());
			preparedStmt.setString(2, j.getJournalName());
			preparedStmt.setDate(3, j.getDateOfPublication());
			preparedStmt.execute();

			for(Editor e : j.getBoardOfEditors()) {	
				if(e instanceof ChiefEditor) {
					query = "INSERT INTO CHIEFEDITORS values (?, ?)";
					preparedStmt = con.prepareStatement(query);
					preparedStmt.setInt(1, e.getEditorId());
					preparedStmt.setInt(2, j.getISSN());
					preparedStmt.execute();
				}

				query = "INSERT INTO EDITOROF values (?, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, e.getEditorId());
				preparedStmt.setInt(2, j.getISSN());
				preparedStmt.execute();

			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addSubmission(Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			//Add submission to article table
			String query = "INSERT INTO ARTICLES values (null, ?, ?, ?, null)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, s.getMainAuthorId());
			preparedStmt.setString(2, s.getTitle());
			preparedStmt.setString(3, s.getSummary());
			preparedStmt.execute();

			ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ARTICLES");
			while(rs.next())
				s.setArticleId(Integer.valueOf(rs.getString("last_id")));

			query = "INSERT INTO SUBMISSIONS values (null, ?, ?)";

			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, s.getArticleId());
			preparedStmt.setString(2, SubmissionStatus.SUBMITTED.asString());
			preparedStmt.execute();

			rs = preparedStmt.executeQuery("select last_insert_id() as last_id from SUBMISSIONS");
			while(rs.next())
				s.setSubmissionId(Integer.valueOf(rs.getString("last_id")));

			for(Author a : s.getAuthors()) {
				query = "INSERT INTO AUTHOROF values (?, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, a.getAuthorId());
				preparedStmt.setInt(2, s.getArticleId());
				preparedStmt.execute();

			}

			ArrayList<PDF> pdfs = s.getVersions();
			PDF pdf = pdfs.get(pdfs.size()-1);
			query = "INSERT INTO PDF values (null, ?, ?, ?)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, s.getSubmissionId());
			preparedStmt.setString(2, pdf.getPdfLink());
			preparedStmt.setDate(3, pdf.getDate());

			preparedStmt.execute();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addSubmissionToReview(Reviewer r, Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			String query = "INSERT INTO REVIEWEROF values (?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, r.getReviewerId());
			preparedStmt.setInt(2, s.getSubmissionId());
			preparedStmt.execute();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addReviewers(ArrayList<Reviewer> reviewers) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			for(Reviewer r : reviewers) {
				String query = "INSERT INTO REVIEWERS values (null, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, r.getAcademicId());
				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from REVIEWERS");
				while(rs.next())
					r.setReviewerId(Integer.valueOf(rs.getString("last_id")));

				int roleId = 0;
				query = "SELECT roleID from ROLES where ROLE = 'Reviewer'";
				preparedStmt = con.prepareStatement(query);
				rs = preparedStmt.executeQuery();
				if(rs.next())
					roleId = rs.getInt("roleID");


				query = "INSERT INTO ACADEMICROLES values (?, ?)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, r.getAcademicId());
				preparedStmt.setInt(2, roleId);
				preparedStmt.execute();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addReview(Review review) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			String query = "INSERT INTO REVIEWS values (?, ?, ?, ?, null)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, review.getReviewerId());
			preparedStmt.setInt(2, review.getSubmissionId());
			preparedStmt.setString(3, review.getSummary());
			preparedStmt.setString(4, review.getTypingErrors());

			preparedStmt.execute();

			for(Remark remark : review.getRemarks()) {
				query = "INSERT INTO REMARKS values (null, ?, ?, ?, null)";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, review.getReviewerId());
				preparedStmt.setInt(2, review.getSubmissionId());
				preparedStmt.setString(3, remark.getCriticism());

				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from REMARKS");
				while(rs.next())
					remark.setRemarkId((Integer.valueOf(rs.getString("last_id"))));
			}

			query = "SELECT COUNT(*) AS REVIEWS FROM REVIEWS WHERE submissionID = ?";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, review.getSubmissionId());
			ResultSet rs = preparedStmt.executeQuery();
			int numReviews = 0;
			while(rs.next())
				numReviews = rs.getInt("REVIEWS");

			if(numReviews == 3) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, SubmissionStatus.REVIEWSRECEIVED.asString());
				preparedStmt.setInt(2, review.getSubmissionId());

				preparedStmt.execute();
			}

		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void setVerdict(Reviewer r, Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			String query = "UPDATE REVIEWS SET verdict = ? WHERE reviewerID = ? and submissionID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, s.getReview(r.getReviewerId()).getVerdict().toString());
			preparedStmt.setInt(2, r.getReviewerId());
			preparedStmt.setInt(3, s.getSubmissionId());

			preparedStmt.execute();

			
			int numVerdicts = 0;
			SubmissionStatus status = null;
			
			query = "SELECT REVIEWS.SUBMISSIONID, COUNT(*) AS REVIEWS, STATUS FROM REVIEWS, "
					+ "SUBMISSIONS WHERE REVIEWS.submissionID = ? AND SUBMISSIONS.submissionID = ? "
					+ "AND verdict IS NOT NULL";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, s.getSubmissionId());
			preparedStmt.setInt(2, s.getSubmissionId());
			try(ResultSet rs = preparedStmt.executeQuery()){
				while(rs.next()) {
					numVerdicts = rs.getInt("REVIEWS");
					status = SubmissionStatus.valueOf(rs.getString("Status"));
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			if(numVerdicts == 3 && status.equals(SubmissionStatus.REVIEWSRECEIVED)){
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try(PreparedStatement ps = con.prepareStatement(query)){
					ps.setString(1, SubmissionStatus.INITIALVERDICT.asString());
					ps.setInt(2, s.getSubmissionId());
					ps.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}

			}else if(numVerdicts == 3 && status.equals(SubmissionStatus.RESPONSESRECEIVED)) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try(PreparedStatement ps = con.prepareStatement(query)){
					ps.setString(1, SubmissionStatus.FINALVERDICT.asString());
					ps.setInt(2, s.getSubmissionId());
					ps.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addResponse(Reviewer r, Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Review review = s.getReview(r.getReviewerId());

			String query = "INSERT INTO RESPONSES VALUES (?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, r.getReviewerId());
			preparedStmt.setInt(2, s.getSubmissionId());
			preparedStmt.execute();	
			
			query = "SELECT COUNT(*) AS RESPONSES FROM RESPONSES WHERE submissionID = ?";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, review.getSubmissionId());
			ResultSet rs = preparedStmt.executeQuery();
			int numResponses = 0;
			while(rs.next())
				numResponses = rs.getInt("RESPONSES");

			if(numResponses == 3) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, SubmissionStatus.RESPONSESRECEIVED.asString());
				preparedStmt.setInt(2, review.getSubmissionId());

				preparedStmt.execute();
			}

			ArrayList<String> answers = review.getResponse().getAnswers();
			ArrayList<Remark> remarks = review.getRemarks();
			for(int i = 0; i < remarks.size(); i++) {
				query = "UPDATE REMARKS SET ANSWER = ? WHERE remarkID = ?";
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, answers.get(i));
				preparedStmt.setInt(2, remarks.get(i).getRemarkId());

				preparedStmt.execute();	
			}

			ArrayList<PDF> pdfs = s.getVersions();
			PDF pdf = pdfs.get(pdfs.size()-1);
			query = "INSERT INTO PDF values (null, ?, ?, ?)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, s.getSubmissionId());
			preparedStmt.setString(2, pdf.getPdfLink());
			preparedStmt.setDate(3, pdf.getDate());

			preparedStmt.execute();


		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean academicExists(String email) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			String query = "SELECT academicID, hash, salt FROM Academics WHERE emailAddress = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, email.trim());
			ResultSet res = preparedStmt.executeQuery();

			if (res.next())
				return true;
			else
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean validateCredentials(String email, String password) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			String query = "SELECT academicID, hash, salt FROM Academics WHERE emailAddress = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, email.trim());
			ResultSet res = preparedStmt.executeQuery();

			int academicID = -1;
			String dbHash = null;
			String dbSalt = null;
			if (res.next()) {
				academicID = res.getInt(1);
				dbHash = res.getString(2);
				dbSalt = res.getString(3);
			}
			System.out.println(academicID + ", " + dbHash + ", " + dbSalt);

			if (academicID != -1) {
				//Generate hash based on fetched salt and entered password
				Hash newHash = new Hash(password, dbSalt);
				password = ""; //Delete password by setting it to an empty string
				return newHash.getHash().equals(dbHash);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println("\nDrivers loaded as properties:");
		System.out.println(System.getProperty("jdbc.drivers"));
		System.out.println("\nDrivers loaded by DriverManager:");
		Enumeration<Driver> list = DriverManager.getDrivers();
		while (list.hasMoreElements())
			System.out.println(list.nextElement());
		System.out.println();
		
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");

			String query = "SHOW TABLES";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet res = preparedStmt.executeQuery();
			while(res.next()) {
				System.out.println(res.getString(1));
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
