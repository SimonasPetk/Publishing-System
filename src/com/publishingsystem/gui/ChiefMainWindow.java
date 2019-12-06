package com.publishingsystem.gui;

import java.awt.Dimension;
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
import com.publishingsystem.mainclasses.Verdict;
import com.publishingsystem.mainclasses.Volume;
import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Edition;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ChiefMainWindow {

	private JFrame frmChiefEditorsDashboard;
	private Editor editor;
	private JTable journalTable;
	private DefaultTableModel journalTableModel;
	private DefaultTableModel editionTableModel;
	private JTable editionTable;
	private ArrayList<EditorOfJournal> chiefEditorOfJournals;
	private ArrayList<Edition> editions;
	private int editionToBePublished = -1;

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
		this.editor = (Editor) roles[0];
		chiefEditorOfJournals = editor.getEditorOfJournals();
		initialize(roles);
	}
	
	public void refreshJournalTable() {
		DefaultTableModel model = ((DefaultTableModel) journalTable.getModel());
		model.setRowCount(0);
		for (int i = 0; i < chiefEditorOfJournals.size(); i++) {
			EditorOfJournal eoj = chiefEditorOfJournals.get(i);
			Journal journal = eoj.getJournal();
			String[] journalString = new String[4];
			journalString[0] = String.valueOf(i+1);
			journalString[1] = journal.getJournalName();
			journalString[2] = String.valueOf(journal.getISSN());
			journalString[3] = journal.getDateOfPublication().toString();
			model.addRow(journalString);
		}
	}
	
	public void refreshEditionTable() {
		DefaultTableModel model = ((DefaultTableModel) editionTable.getModel());
		model.setRowCount(0);
		for (int i = 0; i < editions.size(); i++) {
			Edition edition = editions.get(i);
			ArrayList<Volume> volumes = RetrieveDatabase.getVolumes(edition.getVolume().getJournal().getISSN());
			ArrayList<Edition> editions = RetrieveDatabase.getEditions(edition.getVolume().getVolumeId());
			
			int volNum = -1;
			int vol = 1;
			for(Volume v : volumes) {
				if(v.getVolumeId() == edition.getVolume().getVolumeId()) {
					volNum = vol;
				}
				vol++;
			}
			
			int edNum = -1;
			int ed = 1;
			for(Edition e : editions) {
				if(e.getEditionId() == edition.getEditionId()) {
					edNum = ed;
				}
				ed++;
			}

			String[] editionString = new String[3];
			editionString[0] = String.valueOf(volNum);
			editionString[1] = String.valueOf(edNum);
			editionString[2] = String.valueOf(edition.getArticles().size());
			model.addRow(editionString);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		int width = 1100;
		int height = 740;
		frmChiefEditorsDashboard = new JFrame();
		frmChiefEditorsDashboard.setTitle("Chief Editor's Dashboard");
		frmChiefEditorsDashboard.setBounds(100, 100, width, height);
		frmChiefEditorsDashboard.setMinimumSize(new Dimension(width, height));
		frmChiefEditorsDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChiefEditorsDashboard.setVisible(true);
		
		chiefEditorOfJournals.removeIf(c -> !c.isChiefEditor());
		for(EditorOfJournal eoj : this.editor.getEditorOfJournals()) {
			boolean hasClash = RetrieveDatabase.editorOfJournalHasClash(eoj);
			
			if(hasClash && !eoj.isTempRetired()) {
				if(eoj.isChiefEditor() && RetrieveDatabase.getEditorsOfJournal(eoj.getJournal()).size() == 1) {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName()+"\nPlease add a new editor for this Journal who will be the Chief Editor.");
					new RegistrationWindow(Role.EDITOR, eoj, eoj.getJournal(), frmChiefEditorsDashboard);
					Database.tempRetireEditor(eoj);
					eoj.temporaryRetire();
				}else if(eoj.isChiefEditor()) {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName()+"\nPlease select a new Chief Editor for this Journal.");
					new TransferChiefEditorRole(eoj, frmChiefEditorsDashboard , roles);
					Database.tempRetireEditor(eoj);
					eoj.temporaryRetire();
				}else {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName());	
					Database.tempRetireEditor(eoj);
					eoj.temporaryRetire();
				}
			}else if(!hasClash && eoj.isTempRetired()){
				JOptionPane.showMessageDialog(null, "Your temporary retirement from "+eoj.getJournal().getJournalName()+" has been suspended.");
				Database.reInitiateEditor(eoj);
				eoj.reInitiate();
			}
				
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmChiefEditorsDashboard.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblJournals = new JLabel("Journals:");
		lblJournals.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnPublish = new JButton("Publish Edition");
		btnPublish.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(editionToBePublished != -1) {
					Edition edition = editions.get(editionToBePublished);
					if(edition.getArticles().size() > Edition.getMinarticles()) {
						Database.publishEdition(edition.getEditionId());
						JOptionPane.showMessageDialog(null, "Edition published", "Publishing Edition", 1);
						editions.remove(editionToBePublished);
						refreshEditionTable();
						editionToBePublished = -1;
					}else
						JOptionPane.showMessageDialog(null, "Please wait till the minimum number of articles inside an edition are added", "Error", 0);
					
				}else
					JOptionPane.showMessageDialog(null, "Please select an edition to publish", "Error", 0);
			}
		});
		btnPublish.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmChiefEditorsDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJournals)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 712, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPublish)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(lblJournals)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPublish)))
					.addContainerGap(47, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEditions = new JLabel("Editions:");
		lblEditions.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblEditions, BorderLayout.NORTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		
		editionTableModel = new DefaultTableModel() {
			boolean[] columnEditables = new boolean[] { false, false, false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		editionTableModel.addColumn("Volume No.");
		editionTableModel.addColumn("Edition No.");
		editionTableModel.addColumn("No. of articles");

		editionTable = new JTable(editionTableModel);
		editionTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		editionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		editionTable.getColumnModel().getColumn(1).setPreferredWidth(25);
		editionTable.getColumnModel().getColumn(2).setPreferredWidth(25);
		editionTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		editionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		editionTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editionToBePublished = editionTable.rowAtPoint(arg0.getPoint());
			}
		});
		
		scrollPane_1.setViewportView(editionTable);

		journalTableModel = new DefaultTableModel() {
			boolean[] columnEditables = new boolean[] { false, false, false, false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		journalTableModel.addColumn("No.");
		journalTableModel.addColumn("Journal Name");
		journalTableModel.addColumn("ISSN");
		journalTableModel.addColumn("Date Of Publication");
		journalTable = new JTable(journalTableModel);
		refreshJournalTable();
		journalTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		journalTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		journalTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		journalTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		journalTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		journalTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		journalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		journalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = journalTable.rowAtPoint(arg0.getPoint());
				
				EditorOfJournal eoj = chiefEditorOfJournals.get(row);

				editions = RetrieveDatabase.getEditionsForChiefEditor(eoj.getJournal().getISSN());
				if(editions.size() > 0) {
					editionToBePublished = -1;
					refreshEditionTable();
				}else {
					JOptionPane.showMessageDialog(null, "No edition to be published yet for this Journal.", "Publishing Edition", 1);
				}

			}
		});
		
		scrollPane.setViewportView(journalTable);
		frmChiefEditorsDashboard.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmChiefEditorsDashboard.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem transferChiefEditor = new JMenuItem("Transfer Chief Editor");
		transferChiefEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new EditorJournals(chiefEditorOfJournals, frmChiefEditorsDashboard, "TRANSFER", roles);
			}
		});
		mnMenu.add(transferChiefEditor);

		JMenuItem mntmRetireFromChief = new JMenuItem("Retire From Board Of Editors");
		mntmRetireFromChief.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new EditorJournals(editor.getEditorOfJournals(), frmChiefEditorsDashboard, "RETIRE");

			}
		});
		mnMenu.add(mntmRetireFromChief);

		JMenuItem menuItem = new JMenuItem("Change Password");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new ChangePassword(editor.getEmailId());
			}
		});
		mnMenu.add(menuItem);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new JournalWindow(null);
				frmChiefEditorsDashboard.dispose();
			}
		});
		

		JMenuItem mntmAppointNewEditors = new JMenuItem("Appoint New Editors");
		mntmAppointNewEditors.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new EditorJournals(editor.getEditorOfJournals(), frmChiefEditorsDashboard, "APPOINT");
				
			}
		});
		mnMenu.add(mntmAppointNewEditors);
		mnMenu.add(mntmLogOut);

		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);

		if (roles[0] != null) {
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

		if (roles[1] != null) {
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

		if (roles[2] != null) {
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
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new JournalWindow(roles);
				frmChiefEditorsDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);
	}
}
