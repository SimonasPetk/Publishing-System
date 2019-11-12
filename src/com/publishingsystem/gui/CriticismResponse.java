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
		frmRespondToCriticism.setBounds(100, 100, 472, 515);
		frmRespondToCriticism.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRespondToCriticism.setVisible(true);
		
		
		JScrollPane scrPaneAnswer1 = new JScrollPane();
		scrPaneAnswer1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblYourArticleReviews = new JLabel("Your Article's Reviews");
		lblYourArticleReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourArticleReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneReview1 = new JScrollPane();
		scrPaneReview1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPaneReview1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblReview = new JLabel("Summary");
		lblReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneAnswer2 = new JScrollPane();
		scrPaneAnswer2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnSubmitResponse = new JButton("Submit Response");
		btnSubmitResponse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblTypographicalErr = new JLabel("Typographical Error");
		lblTypographicalErr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticisms = new JLabel("Criticisms");
		lblCriticisms.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmRespondToCriticism.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(150)
					.addComponent(lblYourArticleReviews, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
					.addGap(150))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReview)
					.addContainerGap(381, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneAnswer1, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTypographicalErr, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(271, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCriticisms)
					.addContainerGap(385, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneAnswer2, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(136)
					.addComponent(btnSubmitResponse, GroupLayout.PREFERRED_SIZE, 154, Short.MAX_VALUE)
					.addGap(164))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrPaneReview1, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
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
					.addComponent(scrPaneReview1, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTypographicalErr, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(scrPaneAnswer1, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblCriticisms)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrPaneAnswer2, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
					.addGap(43)
					.addComponent(btnSubmitResponse)
					.addGap(18))
		);
		
		
		
		// ADDIDING ELEMNTS INTO A LIST WILL NEED TO BE OBJECTS of criticsms
		DefaultListModel listModel = new DefaultListModel();
		
		listModel.addElement("smtth");
		listModel.addElement("smt1");
		listModel.addElement("smt2");
		listModel.addElement("smt3");
		listModel.addElement("smt5");
		
		JList list_1 = new JList(listModel);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setValueIsAdjusting(true);

		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 int index = list_1.getSelectedIndex();
		         System.out.println("Index Selected: " + index);
		         String s = (String) list_1.getSelectedValue();
		         System.out.println("Value Selected: " + s);
		         
		         System.out.println(popUp(s));
		         listModel.remove(index);
		         listModel.addElement("test");
		    }
		});
		scrPaneAnswer2.setViewportView(list_1);
		
		JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"ASSSSSSSSSSSSSSSSSSS", "sdffffffff", "dsfffffffff"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				/*list.setValueAt("Press Again", 0, 0);

				if (arg0.getClickCount() == 2 && list.rowAtPoint(arg0.getPoint()) == 0) {

			        JOptionPane.showMessageDialog(tblJournal, "row #" + 0 + " is clicked");
				}*/
			} 
		});
		
		
		
		
		scrPaneAnswer1.setViewportView(list);
		
		JEditorPane dtrpnReview1 = new JEditorPane();
		dtrpnReview1.setText("\r\n");
		dtrpnReview1.setEnabled(false);
		dtrpnReview1.setEditable(false);
		scrPaneReview1.setViewportView(dtrpnReview1);
		frmRespondToCriticism.getContentPane().setLayout(groupLayout);
	}
	
	private String popUp(String criticism) {
		String ans = null;
		JPanel panel = new JPanel();
		
		
		JScrollPane scrPaneCriticism = new JScrollPane();
		scrPaneCriticism.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblAnswerToCriticism = new JLabel("Answer to Criticism");
		lblAnswerToCriticism.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnswerToCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticism = new JLabel("Criticism");
		lblCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblYourAnswer = new JLabel("Your Answer");
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
								.addComponent(lblYourAnswer, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrPaneCriticism, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
								.addComponent(lblCriticism, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
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
			 JOptionPane.showMessageDialog(new JPanel(), "You haven't responded to criticism", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
		return null;
	}
	
}
