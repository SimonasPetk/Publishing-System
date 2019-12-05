package com.publishingsystem.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.RetrieveDatabase;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangePassword {

	private JFrame frmChangePassword;
	private JPasswordField pwdOldPassword;
	private JPasswordField pwdNewPassword;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword window = new ChangePassword(null);
					window.frmChangePassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChangePassword(String email) {
		initialize(email);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String email) {
		frmChangePassword = new JFrame();
		frmChangePassword.setTitle("Change Password");
		frmChangePassword.setBounds(300, 300, 450, 300);
		frmChangePassword.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblChangeYourPassword = new JLabel("Change Your Password");
		lblChangeYourPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangeYourPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton btnChange = new JButton("Change");
		btnChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//6deb6a0a154217bdc7722b34e0e8eb5ee3e6ca402628d780a296c7a1c9cea868,  85DA708FAAF5062C505440642B30D059
				String oldPassword = pwdOldPassword.getText();
				String newPassword = pwdNewPassword.getText();
				System.out.println(oldPassword);
				System.out.println(newPassword);
				System.out.println(email);
				System.out.println("That was it");
				if (Database.validateCredentials(email, oldPassword)) {
					int academicId = RetrieveDatabase.getAcademicIdByEmail(email);
					System.out.println(newPassword.length());
					System.out.println(academicId);
					if (newPassword.length() > 0) {
						if(newPassword.length() > 7) {
							Hash passwordToUpdate = new Hash(newPassword);
							Database.changePassword(academicId, passwordToUpdate.getHash(), passwordToUpdate.getSalt());
							JOptionPane.showMessageDialog(null, "Your password has now been changed", "Success", 1);
							frmChangePassword.dispose();
						}else
							JOptionPane.showMessageDialog(null, "New password has to be at least 8 characters long", "Success", 1);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter a new password", "Error", 0);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Your password is incorrect", "Incorrect Password", 0);
				}
			}
		});
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 15));

		pwdOldPassword = new JPasswordField();
		pwdOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		pwdNewPassword = new JPasswordField();
		pwdNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));

		GroupLayout groupLayout = new GroupLayout(frmChangePassword.getContentPane());
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(110)
										.addComponent(lblChangeYourPassword, GroupLayout.PREFERRED_SIZE, 214,
												Short.MAX_VALUE)
										.addGap(110))
								.addGroup(
										groupLayout
												.createSequentialGroup().addGap(160)
												.addComponent(btnChange, GroupLayout.PREFERRED_SIZE, 114,
														Short.MAX_VALUE)
												.addGap(160))
								.addGroup(groupLayout.createSequentialGroup().addGap(35)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(pwdNewPassword, GroupLayout.DEFAULT_SIZE, 325,
																Short.MAX_VALUE)
														.addGap(42))
												.addGroup(groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																.addComponent(pwdOldPassword, Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
																.addComponent(lblNewPassword, Alignment.LEADING)
																.addComponent(lblCurrentPassword, Alignment.LEADING))
														.addGap(42)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblChangeYourPassword)
						.addGap(10).addComponent(lblCurrentPassword).addGap(10)
						.addComponent(pwdOldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(lblNewPassword).addGap(10)
						.addComponent(pwdNewPassword, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(btnChange).addContainerGap(64, Short.MAX_VALUE)));
		frmChangePassword.getContentPane().setLayout(groupLayout);
		frmChangePassword.setVisible(true);
	}
}
