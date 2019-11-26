package com.publishingsystem.gui;
import java.awt.EventQueue;
import com.publishingsystem.mainclasses.*;
import com.publishingsystem.gui.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import java.util.ArrayList;

public class LoginScreen {

	private JFrame frmLogInScreen;
	private JTextField emailField;
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
		frmLogInScreen.setTitle("Login");
		frmLogInScreen.setBounds(100, 100, 700, 500);
		frmLogInScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogInScreen.setVisible(true);

		JLabel lblEmail = new JLabel("Email Address:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));

		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailField.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    // Get email and password entered then validate
			    String email = emailField.getText();
			    String password = new String(passwordField.getPassword());

			    boolean validCredentials = true;
			    if (email.isEmpty() || password.isEmpty()) validCredentials = false;

			    if (validCredentials) {
			        // Check if the generated hash from password is same as stored hash
			    	boolean correctPassword = Database.validateCredentials(email, password);
                    if (correctPassword) {
                        JOptionPane.showMessageDialog(null, "Login Successful", "Login", 1);
                        password = ""; // clear password for security
                        
                        // Open next window
                        Academic[] userRoles = RetrieveDatabase.getRoles(email);
                        new JournalWindow(userRoles);
                        System.out.println(userRoles[0]);
                        frmLogInScreen.dispose();
                    } else JOptionPane.showMessageDialog(null, "Incorrect email or password", "Login", 0);
			    } else JOptionPane.showMessageDialog(null, "Please fill in all fields", "Login", 0);
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblWelcomeBack = new JLabel("Welcome Back!");
		lblWelcomeBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeBack.setFont(new Font("Tahoma", Font.PLAIN, 25));

		GroupLayout groupLayout = new GroupLayout(frmLogInScreen.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addComponent(lblWelcomeBack, GroupLayout.PREFERRED_SIZE, 643, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 529, GroupLayout.PREFERRED_SIZE))
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
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(480))));
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblWelcomeBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(lblEmail)
					.addGap(5)
					.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblPassword)
					.addGap(5)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addPreferredGap(ComponentPlacement.RELATED, 173, Short.MAX_VALUE))
		);
		frmLogInScreen.getContentPane().setLayout(groupLayout);
	}
}
