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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ReviewArticle {

	private JFrame frmReviewArticle;
	private JTable tblTypoErrors;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewArticle window = new ReviewArticle();
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
	public ReviewArticle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReviewArticle = new JFrame();
		frmReviewArticle.setTitle("Respond to Criticism");
		frmReviewArticle.setBounds(100, 100, 510, 600);
		frmReviewArticle.setMinimumSize(new Dimension(510,600));
		frmReviewArticle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmReviewArticle.setVisible(true);
		
		
		JScrollPane scrPaneTypoErrors = new JScrollPane();
		scrPaneTypoErrors.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblArticleReviews = new JLabel("Please Submit Your Review");
		lblArticleReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticleReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneReview1 = new JScrollPane();
		scrPaneReview1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneReview1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblReview = new JLabel("Summary of the Review");
		lblReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneCriticisms = new JScrollPane();
		scrPaneCriticisms.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnSubmitReview = new JButton("Submit a Review");
		btnSubmitReview.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				frmReviewArticle.dispose();
			}
		});
		btnSubmitReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblTypoErrors = new JLabel("Typographical Error");
		lblTypoErrors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticisms = new JLabel("List of Criticisms");
		lblCriticisms.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNewLabel = new JLabel("Your Verdict");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Strong Accept", "Weak Accept", "Weak Reject", "Strong Reject"}));
		GroupLayout groupLayout = new GroupLayout(frmReviewArticle.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReview)
					.addContainerGap(327, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneTypoErrors, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTypoErrors, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(311, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCriticisms)
					.addContainerGap(381, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(136)
					.addComponent(btnSubmitReview, GroupLayout.PREFERRED_SIZE, 194, Short.MAX_VALUE)
					.addGap(164))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneReview1)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(140)
					.addComponent(lblArticleReviews, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
					.addGap(140))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneCriticisms, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, 0, 474, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblArticleReviews, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblReview)
					.addGap(5)
					.addComponent(scrPaneReview1, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblTypoErrors, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(scrPaneTypoErrors, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblCriticisms)
					.addGap(5)
					.addComponent(scrPaneCriticisms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblNewLabel)
					.addGap(5)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnSubmitReview)
					.addGap(10))
		);
		
		tblTypoErrors = new JTable();
		tblTypoErrors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTypoErrors.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				""
			}
		));
		scrPaneTypoErrors.setViewportView(tblTypoErrors);
		scrPaneTypoErrors.setColumnHeader(null);
		
		
		
		// ADDIDING ELEMNTS INTO A LIST WILL NEED TO BE OBJECTS of criticsms
		DefaultListModel listModel = new DefaultListModel();
		
		listModel.addElement("Click here to add a new criticism / Click on a existant critism to update it");

		JList listOfCriticisms = new JList(listModel);
		listOfCriticisms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfCriticisms.setValueIsAdjusting(true);

		listOfCriticisms.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 String criticism = null;
				 int index = listOfCriticisms.getSelectedIndex();
				 String s = (String) listOfCriticisms.getSelectedValue();
				 
				 
				 // returns all elements of the JList, we start from 1, because 0 is a dummy text 
				 for(int i = 1; i < listOfCriticisms.getModel().getSize(); i++){
			            System.out.println(listOfCriticisms.getModel().getElementAt(i));
			     }
				 
				 System.out.println("Index Selected: " + index);
			     System.out.println("Value Selected: " + s);
		         
			     // popUp window to add new criticsm
		         if (index == 0) {
		        	 criticism = popUp("Add your new Criticism below");
		         } else {
		        	 criticism = popUp(s);
		         }
		         
		         
		         // if it is not the first index and not a null remove the criticsm
		         if (index != 0 && criticism != null) {
		        	 listModel.remove(index);
		         }
		         
		         
		         // add new criticsm to the list;
		         listModel.addElement(criticism); 
		    }
		});
		scrPaneCriticisms.setViewportView(listOfCriticisms);
		
		JEditorPane dtrpnReview1 = new JEditorPane();
		scrPaneReview1.setViewportView(dtrpnReview1);
		frmReviewArticle.getContentPane().setLayout(groupLayout);
	}
	
	private String popUp(String criticism) {
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
	}
}
