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
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Journal;

import java.awt.Button;
import javax.swing.JButton;

public class RetireAsChiefEditor {

	private JFrame frmRetireAsChief;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RetireAsChiefEditor window = new RetireAsChiefEditor(null);
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
	public RetireAsChiefEditor() {
		System.out.println("Initialized");
		initialize(null);
	}
	
	public RetireAsChiefEditor(Journal j) {
		initialize(j);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Journal j) {
		frmRetireAsChief = new JFrame();
		frmRetireAsChief.setTitle("Retire as chief editor");
		frmRetireAsChief.setBounds(100, 100, 432, 302);
		frmRetireAsChief.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblRetireAsChief = new JLabel("Please choose the editor to replace you");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnNewButton = new JButton("Retire");
		GroupLayout groupLayout = new GroupLayout(frmRetireAsChief.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblRetireAsChief, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(68)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(173)
					.addComponent(btnNewButton))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblRetireAsChief)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		ArrayList<EditorOfJournal> ed = j.getBoardOfEditors();
		ArrayList<EditorOfJournal> displayEditors = new ArrayList<EditorOfJournal>();
		for (EditorOfJournal e: ed) {
			if (!e.isChiefEditor()) {
				displayEditors.add(e);
			}
		}
		String[] editors = new String[displayEditors.size()];
		for (int i=0;i<displayEditors.size();i++) {
			editors[i] = displayEditors.get(i).getEditor().getForename();
		}
		JList potentialChiefs = new JList();
		scrollPane.setViewportView(potentialChiefs);
		frmRetireAsChief.getContentPane().setLayout(groupLayout);
		
		potentialChiefs.setModel(new AbstractListModel() {
            String[] values = editors;

		    public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		frmRetireAsChief.getContentPane().setLayout(groupLayout);
	}
}
