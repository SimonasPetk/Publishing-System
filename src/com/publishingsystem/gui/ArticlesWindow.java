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
					//ArticlesWindow window = new ArticlesWindow(58051210);
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
		frmAvailableJournalArticles.setBounds(100, 100, width, height);
		frmAvailableJournalArticles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAvailableJournalArticles.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmAvailableJournalArticles.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		scrollPane_1.setViewportView(editorPane);
		
		tblArticles = new JTable();	
		ArrayList<PublishedArticle> allArticles = RetrieveDatabase.getArticles(selJournal.getISSN());
		Object[][] tableContents = new Object[allArticles.size()][5];
		for (int i = 0; i < allArticles.size(); i++) {
			PublishedArticle currentArticle = allArticles.get(i);
			tableContents[i][0] = currentArticle.getTitle();
			tableContents[i][1] = currentArticle.getAuthorsOfArticle().toString();
			tableContents[i][2] = "some volume and edition?";
			tableContents[i][3] = currentArticle.getPdf().getDate();
			tableContents[i][4] = currentArticle.getPageRange();
		}

		tblArticles.setModel(new DefaultTableModel(tableContents,
				new String[] { "Title ", "Authors", "Volume", "Date Published", "Page Range" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

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
		
		tblArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
					
					int rowPressed = tblArticles.rowAtPoint(e.getPoint());
					
					editorPane.setText(allArticles.get(rowPressed).getSummary());
				} else {
					editorPane.setText(""); // perhaps there is a method like .clear() or smth similar
				}
			}
		});
		
		
		JLabel lblJournalName = new JLabel(selJournal.getJournalName());
		lblJournalName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblAbstract = new JLabel("Abstract:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnDownloadPdf = new JButton("Download PDF");
		btnDownloadPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(articleIndexSelected != -1) {
					try {
						PublishedArticle article = allArticles.get(articleIndexSelected);
				        ArrayList<byte[]> versions = RetrieveDatabase.getPDF(article.getArticleId());
				        for(byte[] pdf : versions) {
							JFileChooser f = new JFileChooser();
					        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					        f.showSaveDialog(null);
					        System.out.println(f.getCurrentDirectory());
					        System.out.println(f.getSelectedFile());
					        OutputStream out = new FileOutputStream(f.getSelectedFile()+".pdf");
							out.write(pdf); //PDF ID
							out.close();
				        }
					}catch(FileNotFoundException fnf) {
						
					}catch(IOException io) {
//						
					}
				}else {
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
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
							.addComponent(btnDownloadPdf)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
							.addGap(16))))
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
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(btnDownloadPdf)
							.addGap(165))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
					.addGap(32))
		);
		
		frmAvailableJournalArticles.getContentPane().setLayout(groupLayout);
		
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
