package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.Author;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Role;

import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class JournalWindow {

	private JFrame frmJournalWindow;
	private JTable tblJournal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JournalWindow window = new JournalWindow(0);
					window.frmJournalWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JournalWindow(int academicID) {
		initialize(academicID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int academicID) {
		frmJournalWindow = new JFrame();
		frmJournalWindow.setTitle("View Journals");
		frmJournalWindow.setBounds(100, 100, 540, 331);
		frmJournalWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJournalWindow.setVisible(true);
		
		JLabel lblAvailabeJournals = new JLabel("Choose a journal to view");
		lblAvailabeJournals.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JScrollPane scrollPanelJournal = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frmJournalWindow.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAvailabeJournals, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPanelJournal, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
							.addGap(10))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblAvailabeJournals)
					.addGap(15)
					.addComponent(scrollPanelJournal, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
					.addGap(10))
		);
		
		tblJournal = new JTable();
		tblJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedJournal = (int)tblJournal.getValueAt(tblJournal.rowAtPoint(arg0.getPoint()), 2);
				System.out.println(selectedJournal);

				/*if (arg0.getClickCount() == 2 && tblJournal.rowAtPoint(arg0.getPoint()) == 0) {

			        JOptionPane.showMessageDialog(tblJournal, "row #" + 0 + " is clicked");
				}*/
				
				new ArticlesWindow(selectedJournal);
				//frmJournalWindow.dispose();
			} 
		});
		tblJournal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ArrayList<Journal> allJournals = RetrieveDatabase.getJournals();
		Object[][] tableContents = new Object[allJournals.size()][3];
		for (int i=0; i<allJournals.size(); i++) {
		    Journal currentJournal = allJournals.get(i);
		    tableContents[i][0] = currentJournal.getJournalName();
		    tableContents[i][1] = currentJournal.getDateOfPublication().toString();
		    tableContents[i][2] = currentJournal.getISSN();
		}
		tblJournal.setModel(new DefaultTableModel(
			tableContents,
			new String[] {
				"Name", "Date", "Number"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		scrollPanelJournal.setViewportView(tblJournal);
		frmJournalWindow.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmJournalWindow.setJMenuBar(menuBar);
				
		JLabel lblLoggedInAs = new JLabel();
		lblLoggedInAs.setFont(new Font("Tahoma", Font.PLAIN, 13));
		menuBar.add(lblLoggedInAs);
		
		JButton btnLogInOut = new JButton();
		if (academicID == 0) btnLogInOut.setText("Log In");
		else {
		    String[] loggedInName = RetrieveDatabase.getNamesByID(academicID);
		    btnLogInOut.setText("Logged in as " + loggedInName[0] + " " + loggedInName[1] + ". Log Out");
		}

		btnLogInOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (academicID == 0) {
			        new LoginScreen();
			    } else {
			        new JournalWindow(0);
			    }
                frmJournalWindow.dispose();
			}});
		menuBar.add(btnLogInOut);
		
		JButton btnSubmitArticle = new JButton("Submit Article");
		btnSubmitArticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (academicID == 0) {
                    JOptionPane.showMessageDialog(null, "Please login before submitting an article", "Submit Article", 0);
				} else {
				    //new RegistrationWindow(Role.AUTHOR);
				    
				    for (Academic acad : RetrieveDatabase.getRoles("kb@gm.com")) System.out.println(acad);
				    
				    //new SubmitArticle(loggedInAuthor);
				    //frmJournalWindow.dispose();
				}
			}
		});
		menuBar.add(btnSubmitArticle);
		
		JButton btnAddJournal = new JButton("Add Journal");
		btnAddJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegistrationWindow(Role.CHIEFEDITOR);
				frmJournalWindow.dispose();
			}
		});
		menuBar.add(btnAddJournal);
	}
}
