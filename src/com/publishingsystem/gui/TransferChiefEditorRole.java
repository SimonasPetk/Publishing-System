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
import com.publishingsystem.mainclasses.RetrieveDatabase;

import java.awt.Button;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class TransferChiefEditorRole {

	private JFrame frmTransferChiefEditorRole;
	private String selectedChiefEditor;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransferChiefEditorRole window = new TransferChiefEditorRole(null,null);
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
		initialize(null, null);
	}
	
	public TransferChiefEditorRole(Journal j,Editor e) {
		initialize(j, e);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Journal j, Editor editor) {
		frmTransferChiefEditorRole = new JFrame();
		frmTransferChiefEditorRole.setTitle("Retire as chief editor");
		frmTransferChiefEditorRole.setBounds(100, 100, 557, 416);
		//RetireAsChiefEditor window = new RetireAsChiefEditor(null);
		frmTransferChiefEditorRole.setVisible(true);
		frmTransferChiefEditorRole.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblRetireAsChief = new JLabel("Please choose the editor to replace you");
		lblRetireAsChief.setHorizontalAlignment(SwingConstants.CENTER);
		lblRetireAsChief.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
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
					new LoginScreen();
					frmTransferChiefEditorRole.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Transfer Unsuccessful", "Transfer", 1);
				}
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
		potentialChiefs.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Pressed");
				System.out.println(editor.getEmailId());
				new ChiefMainWindow(RetrieveDatabase.getRoles(editor.getEmailId()));
				frmTransferChiefEditorRole.dispose();
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frmTransferChiefEditorRole.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(188)
					.addComponent(btnUpdate)
					.addGap(35)
					.addComponent(btnCancel)
					.addGap(162))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
					.addGap(100))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(50)
					.addComponent(lblRetireAsChief, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
					.addGap(50))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRetireAsChief)
					.addGap(27)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnUpdate))
					.addGap(38))
		);
		frmTransferChiefEditorRole.getContentPane().setLayout(groupLayout);
	}
}
