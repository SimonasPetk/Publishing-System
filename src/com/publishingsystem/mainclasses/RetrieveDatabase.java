package com.publishingsystem.mainclasses;

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
					boolean chiefEditor = res.getBoolean("chiefEditor");
					EditorOfJournal editorOfJournal = new EditorOfJournal(journal, editor, chiefEditor);
					journal.addEditorToBoard(editorOfJournal);
					editor.addEditorOfJournal(editorOfJournal);
				}
				roles[0] = editor;
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			query = "SELECT Aut.AUTHORID, MAINAUTHOR, Art.articleID, J.ISSN, name,"
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
				if(author != null) {
					query = "SELECT S.SUBMISSIONID, REVIEWERID, R.SUMMARY, TYPINGERRORS, VERDICT "
							+ "FROM AUTHOROFARTICLE Aoa, ARTICLES Art, SUBMISSIONS S, REVIEWS R "
							+ "WHERE Art.ARTICLEID = Aoa.ARTICLEID "
							+ "AND Aoa.MAINAUTHOR = 1 "
							+ "AND Art.ARTICLEID = S.ARTICLEID "
							+ "AND S.SUBMISSIONID = R.SUBMISSIONID "
							+ "AND authorID = ?";
	
					try(PreparedStatement preparedStmt1 = con.prepareStatement(query)){
						preparedStmt1.setInt(1, author.getAuthorId());
						ResultSet reviewRes = preparedStmt1.executeQuery();
						while(reviewRes.next()) {
							int reviewerID = reviewRes.getInt("REVIEWERID");
							for(AuthorOfArticle aoa : author.getAuthorOfArticles()) {
								Submission s = aoa.getArticle().getSubmission();
								if(aoa.isMainAuthor()) {
									query = "SELECT CRITICISMID, CRITICISM, ANSWER FROM CRITICISMS WHERE SUBMISSIONID = ? AND REVIEWERID = ?";
									try(PreparedStatement preparedStmt2 = con.prepareStatement(query)){
										preparedStmt2.setInt(1, s.getSubmissionId());
										preparedStmt2.setInt(2, reviewerID);
										ResultSet crticismRes = preparedStmt2.executeQuery();
										ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
										while(crticismRes.next()) {
											criticisms.add(new Criticism(crticismRes.getString("CRITICISM"), crticismRes.getString("ANSWER")));
										}
										Review review = new Review(null, s, reviewRes.getString("SUMMARY"), reviewRes.getString("TYPINGERRORS"), criticisms, null);
										s.addReview(review);
									}catch (SQLException ex) {
										ex.printStackTrace();
									}
								}
							}
						}
						roles[1] = author;
					}catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			query = "SELECT R.REVIEWERID, REV.SUMMARY, TYPINGERRORS, REV.SUBMISSIONID, S.ARTICLEID, "
					+ "S.STATUS, A.TITLE, A.SUMMARY AS ARTICLESUMMARY, J.ISSN, J.NAME, J.DATEOFPUBLICATION FROM "
					+ "REVIEWERS R, REVIEWS REV, SUBMISSIONS S, ARTICLES A, AUTHOROFARTICLE Aoa, AUTHORS Aut, JOURNALS J "
					+ "WHERE R.REVIEWERID = REV.REVIEWERID "
					+ "AND REV.SUBMISSIONID = S.SUBMISSIONID "
					+ "AND S.ARTICLEID = A.ARTICLEID "
					+ "AND A.ISSN = J.ISSN "
					+ "AND R.AUTHORID = Aoa.AUTHORID "
					+ "AND Aoa.AUTHORID = Aut.AUTHORID "
					+ "AND Aut.ACADEMICID = ?";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, academicId);
				ResultSet res = preparedStmt.executeQuery();
				Reviewer reviewer = null;
				while(res.next()) {
					if(reviewer == null) {
						reviewer = new Reviewer(res.getInt("REVIEWERID"), title, forename, surname, emailId, university, null);
						reviewer.setAcademicId(academicId);
					}
					Journal journal = new Journal(res.getInt("ISSN"), res.getString("NAME"), res.getDate("dateOfPublication"));
					Article article = new Article(res.getInt("ARTICLEID"), res.getString("TITLE"), res.getString("ARTICLESUMMARY"), journal);
					Submission submission = new Submission(res.getInt("SUBMISSIONID"), article, SubmissionStatus.valueOf(res.getString("STATUS")), null);
					query = "SELECT CRITICISMID, CRITICISM, ANSWER FROM CRITICISMS WHERE SUBMISSIONID = ? AND REVIEWERID = ?";
					try(PreparedStatement preparedStmt2 = con.prepareStatement(query)){
						preparedStmt2.setInt(1, submission.getSubmissionId());
						preparedStmt2.setInt(2, reviewer.getReviewerId());
						ResultSet crticismRes = preparedStmt2.executeQuery();
						ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
						while(crticismRes.next()) {
							criticisms.add(new Criticism(crticismRes.getString("CRITICISM"), crticismRes.getString("ANSWER")));
						}
						Review review = new Review(reviewer, submission, res.getString("SUMMARY"), res.getString("TYPINGERRORS"), criticisms, null);
						reviewer.addReview(submission, review);
					}catch (SQLException ex) {
						ex.printStackTrace();
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
	
	public static int getNumberOfReviewsDone(Article article) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
            statement.execute("USE "+DATABASE+";");
            statement.close();
            int numReviewsDone = 0;
			String query = "SELECT Art.ARTICLEID, A.AUTHORID, R.REVIEWERID, Count(*) AS REVIEWS "
						+ "FROM ARTICLES Art, AUTHOROFARTICLE Aoa, AUTHORS A, REVIEWERS R, REVIEWS Rev "
						+ "WHERE Aoa.AUTHORID = A.AUTHORID "
						+ "AND Aoa.ARTICLEID = Art.ARTICLEID "
						+ "AND R.AUTHORID = Aoa.AUTHORID "
						+ "AND R.REVIEWERID = Rev.REVIEWERID "
						+ "AND Aoa.ARTICLEID = R.ARTICLEID "
						+ "AND Rev.ARTICLEID = R.ARTICLEID "
						+ "AND Art.ARTICLEID = ? "
						+ "GROUP BY A.AUTHORID, R.REVIEWERID";
			try(PreparedStatement preparedStmt = con.prepareStatement(query)){
				preparedStmt.setInt(1, article.getArticleId());
	            ResultSet res = preparedStmt.executeQuery();
				while(res.next()) {
					numReviewsDone++;
				}
			}catch (SQLException ex) {
				ex.printStackTrace();
			}
			return numReviewsDone;
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	public static ArrayList<Submission> getSubmissions(String university){
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
            statement.execute("USE "+DATABASE+";");
            statement.close();
            String query = "SELECT S.SUBMISSIONID, S.STATUS, Art.ARTICLEID, Art.TITLE, Art.SUMMARY, J.ISSN, J.NAME, J.DATEOFPUBLICATION "
            			+ "FROM SUBMISSIONS S, ARTICLES Art, JOURNALS J "
            			+ "WHERE S.ARTICLEID = Art.ARTICLEID "
            			+ "AND Art.ISSN = J.ISSN "
            			+ "AND SUBMISSIONID NOT IN "
            			+ "(SELECT S.SUBMISSIONID "
            			+ "FROM AUTHORS A, AUTHOROFARTICLE Aoa, ARTICLES Art, SUBMISSIONS S "
            			+ "WHERE Aoa.AUTHORID = A.AUTHORID "
            			+ "AND Aoa.ARTICLEID = Art.ARTICLEID "
            			+ "AND Art.ARTICLEID = S.ARTICLEID "
            			+ "AND UNIVERSITY = ?)";
            ArrayList<Submission> submissions = new ArrayList<Submission>();
            try(PreparedStatement preparedStmt = con.prepareStatement(query)){
            	preparedStmt.setString(1, university);
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
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
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
		Academic[] roles1 = RetrieveDatabase.getRoles("j.doe@uniofdoe.ac.uk");
		Editor e = (Editor)roles1[0];
		Author a = (Author)roles1[1];
		Reviewer r = (Reviewer)roles1[2];
		if(e != null) {
			System.out.println(e);
			for(EditorOfJournal eoj : e.getEditorOfJournals()){
				System.out.println(eoj.getEditor().getEditorId() +" "+ eoj.getJournal().getISSN()+" ChiefEditor: "+eoj.isChiefEditor());
			};
		}
		if(a != null) {
			System.out.println("Author: "+a);
			System.out.println("Number of reviews done by team: "+RetrieveDatabase.getNumberOfReviewsDone(a.getAuthorOfArticles().get(1).getArticle()));
			for(Submission s : RetrieveDatabase.getSubmissions(a.getUniversity())){
				System.out.println(s);
			}
//				for(AuthorOfArticle aoa : a.getAuthorOfArticles()){
//					System.out.println(aoa.getAuthor().getAuthorId()+" "+aoa.getArticle().getArticleId()+" MainAuthor: "+aoa.isMainAuthor());
//					Article article = aoa.getArticle();
//					Submission s = article.getSubmission();
//					System.out.println(s.getStatus());
//					System.out.println();
//					if(s != null)
//						for(Review r : s.getReviews())
//							System.out.println(r);
//				};
		}
		if(r != null) {
//				System.out.println("Reviewer "+r);
//				for(Review review : r.getReviews()) {
//					System.out.println(review);
//				}
		}
	}
}