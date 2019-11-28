package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JTable reviewSubmissionsTable;
	private int selectedAuthorOfArticle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    System.out.println("Hello");
				    //Academic[] testAcademic = {null, new Author(6, "Mr", "Alex", "Hall", "ahall8@sheffield.ac.uk", "Sheffield", new Hash("password"))};
				    //Academic[] testAcademic = {null, new Author(7, "Dr", "Kirill", "Bogdanov", "kb@gm.com", "Sheffield", new Hash("password"))};
				    Academic[] testAcademic = null;
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
		this.author = (Author)roles[1];
		initialize(roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
	    // Configure frame
		frmAuthorsDashboard = new JFrame();
		frmAuthorsDashboard.setTitle("Author's Dashboard");
		frmAuthorsDashboard.setBounds(100, 100, 900, 740);
		frmAuthorsDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthorsDashboard.setVisible(true);
		
		
		// Window title
		JLabel lblArticleList = new JLabel("Your articles:");
		lblArticleList.setToolTipText("");
		lblArticleList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		
		// Your articles panel
		JScrollPane scrSubmitted = new JScrollPane();

        ArrayList<AuthorOfArticle> authorOfArticles = author.getAuthorOfArticles();
        tblSubmittedModel = new DefaultTableModel();
        tblSubmittedModel.addColumn("Article ID");
        tblSubmittedModel.addColumn("Title");
        tblSubmittedModel.addColumn("Abstract");
       
        for(int i = 0; i < authorOfArticles.size(); i++) {
            Article article = authorOfArticles.get(i).getArticle();
            String[] articles = new String[3];
            articles[0] = String.valueOf(article.getArticleId());
            articles[1] = article.getTitle();
            articles[2] = article.getSummary();
            tblSubmittedModel.addRow(articles);
        }
        tblSubmitted = new JTable(tblSubmittedModel);
        
        tblSubmitted.setEnabled(false);
        scrSubmitted.setViewportView(tblSubmitted);

		
		// Review panel
		JLabel lblArticlesReview = new JLabel("Article's Review");
        lblArticlesReview.setToolTipText("");
        lblArticlesReview.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrReview = new JScrollPane();
		scrReview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrReview.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JEditorPane editPaneReview = new JEditorPane();
        editPaneReview.setEditable(false);
        scrReview.setViewportView(editPaneReview);
		
		JButton btnRespondToReviews = new JButton("Respond to Reviews");
		btnRespondToReviews.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//new CriticismResponse();
			}
		});
		btnRespondToReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JPanel panel = new JPanel();
		
		JButton btnNewButton = new JButton("New button");
		
		// Configure layout
		GroupLayout groupLayout = new GroupLayout(frmAuthorsDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrSubmitted, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(10)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(scrReview, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)))
										.addComponent(btnRespondToReviews)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)))))
						.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrSubmitted, GroupLayout.PREFERRED_SIZE, 636, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrReview, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(btnRespondToReviews)
							.addGap(15)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
							.addGap(92)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton))
		);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{377, 0};
		gbl_panel.rowHeights = new int[]{16, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		JLabel lblNumberOfReviews = new JLabel();
		lblNumberOfReviews.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNumberOfReviews = new GridBagConstraints();
		gbc_lblNumberOfReviews.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumberOfReviews.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNumberOfReviews.gridx = 0;
		gbc_lblNumberOfReviews.gridy = 0;
		panel.add(lblNumberOfReviews, gbc_lblNumberOfReviews);
		JLabel lblNumberOfReviews_1 = new JLabel();
		lblNumberOfReviews_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfReviews_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNumberOfReviews_1 = new GridBagConstraints();
		gbc_lblNumberOfReviews_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumberOfReviews_1.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfReviews_1.gridx = 0;
		gbc_lblNumberOfReviews_1.gridy = 1;
		panel.add(lblNumberOfReviews_1, gbc_lblNumberOfReviews_1);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		ArrayList<Submission> submissions = RetrieveDatabase.getSubmissions(author.getUniversity());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Article ID");
		model.addColumn("Title");
		model.addColumn("Summary");
		model.addColumn("Select");
		
		
        tblSubmitted.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // Open new window displaying the articles in the selected article
                selectedAuthorOfArticle = Integer.parseInt(((String)tblSubmitted.getValueAt(tblSubmitted.rowAtPoint(arg0.getPoint()), 0)))-1;
            	
        		int	numReviewsDone = RetrieveDatabase.getNumberOfReviewsDone(authorOfArticles.get(selectedAuthorOfArticle).getArticle());
        		
                lblNumberOfReviews.setText("    Number Of Reviews Done By Team: " + numReviewsDone);
                lblNumberOfReviews_1.setText("    Number Of Reviews Left: "+(3-numReviewsDone));
                System.out.println(selectedAuthorOfArticle);
                model.setRowCount(0);
                for(Submission s : submissions) {
        			Object[] submissionString = new Object[4];
        			submissionString[0] = s.getArticle().getArticleId();
        			submissionString[1] = s.getArticle().getTitle();
        			submissionString[2] = s.getArticle().getSummary();
        			submissionString[3] = false;
        			model.addRow(submissionString);
        		}
                
            } 
        });

		reviewSubmissionsTable = new JTable(model) {
			private static final long serialVersionUID = 1L;
			
			boolean[] columnEditables = new boolean[] {
				false, false, false, true
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
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
		};
		
		JButton btnNewButton_1 = new JButton("Review Submissions");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Submission> selectedSubmissions = new ArrayList<Submission>();
				for(int row = 0; row < reviewSubmissionsTable.getRowCount(); row++) {
					if(reviewSubmissionsTable.getValueAt(row, 3).toString() == "true") {
						selectedSubmissions.add(submissions.get(row));
						System.out.println(submissions.get(row).getArticle().getArticleId());
					}
				}
				if(selectedSubmissions.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please select upto 3 submissions to review", "Error in reviewing submission", 0);
				}else if(selectedSubmissions.size() < 4) {
					AuthorOfArticle aoa = authorOfArticles.get(selectedAuthorOfArticle);
					Reviewer reviewer = new Reviewer(aoa);
					Database.registerReviewer(reviewer, selectedSubmissions);
					new ReviewerMainWindow(roles);
					frmAuthorsDashboard.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Too many submissions selected.\n Select at most 3 submissions", "Error in reviewing submission", 0);
				}
				
			}
		});
		reviewSubmissionsTable.setRowSelectionAllowed(false);
		scrollPane.setViewportView(reviewSubmissionsTable);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 5;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
		frmAuthorsDashboard.getContentPane().setLayout(groupLayout);
		
		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		frmAuthorsDashboard.setJMenuBar(menuBar);
		
		JMenu name = new JMenu("Welcome back " + author.getForename());
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
		
		if(roles[2] != null) {
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
