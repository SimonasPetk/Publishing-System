package com.publishingsystem.mainclasses;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class Database {
//	protected static final String CONNECTION = "jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f";
//	protected static final String DATABASE = "team022";

	protected static final String CONNECTION = "jdbc:mysql://localhost:3306/publishing_system?user=root&password=simonass";
	protected static final String DATABASE = "publishing_system";

	public static String getConnectionName() {
		return CONNECTION;
	}

	public static String getDatabaseName() {
		return DATABASE;
	}

	public static void setChiefEditor(EditorOfJournal eoj) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "UPDATE EDITOROFJOURNAL SET ChiefEditor = 1 WHERE editorID = ? AND ISSN = ?;";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, eoj.getEditor().getEditorId());
				preparedStmt.setInt(2, eoj.getJournal().getISSN());
				preparedStmt.execute();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void tempRetireEditor(EditorOfJournal eoj) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "UPDATE EDITOROFJOURNAL SET TempRetired = 1 WHERE editorID = ? and ISSN = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, eoj.getEditor().getEditorId());
				preparedStmt.setInt(2, eoj.getJournal().getISSN());
				preparedStmt.execute();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void reInitiateEditor(EditorOfJournal eoj) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "UPDATE EDITOROFJOURNAL SET TempRetired = 0 WHERE editorID = ? AND ISSN = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, eoj.getEditor().getEditorId());
				preparedStmt.setInt(2, eoj.getJournal().getISSN());
				preparedStmt.execute();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void retireEditor(EditorOfJournal eoj) {
		Editor e = eoj.getEditor();
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "DELETE FROM EDITOROFJOURNAL WHERE editorID = ? AND ISSN = ?;";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, eoj.getEditor().getEditorId());
				preparedStmt.setInt(2, eoj.getJournal().getISSN());
				preparedStmt.execute();
				// Update the next editor to be the chief editor if the chief editor is retiring
				if (eoj.isChiefEditor()) {
					query = "UPDATE EDITOROFJOURNAL SET CHIEFEDITOR=1 " + "WHERE EDITORID = "
							+ "(SELECT EDITORID FROM (SELECT EDITORID FROM EDITOROFJOURNAL WHERE CHIEFEDITOR = 0 AND ISSN = ? ORDER BY EDITORID) AS X LIMIT 1)";
					try (PreparedStatement preparedStmt2 = con.prepareStatement(query)) {
						preparedStmt2.setInt(1, eoj.getJournal().getISSN());
						preparedStmt2.execute();
					}
				}
			}

			if (e.getEditorOfJournals().size() == 1) {
				query = "DELETE FROM EDITORS WHERE EDITORID = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, e.getEditorId());
					preparedStmt.execute();
				}
			}
			statement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Academic[] roles = RetrieveDatabase.getRoles(eoj.getEditor().getEmailId().toLowerCase());
		if (roles[0] == null && roles[1] == null && roles[2] == null) {
			deleteAcademic(e.getAcademicId());
		}
	}

	public static void deleteAcademic(int academicId) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "DELETE FROM ACADEMICS WHERE academicID = ?;";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, academicId);
				preparedStmt.execute();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void removeChiefEditor(EditorOfJournal eoj) {

		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "UPDATE EDITOROFJOURNAL SET ChiefEditor = 0 WHERE editorID = ? and ISSN = ?;";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, eoj.getEditor().getEditorId());
				preparedStmt.setInt(2, eoj.getJournal().getISSN());
				preparedStmt.execute();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static int getEditorIdFromAcademicId(int academicId) {
		int editorId = -1;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "SELECT * FROM EDITORS WHERE academicID = ?;";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, academicId);
				ResultSet res = preparedStmt.executeQuery();
				while (res.next()) {
					editorId = res.getInt("editorID");
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return editorId;
	}

	public static void addAcademicToEditors(int editorId, int issn) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "INSERT INTO EDITOROFJOURNAL values (?, ?,0,0)";

			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, editorId);
				preparedStmt.setInt(2, issn);
				preparedStmt.execute();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void registerEditor(Editor e) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			boolean academicExists = false;
			String query = "SELECT 1 FROM ACADEMICS WHERE emailAddress = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setString(1, e.getEmailId());
				ResultSet res = preparedStmt.executeQuery();
				if (res.next())
					academicExists = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			if (!academicExists) {
				query = "INSERT INTO ACADEMICS values (null, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setString(1, e.getTitle());
					preparedStmt.setString(2, e.getForename());
					preparedStmt.setString(3, e.getSurname());
					preparedStmt.setString(4, e.getUniversity().trim().toLowerCase());
					preparedStmt.setString(5, e.getEmailId().toLowerCase());
					preparedStmt.setString(6, e.getHash().getHash());
					preparedStmt.setString(7, e.getHash().getSalt());

					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ACADEMICS");
					while (rs.next())
						e.setAcademicId(Integer.valueOf(rs.getString("last_id")));
				}
			}

			boolean editorExists = false;
			query = "SELECT 1 FROM EDITORS WHERE ACADEMICID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, e.getAcademicId());
				ResultSet res = preparedStmt.executeQuery();
				if (res.next())
					editorExists = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			if (!editorExists) {
				// Add editor to editor table
				query = "INSERT INTO EDITORS values (null, ?)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, e.getAcademicId());
					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from EDITORS");
					while (rs.next())
						e.setEditorId(Integer.valueOf(rs.getString("last_id")));
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void updateEditorOfJournal(ArrayList<Editor> editor, Journal j) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			for (Editor e : editor) {
				e.addEditorOfJournal(new EditorOfJournal(j, e, false));
				String query = "INSERT INTO EDITOROFJOURNAL values (?, ?, ?, ?)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, e.getEditorId());
					preparedStmt.setInt(2, j.getISSN());
					preparedStmt.setBoolean(3, false);
					preparedStmt.setBoolean(4, false);
					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void registerAuthors(ArrayList<Author> authors) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			for (Author a : authors) {
				boolean academicExists = false;
				String query = "SELECT 1 FROM ACADEMICS WHERE emailAddress = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setString(1, a.getEmailId().toLowerCase());
					ResultSet res = preparedStmt.executeQuery();
					if (res.next())
						academicExists = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				if (!academicExists) {
					query = "INSERT INTO ACADEMICS values (null, ?, ?, ?, ?, ?, ?, ?)";
					try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
						preparedStmt.setString(1, a.getTitle());
						preparedStmt.setString(2, a.getForename());
						preparedStmt.setString(3, a.getSurname());
						preparedStmt.setString(4, a.getUniversity().trim().toLowerCase());
						preparedStmt.setString(5, a.getEmailId().toLowerCase());
						preparedStmt.setString(6, a.getHash().getHash());
						preparedStmt.setString(7, a.getHash().getSalt());

						preparedStmt.execute();
						ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ACADEMICS");
						while (rs.next())
							a.setAcademicId(Integer.valueOf(rs.getString("last_id")));

					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}

				boolean authorExists = false;
				query = "SELECT 1 FROM AUTHORS WHERE academicID = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, a.getAcademicId());
					ResultSet res = preparedStmt.executeQuery();
					if (res.next())
						authorExists = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				if (!authorExists) {
					query = "INSERT INTO AUTHORS values (null, ?, ?, ?, ?)";
					try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
						preparedStmt.setInt(1, a.getAcademicId());
						preparedStmt.setString(2, a.getForename() + " " + a.getSurname());
						preparedStmt.setString(3, a.getUniversity().trim().toLowerCase());
						preparedStmt.setString(4, a.getEmailId().toLowerCase());
						preparedStmt.execute();

						ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from AUTHORS");
						while (rs.next())
							a.setAuthorId((Integer.valueOf(rs.getString("last_id"))));
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addJournal(Journal j) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			// Add journal to journal table
			String query = "INSERT INTO JOURNALS values (?, ?, ?)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, j.getISSN());
				preparedStmt.setString(2, j.getJournalName());
				preparedStmt.setDate(3, j.getDateOfPublication());
				preparedStmt.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			for (EditorOfJournal e : j.getBoardOfEditors()) {
				query = "INSERT INTO EDITOROFJOURNAL values (?, ?, ?, ?)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, e.getEditor().getEditorId());
					preparedStmt.setInt(2, j.getISSN());
					if (e.isChiefEditor())
						preparedStmt.setBoolean(3, true);
					else
						preparedStmt.setBoolean(3, false);
					preparedStmt.setBoolean(4, e.isTempRetired());
					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addSubmission(Article article, byte[] pdfBytes, int numPages) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			// Add submission to article table
			String query = "INSERT INTO ARTICLES values (null, ?, null, ?, ?, 0)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, article.getJournal().getISSN());
				preparedStmt.setString(2, article.getTitle());
				preparedStmt.setString(3, article.getSummary());
				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from ARTICLES");
				while (rs.next())
					article.setArticleId(Integer.valueOf(rs.getString("last_id")));
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			// Add submission to submissions table
			query = "INSERT INTO SUBMISSIONS values (null, ?, ?)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, article.getArticleId());
				preparedStmt.setString(2, SubmissionStatus.SUBMITTED.asString());
				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from SUBMISSIONS");
				while (rs.next())
					article.getSubmission().setSubmissionId(Integer.valueOf(rs.getString("last_id")));
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			// Register each author as an author of the article
			for (AuthorOfArticle a : article.getAuthorsOfArticle()) {
				query = "INSERT INTO AUTHOROFARTICLE values (?, ?, ?)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, a.getAuthor().getAuthorId());
					preparedStmt.setInt(2, article.getArticleId());
					if (a.isMainAuthor())
						preparedStmt.setBoolean(3, true);
					else
						preparedStmt.setBoolean(3, false);
					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			ArrayList<PDF> pdfs = article.getSubmission().getVersions();
			PDF pdf = pdfs.get(pdfs.size() - 1);
			query = "INSERT INTO PDF values (null, ?, ?, ?, ?)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, article.getSubmission().getSubmissionId());
				preparedStmt.setDate(2, pdf.getDate());
				preparedStmt.setBlob(3, PDFConverter.getPDFBlob(pdfBytes));
				preparedStmt.setInt(4, numPages);

				preparedStmt.execute();
				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from PDF");
				while (rs.next())
					pdf.setPdfId(Integer.valueOf(rs.getString("last_id")));
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void registerReviewers(ArrayList<Reviewer> reviewers) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			for (Reviewer r : reviewers) {
				boolean reviewerExists = false;
				String query = "SELECT 1 FROM REVIEWERS WHERE ACADEMICID = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, r.getAcademicId());
					ResultSet res = preparedStmt.executeQuery();
					if (res.next())
						reviewerExists = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (!reviewerExists) {
					query = "INSERT INTO REVIEWERS values (null, ?)";
					try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
						preparedStmt.setInt(1, r.getAcademicId());
						preparedStmt.execute();

						ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from REVIEWERS");
						while (rs.next())
							r.setReviewerId(Integer.valueOf(rs.getString("last_id")));
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void selectSubmissionsToReview(Reviewer r, ArrayList<Submission> submissions, int articleId) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			for (Submission s : submissions) {
				String query = "INSERT INTO REVIEWEROFSUBMISSION values (?, ?)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, r.getReviewerId());
					preparedStmt.setInt(2, s.getSubmissionId());
					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				query = "UPDATE ARTICLES SET NUMREVIEWS = NUMREVIEWS+1 WHERE ARTICLEID = ?";
				try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
					preparedStmt1.setInt(1, articleId);
					preparedStmt1.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				boolean submissionExists = false;
				query = "SELECT 1 FROM SUBMISSIONS WHERE ARTICLEID = ?";
				try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
					preparedStmt1.setInt(1, articleId);
					preparedStmt1.execute();
					ResultSet res = preparedStmt1.executeQuery();
					if (res.next())
						submissionExists = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				boolean publishedArticleExists = false;
				query = "SELECT 1 FROM PUBLISHEDARTICLES WHERE ARTICLEID = ?";
				try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
					preparedStmt1.setInt(1, articleId);
					preparedStmt1.execute();
					ResultSet res = preparedStmt1.executeQuery();
					if (res.next())
						publishedArticleExists = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (!submissionExists && !publishedArticleExists) {
					query = "DELETE Art.*, Aoa.*, A.* "
							+ "ARTICLES Art INNER JOIN AUTHOROFARTICLE Aoa ON Art.ARTICLEID = Aoa.ARTICLEID "
							+ "INNER JOIN AUTHORS A ON Aoa.AUTHORID = A.AUTHORID "
							+ "WHERE Art.NUMREVIEWS = 3 AND Art.ARTICLEID = ?";
					try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
						preparedStmt1.setInt(1, articleId);
						preparedStmt1.execute();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addReview(Review review) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			Reviewer reviewer = review.getReviewerOfSubmission().getReviewer();
			Submission submission = review.getReviewerOfSubmission().getSubmission();

			String query = "INSERT INTO REVIEWS values (?, ?, ?, ?, ?, null)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, reviewer.getReviewerId());
				preparedStmt.setInt(2, submission.getSubmissionId());
				preparedStmt.setString(3, review.getSummary());
				preparedStmt.setString(4, review.getTypingErrors());
				preparedStmt.setString(5, review.getInitialVerdict().asString());
				preparedStmt.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			for (Criticism c : review.getCriticisms()) {
				query = "INSERT INTO CRITICISMS values (null, ?, ?, ?, null)";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setInt(1, reviewer.getReviewerId());
					preparedStmt.setInt(2, submission.getSubmissionId());
					preparedStmt.setString(3, c.getCriticism());

					preparedStmt.execute();

					ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from CRITICISMS");
					while (rs.next())
						c.setCriticismId((Integer.valueOf(rs.getString("last_id"))));
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			query = "SELECT COUNT(*) AS REVIEWS FROM REVIEWS WHERE submissionID = ?";
			int numReviews = 0;
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, submission.getSubmissionId());
				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next())
					numReviews = rs.getInt("REVIEWS");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			if (numReviews == 3) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setString(1, SubmissionStatus.REVIEWSRECEIVED.asString());
					preparedStmt.setInt(2, submission.getSubmissionId());

					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void setVerdict(ReviewerOfSubmission ros) {
		Review r = ros.getReview();
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			Reviewer reviewer = r.getReviewerOfSubmission().getReviewer();
			Submission submission = r.getReviewerOfSubmission().getSubmission();

			String query = "UPDATE REVIEWS SET finalVerdict = ? WHERE reviewerID = ? and submissionID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setString(1, r.getFinalVerdict().asString());
				preparedStmt.setInt(2, reviewer.getReviewerId());
				preparedStmt.setInt(3, submission.getSubmissionId());

				preparedStmt.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			int numFinalVerdicts = 0;

			query = "SELECT REVIEWS.SUBMISSIONID, COUNT(*) AS REVIEWS FROM REVIEWS, "
					+ "SUBMISSIONS WHERE REVIEWS.submissionID = ? AND SUBMISSIONS.submissionID = ? "
					+ "AND finalVerdict IS NOT NULL";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, submission.getSubmissionId());
				preparedStmt.setInt(2, submission.getSubmissionId());
				ResultSet rs = preparedStmt.executeQuery();
				while (rs.next()) {
					numFinalVerdicts = rs.getInt("REVIEWS");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			if (numFinalVerdicts == 3) {
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try (PreparedStatement ps = con.prepareStatement(query)) {
					ps.setString(1, SubmissionStatus.FINALVERDICT.asString());
					ps.setInt(2, submission.getSubmissionId());
					ps.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void deleteReviewer(int reviewerId) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "DELETE FROM REVIEWERS WHERE REVIEWERID = ?";
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, reviewerId);
				ps.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addResponse(ReviewerOfSubmission ros) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			Submission submission = ros.getSubmission();
			ArrayList<Criticism> criticisms = ros.getReview().getCriticisms();
			for (Criticism c : criticisms) {
				String query = "UPDATE CRITICISMS SET ANSWER = ? WHERE criticismID = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setString(1, c.getAnswer());
					preparedStmt.setInt(2, c.getCriticismId());

					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			int numResponded = 0;
			String query = " SELECT COUNT(*) AS NUMRESPONDED FROM REVIEWS R, CRITICISMS C "
					+ "WHERE C.SUBMISSIONID = R.SUBMISSIONID " + "AND C.REVIEWERID = R.REVIEWERID "
					+ "AND ANSWER IS NOT NULL " + "AND R.SUBMISSIONID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, submission.getSubmissionId());
				ResultSet rs = preparedStmt.executeQuery();
				if (rs.next()) {
					numResponded = rs.getInt("NUMRESPONDED");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			if (numResponded == 3) {
				submission.setStatus(SubmissionStatus.RESPONSESRECEIVED);
				query = "UPDATE SUBMISSIONS SET status = ? WHERE submissionID = ?";
				try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
					preparedStmt.setString(1, SubmissionStatus.RESPONSESRECEIVED.asString());
					preparedStmt.setInt(2, submission.getSubmissionId());

					preparedStmt.execute();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void addRevisedSubmission(PDF pdf, byte[] pdfBytes, int numPages) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			Submission s = pdf.getSubmission();
			String query = "INSERT INTO PDF values (null, ?, ?, ?, ?)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, s.getSubmissionId());
				preparedStmt.setDate(2, pdf.getDate());
				preparedStmt.setBlob(3, PDFConverter.getPDFBlob(pdfBytes));
				preparedStmt.setInt(4, numPages);
				preparedStmt.execute();

				ResultSet rs = preparedStmt.executeQuery("select last_insert_id() as last_id from PDF");
				while (rs.next())
					pdf.setPdfId(Integer.valueOf(rs.getString("last_id")));
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean academicExists(String email) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "SELECT 1 FROM ACADEMICS WHERE emailAddress = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setString(1, email.trim().toLowerCase());
				ResultSet res = preparedStmt.executeQuery();
				if (res.next())
					return true;
				else
					return false;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void changePassword(int academicId, String hash, String salt) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "UPDATE ACADEMICS SET hash = ?, salt = ? WHERE academicID = ?;";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setString(1, hash);
				preparedStmt.setString(2, salt);
				preparedStmt.setInt(3, academicId);
				preparedStmt.execute();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean validateCredentials(String email, String password) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "SELECT academicID, hash, salt FROM ACADEMICS WHERE emailAddress = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setString(1, email.trim().toLowerCase());
				ResultSet res = preparedStmt.executeQuery();

				int academicID = -1;
				String dbHash = null;
				String dbSalt = null;
				if (res.next()) {
					academicID = res.getInt(1);
					dbHash = res.getString(2);
					dbSalt = res.getString(3);
				}

				if (academicID != -1) {
					// Generate hash based on fetched salt and entered password
					Hash newHash = new Hash(password, dbSalt);
					password = ""; // Delete password by setting it to an empty string
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

	public static boolean validateJournalTitle(String title) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "SELECT NAME FROM JOURNALS WHERE NAME = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setString(1, title.trim());
				ResultSet res = preparedStmt.executeQuery();

				boolean titleExists = false;

				if (res.next()) {
					titleExists = true;
				}

				if (titleExists) {
					// Generate hash based on fetched salt and entered password

					return titleExists;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean validateJournalISSN(int issn) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "SELECT ISSN FROM JOURNALS WHERE ISSN = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, issn);
				ResultSet res = preparedStmt.executeQuery();

				boolean issnExists = false;

				if (res.next()) {
					issnExists = true;
				}

				if (issnExists) {
					// Generate hash based on fetched salt and entered password

					return issnExists;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void publishEdition(int editionId) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			String query = "UPDATE EDITIONS SET PUBLISHED = 1, MONTH = ? WHERE EDID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, Calendar.getInstance().get(Calendar.MONTH));
				preparedStmt.setInt(2, editionId);
				preparedStmt.execute();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * addEdition
	 * 
	 * Add a given edition to the database and return the primary key of it's new
	 * record
	 * 
	 * @param volNum The volume number the edition is in
	 * @param month  The month of the edition
	 * @return Primary key of the new edition record
	 */
	public static int addEdition(int volNum, int month) {
		int result = -1;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			String query = "INSERT INTO EDITIONS " + "VALUES (null, ?, 0, ?);";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, volNum);
				preparedStmt.setInt(2, month);
				preparedStmt.execute();

				query = "SELECT last_insert_id() AS last_id FROM EDITIONS;";
				try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
					ResultSet res = preparedStmt1.executeQuery();
					if (res.next()) {
						result = res.getInt(1);
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * addVolume
	 * 
	 * Create a new volume for the given year in the given journal in the database
	 * 
	 * @param issn The issn of the given journal
	 * @param year The year of the new volume
	 * @return The primary key of this new volume
	 */
	public static int addVolume(int issn, int year) {
		int result = -1;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "INSERT INTO VOLUMES " + "VALUES (null, ?, ?);";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, year);
				preparedStmt.setInt(2, issn);
				preparedStmt.execute();

				query = "SELECT last_insert_id() AS last_id FROM VOLUMES;";
				try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
					ResultSet res = preparedStmt1.executeQuery();
					if (res.next()) {
						result = res.getInt(1);
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * addPublishedArticle
	 * 
	 * Create a new published article for the given article in the given edition in
	 * the database
	 * 
	 * @param edId      The id of the edition the published article is a part of
	 * @param articleId The id of the article the published article links to
	 * @return
	 */
	public static int addPublishedArticle(int edId, int articleId) {
		int result = -1;
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "INSERT INTO PUBLISHEDARTICLES VALUES (null, ?, ?)";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, articleId);
				preparedStmt.setInt(2, edId);
				preparedStmt.execute();
			}

			query = "SELECT last_insert_id() AS last_id FROM PUBLISHEDARTICLES;";
			try (PreparedStatement preparedStmt1 = con.prepareStatement(query)) {
				ResultSet res = preparedStmt1.executeQuery();
				if (res.next()) {
					result = res.getInt(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static void acceptSubmission(Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();
			String query = "DELETE S.*, Ros.*, Rev.*, C.* "
					+ "FROM SUBMISSIONS S INNER JOIN REVIEWEROFSUBMISSION Ros ON S.SUBMISSIONID = Ros.SUBMISSIONID "
					+ "INNER JOIN REVIEWS Rev ON Rev.SUBMISSIONID = Ros.SUBMISSIONID "
					+ "INNER JOIN CRITICISMS C ON C.SUBMISSIONID = Rev.SUBMISSIONID " + "WHERE S.SUBMISSIONID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, s.getSubmissionId());
				preparedStmt.execute();
			}

			query = "UPDATE ARTICLES A SET PDFID = (SELECT PDFID FROM PDF WHERE SUBMISSIONID = ? ORDER BY PDFID DESC LIMIT 1) WHERE A.ARTICLEID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, s.getSubmissionId());
				preparedStmt.setInt(2, s.getArticle().getArticleId());
				preparedStmt.execute();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void rejectSubmission(Submission s) {
		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			String query = "DELETE Art.*, Aoa.*, A.* "
					+ "ARTICLES Art INNER JOIN AUTHOROFARTICLE Aoa ON Art.ARTICLEID = Aoa.ARTICLEID "
					+ "INNER JOIN AUTHORS A ON Aoa.AUTHORID = A.AUTHORID "
					+ "WHERE Art.NUMREVIEWS = 3 AND Art.ARTICLEID = ?";
			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, s.getArticle().getArticleId());
				preparedStmt.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			query = "DELETE S.*, Ros.*, Rev.*, C.* "
					+ "FROM SUBMISSIONS S INNER JOIN REVIEWEROFSUBMISSION Ros ON S.SUBMISSIONID = Ros.SUBMISSIONID "
					+ "INNER JOIN REVIEWS Rev ON Rev.SUBMISSIONID = Ros.SUBMISSIONID "
					+ "INNER JOIN CRITICISMS C ON C.SUBMISSIONID = Rev.SUBMISSIONID " + "WHERE S.SUBMISSIONID = ?";
			;

			try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
				preparedStmt.setInt(1, s.getSubmissionId());
				preparedStmt.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
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
		System.out.println();
		
		CreateDatabase.printAllRecords("PUBLISHEDARTICLES");
		CreateDatabase.printAllRecords("EDITIONS");

		try (Connection con = DriverManager.getConnection(CONNECTION)) {
			Statement statement = con.createStatement();
			statement.execute("USE " + DATABASE + ";");
			statement.close();

			String query = "SHOW TABLES";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet res = preparedStmt.executeQuery();
			while (res.next()) {
				System.out.println(res.getString(1));
			}
			Statement printStatements = con.createStatement();
			printStatements.execute("USE " + DATABASE + ";");
			printStatements.executeQuery("SELECT * FROM ACADEMICS");
			printStatements.close();

			CreateDatabase.printAllRecords("ACADEMICS");
			CreateDatabase.printAllRecords("EDITOROFJOURNAL");
			//removeChiefEditor(4);
		}catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
