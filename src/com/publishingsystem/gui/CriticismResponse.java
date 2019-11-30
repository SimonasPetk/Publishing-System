package com.publishingsystem.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

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
import com.publishingsystem.mainclasses.Criticism;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.PDF;
import com.publishingsystem.mainclasses.PDFConverter;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;

import java.awt.FlowLayout;

public class CriticismResponse {

	private JFrame frmRespondToCriticism;
	private ArrayList<JTextArea> textAreaAnswers;
	private ArrayList<Criticism> criticisms;
	private String pdfPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriticismResponse window = new CriticismResponse(null, null);
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
	public CriticismResponse(AuthorMainWindow amw, ReviewerOfSubmission ros) {
		textAreaAnswers= new ArrayList<JTextArea>();
		criticisms = ros.getReview().getCriticisms();
		initialize(amw, ros);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(AuthorMainWindow amw, ReviewerOfSubmission ros) {
		frmRespondToCriticism = new JFrame();
		frmRespondToCriticism.setTitle("Respond to Criticism");
		frmRespondToCriticism.setBounds(100, 100, 534, 604);
		frmRespondToCriticism.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRespondToCriticism.setVisible(true);
		
		JLabel lblYourArticleReviews = new JLabel("Your Article's Reviews");
		lblYourArticleReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourArticleReviews.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblReview = new JLabel("Summary");
		lblReview.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnSubmitResponse = new JButton("Submit Response");
		btnSubmitResponse.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				boolean allAnswered = true;
				ArrayList<String> answers = new ArrayList<String>();
				for(JTextArea jta : textAreaAnswers) {
					if(jta.getText().isEmpty()) {
						allAnswered = false;
						break;
					}
					answers.add(jta.getText());
				}
				if(!allAnswered)
					JOptionPane.showMessageDialog(null, "Please answer all criticisms", "Error in response", 0);
				else {
					
					Calendar calendar = Calendar.getInstance();
					PDF revisedPDF = new PDF(-1, new java.sql.Date(calendar.getTime().getTime()), ros.getSubmission());
					ros.getReview().answer(answers);
					amw.refreshReviewTable();
					Database.addResponse(ros);
					Database.addRevisedSubmission(revisedPDF, PDFConverter.getByteArrayFromFile(pdfPath));
					frmRespondToCriticism.dispose();
				}
			}
		});
		btnSubmitResponse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblTypographicalErr = new JLabel("Typographical Error");
		lblTypographicalErr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticisms = new JLabel("Criticisms");
		lblCriticisms.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPdfIsNot = new JLabel("PDF is not yet uploaded");
		
		JButton btnAddUpdatedPdf = new JButton("Add Updated PDF ");
		btnAddUpdatedPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(pdfPath == null) {
					FileDialog fd = new FileDialog(new JFrame());
					fd.setVisible(true);
					File[] f = fd.getFiles();
	
					if(f.length > 0){
						pdfPath = fd.getFiles()[0].getAbsolutePath();
						System.out.println(pdfPath);
						lblPdfIsNot.setText("PDF is successfully uploaded");
					}		
					else {
						lblPdfIsNot.setText("Try Again! PDF did not upload!");
					}
				}else {
					JOptionPane.showMessageDialog(null, "PDF already uploaded", "Error in submission", 0);
				}
			}
		});
		btnAddUpdatedPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPdfIsNot.setLabelFor(btnAddUpdatedPdf);
		
		JLabel lblPleaseSubmitYour = new JLabel("Please submit your accordingly updated article version in PDF format");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout groupLayout = new GroupLayout(frmRespondToCriticism.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(136)
					.addComponent(btnSubmitResponse, GroupLayout.PREFERRED_SIZE, 218, Short.MAX_VALUE)
					.addGap(164))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAddUpdatedPdf)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPdfIsNot)
					.addContainerGap(305, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPleaseSubmitYour, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(150)
					.addComponent(lblYourArticleReviews, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
					.addGap(150))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
						.addComponent(lblReview))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCriticisms)
					.addContainerGap(449, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTypographicalErr, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(335, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblYourArticleReviews)
					.addGap(10)
					.addComponent(lblReview)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(lblTypographicalErr, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblCriticisms)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddUpdatedPdf)
						.addComponent(lblPdfIsNot))
					.addGap(5)
					.addComponent(lblPleaseSubmitYour)
					.addGap(10)
					.addComponent(btnSubmitResponse)
					.addGap(10))
		);
		
		JTextArea textAreaSummary = new JTextArea(ros.getReview().getSummary());
		textAreaSummary.setLineWrap(true);
		textAreaSummary.setEditable(false);
		scrollPane_1.setViewportView(textAreaSummary);
		
		JTextArea textAreaTypeErrors = new JTextArea(ros.getReview().getTypingErrors());
		textAreaTypeErrors.setLineWrap(true);
		textAreaTypeErrors.setEditable(false);
		scrollPane_2.setViewportView(textAreaTypeErrors);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		int qCount = 1;
		int counter = 0;
		for(int i = 0; i < criticisms.size(); i++) {
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(null);
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.insets = new Insets(0, 0, 5, 0);
			gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel_1.gridx = 0;
			gbc_panel_1.gridy = counter;
			panel.add(panel_1, gbc_panel_1);
			panel_1.setLayout(new GridLayout(0, 1, 0, 0));
			
			JLabel lblNewLabel = new JLabel("	"+"Q"+qCount+":"+criticisms.get(i).getCriticism());
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(lblNewLabel);
			
			counter++;
			JTextArea textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setLayout(null);
			textAreaAnswers.add(textArea);
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 10, 0);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 0;
			gbc_textField_1.gridy = counter;
			panel.add(textArea, gbc_textField_1);
			counter++;
			qCount++;
		}	
		frmRespondToCriticism.getContentPane().setLayout(groupLayout);
	}
}
