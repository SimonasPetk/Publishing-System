package com.publishingsystem.gui;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
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
import com.publishingsystem.mainclasses.PDF;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.Reviewer;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Submission;
import com.publishingsystem.mainclasses.Verdict;

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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


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
	private JPanel panel_review;
	private int submissionRowSelectedToReview = -1;

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
		articlesSubmitted = new ArrayList<Article>();
		reviewer = (Reviewer)roles[2];
		submissionsChosenToReview = reviewer.getReviewerOfSubmissions();
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
		frmReviewDashboard.setBounds(100, 100, 1089, 740);
		frmReviewDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReviewDashboard.setVisible(true);
		
		numReviewsToBeDone = RetrieveDatabase.getNumberOfReviewsToBeDone(reviewer.getReviewerId());
		
		JLabel lblArticlesReview = new JLabel("Article's Review:");
		lblArticlesReview.setToolTipText("");
		lblArticlesReview.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnNewButton = new JButton("Download PDF");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(submissionRowSelectedToReview != -1) {
					try {
						ReviewerOfSubmission ros = submissionsChosenToReview.get(submissionRowSelectedToReview);
				        Submission s = ros.getSubmission();
				        ArrayList<byte[]> versions = RetrieveDatabase.getPDF(s.getSubmissionId());
				        for(byte[] pdf : versions) {
							JFileChooser f = new JFileChooser();
					        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					        f.showSaveDialog(null);
					        System.out.println(f.getCurrentDirectory());
					        System.out.println(f.getSelectedFile());
					        OutputStream out = new FileOutputStream(f.getSelectedFile()+".pdf");
							out.write(pdf); //PDF ID
							out.close();
				        }
					}catch(FileNotFoundException fnf) {
						
					}catch(IOException io) {
//						
					}
				}else {
					JOptionPane.showMessageDialog(null, "No article selected", "Error in download", 0);
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
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblArticle = new JLabel("Article:");
		lblArticle.setToolTipText("");
		lblArticle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnReviewArticle = new JButton("Review Article");
		btnReviewArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel pnlVerdict = new JPanel();
        
		GroupLayout groupLayout = new GroupLayout(frmReviewDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrChosenToReview, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lblArticlesYouHave))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnReviewArticle, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
								.addComponent(pnlVerdict, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(15)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblArticle, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(47)
							.addComponent(lblArticlesReview, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(47)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)))
					.addContainerGap())
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
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlVerdict, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(lblArticle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnReviewArticle)
								.addComponent(btnNewButton)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrChosenToReview, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
							.addGap(29)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)))
					.addGap(31))
		);
		
		JComboBox cmbVerdict = new JComboBox();
		cmbVerdict.setModel(new DefaultComboBoxModel(new String[] {"SELECT", "STRONG ACCEPT", "WEAK ACCEPT", "WEAK REJECT", "STRONG REJECT"}));
		cmbVerdict.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnVerdict = new JButton("Submit Final Verdict");
		btnVerdict.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout gl_pnlVerdict = new GroupLayout(pnlVerdict);
		gl_pnlVerdict.setHorizontalGroup(
			gl_pnlVerdict.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlVerdict.createSequentialGroup()
					.addGap(23)
					.addComponent(cmbVerdict, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnVerdict)
					.addContainerGap(234, Short.MAX_VALUE))
		);
		gl_pnlVerdict.setVerticalGroup(
			gl_pnlVerdict.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVerdict.createSequentialGroup()
					.addGroup(gl_pnlVerdict.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbVerdict, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVerdict))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlVerdict.setLayout(gl_pnlVerdict);
		
		panel_review = new JPanel();
		panel_review.setVisible(false);
		scrollPane.setViewportView(panel_review);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{287, 0};
		gbl_panel_2.rowHeights = new int[]{44, 44, 44, 44, 44, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_review.setLayout(gbl_panel_2);
		
		JLabel lblSummary = new JLabel("Summary");
		GridBagConstraints gbc_lblSummary = new GridBagConstraints();
		gbc_lblSummary.fill = GridBagConstraints.BOTH;
		gbc_lblSummary.insets = new Insets(0, 0, 5, 0);
		gbc_lblSummary.gridx = 0;
		gbc_lblSummary.gridy = 0;
		panel_review.add(lblSummary, gbc_lblSummary);
		
		JTextArea textAreaReviewSummary = new JTextArea();
		GridBagConstraints gbc_textArea_2 = new GridBagConstraints();
		gbc_textArea_2.insets = new Insets(0, 0, 5, 0);
		gbc_textArea_2.fill = GridBagConstraints.BOTH;
		gbc_textArea_2.gridx = 0;
		gbc_textArea_2.gridy = 1;
		panel_review.add(textAreaReviewSummary, gbc_textArea_2);
		
		JLabel lblTypingErrors = new JLabel("Typing Errors");
		GridBagConstraints gbc_lblTypingErrors = new GridBagConstraints();
		gbc_lblTypingErrors.fill = GridBagConstraints.BOTH;
		gbc_lblTypingErrors.insets = new Insets(0, 0, 5, 0);
		gbc_lblTypingErrors.gridx = 0;
		gbc_lblTypingErrors.gridy = 2;
		panel_review.add(lblTypingErrors, gbc_lblTypingErrors);
		
		JTextArea textAreaTypoErrors = new JTextArea();
		textAreaTypoErrors.setEditable(false);
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.insets = new Insets(0, 0, 5, 0);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 3;
		panel_review.add(textAreaTypoErrors, gbc_textArea_1);
		
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
		gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel_4 = new JPanel();
		scrollPane_2.setViewportView(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] {220, 0};
		gbl_panel_4.rowHeights = new int[]{40, 0, 0, 40, 40, 40, 40, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.BOTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panel_4.add(lblTitle, gbc_lblTitle);
		
		JTextArea textAreaArticleTitle = new JTextArea();
		textAreaArticleTitle.setEditable(false);
		textAreaArticleTitle.setLineWrap(true);
		GridBagConstraints gbc_textAreaArticleTitle = new GridBagConstraints();
		gbc_textAreaArticleTitle.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaArticleTitle.fill = GridBagConstraints.BOTH;
		gbc_textAreaArticleTitle.gridx = 0;
		gbc_textAreaArticleTitle.gridy = 1;
		panel_4.add(textAreaArticleTitle, gbc_textAreaArticleTitle);
		
		JLabel lblArticleSummary = new JLabel("Summary");
		GridBagConstraints gbc_lblArticleSummary = new GridBagConstraints();
		gbc_lblArticleSummary.fill = GridBagConstraints.BOTH;
		gbc_lblArticleSummary.insets = new Insets(0, 0, 5, 0);
		gbc_lblArticleSummary.gridx = 0;
		gbc_lblArticleSummary.gridy = 2;
		panel_4.add(lblArticleSummary, gbc_lblArticleSummary);
		
		JTextArea textAreaArticleSummary = new JTextArea();
		textAreaArticleSummary.setEditable(false);
		textAreaArticleSummary.setLineWrap(true);
		GridBagConstraints gbc_textAreaArticleSummary = new GridBagConstraints();
		gbc_textAreaArticleSummary.gridheight = 2;
		gbc_textAreaArticleSummary.fill = GridBagConstraints.BOTH;
		gbc_textAreaArticleSummary.gridx = 0;
		gbc_textAreaArticleSummary.gridy = 3;
		panel_4.add(textAreaArticleSummary, gbc_textAreaArticleSummary);
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
		
		btnVerdict.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String v = String.valueOf(cmbVerdict.getSelectedItem()).replaceAll("\\s+","");
				if(v.equals("SELECT")) {
					JOptionPane.showMessageDialog(null, "Please select a final verdict", "Error in submission", 0);
				}else {
					Review review = submissionsChosenToReview.get(submissionRowSelectedToReview).getReview();
					review.setFinalVerdict(Verdict.valueOf(v));
					Database.setVerdict(submissionsChosenToReview.get(submissionRowSelectedToReview).getReview());
					submissionsChosenToReview.remove(submissionRowSelectedToReview);
					refreshChooseToReviewTable();
					pnlVerdict.setVisible(false);
					panel_review.setVisible(false);
				}
			}
		});
		
		pnlVerdict.setVisible(false);
		
		DefaultTableModel str_model1 = new DefaultTableModel();
		tblChooseToReview = new JTable(str_model1);
		refreshChooseToReviewTable();
		tblChooseToReview.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblChooseToReview.getColumnModel().getColumn(0).setPreferredWidth(10);
		tblChooseToReview.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblChooseToReview.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblChooseToReview.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblChooseToReview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblChooseToReview.setEnabled(false);
		scrChosenToReview.setViewportView(tblChooseToReview);
		tblChooseToReview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel_3.removeAll();
				panel_3.updateUI();
				
			    // Open new window displaying the articles in the selected article
				int row = tblChooseToReview.rowAtPoint(arg0.getPoint());
				submissionRowSelectedToReview = row;
				ReviewerOfSubmission ros = submissionsChosenToReview.get(row);
				Review review =  ros.getReview();
				if(review != null) {
					panel_review.setVisible(true);
					textAreaReviewSummary.setText(review.getSummary());
					textAreaTypoErrors.setText(review.getTypingErrors());
					int criticisms = 1;
					int counter = 0;
					boolean answered = true;
					for(Criticism c : review.getCriticisms()) {
						JLabel lbll = new JLabel("Criticism "+criticisms);
						GridBagConstraints gbc_lbll = new GridBagConstraints();
						gbc_lbll.fill = GridBagConstraints.BOTH;
						gbc_lbll.insets = new Insets(0, 0, 5, 0);
						gbc_lbll.gridx = 0;
						gbc_lbll.gridy = counter++;
						panel_3.add(lbll, gbc_lbll);
						
						JTextArea editorPaneCriticism = new JTextArea();
						editorPaneCriticism.setLineWrap(true);
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
						
						String answer = c.getAnswer();
						JTextArea editorPaneAnswer = new JTextArea();
						editorPaneAnswer.setLineWrap(true);
						editorPaneAnswer.setEditable(false);
						editorPaneAnswer.setText(answer == null ? "No answer" : answer);
						if(answer == null)
							answered = false;
						GridBagConstraints gbc_editorPaneAnswer = new GridBagConstraints();
						gbc_editorPaneAnswer.fill = GridBagConstraints.BOTH;
						gbc_editorPaneAnswer.gridx = 0;
						gbc_editorPaneAnswer.gridy = counter++;
						panel_3.add(editorPaneAnswer, gbc_editorPaneAnswer);
						criticisms++;
					}
					if(answered){
						pnlVerdict.setVisible(true);
					}else {
						pnlVerdict.setVisible(false);
					}
				}else {
					panel_review.setVisible(false);
				}
				textAreaArticleSummary.setText(ros.getSubmission().getArticle().getSummary());
				textAreaArticleTitle.setText(ros.getSubmission().getArticle().getTitle());
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
			tblToReview.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
			tblToReview.getColumnModel().getColumn(0).setPreferredWidth(2);
			tblToReview.getColumnModel().getColumn(1).setPreferredWidth(40);
			tblToReview.getColumnModel().getColumn(2).setPreferredWidth(50);
			tblToReview.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
