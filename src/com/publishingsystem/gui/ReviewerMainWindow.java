package com.publishingsystem.gui;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Article;
import com.publishingsystem.mainclasses.AuthorOfArticle;
import com.publishingsystem.mainclasses.Criticism;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.Reviewer;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Submission;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;


public class ReviewerMainWindow {

	private JFrame frmReviewDashboard;
	private JTable tblChooseToReview;
	private JTable tblToReview;
	private JLabel lblArticleListToChoose;
	private ArrayList<ReviewerOfSubmission> submissionsChosenToReview;
	private ArrayList<Article> articlesSubmitted;
	private int numReviewsToBeDone;
	private Reviewer reviewer;
	private JPanel panel;
	private int submissionRowSelectedToReview;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Academic[] roles = new Academic[3];
					ReviewerMainWindow window = new ReviewerMainWindow(roles);
					window.frmReviewDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReviewerMainWindow(Academic[] roles) {
		submissionsChosenToReview = new ArrayList<ReviewerOfSubmission>();
		articlesSubmitted = new ArrayList<Article>();
		reviewer = (Reviewer)roles[2];
		initialize(roles);
	}
	
	public void addSubmissionToReview(Submission s) {
		this.submissionsChosenToReview.add(new ReviewerOfSubmission(reviewer, s));
	}
	
	private void refreshChooseToReviewTable() {
		DefaultTableModel str_model = (DefaultTableModel)tblChooseToReview.getModel();
		str_model.setRowCount(0);
		str_model.setColumnCount(0);
		str_model.addColumn("Submission ID");
		str_model.addColumn("Title");
		str_model.addColumn("Reviewed");
		for(ReviewerOfSubmission ros : submissionsChosenToReview) {
			Submission s = ros.getSubmission();
			Object[] submissionString = new Object[3];
			submissionString[0] = s.getSubmissionId();
			submissionString[1] = s.getArticle().getTitle();
			submissionString[2] = ros.getReview() == null ? "No" : "Yes";
			str_model.addRow(submissionString);
		}
	}
	
	private void refreshToReviewTable() {
		articlesSubmitted = RetrieveDatabase.getArticlesSubmittedByReviewer(reviewer.getReviewerId());
		DefaultTableModel str_model = (DefaultTableModel)tblToReview.getModel();
		str_model.setRowCount(0);
		str_model.setColumnCount(0);
		str_model.addColumn("Article ID");
		str_model.addColumn("Title");
		str_model.addColumn("No. of reviews selected by co-authors");
		for(Article a : articlesSubmitted) {
			Object[] submissionString = new Object[3];
			submissionString[0] = a.getArticleId();
			submissionString[1] = a.getTitle();
			submissionString[2] = a.getNumReviews();
			str_model.addRow(submissionString);
		}
	}
	
	public void refreshTables() {
		numReviewsToBeDone = RetrieveDatabase.getNumberOfReviewsToBeDone(reviewer.getReviewerId());
		if(numReviewsToBeDone == 0)
			panel.setVisible(false);
		else
			lblArticleListToChoose.setText("Number of articles to review remaining : "+numReviewsToBeDone);
		this.refreshChooseToReviewTable();
		this.refreshToReviewTable();
	}
	
