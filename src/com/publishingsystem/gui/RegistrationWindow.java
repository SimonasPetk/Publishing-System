package com.publishingsystem.gui;

import com.publishingsystem.mainclasses.*;

import java.awt.Dimension;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
		initialize(r, null, null, null, null, null);
	}

	public RegistrationWindow(Role r, SubmitArticle submitArticleGUI) {
		initialize(r, submitArticleGUI, null, null, null, null);
	}

	public RegistrationWindow(Role r, AddJournal addJournalGUI) {
		initialize(r, null, addJournalGUI, null, null, null);
	}

	public RegistrationWindow(Role r, EditorOfJournal e) {
		initialize(r, null, null, e, null, null);
	}

	public RegistrationWindow(Role r, Journal j) {
		initialize(r, null, null, null, j, null);
	}

	public RegistrationWindow(Role r, EditorOfJournal e, Journal j) {
		initialize(r, null, null, e, j, null);
	}
	
	public RegistrationWindow(Role r, EditorOfJournal e, Journal j, JFrame cmw) {
		initialize(r, null, null, e, j, cmw);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Role r, SubmitArticle submitArticleGUI, AddJournal addJournalGUI, EditorOfJournal addition,
			Journal editorsJournal, JFrame cmw) {
		frmRegistrationForm = new JFrame();
		frmRegistrationForm.setTitle("Registration Form");
		frmRegistrationForm.setBounds(500, 100, 653, 559);
		frmRegistrationForm.setMinimumSize(new Dimension(653, 559));
		
		frmRegistrationForm.setVisible(true);

		JLabel lblYourTitle = new JLabel("Title:");
		lblYourTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourForenames = new JLabel("Forenames:");
		lblYourForenames.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourSurname = new JLabel("Surname:");
		lblYourSurname.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblCurrentUniversityAffiliation = new JLabel("Current University Affiliation:");
		lblCurrentUniversityAffiliation.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourEmailAddress = new JLabel("Email Address:");
		lblYourEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblYourPassword = new JLabel("Password:");
		lblYourPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblMainAuthorRegistration = new JLabel("Registration Form");
		lblMainAuthorRegistration.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainAuthorRegistration.setFont(new Font("Tahoma", Font.PLAIN, 20));

		pwdfldPassword = new JPasswordField();
		pwdfldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JComboBox<String> comboTitle = new JComboBox<String>();
		comboTitle.setModel(new DefaultComboBoxModel(new String[] { " ", "Dr", "Mr", "Mrs", "Miss", "Ms", "Prof" }));

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
				String errorMessage = "";
				if (title == " " || forenames.isEmpty() || surname.isEmpty() || university.isEmpty() || email.isEmpty()
						|| password.isEmpty()) {
					validCredentials = false;
					JOptionPane.showMessageDialog(null, "Please fill in all of the fields", "Registration Form", 0);
				}else {
					char[] emailCharacters = email.toCharArray();
					int x = 0;
					// must be exactly one @
					int aCount = 0;
					// there cannot be zero .'s
					int dCount = 0;
					while (validCredentials && x < emailCharacters.length) {
						// checking its a valid character or @ or .
						if (Character.isLetter(emailCharacters[x]) || Character.isDigit(emailCharacters[x])) {
							x++;
						} else if (emailCharacters[x] == '@' || emailCharacters[x] == '.') {
							// cannot have @ or . as the final character
							if (x == (emailCharacters.length - 1)) {
								validCredentials = false;
								errorMessage = "Invalid email";
							}
							// cannot have an @ or a . after an @ or a .
							else if (emailCharacters[x + 1] == '@' || emailCharacters[x + 1] == '.') {
								validCredentials = false;
								errorMessage = "Invalid email";
							}
							// cannot have an @ or a . for the starting character
							else if (x == 0) {
								validCredentials = false;
								errorMessage = "Invalid email";
							}
							// cannot have more than one @
							else if (aCount > 1) {
								validCredentials = false;
								errorMessage = "Invalid email";
							}
							// There must be a . after an @, cannot have them in reverse order
							else if (emailCharacters[x] == '@') {
								aCount++;
								boolean dot = false;
								for (int y = x; y < emailCharacters.length; y++) {
									if (emailCharacters[y] == '.') {
										dot = true;
									}
								}
								if (!dot) {
									validCredentials = false;
									errorMessage = "Invalid email";
								}
							} else if (emailCharacters[x] == '.') {
								dCount++;
							}
							x++;
						} else {
							validCredentials = false;
							errorMessage = "Invalid email";
						}
					}
					char[] characters = (forenames + surname + university).toCharArray();
					int i = 0;
					// Added a case for '.' as some people may have initials in their university, or
					// as part of their name
					while (validCredentials && i < characters.length) {
						if (!Character.isLetter(characters[i]) && !(characters[i] == ' ') && !(characters[i] == '-')
								&& !(characters[i] == '.')) {
							validCredentials = false;
							errorMessage = "Names must only contain letters";
						}
						i++;
					}
					if (aCount != 1 || dCount == 0) {
						validCredentials = false;
						errorMessage = "Invalid email";
					}
					if(password.length() < 8) {
						validCredentials = false;
						errorMessage = "Password has to be atleast 8 characters long";
					}
					if (!validCredentials) {
						JOptionPane.showMessageDialog(null, errorMessage, "Registration Form", 0);
					} else {
						if (Database.academicExists(email)) {
							if (r != Role.COAUTHOR && r != Role.EDITOR) {
								// Email taken so cannot be used
								validCredentials = false;
								JOptionPane.showMessageDialog(null, "Email is already in use", "Registration Form", 0);
							} else {
								if (r == Role.COAUTHOR) {
									// Co-author already registered, add the registered account
									submitArticleGUI.addCoAuthor(new Author(-1, title, forenames, surname, email, university, pwdHash));
									validCredentials = false;
									JOptionPane.showMessageDialog(null,
											"Email is already in use. They will be added as a co-author.");
								} else if (r == Role.EDITOR) {
									JOptionPane.showMessageDialog(null,
											"Email is already in use. They will be added as an editor.");
								}
								frmRegistrationForm.dispose();
							}
						}
					}
				}

				// Display message, open SubmitArticle and close this form if the details are
				// valid
			
				if (validCredentials) {
					int academicID = RetrieveDatabase.getAcademicIdByEmail(email);
					switch (r) {
					case AUTHOR:
						Author author = new Author(-1, title, forenames, surname, email, university, pwdHash);
						JOptionPane.showMessageDialog(null, "Registration Successul", "Registration Form", 1);
						new SubmitArticle(author);
						break;
					case COAUTHOR:
						Author coAuthor = new Author(-1, title, forenames, surname, email, university, pwdHash);
						boolean authorExists = false;
						for(Author a : submitArticleGUI.getCoAuthors()) {
							if(a.getEmailId().equals(email))
								authorExists = true;
						}
						if(!authorExists) {
							submitArticleGUI.addCoAuthor(coAuthor);
							JOptionPane.showMessageDialog(null, "Registration Successul", "Registration Form", 1);
						}
						else
							JOptionPane.showMessageDialog(null,
									"CoAuthor already added");
						break;
					case CHIEFEDITOR:
						JOptionPane.showMessageDialog(null, "Registration Successul", "Registration Form", 1);
						Editor chiefEditor = new Editor(-1, title, forenames, surname, email, university,
								pwdHash);
						Academic[] roles = { chiefEditor, null, null }; // New on the system, their only role will be
																		// chiefEditor
						new AddJournal(roles);
						break;
					case EDITOR:
						JOptionPane.showMessageDialog(null, "Registration Successul", "Registration Form", 1);
						Editor editor = new Editor(-1, title, forenames, surname, email, university,
								pwdHash);
						Database.registerEditor(editor);
						Database.addAcademicToEditors(editor.getEditorId(), editorsJournal.getISSN());
						if(cmw != null) {
							JOptionPane.showMessageDialog(null, "Successfully added new chief editor", "Registration Form", 1);
							
							Database.removeChiefEditor(addition);
							Database.setChiefEditor(new EditorOfJournal(addition.getJournal(), editor, true));
							cmw.dispose();
							new JournalWindow(null);
						}
						
						break;
					default:
					}
					frmRegistrationForm.dispose();
						
				}
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));

		GroupLayout groupLayout = new GroupLayout(frmRegistrationForm.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(
								70)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblYourTitle)
								.addComponent(comboTitle, Alignment.TRAILING, 467, 500, Short.MAX_VALUE)
								.addComponent(lblYourForenames)
								.addComponent(txtfldForenames, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467,
										Short.MAX_VALUE)
								.addComponent(lblYourSurname)
								.addComponent(txtfldSurname, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467,
										Short.MAX_VALUE)
								.addComponent(lblCurrentUniversityAffiliation)
								.addComponent(txtfldUniAffiliation, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467,
										Short.MAX_VALUE)
								.addComponent(lblYourEmailAddress)
								.addComponent(txtfldEmail, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467,
										Short.MAX_VALUE)
								.addComponent(lblYourPassword).addComponent(pwdfldPassword, Alignment.TRAILING,
										GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE))
						.addGap(70))
						.addGroup(Alignment.TRAILING,
								groupLayout.createSequentialGroup().addGap(100)
										.addComponent(lblMainAuthorRegistration, GroupLayout.DEFAULT_SIZE, 437,
												Short.MAX_VALUE)
										.addGap(100))
						.addGroup(groupLayout.createSequentialGroup().addGap(260)
								.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE)
								.addGap(260)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblMainAuthorRegistration)
						.addGap(28).addComponent(lblYourTitle).addGap(10)
						.addComponent(comboTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lblYourForenames).addGap(10)
						.addComponent(txtfldForenames, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lblYourSurname).addGap(10)
						.addComponent(txtfldSurname, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lblCurrentUniversityAffiliation).addGap(10)
						.addComponent(txtfldUniAffiliation, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lblYourEmailAddress).addGap(10)
						.addComponent(txtfldEmail, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lblYourPassword).addGap(10)
						.addComponent(pwdfldPassword, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(btnRegister).addContainerGap(53, Short.MAX_VALUE)));
		frmRegistrationForm.getContentPane().setLayout(groupLayout);
	}
}
