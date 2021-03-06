package com.publishingsystem.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.SwingConstants;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Article;
import com.publishingsystem.mainclasses.Author;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.PDF;
import com.publishingsystem.mainclasses.PDFConverter;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Reviewer;
import com.publishingsystem.mainclasses.Role;

import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ListSelectionModel;

public class SubmitArticle {

	private JFrame frmSubmitAnArticle;
	private JTextField txtfldTitle;
	private String selectedJournalName;
	private ArrayList<Author> coAuthors;
	private String pdfPath;
	private JScrollPane scrPaneAuthors;
	private DefaultListModel<String> coAuthorsModel;
	private JList<String> listOfAuthors;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubmitArticle window = new SubmitArticle(new Author(1, "Dr", "kb", "kb", "Sheffield", "kb@gm.com",
							new Hash("9d5be6810a8de8673cf2a5b83f2030393028b71127dd034beb9bd03f3a946302")));
					// SubmitArticle window = new SubmitArticle(null);
					window.frmSubmitAnArticle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ArrayList<Author> getCoAuthors() {
		return this.coAuthors;
	}
	
	/**
	 * Create the application.
	 */
	public SubmitArticle(Author a) {
		coAuthors = new ArrayList<Author>();
		Academic[] newRoles = new Academic[3];
		newRoles[1] = a;
		initialize(a, newRoles);
	}

	public SubmitArticle(Academic[] roles) {
		coAuthors = new ArrayList<Author>();
		Author a = null;
		if (roles[1] == null) {
			if(roles[0] != null) {
				a = new Author(-1, roles[0].getTitle(), roles[0].getForename(), roles[0].getSurname(), roles[0].getEmailId(),
						roles[0].getUniversity(), null);
				a.setAcademicId(roles[0].getAcademicId());
			}
			else if(roles[2] != null) {
				a = new Author(-1, roles[2].getTitle(), roles[2].getForename(), roles[2].getSurname(), roles[2].getEmailId(),
						roles[2].getUniversity(), null);
				a.setAcademicId(roles[2].getAcademicId());
			}
			roles[1] = a;
		} else {
			a = (Author) roles[1];
		}
		initialize(a, roles);
	}

