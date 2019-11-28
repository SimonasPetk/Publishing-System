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
import com.publishingsystem.mainclasses.AuthorOfArticle;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
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


public class ReviewerMainWindow {

	private JFrame frmReviewDashboard;
	private JTable tblChooseToReview;
	private JTable tblToReview;
	private ArrayList<ReviewerOfSubmission> submissionsChosenToReview;
	private int numReviewsToBeDone;

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
		initialize(roles);
	}
	
	public void refreshChooseToReviewTable(DefaultTableModel str_model1) {
		str_model1.setRowCount(0);
		str_model1.addColumn("Article ID");
		str_model1.addColumn("Title");
		for(ReviewerOfSubmission ros : submissionsChosenToReview) {
			Submission s = ros.getSubmission();
			Object[] submissionString = new Object[4];
			submissionString[0] = s.getArticle().getArticleId();
			submissionString[1] = s.getArticle().getTitle();
			submissionString[2] = s.getArticle().getSummary();
			str_model1.addRow(submissionString);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		Reviewer reviewer = (Reviewer)roles[2];
		submissionsChosenToReview = reviewer.getReviewerOfSubmissions();
		frmReviewDashboard = new JFrame();
		frmReviewDashboard.setBounds(100, 100, 900, 740);
		frmReviewDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReviewDashboard.setVisible(true);
		
		numReviewsToBeDone = RetrieveDatabase.getNumberOfReviewsToBeDone(reviewer.getReviewerId());
		
		JScrollPane scrReview = new JScrollPane();
		scrReview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrReview.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton btnRespondToReviews = new JButton("Review Article");
		btnRespondToReviews.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				new ReviewArticle();
			}
		});
		btnRespondToReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
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
		
		JButton btnCheckResponses = new JButton("Check Response");
		btnCheckResponses.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new ReviewArticle();
			}
		});
		btnCheckResponses.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblArticlesYouHave = new JLabel("Articles You Have Chosen to Review:");
		lblArticlesYouHave.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrChosenToReview = new JScrollPane();
		scrChosenToReview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel = new JPanel();
        
		GroupLayout groupLayout = new GroupLayout(frmReviewDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblArticlesYouHave)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
								.addComponent(scrChosenToReview, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnCheckResponses)
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnRespondToReviews, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(scrReview)))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(45)
					.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrReview, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnCheckResponses))
					.addGap(10)
					.addComponent(btnRespondToReviews)
					.addGap(295))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblArticlesYouHave)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrChosenToReview, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
					.addGap(29)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrSubmitted = new JScrollPane();
		panel.add(scrSubmitted, BorderLayout.CENTER);
		scrSubmitted.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblArticleListToChoose = new JLabel("Number of articles to review remaining : "+numReviewsToBeDone);
		panel.add(lblArticleListToChoose, BorderLayout.NORTH);
		lblArticleListToChoose.setToolTipText("");
		lblArticleListToChoose.setFont(new Font("Tahoma", Font.PLAIN, 20));

		DefaultTableModel str_model1 = new DefaultTableModel();
		
		refreshChooseToReviewTable(str_model1);
		
		tblChooseToReview = new JTable(str_model1);
		tblChooseToReview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrChosenToReview.setViewportView(tblChooseToReview);
			
		if(numReviewsToBeDone > 0) {
			JPanel panel_1 = new JPanel();
			panel.add(panel_1, BorderLayout.SOUTH);
			
			
			ArrayList<Submission> submissionsToReview = RetrieveDatabase.getSubmissions(reviewer);
			DefaultTableModel str_model = new DefaultTableModel();
			str_model.addColumn("Article ID");
			str_model.addColumn("Title");
			str_model.addColumn("Select");
			for(Submission s : submissionsToReview) {
    			Object[] submissionString = new Object[4];
    			submissionString[0] = s.getArticle().getArticleId();
    			submissionString[1] = s.getArticle().getTitle();
    			submissionString[2] = false;
    			str_model.addRow(submissionString);
    		}
			

			tblToReview = new JTable(str_model) {
				private static final long serialVersionUID = 1L;
				
				boolean[] columnEditables = new boolean[] {
					false, false, true
				};
				
				@Override
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
				
	            @Override
	            public Class<?> getColumnClass(int column) {
	                switch (column) {
	                    case 0:
	                        return Integer.class;
	                    case 1:
	                        return String.class;
	                    default:
	                        return Boolean.class;
	                }
	            }
			};
			tblToReview.getColumnModel().getColumn(0).setResizable(false);
			tblToReview.getColumnModel().getColumn(1).setResizable(false);
			tblToReview.getColumnModel().getColumn(2).setResizable(false);

			JButton btnSelectArticles = new JButton("Select Articles");
			btnSelectArticles.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
						if(submissionsToReview.size() < numReviewsToBeDone) {
							JOptionPane.showMessageDialog(null, "Kindly wait till the required number of articles are added to the system", "Error in selecting submission", 0);
						}
						else {
							ArrayList<Submission> selectedSubmissions = new ArrayList<Submission>();
							for(int row = 0; row < tblToReview.getRowCount(); row++) {
								if(tblToReview.getValueAt(row, 2).toString() == "true") {
									selectedSubmissions.add(submissionsToReview.get(row));
									System.out.println(submissionsToReview.get(row).getArticle().getArticleId());
								}
							}
							if(selectedSubmissions.size() == 0) {
								JOptionPane.showMessageDialog(null, "Please select "+numReviewsToBeDone+" submissions to review", "Error in selecting submission", 0);
							}else if(selectedSubmissions.size() == numReviewsToBeDone) {
								for(Submission s : selectedSubmissions) {
									submissionsChosenToReview.add(new ReviewerOfSubmission(reviewer, s));
								}
								refreshChooseToReviewTable((DefaultTableModel)tblChooseToReview.getModel());
								Database.selectSubmissionsToReview(reviewer, selectedSubmissions);
								panel.setVisible(false);
							}else {
								JOptionPane.showMessageDialog(null, "Too many submissions selected.\n Select at most "+numReviewsToBeDone+" submissions", "Error in reviewing submission", 0);
							}
						}
				}
			});
			panel_1.add(btnSelectArticles);
			
			scrSubmitted.setViewportView(tblToReview);
		}else {
			panel.setVisible(false);
		}
			
		JEditorPane editPaneReview = new JEditorPane();
		editPaneReview.setText("If empty leave text \"No Review yet\"");
		editPaneReview.setEditable(false);
		scrReview.setViewportView(editPaneReview);
		frmReviewDashboard.getContentPane().setLayout(groupLayout);
		
		
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
				new LoginScreen();
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
