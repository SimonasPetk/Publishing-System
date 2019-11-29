package com.publishingsystem.gui;

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

	private JFrame ArticlesToReview;
	private JTable tblToReview;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseArticlesToReview window = new ChooseArticlesToReview(null, 0, null);
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
	public ChooseArticlesToReview(Reviewer reviewer, int articleId, ReviewerMainWindow rmw) {
		initialize(reviewer, articleId, rmw);
		ArticlesToReview.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Reviewer reviewer, int articleId, ReviewerMainWindow rmw) {
		ArticlesToReview = new JFrame();
		ArticlesToReview.setTitle("Choose What to Review");
		ArticlesToReview.setBounds(100, 100, 683, 402);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblNewLabel = new JLabel("Choose What Articles You Want to Review");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblArticlesChosen = new JLabel("Number of Articles Chosen:");
		lblArticlesChosen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNumberOfArticles = new JLabel("Update this according to the number of articles");
		lblNumberOfArticles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnSelectArticles = new JButton("Select articles");
		btnSelectArticles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(ArticlesToReview.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(50)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
					.addGap(50))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(200)
					.addComponent(btnSelectArticles, GroupLayout.PREFERRED_SIZE, 65, Short.MAX_VALUE)
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
					.addComponent(btnSelectArticles)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		

		ArrayList<Submission> submissionsToReview = RetrieveDatabase.getSubmissions(reviewer);
		DefaultTableModel str_model = new DefaultTableModel();
		str_model.addColumn("Article ID");
		str_model.addColumn("Title");
		str_model.addColumn("Select");
		for(Submission s : submissionsToReview) {
			Object[] submissionString = new Object[4];
			submissionString[0] = s.getArticle().getArticleId();
			submissionString[1] = s.getArticle().getTitle();
			submissionString[2] = false;
			str_model.addRow(submissionString);
		}
		
		
		tblToReview = new JTable(str_model) {
			private static final long serialVersionUID = 1L;
			
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			
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

		btnSelectArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
					ArrayList<Submission> selectedSubmissions = new ArrayList<Submission>();
					for(int row = 0; row < tblToReview.getRowCount(); row++) {
						if(tblToReview.getValueAt(row, 2).toString() == "true") {
							selectedSubmissions.add(submissionsToReview.get(row));
							System.out.println(submissionsToReview.get(row).getArticle().getArticleId());
						}
					}
					if(selectedSubmissions.size() == 0) {
						JOptionPane.showMessageDialog(null, "Please select at most 3 submissions to review", "Error in selecting submission", 0);
					}else if(selectedSubmissions.size() < 4) {
						Database.selectSubmissionsToReview(reviewer, selectedSubmissions, articleId);
						for(Submission s : selectedSubmissions) {
							rmw.addSubmissionToReview(s);
						}
						rmw.refreshTables();
						ArticlesToReview.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Too many submissions selected.\n Select at most 3 submissions", "Error in reviewing submission", 0);
					}
			}
		});
		
		scrollPane.setViewportView(tblToReview);
		ArticlesToReview.getContentPane().setLayout(groupLayout);
	}
}
