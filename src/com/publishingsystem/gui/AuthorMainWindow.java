package com.publishingsystem.gui;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
import com.publishingsystem.mainclasses.*;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

/* NOTE TO DO
 * 
 * 
 * MAKE CONDITIONAL STATEMENTS FOR MAIN AUTHORS THAT WOULD CREATE A MENU BUTTON AND WOULD ALLOW TO ADD NOT MAIN AUTHORS TO THE SYSTEM. 
 * THIS ONLY HAPPENS WHEN A PERSON UPLOADS THE ARTICLE (THE MAIN ARTICLE AUTHOR)
 * 
 * 
 * 
 * 
 */
public class AuthorMainWindow {

	private JFrame frmAuthorsDashboard;
	private DefaultTableModel tblSubmittedModel;
	private JTable tblSubmitted;
	private Author author;
	private int selectedAuthorOfArticle;
	private JTable reviewTable;
	private DefaultTableModel reviewTableModel;
	private ArrayList<ReviewerOfSubmission> reviewersOfSubmission;
	private AuthorOfArticle selectedAoa;
	private String pdfPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Hello");
					// Academic[] testAcademic = {null, new Author(6, "Mr", "Alex", "Hall",
					// "ahall8@sheffield.ac.uk", "Sheffield", new Hash("password"))};
					Academic[] testAcademic = { null,
							new Author(7, "Dr", "Kirill", "Bogdanov", "kb@gm.com", "Sheffield", new Hash("password")) };
					// Academic[] testAcademic = null;
					AuthorMainWindow window = new AuthorMainWindow(testAcademic);
					window.frmAuthorsDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AuthorMainWindow(Academic[] roles) {
		this.author = (Author) roles[1];
		initialize(roles);
	}

	public void enable() {
		this.frmAuthorsDashboard.setEnabled(true);
	}

