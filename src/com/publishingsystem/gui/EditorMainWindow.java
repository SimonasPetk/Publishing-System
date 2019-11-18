package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditorMainWindow {

	private JFrame frmDashboard;
	private JTable tblEditor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorMainWindow window = new EditorMainWindow();
					window.frmDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditorMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDashboard = new JFrame();
		frmDashboard.setTitle("Editor's Dashboard");
		frmDashboard.setBounds(100, 100, 1000, 795);
		frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDashboard.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblArticlesList = new JLabel("Article's List:");
		lblArticlesList.setToolTipText("");
		lblArticlesList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JTextArea txtrAbstract = new JTextArea();
		txtrAbstract.setLineWrap(true);
		txtrAbstract.setEditable(false);
		
		JLabel lblAbstract = new JLabel("Abstract:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnPublishArticle = new JButton("Publish Article");
		btnPublishArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnRejectArticle = new JButton("Reject Article");
		btnRejectArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnDelayPublishing = new JButton("Delay Publishing");
		btnDelayPublishing.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesList)
							.addGap(509)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtrAbstract, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnRejectArticle)
							.addGap(65)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnPublishArticle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDelayPublishing, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblArticlesList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtrAbstract, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnPublishArticle)
								.addComponent(btnRejectArticle))
							.addGap(10)
							.addComponent(btnDelayPublishing)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
							.addGap(20))))
		);
		
		tblEditor = new JTable();
		tblEditor.setEnabled(false);
		tblEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				tblEditor.setValueAt("Press Again", 0, 0);

				if (arg0.getClickCount() == 2 && tblEditor.rowAtPoint(arg0.getPoint()) == 0) {

			        txtrAbstract.append("This is the abstract of the article you pressed on");
				}
			}
		});
		tblEditor.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Articles", "Authors", "Date", "Journal", "Status"
			}
		));
		scrollPane.setViewportView(tblEditor);
		frmDashboard.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmDashboard.setJMenuBar(menuBar);
		
		JMenu mnEditorsMenu = new JMenu("Menu");
		menuBar.add(mnEditorsMenu);
		
		JMenuItem mntmRetireFromEditors = new JMenuItem("Retire From Editors");
		mnEditorsMenu.add(mntmRetireFromEditors);
		
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.print(0);
				new ChangePassword();
				
			}
		});

		mnEditorsMenu.add(mntmChangePassword);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new LoginScreen();
				frmDashboard.dispose();
				//System.exit(0);
			}
		});
		mnEditorsMenu.add(mntmLogOut);
		
		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);
		
		JMenuItem mntmToAuthor = new JMenuItem("Author");
		mntmToAuthor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A AUTHOR IF NOT MAKE ERROR MESSAGE
				///new AuthorMainWindow();
				frmDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToAuthor);
		
		JMenuItem mntmChiefEditor = new JMenuItem("Chief Editor");
		mntmChiefEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A CHIEF EDITOR IF NOT MAKE ERROR MESSAGE
				
				new ChiefMainWindow();
				frmDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmChiefEditor);
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// Allow this always
				new JournalWindow();
				frmDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);
		
		JMenuItem mntmToReviewer = new JMenuItem("Reviewer");
		mntmToReviewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A EDITOR IF NOT MAKE ERROR MESSAGE
				new ReviewerMainWindow();
				frmDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReviewer);
	}
}
