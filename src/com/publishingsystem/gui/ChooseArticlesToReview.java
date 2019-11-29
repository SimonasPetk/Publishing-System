package com.publishingsystem.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class ChooseArticlesToReview {

	private JFrame ArticlesToReview;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseArticlesToReview window = new ChooseArticlesToReview();
					window.ArticlesToReview.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChooseArticlesToReview() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ArticlesToReview = new JFrame();
		ArticlesToReview.setTitle("Choose What to Review");
		ArticlesToReview.setBounds(100, 100, 683, 402);
		ArticlesToReview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblNewLabel = new JLabel("Choose What Articles You Want to Review");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblArticlesChosen = new JLabel("Number of Articles Chosen:");
		lblArticlesChosen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNumberOfArticles = new JLabel("Update this according to the number of articles");
		lblNumberOfArticles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(ArticlesToReview.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(50)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
					.addGap(50))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(200)
					.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 65, Short.MAX_VALUE)
					.addGap(200))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesChosen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNumberOfArticles)))
					.addGap(30))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(15)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArticlesChosen)
						.addComponent(lblNumberOfArticles))
					.addGap(10)
					.addComponent(btnSubmit)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Article Name", "Date", "Smt"
			}
		));
		scrollPane.setViewportView(table);
		ArticlesToReview.getContentPane().setLayout(groupLayout);
	}
}
