package com.publishingsystem.gui;
import java.awt.Dimension;
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
import com.publishingsystem.mainclasses.Edition;
import com.publishingsystem.mainclasses.Editor;
import com.publishingsystem.mainclasses.EditorOfJournal;
import com.publishingsystem.mainclasses.Hash;
import com.publishingsystem.mainclasses.Journal;
import com.publishingsystem.mainclasses.PublishedArticle;
import com.publishingsystem.mainclasses.RetrieveDatabase;
import com.publishingsystem.mainclasses.Review;
import com.publishingsystem.mainclasses.ReviewerOfSubmission;
import com.publishingsystem.mainclasses.Role;
import com.publishingsystem.mainclasses.Submission;
import com.publishingsystem.mainclasses.Verdict;
import com.publishingsystem.mainclasses.Volume;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class EditorMainWindow {

	private JFrame frmDashboard;
	private JTable tblEditor;
	private DefaultTableModel tblEditorModel;
	private Editor editor;
	private ArrayList<Submission> allSubmissions;
	private int selectedSubmissionRow;
	private Verdict[] finalVerdicts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Academic[] roles = RetrieveDatabase.getRoles("j.smith@uniofsmith.ac.uk");
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
	   
	    selectedSubmissionRow = -1;
	    finalVerdicts = new Verdict[3];
	    
	
	    this.editor = (Editor)roles[0];
	    allSubmissions = RetrieveDatabase.getSubmissionsForEditor(editor.getEditorId());
		initialize(roles);
	}
	
	public void refreshEditorTable() {
		DefaultTableModel model = ((DefaultTableModel) tblEditor.getModel());
		model.setRowCount(0);
		for (int i = 0; i < allSubmissions.size(); i++) {
			Submission s = allSubmissions.get(i);
			String[] editorString = new String[4];
			editorString[0] = String.valueOf(i+1);
			editorString[1] = s.getArticle().getJournal().getJournalName();
			editorString[2] = s.getArticle().getTitle();
			editorString[3] = s.getStatus().getStatus();

			model.addRow(editorString);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Academic[] roles) {
		int width = 1080;
		int height = 740;
		frmDashboard = new JFrame();
		frmDashboard.setTitle("Editor's Dashboard");
		frmDashboard.setBounds(100, 100, width, height);
		frmDashboard.setMinimumSize(new Dimension(width, height));
		frmDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDashboard.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frmDashboard.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
		
		for(EditorOfJournal eoj : this.editor.getEditorOfJournals()) {
			boolean hasClash = RetrieveDatabase.editorOfJournalHasClash(eoj);
			System.out.println("EDITOR HAS CLASH?" + hasClash);
			if(hasClash && !eoj.isTempRetired()) {
				if(eoj.isChiefEditor() && RetrieveDatabase.getEditorsOfJournal(eoj.getJournal()).size() == 1) {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName()+"\nPlease add a new editor for this Journal who will be the Chief Editor.");
					new RegistrationWindow(Role.EDITOR, eoj, eoj.getJournal(), frmDashboard);
					Database.tempRetireEditor(eoj);
					eoj.temporaryRetire();
				}else if(eoj.isChiefEditor()) {
					JOptionPane.showMessageDialog(null, "You have been temporarily retired from "+eoj.getJournal().getJournalName()+"\nPlease select a new Chief Editor for this Journal.");
					new TransferChiefEditorRole(eoj, frmDashboard , roles);
					Database.tempRetireEditor(eoj);
					eoj.temporaryRetire();
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
	        	if(selectedSubmissionRow != -1) {
	                // there isn't 3 final verdicts yet, error
		            if (finalVerdicts[0] == null || finalVerdicts[1] == null || finalVerdicts[2] == null) {
		                JOptionPane.showMessageDialog(null, "Three final verdicts haven't been submitted yet", "Error", 0);
	                    return;
		            }
		            
		            boolean remove = false;
		            // get submission object
		            Submission selectedSubmission = allSubmissions.get(selectedSubmissionRow);
		            
		            // check if article should be accepted
		            int[] verdictCount = getVerdictCount(finalVerdicts);
		            if (verdictCount[0] > 0 && verdictCount[3] == 0) {
		                // 2 Strong Accept + NOT Strong Reject = accept
		                  // Strong Accept, Strong Accept, Strong Accept
		                  // Strong Accept, Strong Accept, Weak Accept
		                  // Strong Accept, Strong Accept, Weak Reject
		            	JOptionPane.showMessageDialog(null, "Submission has received Strong Accept and has been Accepted.", "Accepted", 1);
		                acceptSubmission(selectedSubmission);
		                remove = true;
		            } else if (verdictCount[3] > 0 && verdictCount[0] == 0) {
		                // 2 Strong Reject + NOT Strong Accept = reject
		                  // Strong Reject, Strong Reject, Weak Accept
		                  // Strong Reject, Strong Reject, Weak Reject
		                  // Strong Reject, Strong Reject, Strong Reject
		                JOptionPane.showMessageDialog(null, "Submission has received Strong Rejects and has been rejected.", "Rejected", 1);
		                Database.rejectSubmission(selectedSubmission);
		                remove = true;
		            }else if (verdictCount[0] > 0 && verdictCount[3] > 0) {
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
		                    JOptionPane.showMessageDialog(null, "Submission has been Accepted.", "Accepted", 1);
		                    acceptSubmission(selectedSubmission);
		                    remove = true;
		                } else if (selectedFunction == 1) {
		                    // editor has selected to reject the submission
		                    JOptionPane.showMessageDialog(null, "Submission has been rejected.", "Rejected", 1);
		                    Database.rejectSubmission(selectedSubmission);
		                    remove = true;
		                }
		            }else {
		                // no strong, only weak = majority decision
	  	                    // weak accept, weak reject, weak accept
	                        // weak accept, weak reject, weak reject
		                if (verdictCount[1] > verdictCount[2]) {
		                    // 2 weak accepts, 1 weak reject = accept
		                	JOptionPane.showMessageDialog(null, "Submission has received more Weak Accepts than Weak Rejects and has been Accepted.", "Accepted", 1);
		                    acceptSubmission(selectedSubmission);
		                    remove = true;
		                } else {
		                    // 1 weak accept, 2 weak rejects = reject
		                    JOptionPane.showMessageDialog(null, "Submission has received more Weak Rejects than Weak Accepts and has been rejected.", "Rejected", 1);
		                    Database.rejectSubmission(selectedSubmission);
		                    remove = true;
		                }
		            }
		            if(remove) {
		            	allSubmissions.remove(selectedSubmissionRow);
		            	selectedSubmissionRow = -1;
		            	refreshEditorTable();
		            }
	        	}else
	        		JOptionPane.showMessageDialog(null, "No submission selected.", "Error", 0);
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
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblArticlesList)
							.addGap(509)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnAcceptArticle)
						.addComponent(list, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFinalVerdicts, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
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
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
							.addGap(20))))
		);
		
		
		tblEditorModel = new DefaultTableModel(){
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tblEditorModel.addColumn("No.");
		tblEditorModel.addColumn("Journal");
		tblEditorModel.addColumn("Article Title");
		tblEditorModel.addColumn("Status");
		
		tblEditor = new JTable(tblEditorModel);
		refreshEditorTable();
		tblEditor.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblEditor.getColumnModel().getColumn(0).setPreferredWidth(1);
		tblEditor.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblEditor.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblEditor.getColumnModel().getColumn(3).setPreferredWidth(400);
		tblEditor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tblEditor.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent arg0) {
                // get id of selected submission
			    selectedSubmissionRow = tblEditor.rowAtPoint(arg0.getPoint());
                System.out.println(selectedSubmissionRow);
                
                Submission s = allSubmissions.get(selectedSubmissionRow);
                
                boolean canProcess = true;
                for(EditorOfJournal eoj : editor.getEditorOfJournals()) {
                	if(eoj.getJournal().getISSN() == s.getArticle().getJournal().getISSN() && eoj.isTempRetired())
                		canProcess = false;
                }
                
                if(canProcess) {
	                // get verdicts
	                finalVerdicts = RetrieveDatabase.getVerdicts(s.getSubmissionId());
	                System.out.println(finalVerdicts);
	
	                // convert to strings
	                String[] strVerdicts = new String[3];
	                for (int i=0; i<3; i++) {
	                    if (finalVerdicts[i] == null) strVerdicts[i] = "No Verdict";
	                    else strVerdicts[i] = finalVerdicts[i].toString();
	                }
	                
	                // display verdicts
	                list.setModel(new AbstractListModel() {
	                    String[] values = new String[] {strVerdicts[0],
	                                                    strVerdicts[1],
	                                                    strVerdicts[2]};
	                    public int getSize() {
	                        return values.length;
	                    }
	                    public Object getElementAt(int index) {
	                        return values[index];
	                    }
	                });
                }else {
                	selectedSubmissionRow = -1;
                    JOptionPane.showMessageDialog(null, "You cannot process this submission as you have been retired from the board of editors for the journal to which it is submitted.", "Cannot process", 0);
                }
			}
		});
				

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
				new ChangePassword(editor.getEmailId());
				
			}
		});

		mnEditorsMenu.add(mntmChangePassword);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new JournalWindow(null);
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
	    // get the journal the submission will go in
	    Journal submissionsJournal = s.getArticle().getJournal();
	    
	    // set up date formats
        Date now = new Date(System.currentTimeMillis());
        DateFormat dfMonth = new SimpleDateFormat("mm");
        DateFormat dfYear = new SimpleDateFormat("yyyy");
        
	    // get the most recent volume in this journal
	    Volume vol = RetrieveDatabase.getRecentVolume(submissionsJournal.getISSN());
	    if(vol == null) {
	    	int year =  Integer.valueOf(dfYear.format(now));
	    	int volId = Database.addVolume(submissionsJournal.getISSN(), year);
	    	vol = new Volume(year, volId);
	    }
	    vol.setEditions(RetrieveDatabase.getEditions(vol.getVolumeId()));
        // get the most recent edition in this journal
	    Edition ed = RetrieveDatabase.getRecentEdition(vol);
	    
	    if(ed == null) {
	    	int month =  Integer.valueOf(dfMonth.format(now));
	    	int edId = Database.addEdition(vol.getVolumeId(), month);
	    	ed = new Edition(month, edId);
	    }

        // currently, ed is the edition the submission will be added to
        int edId = ed.getEditionId();
        ed.addPublishedArticles(RetrieveDatabase.getPublishedArticles(ed.getEditionId()));
	    if (ed == null || ed.maxArticlesReached()) { // if this month's edition is full or this month's edition is published (ed is null)
	        // need a new edition for next month
	        if (vol.maxEditionsReached() || ed.getEditionMonth() == 12) { // if this year's volume is full or the month is December
	            // need a new year for next year
	            // create next year's volume
	            int newVolId = Database.addVolume(submissionsJournal.getISSN(), Integer.valueOf(dfYear.format(now)) + 1);
	            // create january's edition
	            edId = Database.addEdition(newVolId, 01);
	        } else {
	            // create next month's edition
	            edId = Database.addEdition(vol.getVolumeId(), Integer.valueOf(dfMonth.format(now)) + 1);
            }
	    }	            
        
	    // add submission to database as published article
	    int paID = Database.addPublishedArticle(edId, s.getArticle().getArticleId());
	    Database.acceptSubmission(s);
	    System.out.println(paID);
	    
	    // display message to user
	    JOptionPane.showMessageDialog(null, "Submission has been accepted and will be published soon.", "Success", 1);
	}
}
