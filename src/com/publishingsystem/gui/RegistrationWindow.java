package com.publishingsystem.gui;
import java.awt.EventQueue;
import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Hash;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class RegistrationWindow {

	private JFrame frmRegistrationForm;
	private JPasswordField pwdfldPassword;
	private JTextField txtfldTitle;
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
					RegistrationWindow window = new RegistrationWindow();
					window.frmRegistrationForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegistrationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistrationForm = new JFrame();
		frmRegistrationForm.setTitle("Registration Form");
		frmRegistrationForm.setBounds(100, 100, 653, 559);
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
		
		txtfldTitle = new JTextField();
		txtfldTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldTitle.setColumns(10);
		
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
				// 1. Get entered details
			    String title = txtfldTitle.getText();
			    String forenames = txtfldForenames.getText();
			    String surname = txtfldSurname.getText();
			    String university = txtfldUniAffiliation.getText();
			    String email = txtfldEmail.getText();
			    String password = new String(pwdfldPassword.getPassword());
			    
			    // 2. Calculate password hash and salt
			    Hash pwdHash = new Hash(password);
			    System.out.println(pwdHash);
			    String salt = pwdHash.getSalt();
			    String hash = pwdHash.getHash();
			    
			    // 3. Add academic's details to database
			    try (Connection con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/?user=team022&password=6b78cf2f")) {
			        Statement statement = con.createStatement();
			        //String query = ;
			        //System.out.println(query);
			        statement.executeUpdate("USE team022");
			        statement.executeUpdate("INSERT INTO Academic VALUES("
                            + "null, "
                            + "'" + title + "', "
                            + "'" + forenames + "', "
                            + "'" + surname + "', "
                            + "'" + university + "', "
                            + "'" + email + "', "
                            + "'" + hash + "', "
                            + "'" + salt + "'"
                            +");");
                    /*ResultSet rs = statement.executeQuery("SELECT * FROM Academic");
                    while (rs.next()) System.out.println(rs.);
                    statement.close();
                    //System.out.println(result);
                    /*
                      "title TEXT, "
                    + "forenames TEXT, "
                    + "surname TEXT, "
                    + "university TEXT, "
                    + "emailAddress TEXT, "
                    + "passwordHash TEXT, "
                    + "salt TEXT");
                     */
		        }
		        catch (SQLException ex) {
		            ex.printStackTrace();
		        }
			    
			    // 4. Create Academic object
                
			    
			    //Academic test = new Academic(1, txtfldTitle.getText() , txtfldForename.getText(), txtfldSurname.getText(), txtfldEmail.getText(), txtfldUniAffiliation.getText());
				//System.out.println(test.getTitle());
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
						.addComponent(txtfldTitle, Alignment.TRAILING, 467, 500, Short.MAX_VALUE)
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
					.addComponent(txtfldTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
