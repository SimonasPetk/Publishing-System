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

public class TransferChiefEditorRole {

	private JFrame frmRetireAsChief;
	private String selectedChiefEditor;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransferChiefEditorRole window = new TransferChiefEditorRole(null,null);
					window.frmRetireAsChief.setVisible(true);
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
		initialize(null, null);
	}
	
	public TransferChiefEditorRole(Journal j,Editor e) {
		initialize(j, e);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Journal j, Editor editor) {
		frmRetireAsChief = new JFrame();
		frmRetireAsChief.setTitle("Retire as chief editor");
		frmRetireAsChief.setBounds(100, 100, 489, 375);
		//RetireAsChiefEditor window = new RetireAsChiefEditor(null);
		frmRetireAsChief.setVisible(true);
		frmRetireAsChief.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblRetireAsChief = new JLabel("Please choose the editor to replace you");
		lblRetireAsChief.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean process = false;
				for (EditorOfJournal e: j.getBoardOfEditors()) {
					if ((e.getEditor().getFullName()).equals(selectedChiefEditor)) {
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
				frmRetireAsChief.dispose();
				new LoginScreen();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		ArrayList<EditorOfJournal> ed = j.getBoardOfEditors();
		System.out.println(ed.size());
		ArrayList<EditorOfJournal> displayEditors = new ArrayList<EditorOfJournal>();
		for (EditorOfJournal e: ed) {
			if (!e.isChiefEditor()) {
				displayEditors.add(e);
			}
		}
		String[] editors = new String[displayEditors.size()];
		for (int i=0;i<displayEditors.size();i++) {
			editors[i] = displayEditors.get(i).getEditor().getFullName();
		}
		JList potentialChiefs = new JList();
		scrollPane.setViewportView(potentialChiefs);
		potentialChiefs.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				selectedChiefEditor = (String)potentialChiefs.getSelectedValue();
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
		GroupLayout groupLayout = new GroupLayout(frmRetireAsChief.getContentPane());
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
		frmRetireAsChief.getContentPane().setLayout(groupLayout);
	}
}
