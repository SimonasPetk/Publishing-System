package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Role;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChiefMainWindow {

	private JFrame frmChiefEditorsDashboard;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChiefMainWindow window = new ChiefMainWindow();
					window.frmChiefEditorsDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChiefMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChiefEditorsDashboard = new JFrame();
		frmChiefEditorsDashboard.setTitle("Chief Editor's Dashboard");
		frmChiefEditorsDashboard.setBounds(100, 100, 950, 792);
		frmChiefEditorsDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChiefEditorsDashboard.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblJournals = new JLabel("Journals");
		lblJournals.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnPublishJournal = new JButton("Publish Journal");
		btnPublishJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmChiefEditorsDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
							.addComponent(btnPublishJournal))
						.addComponent(lblJournals))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnPublishJournal))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblJournals)
							.addGap(10)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)))
					.addGap(10))
		);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Journals", "Edition", "Volume", "Date"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		frmChiefEditorsDashboard.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmChiefEditorsDashboard.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmRetireFromChief = new JMenuItem("Retire From Chief Editors");
		mnMenu.add(mntmRetireFromChief);
		
		JMenuItem menuItem = new JMenuItem("Change Password");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new ChangePassword();
			}
		});
		mnMenu.add(menuItem);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				new LoginScreen();
				frmChiefEditorsDashboard.dispose();
				//System.exit(0);
			}
		});
		
		JMenuItem mntmAppointNewEditors = new JMenuItem("Appoint New Editors");
		mntmAppointNewEditors.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new RegistrationWindow(Role.EDITOR);
				
			}
		});
		mnMenu.add(mntmAppointNewEditors);
		mnMenu.add(mntmLogOut);
		
		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);
		
		JMenuItem mntmToAuthor = new JMenuItem("Author");
		mntmToAuthor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A AUTHOR IF NOT MAKE ERROR MESSAGE
				//new AuthorMainWindow();
				frmChiefEditorsDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToAuthor);
		
		JMenuItem mntmToEditor = new JMenuItem("Editor");
		mntmToEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A EDITOR IF NOT MAKE ERROR MESSAGE
				new EditorMainWindow();
				frmChiefEditorsDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToEditor);
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// Allow this always
				new JournalWindow();
				frmChiefEditorsDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);
		
		JMenuItem mntmToReviewer = new JMenuItem("Reviewer");
		mntmToReviewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				// NEEEDS TO CHECK IF IT HAS A RIGHT TO BE A EDITOR IF NOT MAKE ERROR MESSAGE
				new ReviewerMainWindow();
				frmChiefEditorsDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReviewer);
	}
}
