package com.publishingsystem.gui;

import com.publishingsystem.mainclasses.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

public class RegistrationWindow {

	private JFrame frmRegistrationForm;
	private JPasswordField pwdfldPassword;
	private JComboBox comboTitle;
	private JTextField txtfldForenames;
	private JTextField txtfldSurname;
	private JTextField txtfldUniAffiliation;
	private JTextField txtfldEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					RegistrationWindow window = new RegistrationWindow(null);
//					window.frmRegistrationForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegistrationWindow(Role r) {
		initialize(r, null, null);
	}
	
	public RegistrationWindow(Role r, SubmitArticle submitArticleGUI) {
		initialize(r, submitArticleGUI, null);
	}
	
	public RegistrationWindow(Role r, AddJournal addJournalGUI) {
		initialize(r, null, addJournalGUI);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Role r, SubmitArticle submitArticleGUI, AddJournal addJournalGUI) {
		frmRegistrationForm = new JFrame();
		frmRegistrationForm.setTitle("Registration Form");
		frmRegistrationForm.setBounds(500, 100, 653, 559);
		frmRegistrationForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRegistrationForm.setVisible(true);

		JLabel lblYourTitle = new JLabel("Your Title:");
		lblYourTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourForenames = new JLabel("Your Forenames:");
		lblYourForenames.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourSurname = new JLabel("Your Surname:");
		lblYourSurname.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblCurrentUniversityAffiliation = new JLabel("Current University Affiliation:");
		lblCurrentUniversityAffiliation.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourEmailAddress = new JLabel("Your Email Address:");
		lblYourEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourPassword = new JLabel("Your Password:");
		lblYourPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblMainAuthorRegistration = new JLabel("Registration Form");
		lblMainAuthorRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainAuthorRegistration.setFont(new Font("Tahoma", Font.PLAIN, 20));

		pwdfldPassword = new JPasswordField();
		pwdfldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

	    JComboBox<String> comboTitle = new JComboBox<String>();
	    comboTitle.setModel(new DefaultComboBoxModel(new String[] {" ", "Dr", "Mr", "Mrs", "Miss", "Ms", "Prof"}));

		txtfldForenames = new JTextField();
		txtfldForenames.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldForenames.setColumns(10);

		txtfldSurname = new JTextField();
		txtfldSurname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldSurname.setColumns(10);

		txtfldUniAffiliation = new JTextField();
		txtfldUniAffiliation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldUniAffiliation.setColumns(10);

		txtfldEmail = new JTextField();
		txtfldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldEmail.setColumns(10);

		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Get entered details
			    String title = comboTitle.getSelectedItem().toString();
			    String forenames = txtfldForenames.getText().trim();
			    String surname = txtfldSurname.getText().trim();
			    String university = txtfldUniAffiliation.getText();
			    String email = txtfldEmail.getText();
			    String password = new String(pwdfldPassword.getPassword());
			    
	            // Calculate password hash and salt
                Hash pwdHash = new Hash(password);

			    // Validate entered details
			    boolean validCredentials = true;
			    if (title == " " || forenames.isEmpty() || surname.isEmpty() || university.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    validCredentials = false;
                    JOptionPane.showMessageDialog(null, "Please fill in all of the fields", "Registration Form", 0);
                } else {
                    char[] characters = (forenames + surname + university).toCharArray();
                    int i = 0;
                    while (validCredentials && i < characters.length) {
                        if (!Character.isLetter(characters[i]) && !(characters[i] == ' ') && !(characters[i] == '-')) validCredentials = false;
                        i++;
                    }
                    if (!validCredentials) {
                        JOptionPane.showMessageDialog(null, "Names must only contain letters", "Registration Form", 0);
                    } else {
                        if (Database.academicExists(email)) {
                            validCredentials = false;
                            JOptionPane.showMessageDialog(null, "Email is already in use", "Registration Form", 0);
                        }
                    }
                }
			    
			    // Display message, open SubmitArticle and close this form if the details are valid
			    if (validCredentials) {
			        JOptionPane.showMessageDialog(null, "Registration Successul", "Registration Form", 1);
			        new SubmitArticle(new Author(-1, forenames, surname, email, university, password, pwdHash));
			        frmRegistrationForm.dispose();
			    }
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));

		GroupLayout groupLayout = new GroupLayout(frmRegistrationForm.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(70)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblYourTitle)
						.addComponent(comboTitle, Alignment.TRAILING, 467, 500, Short.MAX_VALUE)
						.addComponent(lblYourForenames)
						.addComponent(txtfldForenames, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
						.addComponent(lblYourSurname)
						.addComponent(txtfldSurname, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
						.addComponent(lblCurrentUniversityAffiliation)
						.addComponent(txtfldUniAffiliation, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
						.addComponent(lblYourEmailAddress)
						.addComponent(txtfldEmail, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
						.addComponent(lblYourPassword)
						.addComponent(pwdfldPassword, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE))
					.addGap(70))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(lblMainAuthorRegistration, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
					.addGap(100))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(260)
					.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE)
					.addGap(260))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblMainAuthorRegistration)
					.addGap(28)
					.addComponent(lblYourTitle)
					.addGap(10)
					.addComponent(comboTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblYourForenames)
					.addGap(10)
					.addComponent(txtfldForenames, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblYourSurname)
					.addGap(10)
					.addComponent(txtfldSurname, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblCurrentUniversityAffiliation)
					.addGap(10)
					.addComponent(txtfldUniAffiliation, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblYourEmailAddress)
					.addGap(10)
					.addComponent(txtfldEmail, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblYourPassword)
					.addGap(10)
					.addComponent(pwdfldPassword, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnRegister)
					.addContainerGap(53, Short.MAX_VALUE))
		);
		frmRegistrationForm.getContentPane().setLayout(groupLayout);
	}
}
