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
import com.publishingsystem.mainclasses.Article;
import com.publishingsystem.mainclasses.AuthorOfArticle;
import com.publishingsystem.mainclasses.Database;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Submission;
import com.publishingsystem.mainclasses.Verdict;
import com.publishingsystem.mainclasses.Volume;

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
	private ArrayList<Submission> allSubmissions;
	private int selectedSubmissionId;
	private Verdict[] finalVerdicts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Academic[] roles = RetrieveDatabase.getRoles("jm@gm.com");
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
        // For every journal the editor is an editor of, get all in progress submissions for them
	    allSubmissions = new ArrayList<Submission>();
	    selectedSubmissionId = -1;
	    finalVerdicts = new Verdict[3];
	    for (EditorOfJournal jour : ((Editor)roles[0]).getEditorOfJournals()) {
	        System.out.println(jour);
	        ArrayList<Submission> thisJournalsSubmissions = RetrieveDatabase.getSubmissionsToJournal(jour.getJournal().getISSN());
	        allSubmissions.addAll(thisJournalsSubmissions);
	    }
	
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
		
		
		for(EditorOfJournal eoj : this.editor.getEditorOfJournals()) {
			boolean hasClash = RetrieveDatabase.editorOfJournalHasClash(eoj);
			
			if(hasClash && !eoj.isTempRetired()) {
				if(eoj.isChiefEditor() && RetrieveDatabase.getEditorsOfJournal(eoj.getJournal()).size() == 1) {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName()+"\nPlease add a new editor for this Journal who will be the Chief Editor.");
				}else if(eoj.isChiefEditor()) {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName()+"\nPlease select a new Chief Editor for this Journal.");
				}else {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName());	
				}
				Database.tempRetireEditor(eoj);
				eoj.temporaryRetire();
			}else if(!hasClash && eoj.isTempRetired()){
				JOptionPane.showMessageDialog(null, "Your temporary retirement from "+eoj.getJournal().getJournalName()+" has been suspended.");
				Database.reInitiateEditor(eoj);
				eoj.reInitiate();
			}
				
		}
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblArticlesList = new JLabel("Article's List:");
		lblArticlesList.setToolTipText("");
		lblArticlesList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblFinalVerdicts = new JLabel("Final Verdicts:");
		lblFinalVerdicts.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnAcceptArticle = new JButton("Process Article");
	    btnAcceptArticle.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent arg0) {
                // there isn't 3 final verdicts yet, error
	            if (finalVerdicts[2] == Verdict.NOVERDICT) {
	                JOptionPane.showMessageDialog(null, "Three final verdicts haven't been submitted yet", "Error", 0);
                    return;
	            }
	            
	            // get submission object
	            Submission selectedSubmission = null;
	            for (Submission sub : allSubmissions) {
	                if (sub.getSubmissionId() == selectedSubmissionId) {
	                    selectedSubmission = sub;
	                    break;
	                }
	            }
	            
	            // check if article should be accepted
	            int[] verdictCount = getVerdictCount(finalVerdicts);
	            if (verdictCount[0] == 3) {
	                // 3 strong accept = accept
	                acceptSubmission(selectedSubmission);
	                return;
	            } else if (verdictCount[3] == 3) {
	                // 3 strong reject = reject
	                JOptionPane.showMessageDialog(null, "Submission has received 3 Strong Rejects and has been rejected.", "Rejected", 1);
	                return;
	            } else if (verdictCount[0] > 0 && verdictCount[3] > 0) {
	                // at least 1 strong accept and 1 strong reject + any = discuss
	                    // Strong Accept, Strong Reject, Strong Accept
	                    // Strong Accept, Strong Reject, Weak Accept
	                    // Strong Accept, Strong Reject, Weak Reject
	                    // Strong Accept, Strong Reject, Strong Reject
	                
	                // editor must make a decision to accept/reject the article or cancel processing of the submission now
	                String[] options = {"Accept", "Reject", "Cancel"};
	                int selectedFunction = JOptionPane.showOptionDialog(null, 
	                        "Cannot automatically calculate whether the article should be accepted or rejected. Please make a decision.", 
	                        "No Decision",
	                        JOptionPane.INFORMATION_MESSAGE, 0, null, options, options[0]);
	                System.out.println(selectedFunction);
	                if (selectedFunction == 0) {
	                    // editor has selected to accept the submission
	                    acceptSubmission(selectedSubmission);
	                    return;
	                } else if (selectedFunction == 1) {
	                    // editor has selected to reject the submission
	                    JOptionPane.showMessageDialog(null, "Submission has been rejected.", "Rejected", 1);
	                    return;
	                } else {
	                    // editor has chosen to cancel processing the article now, leave it in the list of submissions
	                    return;
	                }
	            } else {
	                // no strong, only weak = majority decision
  	                    // weak accept, weak reject, weak accept
                        // weak accept, weak reject, weak reject
	                if (verdictCount[1] > verdictCount[2]) {
	                    // 2 weak accepts, 1 weak reject = accept
	                    acceptSubmission(selectedSubmission);
	                    return;
	                } else {
	                    // 1 weak accept, 2 weak rejects = reject
	                    JOptionPane.showMessageDialog(null, "Submission has received more Weak Rejects than Weak Accepts and has been rejected.", "Rejected", 1);
	                    return;
	                }
	            }
	        }
	    });
		btnAcceptArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"", "", ""};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
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
						.addComponent(btnAcceptArticle))
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
							.addComponent(btnAcceptArticle))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
							.addGap(20))))
		);
		
		tblEditor = new JTable();
		tblEditor.setEnabled(false);
		tblEditor.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent arg0) {
                // get id of selected submission
			    selectedSubmissionId = (int)tblEditor.getValueAt(tblEditor.rowAtPoint(arg0.getPoint()), 0);
                System.out.println(selectedSubmissionId);
                
                // get verdicts
                ArrayList<Verdict[]> listVerdicts = RetrieveDatabase.getVerdicts(selectedSubmissionId);
                for (Verdict[] ver : listVerdicts) System.out.println("[" + ver[0] + ", " + ver[1] + "]");
                
                int i = 0;
                for (Verdict[] ver : listVerdicts) {
                    finalVerdicts[i] = ver[0];
                    i++;
                }

                // display verdicts
                list.setModel(new AbstractListModel() {
                    String[] values = new String[] {finalVerdicts[0].toString(),
                                                    finalVerdicts[1].toString(),
                                                    finalVerdicts[2].toString()};
                    public int getSize() {
                        return values.length;
                    }
                    public Object getElementAt(int index) {
                        return values[index];
                    }
                });
			}
		});
				
	    Object[][] tableContents = new Object[allSubmissions.size()][5];
        for (int i=0; i<allSubmissions.size(); i++ ) {
            Submission currentSubmission = allSubmissions.get(i);
            tableContents[i][0] = currentSubmission.getArticle().getArticleId();
            tableContents[i][1] = currentSubmission.getArticle().getTitle();

            ArrayList<AuthorOfArticle> authorsOfArticle = currentSubmission.getArticle().getAuthorsOfArticle();
            System.out.println(authorsOfArticle);
            System.out.println(authorsOfArticle.size());
            String authors = "";
            for (AuthorOfArticle aoa : authorsOfArticle) {
                System.out.println("aoa: " + aoa);
                authors = authors + aoa.getAuthor().getForename() + " " + aoa.getAuthor().getSurname() + "\n";
            }
            tableContents[i][2] = authors;

            tableContents[i][3] = currentSubmission.getArticle().getJournal().getJournalName();
            tableContents[i][4] = currentSubmission.getStatus();
        }
        
		tblEditor.setModel(new DefaultTableModel(
			tableContents,
			new String[] {
				"ID", "Articles", "Authors", "Journal", "Status"
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
				new EditorJournals(editor.getEditorOfJournals(), frmDashboard, "RETIRE");
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
	
	/**
	 * getVerdictCount
	 * 
	 * Take a list of verdicts (e.g. three final verdicts) and return an array of how many times each type of verdict appears
	 * @param verdicts Array of verdicts to be counted
	 * @return integer array where index 0 is count of Strong Accept, 1 is count of Weak Accept, 2 is count of Weak Reject and 3 is count of Strong Reject
	 */
	public int[] getVerdictCount(Verdict[] verdicts) {
	    int[] results = new int[4];
	    for (Verdict verdict : verdicts) {
	        if (verdict == Verdict.STRONGACCEPT) results[0]++;
	        else if (verdict == Verdict.WEAKACCEPT) results[1]++;
	        else if (verdict == Verdict.WEAKREJECT) results[2]++;
	        else if (verdict == Verdict.STRONGREJECT) results[3]++;
	    }
	    return results;
	}
	
	public void acceptSubmission(Submission s) {
	    // get the edition this will be in
	    //ArrayList<Volume> vols = RetrieveDatabase.getVolumes(s.getArticle().getJournal().getISSN());
	    
	    // get the page range this will be in
	    
	    
	    // create new published article object
	    
	    
	    // add to database
	    
	}
}
