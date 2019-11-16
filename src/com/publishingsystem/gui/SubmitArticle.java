package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class SubmitArticle {

	private JFrame frmSubmitAnArticle;
	private JTextField txtfldTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubmitArticle window = new SubmitArticle();
					window.frmSubmitAnArticle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SubmitArticle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSubmitAnArticle = new JFrame();
		frmSubmitAnArticle.setTitle("Submit an Article");
		frmSubmitAnArticle.setBounds(100, 100, 700, 552);
		frmSubmitAnArticle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSubmitAnArticle.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblSubmitANewArticle = new JLabel("Submit a New Article");
		lblSubmitANewArticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubmitANewArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblChooseAJournal = new JLabel("Choose a Journal to which Publish to:");
		lblChooseAJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		txtfldTitle = new JTextField();
		txtfldTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldTitle.setColumns(10);
		
		JLabel lblAbstract = new JLabel("Abstract:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneAbstract = new JScrollPane();
		scrPaneAbstract.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblAuthors = new JLabel("Authors:");
		lblAuthors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneAuthors = new JScrollPane();
		scrPaneAuthors.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblNewLabel_2 = new JLabel("Please Enter Every New Name together with Password in a New Line. (e. g. name; password)");
		
		JButton btnUploadPdf = new JButton("Upload PDF");
		btnUploadPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPdfIsNot = new JLabel("PDF is not yet uploaded");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "To access your Author/Reviewer roles please Log Out and Login In again to the system. Thank you!");
				frmSubmitAnArticle.dispose();
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 15));
		GroupLayout groupLayout = new GroupLayout(frmSubmitAnArticle.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(80)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnUploadPdf)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPdfIsNot))
						.addComponent(lblAuthors)
						.addComponent(lblAbstract)
						.addComponent(lblTitle)
						.addComponent(lblChooseAJournal)
						.addComponent(lblSubmitANewArticle, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
						.addComponent(txtfldTitle, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
						.addComponent(scrPaneAbstract)
						.addComponent(scrPaneAuthors)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
					.addGap(80))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(290)
					.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 104, Short.MAX_VALUE)
					.addGap(290))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblSubmitANewArticle)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblChooseAJournal)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtfldTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAbstract)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrPaneAbstract, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAuthors)
					.addGap(2)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrPaneAuthors, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnUploadPdf)
						.addComponent(lblPdfIsNot))
					.addGap(10)
					.addComponent(btnSubmit)
					.addGap(60))
		);
		
		JEditorPane editPaneAuthors = new JEditorPane();
		scrPaneAuthors.setViewportView(editPaneAuthors);
		
		JEditorPane editPaneAbstract = new JEditorPane();
		scrPaneAbstract.setViewportView(editPaneAbstract);
		
		JList listOfJournals = new JList();
		listOfJournals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listOfJournals);
		listOfJournals.setModel(new AbstractListModel() {
			String[] values = new String[] {"First Journal", "Second journal", "Third Journal", "", "First Journal", "Second journal", "Third Journal", "First Journal", "Second journal", "Third Journal", "First Journal", "Second journal", "Third Journal", "First Journal", "Second journal", "Third Journal", "First Journal", "Second journal", "Third Journal", "First Journal", "Second journal", "Third Journal"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		frmSubmitAnArticle.getContentPane().setLayout(groupLayout);
	}
}
