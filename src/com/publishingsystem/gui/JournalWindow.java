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
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
					JournalWindow window = new JournalWindow();
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
	public JournalWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJournalWindow = new JFrame();
		frmJournalWindow.setTitle("Available Journals, Volumes & Editions");
		frmJournalWindow.setBounds(100, 100, 540, 331);
		frmJournalWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJournalWindow.setVisible(true);
		
		JLabel lblAvailabeJournals = new JLabel("Choose which journal, volume or edition you would like to read:");
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
				
				tblJournal.setValueAt("Press Again", 0, 0);

				if (arg0.getClickCount() == 2 && tblJournal.rowAtPoint(arg0.getPoint()) == 0) {

			        JOptionPane.showMessageDialog(tblJournal, "row #" + 0 + " is clicked");
				}
				
				new ArticlesWindow();
				frmJournalWindow.dispose();
			} 
		});
		tblJournal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tblJournal.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
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
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				new LoginScreen();
				frmJournalWindow.dispose();
				
			}
		});
		menuBar.add(btnLogIn);
		
		JButton btnSubmitArticle = new JButton("Submit Article");
		btnSubmitArticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new RegistrationWindow();
				frmJournalWindow.dispose();
			}
		});
		menuBar.add(btnSubmitArticle);
		
		JButton btnAddJournal = new JButton("Add Journal");
		btnAddJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				new RegistrationWindow();
				frmJournalWindow.dispose();
			}
		});
		menuBar.add(btnAddJournal);
	}
}
