package com.publishingsystem.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Article;
import com.publishingsystem.mainclasses.AuthorOfArticle;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.PublishedArticle;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Submission;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ScrollPaneConstants;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class ArticlesWindow {

	private JFrame frmAvailableJournalArticles;
	private JTable tblArticles;
	private static int articleIndexSelected = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// ArticlesWindow window = new ArticlesWindow(58051210);
					ArticlesWindow window = new ArticlesWindow(12345, null);
					window.frmAvailableJournalArticles.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ArticlesWindow(int journalID, Academic[] roles) {
		initialize(journalID, roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int journalID, Academic[] roles) {
		int width = 1080;
		int height = 740;

		Journal selJournal = RetrieveDatabase.getJournal(journalID);

		frmAvailableJournalArticles = new JFrame();
		frmAvailableJournalArticles.setTitle("View Articles");
		frmAvailableJournalArticles.setBounds(100, 100, 1200, 766);
		frmAvailableJournalArticles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAvailableJournalArticles.setVisible(true);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmAvailableJournalArticles.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		tblArticles = new JTable();
		ArrayList<PublishedArticle> allArticles = RetrieveDatabase.getArticles(selJournal.getISSN());
		Object[][] tableContents = new Object[allArticles.size()][5];
		for (int i = 0; i < allArticles.size(); i++) {
			PublishedArticle currentArticle = allArticles.get(i);
			tableContents[i][0] = currentArticle.getTitle();
			tableContents[i][1] = Integer.toString(currentArticle.getEdition().getVolume().getVolumeNumber()) + "."
								  + Integer.toString(currentArticle.getEdition().getEditionNumber());
			tableContents[i][2] = Integer.toString(currentArticle.getEdition().getVolume().getYear()) + "/" 
								  +  Integer.toString(currentArticle.getEdition().getEditionMonth());
			tableContents[i][3] = currentArticle.getPageRange();
		}

		tblArticles.setModel(new DefaultTableModel(tableContents,
				new String[] { "Title ", "Volume.Edition", "Date Published", "Page Range" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblArticles.getColumnModel().getColumn(0).setPreferredWidth(190);
		tblArticles.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblArticles.getColumnModel().getColumn(3).setPreferredWidth(60);
		tblArticles.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblArticles.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblArticles);

		JLabel lblJournalName = new JLabel(selJournal.getJournalName());
		lblJournalName.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblAbstract = new JLabel("Article:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnDownloadPdf = new JButton("Download PDF");
		btnDownloadPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDownloadPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (articleIndexSelected != -1) {
					try {
						PublishedArticle article = allArticles.get(articleIndexSelected);
						byte[] pdf = RetrieveDatabase.getPDFReader(article.getPdf().getPdfId());
						
						if((int) pdf.length > 0) {
							JFileChooser f = new JFileChooser();
							f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							f.showSaveDialog(null);
							System.out.println(f.getCurrentDirectory());
							System.out.println(f.getSelectedFile());
							OutputStream out = new FileOutputStream(f.getSelectedFile() + ".pdf");
							out.write(pdf); // PDF ID
							out.close();
						} else {
							JOptionPane.showMessageDialog(null, "This article doesn't have a PDF", "Error in download", 0);
						}
					} catch (FileNotFoundException fnf) {

					} catch (IOException io) {
//						
					}
				} else {
					JOptionPane.showMessageDialog(null, "No article selected", "Error in download", 0);
				}

			}
		});

		GroupLayout groupLayout = new GroupLayout(frmAvailableJournalArticles.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJournalName, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnDownloadPdf)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(399, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblJournalName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnDownloadPdf)
							.addGap(165))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE))
					.addGap(32))
		);
		
		JPanel panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panel.add(lblTitle, gbc_lblTitle);
		
		JEditorPane titleArea = new JEditorPane();
		titleArea.setEditable(false);
		titleArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_titleArea = new GridBagConstraints();
		gbc_titleArea.insets = new Insets(0, 0, 5, 0);
		gbc_titleArea.fill = GridBagConstraints.BOTH;
		gbc_titleArea.gridx = 0;
		gbc_titleArea.gridy = 1;
		panel.add(titleArea, gbc_titleArea);
		
		JLabel lblAuthors = new JLabel("Authors");
		lblAuthors.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblAuthors = new GridBagConstraints();
		gbc_lblAuthors.anchor = GridBagConstraints.WEST;
		gbc_lblAuthors.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuthors.gridx = 0;
		gbc_lblAuthors.gridy = 2;
		panel.add(lblAuthors, gbc_lblAuthors);
		
		JEditorPane authorArea = new JEditorPane();
		authorArea.setEditable(false);
		authorArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_authorArea = new GridBagConstraints();
		gbc_authorArea.insets = new Insets(0, 0, 5, 0);
		gbc_authorArea.fill = GridBagConstraints.BOTH;
		gbc_authorArea.gridx = 0;
		gbc_authorArea.gridy = 3;
		panel.add(authorArea, gbc_authorArea);
		
		JLabel lblSummary = new JLabel("Abstract");
		lblSummary.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSummary = new GridBagConstraints();
		gbc_lblSummary.anchor = GridBagConstraints.WEST;
		gbc_lblSummary.insets = new Insets(0, 0, 5, 0);
		gbc_lblSummary.gridx = 0;
		gbc_lblSummary.gridy = 4;
		panel.add(lblSummary, gbc_lblSummary);
		
		JEditorPane abstractArea = new JEditorPane();
		abstractArea.setEditable(false);
		abstractArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_abstractArea = new GridBagConstraints();
		gbc_abstractArea.fill = GridBagConstraints.BOTH;
		gbc_abstractArea.gridx = 0;
		gbc_abstractArea.gridy = 5;
		panel.add(abstractArea, gbc_abstractArea);

		frmAvailableJournalArticles.getContentPane().setLayout(groupLayout);
		
		tblArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int rowPressed = tblArticles.rowAtPoint(e.getPoint());
				String authors = "";
				String mainAuthor = "";
				int articleID = allArticles.get(rowPressed).getArticleId();
				ArrayList<AuthorOfArticle> authorsList = RetrieveDatabase.getAuthors(articleID);
				String articleInfo;
				
				for (AuthorOfArticle author : authorsList) {
					if (author.isMainAuthor()) {
						
						mainAuthor += author.getAuthor().getForename() 
									  + " email: " + author.getAuthor().getEmailId();
					} else {
					
						authors += author.getAuthor().getForename() + " ";
					}					
				}

				titleArea.setText(allArticles.get(rowPressed).getTitle());

				authorArea.setText(mainAuthor + "\n" + authors);
				abstractArea.setText(allArticles.get(rowPressed).getSummary());
				
				articleIndexSelected = rowPressed;
			}
		});
		

		JMenuBar menuBar = new JMenuBar();
		frmAvailableJournalArticles.setJMenuBar(menuBar);

		JButton btnBackToJournals = new JButton("Back To Journals");
		btnBackToJournals.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new JournalWindow(roles);
				frmAvailableJournalArticles.dispose();

			}
		});
		menuBar.add(btnBackToJournals);

	}
}
