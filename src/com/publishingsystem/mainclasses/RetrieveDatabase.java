package com.publishingsystem.mainclasses;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetrieveDatabase extends Database{

	public static ArrayList<Journal> getJournals() {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();

			String query = "SELECT * FROM JOURNALS";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				ResultSet res = preparedStmt.executeQuery();
				ArrayList<Journal> journals = new ArrayList<Journal>();
				while(res.next()) {
					int issn = res.getInt("ISSN");
					String name = res.getString("name");
					Date date = res.getDate("dateOfPublication");
					journals.add(new Journal(issn, name, date));
				}
				return journals;

			}catch (SQLException ex) {
				ex.printStackTrace();
			}

		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/** 
	 * getRoles
	 * Get the possible roles of an academic.
	 * @param email The email of the academic
	 * @return Academic[] an array of academic roles,  
	 * where the zeroth index is editor, first is author
	 * second is reviewer
	 */
	public static Academic[] getRoles(String email){
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			Academic[] roles = new Academic[3];

			String query = "SELECT academicID, title, forename, surname, university, emailAddress FROM ACADEMICS WHERE emailAddress = ?";
			int academicId = -1;
			String title = "", forename = "", surname = "", emailId = "", university = "";

			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setString(1, email);
				ResultSet res = preparedStmt.executeQuery();
				if(res.next()) {
					academicId = res.getInt("academicID");
					title = res.getString("title");
					forename = res.getString("forename");
					surname = res.getString("surname");
					emailId = res.getString("emailAddress");
					university = res.getString("university");
				}else {
					return null;
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

			query = "SELECT E.EDITORID, CHIEFEDITOR, J.ISSN, NAME, DATEOFPUBLICATION "
					+ "FROM EDITORS E, EDITOROFJOURNAL EJ, JOURNALS J "
					+ "WHERE J.ISSN = EJ.ISSN AND E.EDITORID = EJ.EDITORID AND E.ACADEMICID = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, academicId);
				ResultSet res = preparedStmt.executeQuery();
				Editor editor = null;
				while(res.next()) {
					if(editor == null) {
						editor = new Editor(res.getInt("editorID"), title, forename, surname, emailId, university, null);
						editor.setAcademicId(academicId);
					}
					Journal journal = new Journal(res.getInt("ISSN"), res.getString("NAME"), res.getDate("dateOfPublication"));
					journal.setBoardOfEditors(RetrieveDatabase.getEditorsOfJournal(journal.getISSN(), journal));
					boolean chiefEditor = res.getBoolean("chiefEditor");
					EditorOfJournal editorOfJournal = new EditorOfJournal(journal, editor, chiefEditor);
					editor.addEditorOfJournal(editorOfJournal);
				}
				roles[0] = editor;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

			query = "SELECT Aut.AUTHORID, MAINAUTHOR, NUMREVIEWS, Art.articleID, J.ISSN, name,"
					+ " dateOfPublication, title, summary, submissionID, status "
					+ "FROM AUTHORS Aut, AUTHOROFARTICLE Aoa, ARTICLES Art, SUBMISSIONS S, JOURNALS J "
					+ "WHERE Art.ARTICLEID = Aoa.ARTICLEID "
					+ "AND Aut.AUTHORID = Aoa.AUTHORID "
					+ "AND S.articleID = Art.articleID "
					+ "AND J.ISSN = Art.ISSN "
					+ "AND academicId = ?";

			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, academicId);
				ResultSet res = preparedStmt.executeQuery();
				Author author = null;
				while(res.next()) {
					if(author == null) {
						author = new Author(res.getInt("authorID"), title, forename, surname, emailId, university, null);
						author.setAcademicId(academicId);
					}
					Journal journal = new Journal(res.getInt("ISSN"), res.getString("NAME"), res.getDate("dateOfPublication"));
					Article article = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("SUMMARY"), journal);
					article.submit(new Submission( res.getInt("SUBMISSIONID"), article, SubmissionStatus.valueOf(res.getString("STATUS")), null));
					AuthorOfArticle authorOfArticle = new AuthorOfArticle(article, author, res.getBoolean("MAINAUTHOR"));
					article.addAuthorOfArticle(authorOfArticle);
					author.addAuthorOfArticle(authorOfArticle);
				}
				roles[1] = author;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}

			query = "SELECT R.REVIEWERID, R.ACADEMICID, Ros.SUBMISSIONID, S.STATUS, Art.ARTICLEID, Art.TITLE, Art.SUMMARY "
					+ "FROM REVIEWERS R LEFT JOIN REVIEWEROFSUBMISSION Ros ON R.REVIEWERID = Ros.REVIEWERID "
					+ "LEFT JOIN SUBMISSIONS S ON S.SUBMISSIONID = Ros.SUBMISSIONID "
					+ "LEFT JOIN ARTICLES Art ON Art.ARTICLEID = S.ARTICLEID "
					+ "WHERE R.ACADEMICID = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, academicId);
				ResultSet res = preparedStmt.executeQuery();
				Reviewer reviewer = null;
				while(res.next()) {
					if(reviewer == null) {
						reviewer = new Reviewer(res.getInt("ACADEMICID"), res.getInt("REVIEWERID"), title, forename, surname, emailId, university, null);
						reviewer.setAcademicId(academicId);
					}
					if(res.getInt("SUBMISSIONID") != 0 && !res.getBoolean("COMPLETED")) {
						Article a = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("SUMMARY"), null);
						Submission s = new Submission(res.getInt("SUBMISSIONID"), a, SubmissionStatus.valueOf(res.getString("STATUS")), null);
						ReviewerOfSubmission ros = new ReviewerOfSubmission(reviewer, s);
						reviewer.addReviewerOfSubmission(ros);
					}
				}
				if(reviewer != null) {
					for(ReviewerOfSubmission ros : reviewer.getReviewerOfSubmissions()) {
						Submission submission = ros.getSubmission();
						query = "SELECT Rev.SUBMISSIONID, Rev.SUMMARY, Rev.TYPINGERRORS, Rev.VERDICT, C.CRITICISMID, C.CRITICISM, C.ANSWER FROM REVIEWS Rev LEFT JOIN CRITICISMS C "
								+ "ON C.SUBMISSIONID = Rev.SUBMISSIONID AND C.REVIEWERID = Rev.REVIEWERID "
								+ "WHERE Rev.REVIEWERID = ? AND Rev.SUBMISSIONID = ?";
						try(PreparedStatement preparedStmt1 = con.prepareStatement(query)){
							preparedStmt1.setInt(1, reviewer.getReviewerId());
							preparedStmt1.setInt(2, submission.getSubmissionId());
							ResultSet res1 = preparedStmt1.executeQuery();
							Review review = null;
							ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
							while(res1.next()) {
								if(review == null) {
									review = new Review(ros, res1.getString("SUMMARY"), res1.getString("TYPINGERRORS"), criticisms, Verdict.valueOf(res1.getString("VERDICT")));
								}
								int criticismId = res1.getInt("CRITICISMID");
								if(criticismId != 0)
									criticisms.add(new Criticism(res1.getInt("CRITICISMID"), res1.getString("CRITICISM"), res1.getString("ANSWER")));
							}
							ros.addReview(review);
						}catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
				roles[2] = reviewer;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			return roles;
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;

	}
	
	public static void checkReviewsForAuthor(AuthorOfArticle aoa) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			String query = "SELECT S.SUBMISSIONID, REVIEWERID, R.SUMMARY, TYPINGERRORS, VERDICT "
					+ "FROM AUTHOROFARTICLE Aoa, ARTICLES Art, SUBMISSIONS S, REVIEWS R "
					+ "WHERE Art.ARTICLEID = Aoa.ARTICLEID "
					+ "AND Aoa.MAINAUTHOR = 1 "
					+ "AND Art.ARTICLEID = S.ARTICLEID "
					+ "AND S.SUBMISSIONID = R.SUBMISSIONID "
					+ "AND authorID = ? "
					+ "AND Aoa.ARTICLEID = ?";

			try(PreparedStatement preparedStmt1 = con.prepareStatement(query)){
				preparedStmt1.setInt(1, aoa.getAuthor().getAuthorId());
				preparedStmt1.setInt(2, aoa.getArticle().getArticleId());
				ResultSet reviewRes = preparedStmt1.executeQuery();
				while(reviewRes.next()) {
					int reviewerID = reviewRes.getInt("REVIEWERID");
					Submission s = aoa.getArticle().getSubmission();
					boolean reviewerPresent = false;
					for(ReviewerOfSubmission ros : s.getReviewersOfSubmission()) {
						if(ros.getReviewer().getReviewerId() == reviewerID) {
							reviewerPresent = true;
						}
					}
					if(aoa.isMainAuthor() && !reviewerPresent) {
						query = "SELECT CRITICISMID, CRITICISM FROM CRITICISMS WHERE SUBMISSIONID = ? AND REVIEWERID = ?";
						try(PreparedStatement preparedStmt2 = con.prepareStatement(query)){
							preparedStmt2.setInt(1, s.getSubmissionId());
							preparedStmt2.setInt(2, reviewerID);
							ResultSet crticismRes = preparedStmt2.executeQuery();
							ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
							while(crticismRes.next()) {
								criticisms.add(new Criticism(crticismRes.getString("CRITICISM")));
							}
							ReviewerOfSubmission ros = new ReviewerOfSubmission(new Reviewer(0, reviewerID, null, null, null, null, null, null), s);
							Review review = new Review(ros, reviewRes.getString("SUMMARY"), reviewRes.getString("TYPINGERRORS"), criticisms, Verdict.valueOf(reviewRes.getString("VERDICT")));
							ros.addReview(review);
							s.addReviewerOfSubmission(ros);
						}catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static ArrayList<Article> getArticlesSubmittedByReviewer(int reviewerId){
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			String query = "SELECT Art.ARTICLEID, Art.TITLE, Art.SUMMARY, Art.NUMREVIEWS "
					+ "FROM ARTICLES Art, AUTHOROFARTICLE Aoa, AUTHORS A, ACADEMICS Aca, REVIEWERS R "
					+ "WHERE R.ACADEMICID = Aca.ACADEMICID "
					+ "AND Aca.ACADEMICID = A.ACADEMICID "
					+ "AND A.AUTHORID = Aoa.AUTHORID "
					+ "AND Aoa.ARTICLEID = Art.ARTICLEID "
					+ "AND R.REVIEWERID = ? "
					+ "AND numREVIEWS != 3";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, reviewerId);
				ResultSet res = preparedStmt.executeQuery();
				ArrayList<Article> articles = new ArrayList<Article>();
				while(res.next()) {
					Article article = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("SUMMARY"), null);
					article.setNumReviews(res.getInt("NUMREVIEWS"));
					articles.add(article);
				}
				return articles;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static int getNumberOfReviewsToBeDone(int reviewerId) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			int numArticles = 0;
			int numReviewsDone = 0;
			String query = "SELECT Art.NUMREVIEWS "
					+ "FROM ARTICLES Art, AUTHOROFARTICLE Aoa, AUTHORS A, ACADEMICS Aca, REVIEWERS R "
					+ "WHERE R.ACADEMICID = Aca.ACADEMICID "
					+ "AND Aca.ACADEMICID = A.ACADEMICID "
					+ "AND A.AUTHORID = Aoa.AUTHORID "
					+ "AND Aoa.ARTICLEID = Art.ARTICLEID "
					+ "AND R.REVIEWERID = ? "
					+ "AND numREVIEWS != 3";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1,reviewerId);
				ResultSet res = preparedStmt.executeQuery();
				while(res.next()) {
					numArticles++;
					numReviewsDone += res.getInt("NUMREVIEWS");
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			return numArticles*3-numReviewsDone;
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static ArrayList<Submission> getSubmissions(Reviewer r){
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			String query = "SELECT S.SUBMISSIONID, S.STATUS, Art.ARTICLEID, Art.TITLE, Art.SUMMARY, J.ISSN, J.NAME, J.DATEOFPUBLICATION "
					+ "FROM SUBMISSIONS S, ARTICLES Art, JOURNALS J "
					+ "WHERE S.ARTICLEID = Art.ARTICLEID "
					+ "AND Art.ISSN = J.ISSN "
					+ "AND SUBMISSIONID NOT IN "
					//Don't get submissions where you have clash
					+ "(SELECT S.SUBMISSIONID FROM AUTHORS A, AUTHOROFARTICLE Aoa, ARTICLES Art, SUBMISSIONS S "
					+ "WHERE Aoa.AUTHORID = A.AUTHORID "
					+ "AND Aoa.ARTICLEID = Art.ARTICLEID "
					+ "AND Art.ARTICLEID = S.ARTICLEID "
					+ "AND A.UNIVERSITY = ?) "
					//Don't get submissions which the reviewer has already decided to review
					+ "AND SUBMISSIONID NOT IN "
					+ "(SELECT SUBMISSIONID FROM REVIEWEROFSUBMISSION "
					+ "WHERE REVIEWERID = ?)";
			ArrayList<Submission> submissions = new ArrayList<Submission>();
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setString(1, r.getUniversity());
				preparedStmt.setInt(2, r.getReviewerId());
				ResultSet res = preparedStmt.executeQuery();
				while(res.next()) {
					Journal journal = new Journal(res.getInt("ISSN"), res.getString("name"), res.getDate("dateOfPublication"));
					Article	article = new Article(res.getInt("articleID"), res.getString("title"), res.getString("summary"), journal);
					submissions.add(new Submission(res.getInt("submissionID"), article, SubmissionStatus.valueOf(res.getString("status")), null));
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			return submissions;
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Submission> getSubmissionsToJournal(int issn){
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
            Statement statement = con.createStatement();
            statement.execute("USE "+DATABASE+";");
            String query = "SELECT S.SUBMISSIONID, S.STATUS, A.ARTICLEID, A.TITLE, A.SUMMARY, "
            		+ "J.ISSN, J.NAME, J.DATEOFPUBLICATION "
            		+ "FROM SUBMISSIONS S, ARTICLES A, JOURNALS J "
            		+ "WHERE S.ARTICLEID = A.ARTICLEID "
            		+ "AND A.ISSN = J.ISSN "
            		+ "AND J.ISSN = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, issn);
				ResultSet res = preparedStmt.executeQuery();
				ArrayList<Submission> submissions = new ArrayList<Submission>();
				while(res.next()) {
					Journal j = new Journal(res.getInt("ISSN"), res.getString("NAME"), res.getDate("dateOfPublication"));
					Article article = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("SUMMARY"), j);
					Submission submission = new Submission(res.getInt("SUBMISSIONID"), article, SubmissionStatus.valueOf(res.getString("STATUS")), null);
					submissions.add(submission);
				}
				return submissions;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] getPDF(int pdfId) {
		try (Connection con = DriverManager.getConnection(CONNECTION)){
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			statement.close();
			String query = "SELECT PDFTEXT FROM PDF WHERE pdfID = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, pdfId);
				ResultSet res = preparedStmt.executeQuery();
				while(res.next()) {
					Blob blob = res.getBlob("PDFTEXT");
					int blobLength = (int) blob.length();  
					return blob.getBytes(1, blobLength);
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Journal getJournal(int issn) {
		Journal result = null;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT ISSN, name, dateOfPublication dateOfPublication FROM JOURNALS WHERE issn = " + issn + ";";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) {
				int resISSN = res.getInt(1);
				String resName = res.getString(2);
				Date resDate = res.getDate(3);
				result = new Journal(resISSN, resName, resDate);
				ArrayList<EditorOfJournal> editors = getEditorsOfJournal(resISSN,result);
				result.setBoardOfEditors(editors);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<PublishedArticle> getArticles(int issn) {
		ArrayList<PublishedArticle> articlesPublished = new ArrayList<PublishedArticle>();
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
            Statement statement = con.createStatement();
            statement.execute("USE "+DATABASE+";");
            /*String query = "SELECT V.VOLNUM, V.YEAR, E.EDNUM, E.MONTH, P.ARTICLEID, P.PAGERANGE, A.TITLE, A.SUMMARY, AUT.AUTHORID, AUTS.AUTHORNAME, AUTS.UNIVERSITY, AUTS.EMAILADDRESS, PDF.PDFID" +
            			   "FROM VOLUMES V, EDITIONS E, PUBLISHEDARTICLES P, ARTICLES A, AUTHOROFARTICLE AUT, AUTHORS AUTS, PDF" +
            			   "WHERE V.ISSN = ? AND V.VOLNUM = E.VOLNUM AND E.EDNUM = P.EDNUM AND P.ARTICLEID = A.ARTICLEID" +
            			   "AND A.ARTICLEID = AUT.ARTICLEID AND AUT.AUTHORID = AUTS.AUTHORID AND A.PDFID = PDF.PDFID;";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, issn);
				System.out.println(preparedStmt);
				ResultSet res = preparedStmt.executeQuery();
				ArrayList<Edition> eds = new ArrayList<Edition>();
			
				while(res.next()) {
					
					Article article = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("SUMMARY"), null);
					PublishedArticle PublishedArticle = new PublishedArticle(article, res.getString("PAGERANGE"), res.getInt("EDNUM"));
					
					articlesPublished.add(PublishedArticle);
				}
				
				Edition ed = new Edition(res.getString("EDNUM"), res.getInt("MONTH"), articlesPublished);
				eds.add(ed);
				Volume vol = new Volume(res.getString("VOLNUM"), res.getInt("YEAR"), eds);
				
				return articlesPublished;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}*/

            String query = "SELECT V.VOLNUM, V.YEAR, E.EDNUM, E.MONTH, P.ARTICLEID, P.PAGERANGE, A.TITLE, A.SUMMARY, AUT.AUTHORID, AUTS.AUTHORNAME, AUTS.UNIVERSITY, AUTS.EMAILADDRESS, PDF.PDFID " +
                    "FROM VOLUMES V, EDITIONS E, PUBLISHEDARTICLES P, ARTICLES A, AUTHOROFARTICLE AUT, AUTHORS AUTS, PDF " +
                    "WHERE V.ISSN = " + issn + " AND V.VOLNUM = E.VOLNUM AND E.EDNUM = P.EDNUM AND P.ARTICLEID = A.ARTICLEID " +
                    "AND A.ARTICLEID = AUT.ARTICLEID AND AUT.AUTHORID = AUTS.AUTHORID AND A.PDFID = PDF.PDFID;";
            ResultSet res = statement.executeQuery(query);
            ArrayList<Edition> eds = new ArrayList<Edition>();
            
            while(res.next()) {    
                Article article = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("SUMMARY"), null);
                PublishedArticle PublishedArticle = new PublishedArticle(article, res.getString("PAGERANGE"), res.getInt("EDNUM"));
                
                articlesPublished.add(PublishedArticle);
                
                Edition ed = new Edition(res.getString("EDNUM"), res.getInt("MONTH"), articlesPublished);
                eds.add(ed);
                //Volume vol = new Volume(res.getString("VOLNUM"), res.getInt("YEAR"), eds);
            }
            
            return articlesPublished;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	// SELECT J.ISSN, V.VOLNUM, E.EDNUM, P.ARTICLEID, A.ARTICLEID, AUT.AUTHORID, AUTS.AUTHORNAME FROM JOURNALS J, VOLUMES V, EDITIONS E, PUBLISHEDARTICLES P, ARTICLES A, AUTHOROFARTICLE AUT, AUTHORS AUTS WHERE AUT.ARTICLEID = A.ARTICLEID AND AUT.AUTHORID = AUTS.AUTHORID;
	
	
	
	public static ArrayList<EditorOfJournal> getEditorsOfJournal(int issn,Journal j) {
		ArrayList<EditorOfJournal> editorsOfJournal = new ArrayList<EditorOfJournal>();
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT Aca.ACADEMICID, Aca.TITLE, Aca.FORENAME, Aca.SURNAME, Aca.UNIVERSITY, Aca.EMAILADDRESS, "
					+ "E.EDITORID FROM ACADEMICS Aca, EDITORS E, EDITOROFJOURNAL Eoj WHERE Aca.ACADEMICID = E.ACADEMICID "
					+ "AND E.EDITORID = Eoj.EDITORID AND Eoj.ISSN = " + issn +";";
			ResultSet res = statement.executeQuery(query);
			ArrayList<Editor> editors = new ArrayList<Editor>();
			Editor tempEditor = null;
			while (res.next()) {
				tempEditor = new Editor(res.getInt(7),res.getString(2),res.getString(3),res.getString(4),res.getString(5), res.getString(6),null);
				tempEditor.setAcademicId(res.getInt(1));
			    editors.add(tempEditor);
			}
			for (Editor e: editors) {
				if (!isRetiredByEditorId(e.getEditorId())) {
					editorsOfJournal.add(new EditorOfJournal(j,e,isChiefEditorByEditorId(e.getEditorId())));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return editorsOfJournal;
	}
	
	/*public static ArrayList<EditorOfJournal> getEditorsOfJournal(int issn, Journal j) {
		ArrayList<EditorOfJournal> editorsOfJournal = new ArrayList<EditorOfJournal>();
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT editorID FROM EDITOROFJOURNAL WHERE issn = " + issn + ";";
			ResultSet res = statement.executeQuery(query);
			List<Integer> ids = new ArrayList<Integer>();
			while (res.next()) {
				ids.add(res.getInt(1));
			}
			List<Integer> aca = new ArrayList<Integer>();
			for(int i: ids) {
				aca.add(getAcademicIdByEditorId(i));
			}
			ArrayList<Editor> editors = new ArrayList<Editor>();
			for(int i: aca) {
				editors.add(getEditorByAcademicId(i));
			}
			for(Editor e: editors) {
				editorsOfJournal.add(new EditorOfJournal(j,e,isChiefEditorByEditorId(e.getEditorId())));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return editorsOfJournal;
	}*/
	
	public static boolean isChiefEditorByEditorId(int editorId) {
		boolean result = false;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT ChiefEditor FROM EDITOROFJOURNAL WHERE editorID= '" + editorId + "';";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) {
				if (res.getInt(1) == 1) {
					result = true;
				}
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
	    return result;
	}
	
	public static boolean isRetiredByEditorId(int editorId) {
		boolean result = false;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT Retired FROM EDITOROFJOURNAL WHERE editorID= '" + editorId + "';";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) {
				if (res.getInt(1) == 1) {
					result = true;
				}
			}
		} catch (SQLException ex) {
				ex.printStackTrace();
		}
		return result;
	}
	
	public static int getAcademicIdByEditorId(int editorId) {
		int result = -1;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT academicID FROM EDITORS WHERE  editorID = '" + editorId + "';";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) result = res.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static Editor getEditorByAcademicId(int academicId) {
		Editor editor = null;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT title, forename, surname, emailAddress, university, hash FROM ACADEMICS WHERE academicID = " + academicId + ";";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) {
				String resTitle = res.getString(1);
				String resForenames = res.getString(2);
				String resSurname = res.getString(3);
				String resEmail = res.getString(4);
				String resUniversity = res.getString(5);
				//Hash resHash = res.getString(6);
				Hash resHash = null;
				
				editor = new Editor(academicId, resTitle, resForenames, resSurname, resEmail, resUniversity, resHash);
			}
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return editor;
	}

	public static int getAcademicIdByEmail(String email) {
		int result = -1;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT academicID FROM ACADEMICS WHERE emailAddress = '" + email + "';";
			System.out.println(query);
			ResultSet res = statement.executeQuery(query);
			if (res.next()) result = res.getInt(1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static Author getAuthorByID(int academicID) {
		Author result = null;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT title, forename, surname, emailAddress, university, hash FROM ACADEMICS WHERE academicID = " + academicID + ";";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) {
				String resTitle = res.getString(1);
				String resForenames = res.getString(2);
				String resSurname = res.getString(3);
				String resEmail = res.getString(4);
				String resUniversity = res.getString(5);
				//Hash resHash = res.getString(6);
				Hash resHash = null;

				result = new Author(academicID, resTitle, resForenames, resSurname, resEmail, resUniversity, resHash);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;        
	}

	public static String[] getNamesByID(int academicID) {
		String[] results = new String[2];
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE "+DATABASE+";");
			String query = "SELECT forename, surname FROM ACADEMICS WHERE academicID = " + academicID + ";";
			ResultSet res = statement.executeQuery(query);
			if (res.next()) {
				results[0] = res.getString(1);
				results[1] = res.getString(2);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return results;
	}

	public static void main(String[] args) {
		for(Article a : RetrieveDatabase.getArticlesSubmittedByReviewer(5)) {
			System.out.println(a.getNumReviews());
		};
		Academic[] roles1 = RetrieveDatabase.getRoles("j.smith@uniofsmith.ac.uk");
		Editor e = (Editor)roles1[0];
		Author a = (Author)roles1[1];
		Reviewer r = (Reviewer)roles1[2];
		if(e != null) {
			System.out.println(e);
			for(EditorOfJournal eoj : e.getEditorOfJournals()){
				System.out.println(eoj.getEditor().getEditorId() +" "+ eoj.getJournal().getISSN()+" ChiefEditor: "+eoj.isChiefEditor());
			};
		}
//		if(a != null) {
//			System.out.println("Author: "+a);
//			for(AuthorOfArticle aoa : a.getAuthorOfArticles()){
//				System.out.println(aoa.getAuthor().getAuthorId()+" "+aoa.getArticle().getArticleId()+" MainAuthor: "+aoa.isMainAuthor());
//				Article article = aoa.getArticle();
//				Submission s = article.getSubmission();
//				System.out.println(s.getStatus());
//				System.out.println();
//				if(s != null)
//					for(ReviewerOfSubmission ros : s.getReviewersOfSubmission())
//						System.out.println(ros.getReview());
//			};
//		}
		if(r != null) {
			System.out.println("Reviewer "+r);
			for(ReviewerOfSubmission ros : r.getReviewerOfSubmissions()) {
				System.out.println(ros.getReview());
			}
		}
	}
}
