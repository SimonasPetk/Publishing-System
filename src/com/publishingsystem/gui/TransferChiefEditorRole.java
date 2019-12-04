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
import javax.swing.JMenuItem;
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
import com.publishingsystem.mainclasses.RetrieveDatabase;

import java.awt.Button;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class TransferChiefEditorRole {

	private JFrame frmTransferChiefEditorRole;
	private String selectedChiefEditor;
	private ArrayList<EditorOfJournal> editorsOfJournal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransferChiefEditorRole window = new TransferChiefEditorRole(null, null, null);
					window.frmTransferChiefEditorRole.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TransferChiefEditorRole() {
		System.out.println("Initialized");
		initialize(null, null, null);
	}
	
	public TransferChiefEditorRole(EditorOfJournal eoj, JFrame chiefWindow , Academic[] roles) {
		initialize(eoj, chiefWindow, roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(EditorOfJournal chiefEoj, JFrame chiefWindow, Academic[] roles) {
		int width = 600;
		int height = 300;
		frmTransferChiefEditorRole = new JFrame();
		frmTransferChiefEditorRole.setTitle("Transfer as chief editor");
		frmTransferChiefEditorRole.setBounds(100, 100, width, height);
		frmTransferChiefEditorRole.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmTransferChiefEditorRole.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);

		JLabel lblRetireAsChief = new JLabel("Please choose the editor to replace you");
		lblRetireAsChief.setHorizontalAlignment(SwingConstants.CENTER);
		lblRetireAsChief.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(selectedChiefEditor != null) {
					for (EditorOfJournal eoj : editorsOfJournal) {
						if ((eoj.getEditor().getFullName()).equals(selectedChiefEditor)) {
							eoj.setChiefEditor(true);
							Database.setChiefEditor(eoj);
							chiefEoj.setChiefEditor(false);
							JOptionPane.showMessageDialog(null, "Transfer Successful", "Transfer", 1);
							chiefWindow.dispose();
							new JournalWindow(roles);
							frmTransferChiefEditorRole.dispose();
							break;
						}
					}
					Database.removeChiefEditor(chiefEoj);
				}else
					JOptionPane.showMessageDialog(null, "Please select an Editor to transfer your Chief Editor role to", "Transfer", 0);
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));

		
		editorsOfJournal = RetrieveDatabase.getEditorsOfJournal(chiefEoj.getJournal());
		editorsOfJournal.removeIf(e -> e.isTempRetired() || e.isChiefEditor());
		
		String[] editors = new String[editorsOfJournal.size()];
		for (int i = 0; i < editorsOfJournal.size(); i++) {
			editors[i] = editorsOfJournal.get(i).getEditor().getFullName();
		}
		JList potentialChiefs = new JList();
		potentialChiefs.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(potentialChiefs);
		potentialChiefs.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				selectedChiefEditor = (String) potentialChiefs.getSelectedValue();
				System.out.println(selectedChiefEditor);
			}

		});

		potentialChiefs.setModel(new AbstractListModel() {
			String[] values = editors;

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Pressed");
				frmTransferChiefEditorRole.dispose();
			}
		});

		GroupLayout groupLayout = new GroupLayout(frmTransferChiefEditorRole.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(205)
					.addComponent(btnUpdate)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancel)
					.addGap(211))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 559, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(51)
					.addComponent(lblRetireAsChief, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
					.addGap(49))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblRetireAsChief)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUpdate)
						.addComponent(btnCancel))
					.addGap(38))
		);
		frmTransferChiefEditorRole.getContentPane().setLayout(groupLayout);
	}
}
