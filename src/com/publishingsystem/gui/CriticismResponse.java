package com.publishingsystem.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class CriticismResponse {

	private JFrame frmRespondToCriticism;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriticismResponse window = new CriticismResponse();
					window.frmRespondToCriticism.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CriticismResponse() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRespondToCriticism = new JFrame();
		frmRespondToCriticism.setTitle("Respond to Criticism");
		frmRespondToCriticism.setBounds(100, 100, 470, 810);
		frmRespondToCriticism.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRespondToCriticism.setVisible(true);
		
		JScrollPane scrPaneAnswer1 = new JScrollPane();
		scrPaneAnswer1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneAnswer1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblYourArticleReviews = new JLabel("Your Article's Reviews");
		lblYourArticleReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourArticleReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneReview1 = new JScrollPane();
		scrPaneReview1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneReview1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblReview = new JLabel("Review 1");
		lblReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblReview_1 = new JLabel("Review 2");
		lblReview_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneReview2 = new JScrollPane();
		scrPaneReview2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneReview2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane scrPaneAnswer2 = new JScrollPane();
		scrPaneAnswer2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneAnswer2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane scrPaneAnswer3 = new JScrollPane();
		scrPaneAnswer3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneAnswer3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane scrPaneReview3 = new JScrollPane();
		scrPaneReview3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneReview3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblReview_2 = new JLabel("Review 3");
		lblReview_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnSubmitResponse = new JButton("Submit Response");
		btnSubmitResponse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmRespondToCriticism.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(150)
					.addComponent(lblYourArticleReviews, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(150))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneReview1, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReview)
					.addContainerGap(370, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneAnswer1, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(150)
					.addComponent(btnSubmitResponse, GroupLayout.PREFERRED_SIZE, 129, Short.MAX_VALUE)
					.addGap(150))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrPaneAnswer2, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
						.addComponent(lblReview_1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrPaneReview2, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrPaneReview3, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
						.addComponent(lblReview_2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrPaneAnswer3, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblYourArticleReviews)
					.addGap(10)
					.addComponent(lblReview)
					.addGap(10)
					.addComponent(scrPaneReview1, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(scrPaneAnswer1, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblReview_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrPaneReview2, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(scrPaneAnswer2, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblReview_2, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrPaneReview3, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(scrPaneAnswer3, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(btnSubmitResponse)
					.addGap(20))
		);
		
		JEditorPane dtrpnReview3 = new JEditorPane();
		dtrpnReview3.setText("\r\n");
		dtrpnReview3.setEnabled(false);
		dtrpnReview3.setEditable(false);
		scrPaneReview3.setViewportView(dtrpnReview3);
		
		JEditorPane editPaneAnswer3 = new JEditorPane();
		scrPaneAnswer3.setViewportView(editPaneAnswer3);
		
		JEditorPane editPaneAnswer2 = new JEditorPane();
		scrPaneAnswer2.setViewportView(editPaneAnswer2);
		
		JEditorPane dtrpnReview2 = new JEditorPane();
		dtrpnReview2.setText("\r\n");
		dtrpnReview2.setEnabled(false);
		dtrpnReview2.setEditable(false);
		scrPaneReview2.setViewportView(dtrpnReview2);
		
		JEditorPane dtrpnReview1 = new JEditorPane();
		dtrpnReview1.setText("\r\n");
		dtrpnReview1.setEnabled(false);
		dtrpnReview1.setEditable(false);
		scrPaneReview1.setViewportView(dtrpnReview1);
		
		JEditorPane editPaneAnswer1 = new JEditorPane();
		scrPaneAnswer1.setViewportView(editPaneAnswer1);
		frmRespondToCriticism.getContentPane().setLayout(groupLayout);
	}
}