	public void addCoAuthor(Author coAuthor) {
		this.coAuthors.add(coAuthor);
		this.coAuthorsModel.clear();
		for (Author author : coAuthors) {
			coAuthorsModel.addElement(author.getForename() + " " + author.getSurname() + " (" + author.getEmailId() + ")");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Author mainAuthor, Academic[] roles) {
		frmSubmitAnArticle = new JFrame();
		frmSubmitAnArticle.setTitle("Submit an Article");
		frmSubmitAnArticle.setBounds(100, 100, 700, 552);
		frmSubmitAnArticle.setMinimumSize(new Dimension(700, 552));
		frmSubmitAnArticle.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new JournalWindow(null);
				frmSubmitAnArticle.dispose();
			}
		});
		frmSubmitAnArticle.setVisible(true);
		selectedJournalName = null;
		JScrollPane scrollPane = new JScrollPane();

		JLabel lblSubmitANewArticle = new JLabel("Submit a New Article");
		lblSubmitANewArticle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubmitANewArticle.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblChooseAJournal = new JLabel("Choose a Journal to which Publish to:");
		lblChooseAJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));

		txtfldTitle = new JTextField();
		txtfldTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfldTitle.setColumns(10);

		JScrollPane scrPaneAbstract = new JScrollPane();
		scrPaneAbstract.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JLabel lblAbstract = new JLabel("Abstract:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JEditorPane editPaneAbstract = new JEditorPane();
		editPaneAbstract.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrPaneAbstract.setViewportView(editPaneAbstract);

		// Authors of article
		JLabel lblAuthors = new JLabel("Authors:");
		lblAuthors.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblNewLabel_2 = new JLabel("Please Register Your Article's Every Co-Author");

		JScrollPane scrPaneAuthors = new JScrollPane();
		scrPaneAuthors.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		coAuthorsModel = new DefaultListModel<>();
		listOfAuthors = new JList<>(coAuthorsModel);
		listOfAuthors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		listOfAuthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrPaneAuthors.setViewportView(listOfAuthors);

		String[] listOfAuthors = new String[coAuthors.size()];
		for (int i = 0; i < coAuthors.size(); i++) {
			listOfAuthors[i] = coAuthors.get(i).getForename() + " " + coAuthors.get(i).getSurname();
		}

		SubmitArticle submitArticleGUI = this;
		JButton btnRegisterANew = new JButton("Register a New Co-Author");
		btnRegisterANew.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new RegistrationWindow(Role.COAUTHOR, submitArticleGUI);
			}
		});
		btnRegisterANew.setFont(new Font("Tahoma", Font.PLAIN, 15));

		// PDF
		JButton btnUploadPdf = new JButton("Upload PDF");
		JLabel lblPdfIsNot = new JLabel("PDF is not yet uploaded");
		btnUploadPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				FileDialog fd = new FileDialog(new JFrame());
				fd.setVisible(true);
				File[] f = fd.getFiles();

				if (f.length > 0) {
					pdfPath = fd.getFiles()[0].getAbsolutePath();
					try {
						PDDocument document = PDDocument.load(new File(pdfPath));
						document.close();
					}catch(Exception ex) {
						pdfPath = null;
						JOptionPane.showMessageDialog(null, "Please upload only PDF files", "Error in submission", 0);
					}
					lblPdfIsNot.setText("PDF is successfully uploaded");
				} else {
					lblPdfIsNot.setText("Try Again! PDF did not upload!");
				}
			}
		});
		btnUploadPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JList listOfJournals = new JList();
		listOfJournals.setFont(new Font("Tahoma", Font.PLAIN, 15));
		listOfJournals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listOfJournals);
		listOfJournals.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				selectedJournalName = (String) listOfJournals.getSelectedValue();
			}

		});
		System.out.println(selectedJournalName);

		ArrayList<Journal> allJournals = RetrieveDatabase.getJournals();
		String[] listContents = new String[allJournals.size()];
		for (int i = 0; i < allJournals.size(); i++) {
			listContents[i] = allJournals.get(i).getJournalName();
		}

		// Submit Button
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				JOptionPane.showMessageDialog(null, "To access your Author/Reviewer roles please Log Out and Login In again to the system. Thank you!");
				String title = txtfldTitle.getText();
				String summary = editPaneAbstract.getText();
				System.out.println(summary);
				Journal journal = null;
				for (Journal item : allJournals) {
					if (item.getJournalName().equals(selectedJournalName)) {
						journal = item;
					}
				}
				String errorMessage = "";
				if (title.isEmpty()) {
					errorMessage += "Please give a title. ";
				}
				if (summary.isEmpty()) {
					errorMessage += "Please give a summary. ";
				}
				if (journal == null) {
					errorMessage += "Please select a journal. ";
				}
				if (pdfPath == null) {
					errorMessage += "Please upload a pdf of your article.";
				}
				if (errorMessage.length() > 0) {
					JOptionPane.showMessageDialog(null, errorMessage, "Error in submission", 0);
				}

				if (title != null && summary != null && journal != null && pdfPath != null && !title.isEmpty()
						&& !summary.isEmpty()) {
					byte[] pdf = PDFConverter.getByteArrayFromFile(pdfPath);
					Article article = new Article(-1, title, summary, journal);
					mainAuthor.registerCoAuthors(article, coAuthors);
					Calendar calendar = Calendar.getInstance();
					mainAuthor.submit(article,
							new PDF(-1, new java.sql.Date(calendar.getTime().getTime()), article.getSubmission()));

					coAuthors.add(mainAuthor);

					// ADDING TO THE DATABASE;
					Database.registerAuthors(coAuthors);
					Database.addSubmission(article, pdf, PDFConverter.getNumPagesFromFile(pdfPath));

					ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
					for (Author a : coAuthors) {
						reviewers.add(new Reviewer(a));
					}
					Database.registerReviewers(reviewers);
					if (roles[2] == null)
						roles[2] = reviewers.get(reviewers.size() - 1);
					new AuthorMainWindow(roles);
					// This is for just adding co-authors
					frmSubmitAnArticle.dispose();
				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 15));

		btnRegisterANew.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmSubmitAnArticle.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(80)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(btnUploadPdf)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblPdfIsNot))
								.addComponent(lblAuthors).addComponent(lblAbstract).addComponent(lblTitle)
								.addComponent(lblChooseAJournal)
								.addComponent(lblSubmitANewArticle, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
								.addComponent(txtfldTitle, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(scrPaneAuthors, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
										.addGap(50).addComponent(btnRegisterANew).addGap(35))
								.addComponent(scrPaneAbstract, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
						.addGap(80))
				.addGroup(groupLayout.createSequentialGroup().addGap(290)
						.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 104, Short.MAX_VALUE).addGap(290)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblSubmitANewArticle)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblChooseAJournal)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblTitle)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtfldTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblAbstract)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrPaneAbstract, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblAuthors).addGap(2)
						.addComponent(lblNewLabel_2)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(10)
										.addComponent(scrPaneAuthors, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnUploadPdf).addComponent(lblPdfIsNot))
										.addGap(10))
								.addGroup(groupLayout.createSequentialGroup().addGap(30).addComponent(btnRegisterANew)
										.addGap(10)))
						.addComponent(btnSubmit).addGap(13)));

		listOfJournals.setModel(new AbstractListModel() {
			// String[] values = new String[] {"First Journal", "Second journal", "Third
			// Journal", "", "First Journal", "Second journal", "Third Journal", "First
			// Journal", "Second journal", "Third Journal", "First Journal", "Second
			// journal", "Third Journal", "First Journal", "Second journal", "Third
			// Journal", "First Journal", "Second journal", "Third Journal", "First
			// Journal", "Second journal", "Third Journal"};
			String[] values = listContents;

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
