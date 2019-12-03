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

public class RegisterAcademicAsEditor {

	private JFrame frmRegisterAcademicAsEditor;
	private String selectedAcademic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterAcademicAsEditor window = new RegisterAcademicAsEditor(null);
					window.frmRegisterAcademicAsEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterAcademicAsEditor() {
		System.out.println("Initialized");
		initialize(null);
	}

	public RegisterAcademicAsEditor(Journal j) {
		initialize(j);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Journal j) {
		frmRegisterAcademicAsEditor = new JFrame();
		frmRegisterAcademicAsEditor.setTitle("Appoint an academic");
		frmRegisterAcademicAsEditor.setBounds(100, 100, 489, 375);
		// RetireAsChiefEditor window = new RetireAsChiefEditor(null);
		frmRegisterAcademicAsEditor.setVisible(true);
		frmRegisterAcademicAsEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblRetireAsChief = new JLabel("Please choose the editor to replace you");
		lblRetireAsChief.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		ArrayList<Academic> aca = RetrieveDatabase.getAllAcademics();
		ArrayList<Academic> onesToDelete = new ArrayList<Academic>();
		for (EditorOfJournal e : j.getBoardOfEditors()) {
			if (aca.size() != 0) {
				for (Academic a : aca) {
					if ((e.getEditor().getEmailId()).equals(a.getEmailId())) {
						onesToDelete.add(a);
					}
				}
			}
		}
		for (Academic a : onesToDelete) {
			aca.remove(a);
		}
		if (aca.size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no academics for you to register", "Error", 1);
			frmRegisterAcademicAsEditor.dispose();
		}
		String[] academics = new String[aca.size()];
		for (int i = 0; i < aca.size(); i++) {
			academics[i] = aca.get(i).getFullName();
		}
		JList academicList = new JList();
		scrollPane.setViewportView(academicList);
		academicList.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				selectedAcademic = (String) academicList.getSelectedValue();
			}

		});

		JButton btnUpdate = new JButton("Register");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (Academic a : aca) {
					if ((a.getFullName()).equals(selectedAcademic)) {
						System.out.println(a.getFullName());
						System.out.println(j.getISSN());
						System.out.println(a.getAcademicId());
						Database.addAcademicToEditors(a.getAcademicId(), j.getISSN());
						Editor temp = new Editor(Database.getEditorIdFromAcademicId(a.getAcademicId()), a.getTitle(),
								a.getForename(), a.getSurname(), a.getUniversity(), a.getEmailId(), null);
						j.addEditorToBoard(new EditorOfJournal(j, temp, false));
					}
				}
				frmRegisterAcademicAsEditor.dispose();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));

		academicList.setModel(new AbstractListModel() {
			String[] values = academics;

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmRegisterAcademicAsEditor.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING,
						groupLayout.createSequentialGroup().addContainerGap(215, Short.MAX_VALUE)
								.addComponent(btnUpdate).addGap(189))
				.addGroup(groupLayout.createSequentialGroup().addGap(80).addComponent(scrollPane).addGap(112))
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(lblRetireAsChief, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(39, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblRetireAsChief)
						.addGap(30).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE).addGap(18)
						.addComponent(btnUpdate).addGap(38)));
		frmRegisterAcademicAsEditor.getContentPane().setLayout(groupLayout);
	}
}
