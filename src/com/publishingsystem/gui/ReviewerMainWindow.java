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

import com.publishingsystem.mainclasses.RetrieveDatabase;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


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
        
		GroupLayout groupLayout = new GroupLayout(frmReviewDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrSubmitted, GroupLayout.PREFERRED_SIZE, 551, GroupLayout.PREFERRED_SIZE)
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(btnCheckResponses)
									.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnRespondToReviews, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addComponent(scrReview, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
								.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)))
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
						.addComponent(scrSubmitted, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesReview, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(scrReview, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton)
								.addComponent(btnCheckResponses))
							.addGap(10)
							.addComponent(btnRespondToReviews)
							.addGap(284)))
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
				//new AuthorMainWindow();
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
