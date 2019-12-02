package com.publishingsystem.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.publishingsystem.mainclasses.Academic;
import com.publishingsystem.mainclasses.AuthorOfArticle;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Submission;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class EditorMainWindow {

	private JFrame frmDashboard;
	private JTable tblEditor;
	private Editor editor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Academic[] roles = {new Editor(1, "Mr", "Alex", "Hall", "Sheffield", "ahall8@sheffield.ac.uk", new Hash("a")), null, null};
					EditorMainWindow window = new EditorMainWindow(roles);
					window.frmDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditorMainWindow(Academic[] roles) {
		this.editor = (Editor)roles[0];
		initialize(roles);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		frmDashboard = new JFrame();
		frmDashboard.setTitle("Editor's Dashboard");
		frmDashboard.setBounds(100, 100, 1000, 795);
		frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDashboard.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblArticlesList = new JLabel("Article's List:");
		lblArticlesList.setToolTipText("");
		lblArticlesList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblFinalVerdicts = new JLabel("Final Verdicts:");
		lblFinalVerdicts.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnAcceptArticle = new JButton("Accept Article");
		btnAcceptArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnRejectArticle = new JButton("Reject Article");
		btnRejectArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Verdict1", "Verdict2", "Verdict3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JButton btnDownloadPdf = new JButton("Download PDF");
		btnDownloadPdf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesList)
							.addGap(509)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblFinalVerdicts, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnRejectArticle)
							.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnAcceptArticle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDownloadPdf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArticlesList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFinalVerdicts, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAcceptArticle)
								.addComponent(btnRejectArticle))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDownloadPdf)
							.addGap(563))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
							.addGap(20))))
		);
		
		tblEditor = new JTable();
		tblEditor.setEnabled(false);
		tblEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2 && tblEditor.rowAtPoint(arg0.getPoint()) == 0) {
			      //  txtrAbstract.append("This is the abstract of the article you pressed on");
				}
			}
		});
		
		ArrayList<Submission> allSubmissions = new ArrayList<Submission>();
    	for (EditorOfJournal jour : ((Editor)roles[0]).getEditorOfJournals()) {
    	    System.out.println(jour);
    	    // For every journal the editor is an editor of, get all in progress submissions for them
    	    ArrayList<Submission> thisJournalsSubmissions = RetrieveDatabase.getSubmissionsToJournal(jour.getJournal().getISSN());
    	    allSubmissions.addAll(thisJournalsSubmissions);
    	}
		
	    Object[][] tableContents = new Object[allSubmissions.size()][4];
        for (int i=0; i<allSubmissions.size(); i++ ) {
            Submission currentSubmission = allSubmissions.get(i);
            tableContents[i][0] = currentSubmission.getArticle().getTitle();

            ArrayList<AuthorOfArticle> authorsOfArticle = currentSubmission.getArticle().getAuthorsOfArticle();
            System.out.println(authorsOfArticle);
            System.out.println(authorsOfArticle.size());
            String authors = "";
            for (AuthorOfArticle aoa : authorsOfArticle) {
                System.out.println("aoa: " + aoa);
                authors = authors + aoa.getAuthor().getForename() + " " + aoa.getAuthor().getSurname() + "\n";
            }
            tableContents[i][1] = authors;

            tableContents[i][2] = currentSubmission.getArticle().getJournal().getJournalName();
            tableContents[i][3] = currentSubmission.getStatus();
        }
        
		tblEditor.setModel(new DefaultTableModel(
			tableContents,
			new String[] {
				"Articles", "Authors", "Journal", "Status"
			}
		));
		scrollPane.setViewportView(tblEditor);
		frmDashboard.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmDashboard.setJMenuBar(menuBar);
		
		JMenu mnEditorsMenu = new JMenu("Menu");
		menuBar.add(mnEditorsMenu);
		
		JMenuItem mntmRetireFromEditors = new JMenuItem("Retire From Board Of Editors");
		mnEditorsMenu.add(mntmRetireFromEditors);
		mntmRetireFromEditors.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new RetireFromWhichJournal(editor.getEditorOfJournals(), frmDashboard);
			}
		});
		
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.print(0);
				new ChangePassword();
				
			}
		});

		mnEditorsMenu.add(mntmChangePassword);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new LoginScreen();
				frmDashboard.dispose();
				//System.exit(0);
			}
		});
		mnEditorsMenu.add(mntmLogOut);
		
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
						frmDashboard.dispose();
					}
				});
				mnChangeRole.add(mntmToEditor);
			}
		}
		
		if(roles[1] != null) {
			JMenuItem mntmToEditor = new JMenuItem("Author");
			mntmToEditor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					new AuthorMainWindow(roles);
					frmDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToEditor);
		}
		
		if(roles[2] != null) {
			JMenuItem mntmToReviewer = new JMenuItem("Reviewer");
			mntmToReviewer.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					
					new ReviewerMainWindow(roles);
					frmDashboard.dispose();
				}
			});
			mnChangeRole.add(mntmToReviewer);
		}
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mntmToReader.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new JournalWindow(roles);
				frmDashboard.dispose();
			}
		});
		mnChangeRole.add(mntmToReader);
	}
}
