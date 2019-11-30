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
		initialize(roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
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
		
		JMenuItem transferChiefEditor = new JMenuItem("Transfer Chief Editor");
		transferChiefEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("This is an option");
			}
		});
		
		JMenuItem mntmRetireFromChief = new JMenuItem("Retire From Chief Editors");
		mntmRetireFromChief.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("Should have started doneing that");
				for (EditorOfJournal e: editor.getJournal().getBoardOfEditors()) {
					System.out.println(e.getEditor().getFullName());
				}
				System.out.println("Should have done that");
				if (editor.getJournal().getBoardOfEditors().size() <= 1) {
					JOptionPane.showMessageDialog(null, "You don't have enough editors to retire ", "Error", 1);
				}
				else {
					ArrayList<EditorOfJournal> newBoardOfEditors = new ArrayList<EditorOfJournal>(); 
					for(EditorOfJournal e:editor.getJournal().getBoardOfEditors()) {
						System.out.println(e.getEditor().getFullName());
						System.out.println(e.getEditor().getEditorId());
						System.out.println(editor.getEditorId());
						if (e.getEditor().getAcademicId() != editor.getAcademicId()) {
							newBoardOfEditors.add(e);
						}
						else {
							e.retire(editor.getJournal().getISSN(), editor.getEmailId());
						}
					}
					if (newBoardOfEditors.get(0).getEditor().getEditorId() != editor.getEditorId()) {
						newBoardOfEditors.get(0).setChiefEditor();
					}
					else {
						newBoardOfEditors.get(1).setChiefEditor();
					}
					editor.getJournal().setBoardOfEditors(newBoardOfEditors);
					new LoginScreen();
					JOptionPane.showMessageDialog(null, "You have retired", "Retirement", 1);
					frmChiefEditorsDashboard.dispose();
				}
				
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
				new RegistrationWindow (Role.EDITOR, add);
			}
		});
		mnMenu.add(mntmAppointNewEditors);
		mnMenu.add(mntmLogOut);
		
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
