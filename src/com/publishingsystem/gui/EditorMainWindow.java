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
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;

import com.publishingsystem.mainclasses.Article;
import com.publishingsystem.mainclasses.Submission;
import com.publishingsystem.mainclasses.Review;

public class EditorMainWindow {

	private JFrame frmDashboard;
	private JTable tblEditor;
	private Editor editor;
	private ArrayList<Submission> submissions;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorMainWindow window = new EditorMainWindow(null);
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

		JTextArea txtrAbstract = new JTextArea();
		txtrAbstract.setLineWrap(true);
		txtrAbstract.setEditable(false);

		JLabel lblAbstract = new JLabel("Abstract:");
		lblAbstract.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton btnPublishArticle = new JButton("Publish Article");
		btnPublishArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton btnRejectArticle = new JButton("Reject Article");
		btnRejectArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton btnDelayPublishing = new JButton("Delay Publishing");
		btnDelayPublishing.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(19)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 596, GroupLayout.PREFERRED_SIZE)
										.addGap(32)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtrAbstract, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnRejectArticle)
										.addGap(65)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnPublishArticle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnDelayPublishing, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))))
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(10)
										.addComponent(lblAbstract, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtrAbstract, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnPublishArticle)
												.addComponent(btnRejectArticle))
										.addGap(10)
										.addComponent(btnDelayPublishing))
								.addGroup(groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 692, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(49, Short.MAX_VALUE))
				);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblArticlesList = new JLabel("List of Journals:");
		panel.add(lblArticlesList, BorderLayout.NORTH);
		lblArticlesList.setToolTipText("");
		lblArticlesList.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		tblEditor = new JTable();
		tblEditor.setEnabled(false);
		DefaultTableModel model = new DefaultTableModel();
		tblEditor.setModel(model);
		scrollPane.setViewportView(tblEditor);
		model.addColumn("ISSN");
		model.addColumn("Journal Name");
		model.addColumn("Date of publication");
		model.addColumn("Chief Editor");
		model.addColumn("Temporarily retired");
		for(EditorOfJournal eoj : editor.getEditorOfJournals()) {
			Journal journal = eoj.getJournal();
			String[] journalRow = {String.valueOf(journal.getISSN()), 
					journal.getJournalName(),
					journal.getDateOfPublication().toString(),
					eoj.isChiefEditor() ? "yes" : "no",
							eoj.isTempRetired() ? "yes" : "no"};
			model.addRow(journalRow);
		}
		tblEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedJournal = Integer.valueOf(String.valueOf(tblEditor.getValueAt(tblEditor.rowAtPoint(arg0.getPoint()), 0)));	
				boolean isRetired = false;
				for(EditorOfJournal eoj : editor.getEditorOfJournals()) {
					if(eoj.isTempRetired() && eoj.isChiefEditor()) {
						//RAISE SOME ERROR AS TO APPOINT A NEW EDITOR
					}else if(eoj.isTempRetired()) {
						JOptionPane.showMessageDialog(null, "You have been retired from this journal due to affiliation conflicts", "Error in showing articles", 0);
					}
				}
				if(!isRetired) {
					lblArticlesList.setText("List of articles");
					DefaultTableModel model = (DefaultTableModel) tblEditor.getModel();
					model.setRowCount(0);
					model.setColumnCount(0);
					model.addColumn("Article ID");
					model.addColumn("Title");
					model.addColumn("Submission Status");
					model.addColumn("Reviews revieved");
					model.addColumn("Verdict 1");
					model.addColumn("Verdict 2");
					model.addColumn("Verdict 3");
					submissions = RetrieveDatabase.getSubmissionsToJournal(selectedJournal);
					for(Submission s : submissions) {
						String[] submissionString = new String[7];
						Article article = s.getArticle();
						ArrayList<Review> reviews = s.getReviews();
						submissionString[0] = String.valueOf(article.getArticleId());
						submissionString[1] = article.getTitle();
						submissionString[2] = s.getStatus().toString();
						submissionString[3] = String.valueOf(reviews.size());
						submissionString[4] = reviews.size() > 0 ? reviews.get(0).getVerdict().toString() : "No verdict";
						submissionString[5] = reviews.size() > 1 ? reviews.get(1).getVerdict().toString() : "No verdict";
						submissionString[6] = reviews.size() > 2 ? reviews.get(2).getVerdict().toString() : "No verdict";
						model.addRow(submissionString);
						
					}
					tblEditor.removeMouseListener(this);
				}
			} 
		});

		frmDashboard.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmDashboard.setJMenuBar(menuBar);

		JMenu mnEditorsMenu = new JMenu("Menu");
		menuBar.add(mnEditorsMenu);

		JMenuItem mntmRetireFromEditors = new JMenuItem("Retire From Editors");
		mnEditorsMenu.add(mntmRetireFromEditors);

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
