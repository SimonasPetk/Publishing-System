package com.publishingsystem.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Author;
import com.publishingsystem.mainclasses.Role;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AuthorReviewRegistration {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorReviewRegistration window = new AuthorReviewRegistration(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AuthorReviewRegistration(Role r, Author a) {
		initialize(a, null, null);
	}
	
	public AuthorReviewRegistration(Role r, Author a, SubmitArticle submitArticleGUI) {
		initialize(a, null, submitArticleGUI);
	}
	
	public AuthorReviewRegistration(Academic[] roles) {
		initialize(null, roles, null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Author a, Academic[] roles, SubmitArticle submitArticleGUI) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 157);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the number of submissions to review");
		lblPleaseEnterThe.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GridBagConstraints gbc_lblPleaseEnterThe = new GridBagConstraints();
		gbc_lblPleaseEnterThe.gridwidth = 8;
		gbc_lblPleaseEnterThe.anchor = GridBagConstraints.EAST;
		gbc_lblPleaseEnterThe.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseEnterThe.gridx = 0;
		gbc_lblPleaseEnterThe.gridy = 1;
		panel.add(lblPleaseEnterThe, gbc_lblPleaseEnterThe);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 2;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int numReviews = -1;
				try {
					numReviews = Integer.valueOf(textField.getText());
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Please enter a valid number of submissions(MAX 3)", "Error in submission", 0);
				}
				if(numReviews > 3 || numReviews < 0) {
					JOptionPane.showMessageDialog(null, "Please enter a valid number of submissions(MAX 3)", "Error in submission", 0);
				}else {
					if(submitArticleGUI != null) {
						submitArticleGUI.addCoAuthor(a, numReviews);
					}else if(roles != null) {
						new SubmitArticle(roles, numReviews);
					}else {
						new SubmitArticle(a, numReviews);
					}
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 3;
		gbc_btnSubmit.gridy = 3;
		panel.add(btnSubmit, gbc_btnSubmit);
	}

}
