package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Role;
import com.publishingsystem.mainclasses.Academic;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;

public class AddJournal {

	private JFrame frmAddJournal;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddJournal window = new AddJournal(null);
					window.frmAddJournal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddJournal(Academic[] roles) {
		initialize(roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		final JPanel panel = new JPanel();
		frmAddJournal = new JFrame();
		frmAddJournal.setTitle("Add Journal");
		frmAddJournal.setBounds(100, 100, 591, 283);
		frmAddJournal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAddJournal.setVisible(true);
		
		JLabel lblAddANew = new JLabel("Add a New Journal to the System");
		lblAddANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddANew.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblNewLabel = new JLabel("Journal Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("International Standard Serial Number (ISSN):");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setColumns(10);
		
		JButton btnAddJournal = new JButton("Add Journal");
		btnAddJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
			    // Create new journal object
				String journalName = textField.getText();
				String JournalISSN = textField_1.getText();
				
				//validating ISSN and Journal
				int issn = 0;
				boolean validCredentials = true;
				if (journalName.length() == 0 && JournalISSN.length() == 0) {
					JOptionPane.showMessageDialog(panel, "Please enter a journal name and a journal ISSN", "Error", JOptionPane.WARNING_MESSAGE);
					validCredentials = false;
				}
				else if (journalName.length() == 0) {
					JOptionPane.showMessageDialog(panel, "Please enter a journal name", "Error", JOptionPane.WARNING_MESSAGE);
					validCredentials = false;
				}
				else if (JournalISSN.length() == 0) {
					JOptionPane.showMessageDialog(panel, "Please enter an ISSN", "Error", JOptionPane.WARNING_MESSAGE);
					validCredentials = false;
				}
				else if (JournalISSN.length() != 8) {
					JOptionPane.showMessageDialog(panel, "Please enter an ISSN of the correct length", "Error", JOptionPane.WARNING_MESSAGE);
					validCredentials = false;
				}
				else {
					try {
						issn = Integer.parseInt(JournalISSN);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(panel, "Please only enter 8 digits of ISSN code, No other characters are needed.", "Warning", JOptionPane.WARNING_MESSAGE);
				    }
				}
				if (validCredentials) {
					Date now = new Date(System.currentTimeMillis());
					Journal newJournal = new Journal(Integer.parseInt(JournalISSN), journalName, now);
					// Register the chief editor as a chief editor
					ArrayList<Editor> editors = new ArrayList<Editor>();
	                editors.add((Editor)roles[0]);
	                Database.registerEditors(editors);
					
	                // Add the editor to the journal as the chief editor
					EditorOfJournal chief = new EditorOfJournal(newJournal, (Editor)roles[0], true);
					//ArrayList<EditorOfJournal> editorOfNewJournal = new ArrayList<EditorOfJournal>();
					//editorOfNewJournal.add(chief);
					newJournal.addEditorToBoard(chief);

					// Record that the editor is an editor of the new journal
					((Editor)roles[0]).addEditorOfJournal(chief);
					
					// Add the journal to the database
					Database.addJournal(newJournal);
					
					// Open ChiefMainWindow
					frmAddJournal.dispose();
					new ChiefMainWindow(roles);
				}
			}
		});
		btnAddJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmAddJournal.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(140)
					.addComponent(lblAddANew, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(140))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(68)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addContainerGap(78, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(219)
					.addComponent(btnAddJournal)
					.addContainerGap(248, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAddANew)
					.addGap(20)
					.addComponent(lblNewLabel)
					.addGap(10)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblNewLabel_1)
					.addGap(10)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnAddJournal)
					.addContainerGap(348, Short.MAX_VALUE))
		);
		frmAddJournal.getContentPane().setLayout(groupLayout);
	}
}
