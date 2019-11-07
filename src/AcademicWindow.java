import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AcademicWindow {

	private JFrame frmAcademicDashboard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AcademicWindow window = new AcademicWindow();
					window.frmAcademicDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AcademicWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAcademicDashboard = new JFrame();
		frmAcademicDashboard.setTitle("Academic Dashboard");
		frmAcademicDashboard.setBounds(100, 100, 450, 300);
		frmAcademicDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnAddJournal = new JButton("Add a Journal");
		btnAddJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				new AddJournal();
			}
		});
		btnAddJournal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnSubmitArticle = new JButton("Submit an Article");
		btnSubmitArticle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new SubmitArticle();
			}
		});
		btnSubmitArticle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblAcademicDashboard = new JLabel("Academic Dashboard");
		lblAcademicDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcademicDashboard.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GroupLayout groupLayout = new GroupLayout(frmAcademicDashboard.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(100)
					.addComponent(lblAcademicDashboard, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(100))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(150)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAddJournal, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSubmitArticle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(150))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAcademicDashboard)
					.addGap(28)
					.addComponent(btnAddJournal)
					.addGap(34)
					.addComponent(btnSubmitArticle)
					.addGap(87))
		);
		frmAcademicDashboard.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmAcademicDashboard.setJMenuBar(menuBar);
		
		JMenu mntmAcademicMenu = new JMenu("Menu");
		menuBar.add(mntmAcademicMenu);
		
		JMenuItem mntmDeleteMyAccount = new JMenuItem("Delete My Account");
		mntmAcademicMenu.add(mntmDeleteMyAccount);
		
		JMenuItem mntmChangePass = new JMenuItem("Change Password");
		mntmAcademicMenu.add(mntmChangePass);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmAcademicMenu.add(mntmLogOut);
		
		JMenu mnChangeRole = new JMenu("Change My Role");
		menuBar.add(mnChangeRole);
		
		JMenuItem mntmToAuthor = new JMenuItem("Author");
		mnChangeRole.add(mntmToAuthor);
		
		JMenuItem mntmChiefEditor = new JMenuItem("Chief Editor");
		mnChangeRole.add(mntmChiefEditor);
		
		JMenuItem mntmToEditor = new JMenuItem("Editor");
		mnChangeRole.add(mntmToEditor);
		
		JMenuItem mntmToReader = new JMenuItem("Reader");
		mnChangeRole.add(mntmToReader);
		
		JMenuItem mntmToReviewer = new JMenuItem("Reviewer");
		mnChangeRole.add(mntmToReviewer);
	}
}
