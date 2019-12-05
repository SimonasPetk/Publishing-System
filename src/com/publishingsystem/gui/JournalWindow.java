package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

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
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
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
import java.awt.Dimension;
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
    private ArrayList<Journal> allJournals;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JournalWindow window = new JournalWindow(null);
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
	public JournalWindow(Academic[] roles) {
	    this.allJournals = RetrieveDatabase.getJournals();
		initialize(roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		int width = 1080;
		int height = 740;
	    // Define the frame
		frmJournalWindow = new JFrame();
		frmJournalWindow.setTitle("View Journals");
		frmJournalWindow.setBounds(100, 100, width, height);
		frmJournalWindow.setMinimumSize(new Dimension(width, height));
		frmJournalWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJournalWindow.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmJournalWindow.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
		
		// Window description
		JLabel lblAvailabeJournals = new JLabel("Choose a journal to view");
		lblAvailabeJournals.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// 
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
		
		// Table of journals
		tblJournal = new JTable();
		tblJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    // Open new window displaying the articles in the selected article
				int selectedJournal = (int)tblJournal.getValueAt(tblJournal.rowAtPoint(arg0.getPoint()), 2);				
				new ArticlesWindow(selectedJournal, roles);
				frmJournalWindow.dispose();
			} 
		});
		tblJournal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblJournal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
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
				"Title", "Date of Publication", "ISSN"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblJournal.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPanelJournal.setViewportView(tblJournal);
		frmJournalWindow.getContentPane().setLayout(groupLayout);
		
		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		frmJournalWindow.setJMenuBar(menuBar);
		
		// Log in / Log out button
		JButton btnLogInOut = new JButton();
		if (roles == null) btnLogInOut.setText("Log In");
		else btnLogInOut.setText("Log Out");

		btnLogInOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (roles == null) {
			        new LoginScreen();
			    } else {
			        new JournalWindow(null);
			    }
                frmJournalWindow.dispose();
			}});
		menuBar.add(btnLogInOut);

		// Submit article button
		JButton btnSubmitArticle = new JButton("Submit Article");
		btnSubmitArticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (allJournals.size() == 0) {
			        // If there are no journals, an article cannot be submitted
			        JOptionPane.showMessageDialog(null, "No journals on the system.", "Error", 0);
			    } else {
      				if(roles == null) {
    				    new RegistrationWindow(Role.AUTHOR);
    				}else {
    					new SubmitArticle(roles);
    				}
    				frmJournalWindow.dispose();
			    }
			}
		});
		menuBar.add(btnSubmitArticle);
		
		// Create a journal button
		JButton btnAddJournal = new JButton("Add Journal");
		btnAddJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (roles == null) {
			        // New academic, needs to register
	                new RegistrationWindow(Role.CHIEFEDITOR);
			    } else {
			        if (roles[0] == null) {
			        	if(roles[1] != null) {
							roles[0] = new Author(-1, roles[1].getTitle(), roles[1].getForename(), roles[1].getSurname(), roles[1].getEmailId(),
									roles[1].getUniversity(), null);
							roles[0].setAcademicId(roles[1].getAcademicId());
						}
						else if(roles[2] != null) {
							roles[0] = new Author(-1, roles[2].getTitle(), roles[2].getForename(), roles[2].getSurname(), roles[2].getEmailId(),
									roles[2].getUniversity(), null);
							roles[0].setAcademicId(roles[2].getAcademicId());
						}     
                        new AddJournal(roles);
			        } else {
			            // Existing academic and editor of another article
			            // Need to be registered on the new journal
	                    new AddJournal(roles);
			        }
			    }
			    frmJournalWindow.dispose();
			}
		});
		menuBar.add(btnAddJournal);
		
		if(roles != null) {
			JMenu mnChangeRole = new JMenu("Change My Role");
			menuBar.add(mnChangeRole);
			
			if(roles[0] != null) {
				Editor chiefEditor = (Editor)roles[0];
				boolean isChiefEditor = false;
				for(EditorOfJournal eoj : chiefEditor.getEditorOfJournals()) {
					if(eoj.isChiefEditor()) {
						isChiefEditor = true;
						break;
					}
				}
				if(isChiefEditor) {
					JMenuItem mntmToEditor = new JMenuItem("Chief Editor");
					mntmToEditor.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							new ChiefMainWindow(roles);
							frmJournalWindow.dispose();
						}
					});
					mnChangeRole.add(mntmToEditor);
				}
				JMenuItem mntmToEditor = new JMenuItem("Editor");
				mntmToEditor.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						new EditorMainWindow(roles);
						frmJournalWindow.dispose();
					}
				});
				mnChangeRole.add(mntmToEditor);
			}
		
			if(roles[1] != null) {
				JMenuItem mntmToAuthor = new JMenuItem("Author");
				mntmToAuthor.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						new AuthorMainWindow(roles);
						frmJournalWindow.dispose();
					}
				});
				mnChangeRole.add(mntmToAuthor);
			}
			
			if(roles[2] != null) {
				JMenuItem mntmToReviewer = new JMenuItem("Reviewer");
				mntmToReviewer.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						new ReviewerMainWindow(roles);
						frmJournalWindow.dispose();
					}
				});
				mnChangeRole.add(mntmToReviewer);
			}
		}
	}
}
