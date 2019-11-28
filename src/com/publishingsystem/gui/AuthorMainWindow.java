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
									.addComponent(btnNewButton))))
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
							.addGap(459)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton))
		);

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
