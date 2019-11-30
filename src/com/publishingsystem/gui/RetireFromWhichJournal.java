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

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;

import java.awt.Button;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RetireFromWhichJournal {

	private JFrame frmRetireFromWhichJournal;
	private String selectedJournal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RetireFromWhichJournal window = new RetireFromWhichJournal(null,null);
					window.frmRetireFromWhichJournal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RetireFromWhichJournal() {
		System.out.println("Initialized");
		initialize(null, null);
	}
	
	public RetireFromWhichJournal(ArrayList<Journal> j,Editor e) {
		initialize(j, e);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ArrayList<Journal> j, Editor editor) {
		frmRetireFromWhichJournal = new JFrame();
		frmRetireFromWhichJournal.setTitle("Retire from which journal?");
		frmRetireFromWhichJournal.setBounds(100, 100, 489, 375);
		//RetireAsChiefEditor window = new RetireAsChiefEditor(null);
		frmRetireFromWhichJournal.setVisible(true);
		frmRetireFromWhichJournal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblRetireAsChief = new JLabel("Please choose the editor to replace you");
		lblRetireAsChief.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnUpdate = new JButton("Retire");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(selectedJournal);
				for (Journal jojo: j) {
					if ((jojo.getJournalName()).equals(selectedJournal)) {
						Database.retireEditor(editor, jojo.getISSN(), editor.getEmailId());
					}
				}
				new LoginScreen();
				frmRetireFromWhichJournal.dispose();
				/*for (EditorOfJournal e: j.getBoardOfEditors()) {
					if ((e.getEditor().getFullName()).equals(selectedJournal)) {
						e.setChiefEditor();
						Database.removeChiefEditor(editor.getEditorId());
						process = true;
					}
				}
				if (process) {
					JOptionPane.showMessageDialog(null, "Transfer Successful", "Transfer", 1);
				}
				else {
					JOptionPane.showMessageDialog(null, "Transfer Unsuccessful", "Transfer", 1);
				}
				frmRetireFromWhichJournal.dispose();
				new LoginScreen();*/
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));

		String[] journals = new String[j.size()];
		for (int i = 0; i<j.size();i++) {
			journals[i] = j.get(i).getJournalName();
		}
		JList journalList = new JList();
		scrollPane.setViewportView(journalList);
		journalList.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				selectedJournal = (String)journalList.getSelectedValue();
				System.out.println(selectedJournal);
			}

		});
		
		journalList.setModel(new AbstractListModel() {
            String[] values = journals;

		    public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmRetireFromWhichJournal.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(215, Short.MAX_VALUE)
					.addComponent(btnUpdate)
					.addGap(189))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(80)
					.addComponent(scrollPane)
					.addGap(112))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblRetireAsChief, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRetireAsChief)
					.addGap(30)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnUpdate)
					.addGap(38))
		);
		frmRetireFromWhichJournal.getContentPane().setLayout(groupLayout);
	}
}
