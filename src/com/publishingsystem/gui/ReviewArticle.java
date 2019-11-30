package com.publishingsystem.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Criticism;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Verdict;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ReviewArticle {

	private JFrame frmReviewArticle;
	private ArrayList<JEditorPane> edPaneCriticisms;
	private int criticisms = 1;
	private int counter = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewArticle window = new ReviewArticle(null, null);
					window.frmReviewArticle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReviewArticle(ReviewerOfSubmission ros, ReviewerMainWindow rmw) {
		edPaneCriticisms = new ArrayList<JEditorPane>();
		initialize(ros, rmw);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ReviewerOfSubmission ros, ReviewerMainWindow rmw) {
		frmReviewArticle = new JFrame();
		frmReviewArticle.setTitle("Review Article");
		frmReviewArticle.setBounds(100, 100, 545, 649);
		frmReviewArticle.setMinimumSize(new Dimension(510,600));
		frmReviewArticle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmReviewArticle.setVisible(true);
		
		JLabel lblArticleReviews = new JLabel("Please Submit Your Review");
		lblArticleReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticleReviews.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrPaneReview1 = new JScrollPane();
		scrPaneReview1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneReview1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblReview = new JLabel("Summary of the Review");
		lblReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblTypoErrors = new JLabel("Typographical Error");
		lblTypoErrors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticisms = new JLabel("List of Criticisms");
		lblCriticisms.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNewLabel = new JLabel("Your Verdict");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SELECT", "STRONG ACCEPT", "WEAK ACCEPT", "WEAK REJECT", "STRONG REJECT"}));
		
		JScrollPane scrTypo = new JScrollPane();
		scrTypo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JEditorPane edPaneSummary = new JEditorPane();
		scrPaneReview1.setViewportView(edPaneSummary);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		JEditorPane edPaneTypoErrors = new JEditorPane();
		scrTypo.setViewportView(edPaneTypoErrors);
		
		JButton btnSubmitReview = new JButton("Submit Review");
		btnSubmitReview.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String summary = edPaneSummary.getText();
				String typoErrors = edPaneTypoErrors.getText();
				String verdict = String.valueOf(comboBox.getSelectedItem()).replaceAll("\\s+","");
				boolean criticismsOK = true;
				ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
				for(JEditorPane jep : edPaneCriticisms) {
					String c = jep.getText();
					if(c.isEmpty()) {
						criticismsOK = false;
						break;
					}
					criticisms.add(new Criticism(c));
				}
				
				if(summary.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please give a summary of your review", "Error in submission", 0);
				}
				if(typoErrors.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter if there were any typing errors", "Error in submission", 0);
				}
				if(!criticismsOK) {
					JOptionPane.showMessageDialog(null, "Please give fill all criticism fields", "Error in submission", 0);
				}
				if(verdict.equals("SELECT")){
					JOptionPane.showMessageDialog(null, "Please give a verdict", "Error in submission", 0);
				}
				if(!summary.isEmpty() && !typoErrors.isEmpty() && criticismsOK && !verdict.equals("SELECT")) {
					Review review = new Review(ros, summary, typoErrors, criticisms, Verdict.valueOf(verdict));
					Database.addReview(review);
					rmw.addReview(review);
					frmReviewArticle.dispose();
				}
			}
		});
		btnSubmitReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		JPanel panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnAddCriticism = new JButton("Add Criticism");
		btnAddCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddCriticism.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				counter++;
				JLabel lblNewLbl = new JLabel("Criticism "+criticisms);
				GridBagConstraints gbc_lblNewLbl = new GridBagConstraints();
				gbc_lblNewLbl.anchor = GridBagConstraints.WEST;
				gbc_lblNewLbl.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLbl.gridx = 0;
				gbc_lblNewLbl.gridy = counter;
				panel.add(lblNewLbl, gbc_lblNewLbl);
				counter++;
				JEditorPane textArea = new JEditorPane();
				edPaneCriticisms.add(textArea);
				GridBagConstraints gbc_textArea = new GridBagConstraints();
				gbc_textArea.insets = new Insets(0, 0, 5, 0);
				gbc_textArea.fill = GridBagConstraints.BOTH;
				gbc_textArea.gridx = 0;
				gbc_textArea.gridy = counter;
				panel.add(textArea, gbc_textArea);

				frmReviewArticle.pack();
				frmReviewArticle.repaint();
				criticisms++;
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmReviewArticle.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(120)
					.addComponent(lblArticleReviews, GroupLayout.PREFERRED_SIZE, 237, Short.MAX_VALUE)
					.addGap(120))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(150)
					.addComponent(btnSubmitReview, GroupLayout.PREFERRED_SIZE, 223, Short.MAX_VALUE)
					.addGap(150))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(255, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap(427, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(190)
					.addComponent(btnAddCriticism, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(190))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCriticisms)
					.addContainerGap(404, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTypoErrors, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(334, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrTypo, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneReview1, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReview)
					.addContainerGap(327, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblArticleReviews)
					.addGap(10)
					.addComponent(lblReview)
					.addGap(10)
					.addComponent(scrPaneReview1, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblTypoErrors, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrTypo, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblCriticisms)
					.addGap(10)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnAddCriticism)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addGap(10)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(btnSubmitReview)
					.addContainerGap(25, Short.MAX_VALUE))
		);

		
		JLabel lblCriticism_1 = new JLabel("Criticism "+criticisms);
		GridBagConstraints gbc_lblCriticism_1 = new GridBagConstraints();
		gbc_lblCriticism_1.anchor = GridBagConstraints.WEST;
		gbc_lblCriticism_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblCriticism_1.gridx = 0;
		gbc_lblCriticism_1.gridy = counter;
		panel.add(lblCriticism_1, gbc_lblCriticism_1);
		counter++;
		criticisms++;
		JEditorPane textArea = new JEditorPane();
		//textArea.setRows(2);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = counter;
		panel.add(textArea, gbc_textArea);
		edPaneCriticisms.add(textArea);
		
		
		
		// ADDIDING ELEMNTS INTO A LIST WILL NEED TO BE OBJECTS of criticsms
		DefaultListModel listModel = new DefaultListModel();
		
		listModel.addElement("Click here to add a new criticism / Click on a existant critism to update it");
		frmReviewArticle.getContentPane().setLayout(groupLayout);
	}
	
	/*private String popUp(String criticism) {
		String ans = null;
		JPanel panel = new JPanel();
		
		
		JScrollPane scrPaneCriticism = new JScrollPane();
		scrPaneCriticism.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblAnswerToCriticism = new JLabel("Please Add Your Criticism");
		lblAnswerToCriticism.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnswerToCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticism = new JLabel("Previous Criticism");
		lblCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblYourAnswer = new JLabel("New Criticism");
		lblYourAnswer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneAnswer = new JScrollPane();
		scrPaneAnswer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGap(250)
							.addComponent(lblAnswerToCriticism, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
							.addGap(250))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrPaneAnswer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
								.addComponent(lblYourAnswer, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrPaneCriticism, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
								.addComponent(lblCriticism, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAnswerToCriticism, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(25))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCriticism, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrPaneCriticism, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblYourAnswer, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrPaneAnswer, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
					.addGap(10))
		);
		
		JEditorPane editPaneAnswer = new JEditorPane();
		scrPaneAnswer.setViewportView(editPaneAnswer);
		
		JEditorPane editPaneCriticsm = new JEditorPane();
		editPaneCriticsm.setText(criticism);
		editPaneCriticsm.setEditable(false);
		scrPaneCriticism.setViewportView(editPaneCriticsm);
		panel.setLayout(gl_panel);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Data Entry", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION && !editPaneAnswer.getText().isEmpty()) {
			ans = editPaneAnswer.getText();
			return ans;
		} else if (result == JOptionPane.OK_OPTION && editPaneAnswer.getText().isEmpty()) {
			 JOptionPane.showMessageDialog(new JPanel(), "You haven't entered criticism", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
		return null;
	}*/
}
