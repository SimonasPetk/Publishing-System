package com.publishingsystem.mainclasses;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.*;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class Database {
	protected static final String CONNECTION = "jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f";
	protected static final String DATABASE = "team022";

	//localhost
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
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			
			for(Editor e : editors) {
				boolean academicExists = false;
				String query = "SELECT 1 FROM ACADEMICS WHERE emailAddress = ?";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setString(1, e.getEmailId());
					ResultSet res = preparedStmt.executeQuery();
					if (res.next())
						academicExists = true;
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
				if(academicExists)
					continue;
				query = "INSERT INTO ACADEMICS values (null, ?, ?, ?, ?, ?, ?, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
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
				}

				//Add editor to editor table
				query = "INSERT INTO EDITORS values (null, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setInt(1, e.getAcademicId());
					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from EDITORS");
					while(rs.next())
						e.setEditorId(Integer.valueOf(rs.getString("last_id")));
				}catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/*public static void registerEditorAsChiefEditor(ArrayList<Editor> editor) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			ArrayList<Journal> allJournals = RetrieveDatabase.getJournals();
	        String[] listContents = new String[allJournals.size()];
	        for (int i=0; i<allJournals.size(); i++) {
	            listContents[i] = allJournals.get(i).getJournalName();
	        }
			registerEditors(editor);
			for(Editor e : editor) {
				System.out.println(e.getEditorOfJournals());
				e.addEditorOfJournal(new EditorOfJournal(journal, e, false));
				String query = "INSERT INTO EDITOROFJOURNAL values (?, ?, ?, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setInt(1, e.getEditorId());
					preparedStmt.setInt(2, journal.getISSN());
					preparedStmt.setBoolean(3, false);
					preparedStmt.setBoolean(4, false);
					preparedStmt.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}*/

	public static void registerAuthors(ArrayList<Author> authors) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			for(Author a : authors) {
				boolean academicExists = false;
				String query = "SELECT 1 FROM ACADEMICS WHERE emailAddress = ?";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setString(1, a.getEmailId());
					ResultSet res = preparedStmt.executeQuery();
					if (res.next())
						academicExists = true;
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
				if(academicExists)
					continue;
				
				query = "INSERT INTO ACADEMICS values (null, ?, ?, ?, ?, ?, ?, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
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

				}catch (SQLException ex) {
					ex.printStackTrace();
				}

				query = "INSERT INTO AUTHORS values (null, ?, ?, ?, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setInt(1, a.getAcademicId());
					preparedStmt.setString(2, a.getForename()+a.getSurname());
					preparedStmt.setString(3, a.getUniversity());
					preparedStmt.setString(4, a.getEmailId());
					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from AUTHORS");
					while(rs.next())
						a.setAuthorId((Integer.valueOf(rs.getString("last_id"))));
				}catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addJournal(Journal j) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			//Add journal to journal table
			String query = "INSERT INTO JOURNALS values (?, ?, ?)";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, j.getISSN());
				preparedStmt.setString(2, j.getJournalName());
				preparedStmt.setDate(3, j.getDateOfPublication());
				preparedStmt.execute();
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

			for(EditorOfJournal e : j.getBoardOfEditors()) {
				query = "INSERT INTO EDITOROFJOURNAL values (?, ?, ?, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setInt(1, e.getEditor().getEditorId());
					preparedStmt.setInt(2, j.getISSN());
					if(e.isChiefEditor())
						preparedStmt.setBoolean(3, true);
					else
						preparedStmt.setBoolean(3, false);
					preparedStmt.setBoolean(4, e.isTempRetired());
					preparedStmt.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addSubmission(Article article, byte[] pdfBytes) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			
			// Add submission to article table
			String query = "INSERT INTO ARTICLES values (null, ?, null, ?, ?)";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, article.getJournal().getISSN());
				preparedStmt.setString(2, article.getTitle());
				preparedStmt.setString(3, article.getSummary());
				preparedStmt.execute();		
				
				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ARTICLES");
				while(rs.next())
					article.setArticleId(Integer.valueOf(rs.getString("last_id")));
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			// Add submission to submissions table
		    query = "INSERT INTO SUBMISSIONS values (null, ?, ?)";
		    try(PreparedStatement preparedStmt = con.prepareStatement(query)){
		    	preparedStmt.setInt(1, article.getArticleId());
		    	preparedStmt.setString(2, SubmissionStatus.SUBMITTED.asString());
		    	preparedStmt.execute();

		    	ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from SUBMISSIONS");
		    	while(rs.next())
		    		article.getSubmission().setSubmissionId(Integer.valueOf(rs.getString("last_id")));
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }

		    // Register each author as an author of the article
		    for(AuthorOfArticle a : article.getAuthorsOfArticle()) {
		    	query = "INSERT INTO AUTHOROFARTICLE values (?, ?, ?)";
		    	try(PreparedStatement preparedStmt = con.prepareStatement(query)) {
		    		preparedStmt.setInt(1, a.getAuthor().getAuthorId());
		    		preparedStmt.setInt(2, article.getArticleId());
		    		if(a.isMainAuthor())
		    			preparedStmt.setBoolean(3, true);
		    		else
		    			preparedStmt.setBoolean(3, false);
		    		preparedStmt.execute();
		    	} catch (SQLException ex) {
		    		ex.printStackTrace();
		    	}
		    }
	
		    ArrayList<PDF> pdfs = article.getSubmission().getVersions();
		    PDF pdf = pdfs.get(pdfs.size()-1);
		    query = "INSERT INTO PDF values (null, ?, ?, ?)";
		    try(PreparedStatement preparedStmt = con.prepareStatement(query)){
		    	preparedStmt.setInt(1, article.getSubmission().getSubmissionId());
		    	preparedStmt.setDate(2, pdf.getDate());
		    	preparedStmt.setBlob(3, PDFConverter.getPDFBlob(pdfBytes));
		    	

		    	preparedStmt.execute();
		    	ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from PDF");
		    	while(rs.next())
		    		pdf.setPdfId(Integer.valueOf(rs.getString("last_id")));
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
    }

	public static void addReviewers(ArrayList<Reviewer> reviewers) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			for(Reviewer r : reviewers) {
				String query = "INSERT INTO REVIEWERS values (null, ?, ?)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setInt(1, r.getAuthorOfArticle().getAuthor().getAuthorId());
					preparedStmt.setInt(2, r.getAuthorOfArticle().getArticle().getArticleId());
					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from REVIEWERS");
					while(rs.next())
						r.setReviewerId(Integer.valueOf(rs.getString("last_id")));
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addReview(Review review) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			Reviewer reviewer = review.getReviewer();
			Submission submission = review.getSubmission();
			
			String query = "INSERT INTO REVIEWS values (?, ?, ?, ?, ?, null)";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, reviewer.getReviewerId());
				preparedStmt.setInt(2, submission.getSubmissionId());
				preparedStmt.setInt(3, review.getArticle().getArticleId());
				preparedStmt.setString(4, review.getSummary());
				preparedStmt.setString(5, review.getTypingErrors());

				preparedStmt.execute();
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

			for(Criticism c : review.getCriticisms()) {
				query = "INSERT INTO CRITICISMS values (null, ?, ?, ?, null)";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setInt(1, reviewer.getReviewerId());
					preparedStmt.setInt(2, submission.getSubmissionId());
					preparedStmt.setString(3, c.getCriticism());

					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from CRITICISMS");
					while(rs.next())
						c.setCriticismId((Integer.valueOf(rs.getString("last_id"))));
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			query = "SELECT COUNT(*) AS REVIEWS FROM REVIEWS WHERE submissionID = ?";
			int numReviews = 0;
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, submission.getSubmissionId());
				ResultSet rs = preparedStmt.executeQuery();
				while(rs.next())
					numReviews = rs.getInt("REVIEWS");
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			if(numReviews == 3) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setString(1, SubmissionStatus.REVIEWSRECEIVED.asString());
					preparedStmt.setInt(2, submission.getSubmissionId());

					preparedStmt.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void setVerdict(Review r) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			
			Reviewer reviewer = r.getReviewer();
			Submission submission = r.getSubmission();

			String query = "UPDATE REVIEWS SET verdict = ? WHERE reviewerID = ? and submissionID = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setString(1, r.getVerdict().toString());
				preparedStmt.setInt(2, reviewer.getReviewerId());
				preparedStmt.setInt(3, submission.getSubmissionId());

				preparedStmt.execute();
			}catch (SQLException ex) {
				ex.printStackTrace();
			}


			int numVerdicts = 0;
			SubmissionStatus status = null;

			query = "SELECT REVIEWS.SUBMISSIONID, COUNT(*) AS REVIEWS, STATUS FROM REVIEWS, "
					+ "SUBMISSIONS WHERE REVIEWS.submissionID = ? AND SUBMISSIONS.submissionID = ? "
					+ "AND verdict IS NOT NULL";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, submission.getSubmissionId());
				preparedStmt.setInt(2, submission.getSubmissionId());
				ResultSet rs = preparedStmt.executeQuery();
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
					ps.setInt(2, submission.getSubmissionId());
					ps.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}

			}else if(numVerdicts == 3 && status.equals(SubmissionStatus.RESPONSESRECEIVED)) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try(PreparedStatement ps = con.prepareStatement(query)){
					ps.setString(1, SubmissionStatus.FINALVERDICT.asString());
					ps.setInt(2, submission.getSubmissionId());
					ps.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addResponse(Review r) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			Submission submission = r.getSubmission();

			boolean allCriticismsAnswered = false;
			String query = "SELECT COUNT(*) AS ANSWERSLEFT FROM CRITICISMS WHERE submissionID = ? AND answer = null";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, submission.getSubmissionId());
				ResultSet rs = preparedStmt.executeQuery();
				while(rs.next()) {
					int numAnswersLeft = rs.getInt("ANSWERSLEFT");
					if(numAnswersLeft == 0)
						allCriticismsAnswered = true;
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

			if(allCriticismsAnswered) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setString(1, SubmissionStatus.RESPONSESRECEIVED.asString());
					preparedStmt.setInt(2, submission.getSubmissionId());

					preparedStmt.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			ArrayList<Criticism> criticisms = r.getCriticisms();
			for(Criticism c : criticisms) {
				query = "UPDATE CRITICISMS SET ANSWER = ? WHERE criticismID = ?";
				try(PreparedStatement preparedStmt = con.prepareStatement(query)){
					preparedStmt.setString(1, c.getAnswer());
					preparedStmt.setInt(2, c.getCriticismId());

					preparedStmt.execute();
				}catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void addRevisedSubmission(PDF pdf, byte[] pdfBytes) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();

			Submission s = pdf.getSubmission();
			String query = "INSERT INTO PDF values (null, ?, ?, ?)";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, s.getSubmissionId());
				preparedStmt.setDate(2, pdf.getDate());
				preparedStmt.setBlob(3, PDFConverter.getPDFBlob(pdfBytes));
				preparedStmt.execute();
				
				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from PDF");
				while(rs.next())
					pdf.setPdfId(Integer.valueOf(rs.getString("last_id")));
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void acceptArticle(Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			
			
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean academicExists(String email) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			String query = "SELECT 1 FROM ACADEMICS WHERE emailAddress = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setString(1, email.trim());
				ResultSet res = preparedStmt.executeQuery();
				if (res.next())
					return true;
				else
					return false;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean validateCredentials(String email, String password) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			String query = "SELECT academicID, hash, salt FROM ACADEMICS WHERE emailAddress = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
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
			statement.close();

			String query = "SHOW TABLES";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet res = preparedStmt.executeQuery();
			while(res.next()) {
				System.out.println(res.getString(1));
			}
			Statement printStatements = con.createStatement();
			printStatements.execute("USE "+DATABASE+";");
			printStatements.executeQuery("SELECT * FROM ACADEMICS");
			printStatements.close();
			System.out.println("Done");
		}catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
	
}
