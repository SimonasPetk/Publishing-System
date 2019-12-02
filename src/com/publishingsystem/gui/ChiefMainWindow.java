package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Role;
import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class ChiefMainWindow {

	private JFrame frmChiefEditorsDashboard;
	private Editor editor;
	private Journal journal;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChiefMainWindow window = new ChiefMainWindow(null);
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
	public ChiefMainWindow(Academic[] roles) {
		this.editor = (Editor)roles[0];
		this.journal = editor.getJournal();
		initialize(roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		frmChiefEditorsDashboard = new JFrame();
		frmChiefEditorsDashboard.setTitle("Chief Editor's Dashboard");
		frmChiefEditorsDashboard.setBounds(100, 100, 948, 499);
		frmChiefEditorsDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChiefEditorsDashboard.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblJournals = new JLabel("Journals:");
		lblJournals.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnPublish = new JButton("Publish");
		btnPublish.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmChiefEditorsDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
							.addGap(304)
							.addComponent(btnPublish))
						.addComponent(lblJournals))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPublish)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblJournals)
							.addGap(10)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)))
					.addGap(10))
		);
		
		/*
		 * This is the code to get stuff and put it in the table
		 * Taken from JournalWindow.java
		 *
        ArrayList<Article> allJournals = RetrieveDatabase.getJournals();
        Object[][] tableContents = new Object[allJournals.size()][3];
        for (int i=0; i<allJournals.size(); i++) {
            Journal currentJournal = allJournals.get(i);
            tableContents[i][0] = currentJournal.getJournalName();
            tableContents[i][1] = currentJournal.getDateOfPublication().toString();
            tableContents[i][2] = currentJournal.getISSN();
        }
        tblJournal.setModel(new DefaultTableModel(
            tableContents,
            new String[] {
                "Title", "Date of Publication", "ISSN"
            }
        */
		
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
				"Journal Name", "Edition Number", "Volume", "Number of Articles"
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
		
		JMenuItem transferChiefEditor = new JMenuItem("Transfer Chief Editor");
		transferChiefEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (journal.getBoardOfEditors().size() <= 1) {
					JOptionPane.showMessageDialog(null, "You have no editors to transfer your role too", "Error", 1);
				}
				else {
					new TransferChiefEditorRole(journal,editor);
				}
			}
		});
		mnMenu.add(transferChiefEditor);
		
		JMenuItem mntmRetireFromChief = new JMenuItem("Retire From Board Of Editors");
		mntmRetireFromChief.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new RetireFromWhichJournal(editor.getEditorOfJournals(), frmChiefEditorsDashboard);
					
			}
		});
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
				System.out.println(editor.getEditorOfJournals());
				ArrayList<EditorOfJournal> editorOfJournals = editor.getEditorOfJournals();
				System.out.println(editorOfJournals.toString());
				EditorOfJournal oneEditor = editorOfJournals.get(0);
				Journal currentJournal = oneEditor.getJournal();
				EditorOfJournal add = new EditorOfJournal(currentJournal);
				new RegistrationWindow (Role.EDITOR, add,currentJournal);
				journal.setBoardOfEditors(RetrieveDatabase.getEditorsOfJournal(journal));
			}
		});
		mnMenu.add(mntmAppointNewEditors);
		mnMenu.add(mntmLogOut);
		
		/*JMenuItem appointAcademic = new JMenuItem("Appoint Academic as editor");
		appointAcademic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new RegisterAcademicAsEditor(editor.getJournal());
			}
		});
		mnMenu.add(appointAcademic);*/
		
		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);
		
		if(roles[0] != null) {
			JMenuItem mntmToEditor = new JMenuItem("Editor");
			mntmToEditor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new EditorMainWindow(roles);
					frmChiefEditorsDashboard.dispose();
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
					frmChiefEditorsDashboard.dispose();
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
					frmChiefEditorsDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToReviewer);
		}
	}
}
