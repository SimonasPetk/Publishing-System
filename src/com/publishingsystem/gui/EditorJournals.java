package com.publishingsystem.gui;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Role;
import com.publishingsystem.mainclasses.Submission;

import java.awt.Button;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class EditorJournals {

	private JFrame frmEditorJournals;
	private int selectedJournal = -1;
	private JTable journalTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorJournals window = new EditorJournals(null, null, null);
					window.frmEditorJournals.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditorJournals() {
		System.out.println("Initialized");
		initialize(null, null, null);
	}

	public EditorJournals(ArrayList<EditorOfJournal> eojs, JFrame editorWindow, String option) {
		initialize(eojs, editorWindow, option);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ArrayList<EditorOfJournal> eojs, JFrame editorWindow, String option) {
		frmEditorJournals = new JFrame();
		if(option.equals("RETIRE"))
			frmEditorJournals.setTitle("Retire from journal");
		else
			frmEditorJournals.setTitle("Appoint new editor");
		frmEditorJournals.setBounds(100, 100, 489, 342);
		// RetireAsChiefEditor window = new RetireAsChiefEditor(null);
		frmEditorJournals.setVisible(true);

		JLabel lblMain = new JLabel();
		if(option.equals("RETIRE"))
			lblMain.setText("Please select the journal to retire from");
		else
			lblMain.setText("Please select the journal to add new editor");
		lblMain.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnUpdate = new JButton();
		if(option.equals("RETIRE"))
			btnUpdate.setText("Retire");
		else
			btnUpdate.setText("Add Editor");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(selectedJournal);
				if(selectedJournal != -1) {
					EditorOfJournal eoj = eojs.get(selectedJournal);
					if(option.equals("RETIRE")) {
						ArrayList<EditorOfJournal> coEditors = RetrieveDatabase.getEditorsOfJournal(eoj.getJournal());
						if (coEditors.size() > 1) {
							if (eoj.isChiefEditor()) {
								int dialogResult = JOptionPane.showConfirmDialog(null,
										"You will be removed as a Chief Editor. Are you sure?");
								if (dialogResult == JOptionPane.YES_OPTION) {
									Database.retireEditor(eoj);
									eoj.getEditor().getEditorOfJournals().remove(eoj);
									editorWindow.dispose();
									new LoginScreen();
								}
							} else {
								Database.retireEditor(eoj);
							}
							if (eoj.getEditor().getEditorOfJournals().size() == 0) {
								editorWindow.dispose();
								new JournalWindow(null);
							}
							frmEditorJournals.dispose();
						} else {
							JOptionPane.showMessageDialog(null,
									"Please add another editor to the board of editors for this journal before retiring. ",
									"Error in retiring", 0);
						}
					}else if(option.equals("APPOINT")){
						frmEditorJournals.dispose();
						new RegistrationWindow(Role.EDITOR, eoj, eoj.getJournal());
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please select a journal", "Error", 1);
				}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));

		GroupLayout groupLayout = new GroupLayout(frmEditorJournals.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(24)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblMain, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 436,
										Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup().addGap(195).addComponent(btnUpdate)))
				.addContainerGap(29, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(30).addComponent(lblMain)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnUpdate).addGap(44)));

		DefaultTableModel journalTableModel = new DefaultTableModel() {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		journalTableModel.addColumn("No.");
		journalTableModel.addColumn("Name");
		int counter = 1;
		for (EditorOfJournal eoj : eojs) {
			Object[] journalRow = new Object[2];
			journalRow[0] = counter;
			journalRow[1] = eoj.getJournal().getJournalName();
			journalTableModel.addRow(journalRow);
			counter++;
		}

		journalTable = new JTable(journalTableModel);
		journalTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		journalTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		journalTable.getColumnModel().getColumn(0).setPreferredWidth(5);
		journalTable.getColumnModel().getColumn(1).setPreferredWidth(350);
		journalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		journalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectedJournal = journalTable.rowAtPoint(arg0.getPoint());
			}
		});
		scrollPane.setViewportView(journalTable);
		frmEditorJournals.getContentPane().setLayout(groupLayout);
	}
}
