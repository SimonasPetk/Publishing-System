package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
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
	private JTable tblSubmitted;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorMainWindow window = new AuthorMainWindow();
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
	public AuthorMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAuthorsDashboard = new JFrame();
		frmAuthorsDashboard.setTitle("Author's Dashboard");
		frmAuthorsDashboard.setBounds(100, 100, 900, 740);
		frmAuthorsDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthorsDashboard.setVisible(true);
		
		JLabel lblArticleList = new JLabel("Article's List:");
		lblArticleList.setToolTipText("");
		lblArticleList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrSubmitted = new JScrollPane();
		
		JButton btnSubmitAnArticle = new JButton("Submit an Article");
		btnSubmitAnArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrReview = new JScrollPane();
		scrReview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrReview.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton btnRespondToReviews = new JButton("Respond to Reviews");
		btnRespondToReviews.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				new CriticismResponse();
			}
		});
		btnRespondToReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblArticlesReview = new JLabel("Article's Review");
		lblArticlesReview.setToolTipText("");
		lblArticlesReview.setFont(new Font("Tahoma", Font.PLAIN, 20));
        
		GroupLayout groupLayout = new GroupLayout(frmAuthorsDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrSubmitted, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnRespondToReviews))
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addGap(10)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrReview)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 610, Short.MAX_VALUE)
							.addComponent(btnSubmitAnArticle)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSubmitAnArticle))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(35)
							.addComponent(scrReview, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(btnRespondToReviews)
							.addGap(310))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrSubmitted, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
							.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		JEditorPane editPaneReview = new JEditorPane();
		editPaneReview.setEditable(false);
		scrReview.setViewportView(editPaneReview);
		
		tblSubmitted = new JTable();
		tblSubmitted.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		tblSubmitted.setEnabled(false);
		scrSubmitted.setViewportView(tblSubmitted);
		frmAuthorsDashboard.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmAuthorsDashboard.setJMenuBar(menuBar);
		
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
				System.out.println(0);
				System.exit(0); 
			}
		});
		menu.add(mntmLogOut);
		
		JMenu mnChangeMyRole = new JMenu("Change My Role");
		menuBar.add(mnChangeMyRole);
		
		JMenuItem mntmChiefEditor = new JMenuItem("Chief Editor");
		mnChangeMyRole.add(mntmChiefEditor);
		
		JMenuItem mntmEditor = new JMenuItem("Editor");
		mnChangeMyRole.add(mntmEditor);
		
		JMenuItem mntmReader = new JMenuItem("Reader");
		mnChangeMyRole.add(mntmReader);
		
		JMenuItem mntmReviewer= new JMenuItem("Reviewer");
		mnChangeMyRole.add(mntmReviewer);
		
	}
}