	public void addReview(Review r) {
		this.submissionsChosenToReview.get(submissionRowSelectedToReview).addReview(r);
		this.refreshTables();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		submissionsChosenToReview = reviewer.getReviewerOfSubmissions();
		frmReviewDashboard = new JFrame();
		frmReviewDashboard.setBounds(100, 100, 900, 740);
		frmReviewDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReviewDashboard.setVisible(true);
		
		numReviewsToBeDone = RetrieveDatabase.getNumberOfReviewsToBeDone(reviewer.getReviewerId());
		
		JButton btnCheckResponses = new JButton("Check Responses");
		btnCheckResponses.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		btnCheckResponses.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblArticlesReview = new JLabel("Article's Review");
		lblArticlesReview.setToolTipText("");
		lblArticlesReview.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnNewButton = new JButton("Download PDF");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					// Make this into a thread or maybe you Progress monitors.
					JOptionPane.showMessageDialog(null, "The File will open soon, Press OK", "Reviewer Window", JOptionPane.INFORMATION_MESSAGE);
					OutputStream out = new FileOutputStream((System.getProperty("user.home") + "/Desktop/" + "ArticleToReview.pdf"));
					out.write(RetrieveDatabase.getPDF(1)); //PDF ID
					if(!Desktop.isDesktopSupported()){
			            System.out.println("Desktop is not supported");
			            JOptionPane.showMessageDialog(null, "Unable to open the pdf file", "Reviewer Window", 0);
			            out.close();
			            return;
					} 
					
					File file = new File((System.getProperty("user.home") + "/Desktop/" + "ArticleToReview.pdf"));
					Desktop desktop = Desktop.getDesktop();
			        if(file.exists()) {
			        	desktop.open(file);
			        }
					out.close();
				}catch(FileNotFoundException fnf) {
					
				}catch(IOException io) {
					
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblArticlesYouHave = new JLabel("Articles You Have Chosen to Review:");
		lblArticlesYouHave.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrChosenToReview = new JScrollPane();
		scrChosenToReview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel = new JPanel();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JLabel lblArticle = new JLabel("Article");
		lblArticle.setToolTipText("");
		lblArticle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnReviewArticle = new JButton("Review Article");
		btnReviewArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrollPane = new JScrollPane();
        
		GroupLayout groupLayout = new GroupLayout(frmReviewDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrChosenToReview, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnReviewArticle, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnCheckResponses, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
									.addGap(145))
								.addComponent(lblArticle, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesYouHave)
							.addPreferredGap(ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
							.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addGap(22)))
					.addGap(16))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArticlesYouHave)
						.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCheckResponses)
							.addGap(27)
							.addComponent(lblArticle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton)
								.addComponent(btnReviewArticle)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrChosenToReview, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
							.addGap(29)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)))
					.addGap(31))
		);
		
		JPanel panel_review = new JPanel();
		panel_review.setVisible(false);
		scrollPane.setViewportView(panel_review);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{287, 0};
		gbl_panel_2.rowHeights = new int[]{44, 44, 44, 44, 44, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_review.setLayout(gbl_panel_2);
		
		JLabel lblSummary = new JLabel("Summary");
		GridBagConstraints gbc_lblSummary = new GridBagConstraints();
		gbc_lblSummary.fill = GridBagConstraints.BOTH;
		gbc_lblSummary.insets = new Insets(0, 0, 5, 0);
		gbc_lblSummary.gridx = 0;
		gbc_lblSummary.gridy = 0;
		panel_review.add(lblSummary, gbc_lblSummary);
		
		JEditorPane editorPaneReviewSummary = new JEditorPane();
		editorPaneReviewSummary.setEditable(false);
		GridBagConstraints gbc_editorPane_1 = new GridBagConstraints();
		gbc_editorPane_1.fill = GridBagConstraints.BOTH;
		gbc_editorPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_editorPane_1.gridx = 0;
		gbc_editorPane_1.gridy = 1;
		panel_review.add(editorPaneReviewSummary, gbc_editorPane_1);
		
		JLabel lblTypingErrors = new JLabel("Typing Errors");
		GridBagConstraints gbc_lblTypingErrors = new GridBagConstraints();
		gbc_lblTypingErrors.fill = GridBagConstraints.BOTH;
		gbc_lblTypingErrors.insets = new Insets(0, 0, 5, 0);
		gbc_lblTypingErrors.gridx = 0;
		gbc_lblTypingErrors.gridy = 2;
		panel_review.add(lblTypingErrors, gbc_lblTypingErrors);
		
		JEditorPane editorPaneTypingErrors = new JEditorPane();
		editorPaneTypingErrors.setEditable(false);
		GridBagConstraints gbc_editorPane_1_1 = new GridBagConstraints();
		gbc_editorPane_1_1.fill = GridBagConstraints.BOTH;
		gbc_editorPane_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_editorPane_1_1.gridx = 0;
		gbc_editorPane_1_1.gridy = 3;
		panel_review.add(editorPaneTypingErrors, gbc_editorPane_1_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridheight = 4;
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 4;
		panel_review.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{287, 0};
		gbl_panel_3.rowHeights = new int[]{27, 27, 27, 27, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel_4 = new JPanel();
		scrollPane_2.setViewportView(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{287, 0};
		gbl_panel_4.rowHeights = new int[]{40, 40, 40, 40, 40, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.BOTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panel_4.add(lblTitle, gbc_lblTitle);
		
		JEditorPane editorPaneArticleTitle = new JEditorPane();
		editorPaneArticleTitle.setEditable(false);
		GridBagConstraints gbc_editorPaneArticleTitle = new GridBagConstraints();
		gbc_editorPaneArticleTitle.fill = GridBagConstraints.BOTH;
		gbc_editorPaneArticleTitle.insets = new Insets(0, 0, 5, 0);
		gbc_editorPaneArticleTitle.gridx = 0;
		gbc_editorPaneArticleTitle.gridy = 1;
		panel_4.add(editorPaneArticleTitle, gbc_editorPaneArticleTitle);
		
		JLabel lblArticleSummary = new JLabel("Summary");
		GridBagConstraints gbc_lblArticleSummary = new GridBagConstraints();
		gbc_lblArticleSummary.fill = GridBagConstraints.BOTH;
		gbc_lblArticleSummary.insets = new Insets(0, 0, 5, 0);
		gbc_lblArticleSummary.gridx = 0;
		gbc_lblArticleSummary.gridy = 2;
		panel_4.add(lblArticleSummary, gbc_lblArticleSummary);
		
		JEditorPane editorPaneArticleSummary = new JEditorPane();
		editorPaneArticleSummary.setEditable(false);
		GridBagConstraints gbc_editorPaneArticleSummary = new GridBagConstraints();
		gbc_editorPaneArticleSummary.gridheight = 2;
		gbc_editorPaneArticleSummary.fill = GridBagConstraints.BOTH;
		gbc_editorPaneArticleSummary.insets = new Insets(0, 0, 5, 0);
		gbc_editorPaneArticleSummary.gridx = 0;
		gbc_editorPaneArticleSummary.gridy = 3;
		panel_4.add(editorPaneArticleSummary, gbc_editorPaneArticleSummary);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblArticleListToChoose = new JLabel("Number of articles to review remaining : "+numReviewsToBeDone);
		panel.add(lblArticleListToChoose, BorderLayout.NORTH);
		lblArticleListToChoose.setToolTipText("");
		lblArticleListToChoose.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrSubmitted = new JScrollPane();
		panel.add(scrSubmitted, BorderLayout.CENTER);
		scrSubmitted.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frmReviewDashboard.getContentPane().setLayout(groupLayout);
		
		ReviewerMainWindow rmw = this;
		
		btnReviewArticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ReviewerOfSubmission ros = submissionsChosenToReview.get(submissionRowSelectedToReview);
				if(ros.getReview() == null)
					new ReviewArticle(submissionsChosenToReview.get(submissionRowSelectedToReview), rmw);
			}
		});
		
		DefaultTableModel str_model1 = new DefaultTableModel();
		tblChooseToReview = new JTable(str_model1);
		refreshChooseToReviewTable();
		tblChooseToReview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblChooseToReview.setEnabled(false);
		scrChosenToReview.setViewportView(tblChooseToReview);
		tblChooseToReview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    // Open new window displaying the articles in the selected article
				int row = tblChooseToReview.rowAtPoint(arg0.getPoint());
				submissionRowSelectedToReview = row;
				ReviewerOfSubmission ros = submissionsChosenToReview.get(row);
				Review review =  ros.getReview();
				if(review != null) {
					btnReviewArticle.setText("Submit Final Verdict");
					panel_review.setVisible(true);
					editorPaneReviewSummary.setText(review.getSummary());
					editorPaneTypingErrors.setText(review.getTypingErrors());
					int criticisms = 1;
					int counter = 0;
					for(Criticism c : review.getCriticisms()) {
						JLabel lbll = new JLabel("Criticism "+criticisms);
						GridBagConstraints gbc_lbll = new GridBagConstraints();
						gbc_lbll.fill = GridBagConstraints.BOTH;
						gbc_lbll.insets = new Insets(0, 0, 5, 0);
						gbc_lbll.gridx = 0;
						gbc_lbll.gridy = counter++;
						panel_3.add(lbll, gbc_lbll);
						
						JEditorPane editorPaneCriticism = new JEditorPane();
						editorPaneCriticism.setEditable(false);
						editorPaneCriticism.setText(c.getCriticism());
						GridBagConstraints gbc_editorPaneCriticism = new GridBagConstraints();
						gbc_editorPaneCriticism.fill = GridBagConstraints.BOTH;
						gbc_editorPaneCriticism.insets = new Insets(0, 0, 5, 0);
						gbc_editorPaneCriticism.gridx = 0;
						gbc_editorPaneCriticism.gridy = counter++;
						panel_3.add(editorPaneCriticism, gbc_editorPaneCriticism);
						
						JLabel lblL = new JLabel("Answer");
						GridBagConstraints gbc_lblL = new GridBagConstraints();
						gbc_lblL.fill = GridBagConstraints.BOTH;
						gbc_lblL.insets = new Insets(0, 0, 5, 0);
						gbc_lblL.gridx = 0;
						gbc_lblL.gridy = counter++;
						panel_3.add(lblL, gbc_lblL);
						
						JEditorPane editorPaneAnswer = new JEditorPane();
						editorPaneAnswer.setEditable(false);
						editorPaneAnswer.setText(c.getAnswer());
						GridBagConstraints gbc_editorPaneAnswer = new GridBagConstraints();
						gbc_editorPaneAnswer.fill = GridBagConstraints.BOTH;
						gbc_editorPaneAnswer.gridx = 0;
						gbc_editorPaneAnswer.gridy = counter++;
						panel_3.add(editorPaneAnswer, gbc_editorPaneAnswer);
					}
				}else {
					panel_3.removeAll();
					panel_3.updateUI();
					panel_review.setVisible(false);
				}
				editorPaneArticleSummary.setText(ros.getSubmission().getArticle().getSummary());
				editorPaneArticleTitle.setText(ros.getSubmission().getArticle().getTitle());
				System.out.println(row);
			} 
		});
			
		articlesSubmitted = RetrieveDatabase.getArticlesSubmittedByReviewer(reviewer.getReviewerId());
		if(articlesSubmitted.size() > 0) {
			JPanel panel_1 = new JPanel();
			panel.add(panel_1, BorderLayout.SOUTH);
			
			DefaultTableModel str_model = new DefaultTableModel();			

			tblToReview = new JTable(str_model);
			refreshToReviewTable();
			tblToReview.getColumnModel().getColumn(0).setResizable(false);
			tblToReview.getColumnModel().getColumn(1).setResizable(false);
			tblToReview.getColumnModel().getColumn(2).setResizable(false);
			tblToReview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tblToReview.setEnabled(false);
			tblToReview.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
				    // Open new window displaying the articles in the selected article
					int selectedArticle = (int)tblToReview.getValueAt(tblToReview.rowAtPoint(arg0.getPoint()), 0);				
					System.out.println(selectedArticle);
					new ChooseArticlesToReview(reviewer, selectedArticle, rmw);
				} 
			});
			
			scrSubmitted.setViewportView(tblToReview);
		}else {
			panel.setVisible(false);
		}
		
		
		JMenuBar menuBar = new JMenuBar();
		frmReviewDashboard.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem menuItem_1 = new JMenuItem("Change Password");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new ChangePassword();
			}
		});
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Log Out");
		menuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new JournalWindow(null);
				frmReviewDashboard.dispose();
				//System.exit(0);
			}
		});
		
		menu.add(menuItem_2);
		
		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);
		
		
		if(roles[0] != null) {
			Editor chiefEditor = (Editor)roles[0];
			boolean isChiefEditor = false;
			for(EditorOfJournal eoj : chiefEditor.getEditorOfJournals()) {
				if(eoj.isChiefEditor()) {
					isChiefEditor = true;
					break;
				}
			}
			if(isChiefEditor) {
				JMenuItem mntmToEditor = new JMenuItem("Chief Editor");
				mntmToEditor.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						new ChiefMainWindow(roles);
						frmReviewDashboard.dispose();
					}
				});
				mnChangeRole.add(mntmToEditor);
			}
			JMenuItem mntmToEditor = new JMenuItem("Editor");
			mntmToEditor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new EditorMainWindow(roles);
					frmReviewDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToEditor);
		}
		
		if(roles[1] != null) {
			JMenuItem mntmToEditor = new JMenuItem("Author");
			mntmToEditor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new AuthorMainWindow(roles);
					frmReviewDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToEditor);
		}
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new JournalWindow(roles);
				frmReviewDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);

	}
}
