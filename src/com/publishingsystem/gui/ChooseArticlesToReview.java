package com.publishingsystem.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Reviewer;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Submission;

import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class ChooseArticlesToReview {

	private JFrame articlesToReview;
	private JTable tblToReview;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseArticlesToReview window = new ChooseArticlesToReview(null, 0, null);
					window.articlesToReview.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChooseArticlesToReview(Reviewer reviewer, int articleId, ReviewerMainWindow rmw) {
		initialize(reviewer, articleId, rmw);
		articlesToReview.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Reviewer reviewer, int articleId, ReviewerMainWindow rmw) {
		int width = 610;
		int height = 310;
		articlesToReview = new JFrame();
		articlesToReview.setTitle("Choose What to Review");
		articlesToReview.setBounds(100, 100, width, height);
		articlesToReview.setMinimumSize(new Dimension(width, height));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel lblNewLabel = new JLabel("Choose Articles You Want to Review");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnSelectArticles = new JButton("Select articles");
		btnSelectArticles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(articlesToReview.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(163)
					.addComponent(btnSelectArticles, GroupLayout.PREFERRED_SIZE, 283, Short.MAX_VALUE)
					.addGap(154))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(34, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(50)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
					.addGap(50))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(15)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelectArticles, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
					.addGap(12))
		);

		ArrayList<Submission> submissionsToReview = RetrieveDatabase.getSubmissions(reviewer);
		DefaultTableModel str_model = new DefaultTableModel();
		str_model.addColumn("No.");
		str_model.addColumn("Title");
		str_model.addColumn("Select");
		int counter = 1;
		for (Submission s : submissionsToReview) {
			Object[] submissionString = new Object[4];
			submissionString[0] = String.valueOf(counter);
			submissionString[1] = s.getArticle().getTitle();
			submissionString[2] = false;
			str_model.addRow(submissionString);
			counter++;
		}

		tblToReview = new JTable(str_model) {
			private static final long serialVersionUID = 1L;

			boolean[] columnEditables = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return String.class;
				default:
					return Boolean.class;
				}
			}
		};
		tblToReview.getColumnModel().getColumn(0).setResizable(false);
		tblToReview.getColumnModel().getColumn(1).setResizable(false);
		tblToReview.getColumnModel().getColumn(2).setResizable(false);
		tblToReview.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblToReview.getColumnModel().getColumn(0).setPreferredWidth(1);
		tblToReview.getColumnModel().getColumn(1).setPreferredWidth(300);
		tblToReview.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblToReview.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnSelectArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ArrayList<Submission> selectedSubmissions = new ArrayList<Submission>();
				for (int row = 0; row < tblToReview.getRowCount(); row++) {
					if (tblToReview.getValueAt(row, 2).toString() == "true") {
						selectedSubmissions.add(submissionsToReview.get(row));
						System.out.println(submissionsToReview.get(row).getArticle().getArticleId());
					}
				}
				if (selectedSubmissions.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please select at most 3 submissions to review",
							"Error in selecting submission", 0);
				} else if (selectedSubmissions.size() < 4) {
					Database.selectSubmissionsToReview(reviewer, selectedSubmissions, articleId);
					for (Submission s : selectedSubmissions) {
						rmw.addSubmissionToReview(s);
					}
					rmw.refreshTables();
					articlesToReview.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Too many submissions selected.\n Select at most 3 submissions",
							"Error in reviewing submission", 0);
				}
			}
		});

		scrollPane.setViewportView(tblToReview);
		articlesToReview.getContentPane().setLayout(groupLayout);
	}
}
