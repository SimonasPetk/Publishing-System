package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen {

	private JFrame frmLogInScreen;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
					window.frmLogInScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogInScreen = new JFrame();
		frmLogInScreen.setTitle("Log In Screen");
		frmLogInScreen.setBounds(100, 100, 700, 500);
		frmLogInScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    // get username and passwords entered
			    String username = usernameField.getText();
			    String password = new String(passwordField.getPassword());
			    
			    System.out.println(Hash.generate());
			    
			    // get stored hash and salt from database for given username
			    String actualHash = "(a hash that is 256 characters)";
			    byte[] salt = {'a', 'b'};
			    
			    // generate hash based on fetched salt and entered password
			    Hash newHash = new Hash(password, salt);
			    boolean equal = newHash.getHash().equals(actualHash);
			    
			    // check if this hash is same as stored hash
			    System.out.println(equal);
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblWelcomeBack = new JLabel("Welcome Back!");
		lblWelcomeBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeBack.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JButton btnLoginAsA = new JButton("Login as a Reader");
		btnLoginAsA.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new AvailableNumbers();
				frmLogInScreen.dispose();
			}
		});
		btnLoginAsA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnAcademicRegister = new JButton("Register as an Author");
		btnAcademicRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new RegistrationWindow();
			}
		});
		btnAcademicRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmLogInScreen.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addComponent(lblWelcomeBack, GroupLayout.PREFERRED_SIZE, 643, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 529, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 529, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(300)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(290)
					.addComponent(btnLoginAsA))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(480)
					.addComponent(btnAcademicRegister, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblWelcomeBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(lblUsername)
					.addGap(5)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblPassword)
					.addGap(5)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(btnLoginAsA, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
					.addComponent(btnAcademicRegister, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		frmLogInScreen.getContentPane().setLayout(groupLayout);
	}
}
