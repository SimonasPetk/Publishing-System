package com.publishingsystem.gui;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ReviewerMainWindow {

	private JFrame frmReviewDashboard;
	private JTable tblReview;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewerMainWindow window = new ReviewerMainWindow();
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
	public ReviewerMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReviewDashboard = new JFrame();
		frmReviewDashboard.setBounds(100, 100, 900, 740);
		frmReviewDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReviewDashboard.setVisible(true);
		
		JLabel lblArticleList = new JLabel("Article's List:");
		lblArticleList.setToolTipText("");
		lblArticleList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrSubmitted = new JScrollPane();
		
		JScrollPane scrReview = new JScrollPane();
		scrReview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrReview.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton btnRespondToReviews = new JButton("Review Article");
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
        
		GroupLayout groupLayout = new GroupLayout(frmReviewDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrSubmitted, GroupLayout.PREFERRED_SIZE, 551, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(16)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(scrReview, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
										.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnRespondToReviews))))
						.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblArticleList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrSubmitted, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(scrReview, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRespondToReviews)
							.addGap(314)))
					.addContainerGap())
		);
		
		JEditorPane editPaneReview = new JEditorPane();
		editPaneReview.setText("If empty leave text \"No Review yet\"");
		editPaneReview.setEditable(false);
		scrReview.setViewportView(editPaneReview);
		
		tblReview = new JTable();
		tblReview.setModel(new DefaultTableModel(
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
		tblReview.setEnabled(false);
		scrSubmitted.setViewportView(tblReview);
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
		
		JMenuItem mntmToAuthor = new JMenuItem("Author");
		mntmToAuthor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A AUTHOR IF NOT MAKE ERROR MESSAGE
				new AuthorMainWindow();
				frmReviewDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToAuthor);
		
		JMenuItem mntmChiefEditor = new JMenuItem("Chief Editor");
		mntmChiefEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A CHIEF EDITOR IF NOT MAKE ERROR MESSAGE
				
				new ChiefMainWindow();
				frmReviewDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmChiefEditor);
		
		JMenuItem mntmToEditor = new JMenuItem("Editor");
		mntmToEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A EDITOR IF NOT MAKE ERROR MESSAGE
				new EditorMainWindow();
				frmReviewDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToEditor);
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// Allow this always
				new JournalWindow();
				frmReviewDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);

	}

}
