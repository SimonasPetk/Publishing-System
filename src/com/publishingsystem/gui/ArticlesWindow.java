package com.publishingsystem.gui;
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
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JEditorPane;

public class ArticlesWindow {

	private JFrame frmAvailableJournalArticles;
	private JTable tblArticles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
	    Journal selJournal = RetrieveDatabase.getJournal(journalID);
	    
		frmAvailableJournalArticles = new JFrame();
		frmAvailableJournalArticles.setTitle("View Articles");
		frmAvailableJournalArticles.setBounds(100, 100, 850, 700);
		frmAvailableJournalArticles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAvailableJournalArticles.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		scrollPane_1.setViewportView(editorPane);
		
		tblArticles = new JTable();
		tblArticles.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if (e.getClickCount() == 2 && tblArticles.rowAtPoint(e.getPoint()) == 0) {
					
					editorPane.setText("This is a dummy summary added, still need to do a lot of work on it, for example to know which article was pressed, and then show that summary, now everything is hardcoded\n"
										+ " Possible solution would be to make article id the same as it displayed in the list, however this might not be possible due to articles having id associated with all of the articles not with a specified journal");
				} else {
					editorPane.setText(""); // perhaps there is a method like .clear() or smth similar
				}
			}
		});
		tblArticles.setModel(new DefaultTableModel(
			new Object[][] {
				{"Article Name", "Article smth", "Volume", "Number", "Page range"},
				{"sdasdas", null, null, null, null},
				{null, null, "sd", null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, "sdad", null, null, null},
				{null, null, null, null, null},
				{null, null, null, "dsad", null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Article Name", "Journal", "Volume", "Number", "Page-Range"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblArticles.getColumnModel().getColumn(0).setPreferredWidth(190);
		tblArticles.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblArticles.getColumnModel().getColumn(3).setPreferredWidth(60);
		scrollPane.setViewportView(tblArticles);
		
		JLabel lblNewLabel = new JLabel(selJournal.getJournalName());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblAbstract = new JLabel("Abstract:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnDownloadPdf = new JButton("Download PDF");
	
		GroupLayout groupLayout = new GroupLayout(frmAvailableJournalArticles.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
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
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmBackToJournals = new JMenuItem("Back To Journals");
		mntmBackToJournals.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				new JournalWindow(roles);
				frmAvailableJournalArticles.dispose();
			}
		});
		mnMenu.add(mntmBackToJournals);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				new LoginScreen();
				frmAvailableJournalArticles.dispose();
			}
		});
		mnMenu.add(mntmLogOut);
	}
}