	public void refreshReviewTable() {
		DefaultTableModel model = ((DefaultTableModel) reviewTable.getModel());
		model.setRowCount(0);
		for (int i = 0; i < reviewersOfSubmission.size(); i++) {
			ReviewerOfSubmission ros = reviewersOfSubmission.get(i);
			Review review = reviewersOfSubmission.get(i).getReview();
			Verdict finalVerdict = review.getFinalVerdict();
			boolean responded = RetrieveDatabase.checkIfAllAnswered(ros);
			String[] reviewString = new String[5];
			reviewString[0] = "Reviewer " + (i + 1);
			reviewString[1] = review.getSummary();
			reviewString[2] = review.getInitialVerdict().toString();
			reviewString[3] = finalVerdict == null ? "NONE" : finalVerdict.toString();
			reviewString[4] = responded ? "Yes" : "No";

			model.addRow(reviewString);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		int width = 1080;
		int height = 740;
		// Configure frame
		frmAuthorsDashboard = new JFrame();
		frmAuthorsDashboard.setTitle("Author's Dashboard");
		frmAuthorsDashboard.setBounds(100, 100, width, height);
		frmAuthorsDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthorsDashboard.setVisible(true);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmAuthorsDashboard.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);

		// Window title
		JLabel lblArticleList = new JLabel("Your articles:");
		lblArticleList.setToolTipText("");
		lblArticleList.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// Your articles panel
		JScrollPane scrSubmitted = new JScrollPane();

		ArrayList<AuthorOfArticle> authorOfArticles = author.getAuthorOfArticles();
		tblSubmittedModel = new DefaultTableModel();
		tblSubmittedModel.addColumn("Article ID");
		tblSubmittedModel.addColumn("Main Author");
		tblSubmittedModel.addColumn("Title");
		tblSubmittedModel.addColumn("Status");

		for (int i = 0; i < authorOfArticles.size(); i++) {
			AuthorOfArticle aoa = authorOfArticles.get(i);
			Article article = aoa.getArticle();
			String[] articles = new String[4];
			articles[0] = "  " + String.valueOf(article.getArticleId());
			articles[1] = aoa.isMainAuthor() ? "Yes" : "No";
			articles[2] = article.getTitle();
			articles[3] = article.getSubmission().getStatus().getStatus();
			tblSubmittedModel.addRow(articles);
		}
		tblSubmitted = new JTable(tblSubmittedModel);
		tblSubmitted.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblSubmitted.getColumnModel().getColumn(0).setPreferredWidth(1);
		tblSubmitted.getColumnModel().getColumn(1).setPreferredWidth(10);
		tblSubmitted.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblSubmitted.getColumnModel().getColumn(3).setPreferredWidth(500);
		tblSubmitted.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblSubmitted.setEnabled(false);
		scrSubmitted.setViewportView(tblSubmitted);

		tblSubmitted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = tblSubmitted.rowAtPoint(e.getPoint());
				if (r >= 0 && r < tblSubmitted.getRowCount()) {
					tblSubmitted.setRowSelectionInterval(r, r);
				} else {
					tblSubmitted.clearSelection();
				}

				int rowindex = tblSubmitted.getSelectedRow();
				if (rowindex < 0)
					return;
			}
		});

		JPanel panelArticleReviews = new JPanel();
		panelArticleReviews.setVisible(false);

		JLabel lblSmthsadas = new JLabel("To check for reviews, click on an entry");

		// Configure layout
		GroupLayout groupLayout = new GroupLayout(frmAuthorsDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelArticleReviews, GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE)
							.addContainerGap())
						.addComponent(scrSubmitted, GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSmthsadas)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(261, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(lblSmthsadas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrSubmitted, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelArticleReviews, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panelArticleReviews.setLayout(new BorderLayout(0, 0));

		// Review panel
		JLabel lblArticlesReview = new JLabel("Article's Reviews (To respond to one, click on an entry)");
		panelArticleReviews.add(lblArticlesReview, BorderLayout.NORTH);
		lblArticlesReview.setToolTipText("");
		lblArticlesReview.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JPanel panel = new JPanel();
		panelArticleReviews.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnSubmitRevisedPdf = new JButton("Submit Revised PDF");
		btnSubmitRevisedPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Submission s =selectedAoa.getArticle().getSubmission();
				int numPDF = RetrieveDatabase.getNumPDF(s.getSubmissionId());
				int dialogResult = JOptionPane.showConfirmDialog (null, "You will only be able to upload a revised submission once. Are you sure?","Warning", 0);
				if(dialogResult == JOptionPane.YES_OPTION){
					if(numPDF == 1) {
						if (pdfPath == null) {
							FileDialog fd = new FileDialog(new JFrame());
							fd.setVisible(true);
							File[] f = fd.getFiles();
		
							if (f.length > 0) {
								pdfPath = fd.getFiles()[0].getAbsolutePath();
								PDF revisedPDF = new PDF(-1, new java.sql.Date(Calendar.getInstance().getTime().getTime()), s);
								Database.addRevisedSubmission(revisedPDF, PDFConverter.getByteArrayFromFile(pdfPath), PDFConverter.getNumPagesFromFile(pdfPath));
								JOptionPane.showMessageDialog(null, "PDF successfully uploaded", "Success in submission", 1);
							} else {
								JOptionPane.showMessageDialog(null, "Try again, PDF did not upload", "Error in submission", 0);
							}
						} else {
							JOptionPane.showMessageDialog(null, "PDF already uploaded", "Error in submission", 0);
						}
					}else {
						JOptionPane.showMessageDialog(null, "You have already uploaded the revised PDF", "Error in submission", 0);
					}
				}
			}
		});
		panel.add(btnSubmitRevisedPdf, BorderLayout.WEST);

		JScrollPane scrollPane_2 = new JScrollPane();
		panelArticleReviews.add(scrollPane_2, BorderLayout.CENTER);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		reviewTableModel = new DefaultTableModel();
		reviewTableModel.addColumn("Reviewer");
		reviewTableModel.addColumn("Summary");
		reviewTableModel.addColumn("Initial Verdict");
		reviewTableModel.addColumn("Final Verdict");
		reviewTableModel.addColumn("Responded");

		AuthorMainWindow amw = this;

		reviewTable = new JTable(reviewTableModel);
		reviewTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		reviewTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		reviewTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		reviewTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		reviewTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		reviewTable.getColumnModel().getColumn(4).setPreferredWidth(10);
		reviewTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reviewTable.setEnabled(false);
		scrollPane_2.setViewportView(reviewTable);

		reviewTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = reviewTable.rowAtPoint(e.getPoint());
				if (r >= 0 && r < reviewTable.getRowCount()) {
					reviewTable.setRowSelectionInterval(r, r);
				} else {
					reviewTable.clearSelection();
				}

				int rowindex = reviewTable.getSelectedRow();
				if (rowindex < 0)
					return;
			}
		});

		reviewTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = reviewTable.rowAtPoint(arg0.getPoint());
				System.out.println(row);
				ReviewerOfSubmission ros = reviewersOfSubmission.get(row);

				if (!ros.getReview().responsesRecieved()) {
					frmAuthorsDashboard.setEnabled(false);
					new CriticismResponse(amw, ros, row + 1);
				}
			}
		});

		tblSubmitted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = tblSubmitted.rowAtPoint(arg0.getPoint());
				AuthorOfArticle aoa = authorOfArticles.get(row);

				if (aoa.isMainAuthor()) {
					RetrieveDatabase.checkReviewsForAuthor(aoa);
					reviewersOfSubmission = aoa.getArticle().getSubmission().getReviewersOfSubmission();
					if (reviewersOfSubmission.size() > 0) {
						selectedAoa = aoa;
						panelArticleReviews.setVisible(true);
						refreshReviewTable();
					} else {
						selectedAoa = null;
						JOptionPane.showMessageDialog(null, "No reviews recieved yet.");
					}
				} else {
					panelArticleReviews.setVisible(false);
				}
			}
		});

		frmAuthorsDashboard.getContentPane().setLayout(groupLayout);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		frmAuthorsDashboard.setJMenuBar(menuBar);

		JMenu name = new JMenu("Welcome back " + author.getForename());
		name.setEnabled(false);
		menuBar.add(name);

		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new ChangePassword();
			}
		});
		menu.add(mntmChangePassword);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new JournalWindow(null);
				frmAuthorsDashboard.dispose();
			}
		});
		menu.add(mntmLogOut);

		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);

		if (roles[0] != null) {
			Editor chiefEditor = (Editor) roles[0];
			boolean isChiefEditor = false;
			for (EditorOfJournal eoj : chiefEditor.getEditorOfJournals()) {
				if (eoj.isChiefEditor()) {
					isChiefEditor = true;
					break;
				}
			}
			if (isChiefEditor) {
				JMenuItem mntmToEditor = new JMenuItem("Chief Editor");
				mntmToEditor.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						new ChiefMainWindow(roles);
						frmAuthorsDashboard.dispose();
					}
				});
				mnChangeRole.add(mntmToEditor);
			}
			JMenuItem mntmToEditor = new JMenuItem("Editor");
			mntmToEditor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new EditorMainWindow(roles);
					frmAuthorsDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToEditor);
		}

		if (roles[2] != null) {
			JMenuItem mntmToReviewer = new JMenuItem("Reviewer");
			mntmToReviewer.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					new ReviewerMainWindow(roles);
					frmAuthorsDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToReviewer);
		}

		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new JournalWindow(roles);
				frmAuthorsDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);
	}
}
