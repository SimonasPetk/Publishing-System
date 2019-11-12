package com.publishingsystem.mainclasses;

import java.util.ArrayList;
import java.sql.*;

public class TestRun {

	public static void main(String[] args) {
		//Author
		String authorTitle = "Mr";
		String forename = "J.";
		String surname = "Doe";
		String emailId = "j.doe@uniofdoe.ac.uk";
		String university =  "Uni of doe";
		int mainAuthorId = -1;
		
		//Submission
		String submissionTitle = "How doe works";
		String summary = "Article about how doe works";
		String pdf = "PDF_TEST_LINK.pdf";
		int submissionId = -1;
		

		try (Connection con = DriverManager.getConnection(Database.getConnectionName())){
			//Add the author to academic table
			String query = "INSERT INTO ACADEMIC values (null, ?, ?, ?, ?, ?, null, null)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, authorTitle);
			preparedStmt.setString(2, forename);
			preparedStmt.setString(3, surname);
			preparedStmt.setString(4, emailId);
			preparedStmt.setString(5, university);
	
			preparedStmt.execute();
			
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select last_insert_id() as last_id from ACADEMIC");
			while(rs.next())
				mainAuthorId = Integer.valueOf(rs.getString("last_id"));
			
			
			//Add submission to the submission table
			query = "INSERT INTO SUBMISSION values (null, ?, ?)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, mainAuthorId);
			preparedStmt.setString(2, SubmissionStatus.SUBMITTED.toString());
			preparedStmt.execute();
			
			statement = con.createStatement();
			rs = statement.executeQuery("select last_insert_id() as last_id from SUBMISSION");
			while(rs.next())
				submissionId = Integer.valueOf(rs.getString("last_id"));
			
			//Add article to the article table
			query = "INSERT INTO ARTICLE values (?, ?, ?, ?, null, null)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, submissionId);
			preparedStmt.setString(2, submissionTitle);
			preparedStmt.setString(3, summary);
			preparedStmt.setString(4, pdf);
	
			preparedStmt.execute();
			
			//Add the author to author table
			query = "INSERT INTO AUTHOR values (?, ?)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, mainAuthorId);
			preparedStmt.setInt(2, submissionId);
	
			preparedStmt.execute();
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	
		
		
		Author a = new Author(mainAuthorId, authorTitle, forename, surname, emailId, university);
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a);
		
		Submission s = new Submission(authors, submissionTitle, summary, submissionId, pdf, 1);
		
		a.submitArticle(s);
		System.out.println(a.getSubmissionStatus(s));
		
		//Reviewer and reviews
		Reviewer [] rvs = new Reviewer[3];
		for(int i = 0; i < rvs.length; i++) {
			rvs[i] = new Reviewer(1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", i);
			Reviewer r = rvs[i];
			r.addSubmissionToReview(s);
	
			ArrayList<String> criticisms = new ArrayList<String>();
			criticisms.add("Perhaps explain origin of doe");
			
			Review review = new Review(r.getReviewerId(), s.getSubmissionId(), "Good", "None", criticisms);
			s.addReview(review);
		}

		System.out.println(a.getSubmissionStatus(s));
		
		//Verdicts
		for(Reviewer r : rvs) {
			Verdict [] verdicts = Verdict.values();
			int rndIndex = (int)(Math.random()*verdicts.length);
			Verdict rndVerdict = verdicts[rndIndex];
			r.setVerdict(s, rndVerdict);
		}
		
		System.out.println(a.getSubmissionStatus(s));
		
		//Responses
		for(Reviewer r : rvs) {
			ArrayList<String> answers = new ArrayList<String>();
			answers.add("Explained on page 22 in revised article");
			Response response = new Response(s.getSubmissionId(), r.getReviewerId(), answers);
			a.respond(s, r.getReviewerId() , response);
		}
		
		System.out.println(a.getSubmissionStatus(s));
		
		
		//Final decision
		Editor e = new Editor(1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", 10);
		e.acceptArticle(s);
		
		System.out.println(a.getSubmissionStatus(s));
		
	}
}
