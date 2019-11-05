import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ReviewerMainWindow {

	private JFrame frmReviewDashboard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewerMainWindow window = new ReviewerMainWindow();
					window.frmReviewDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReviewerMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReviewDashboard = new JFrame();
		frmReviewDashboard.setTitle("Review's Dashboard");
		frmReviewDashboard.setBounds(100, 100, 450, 300);
		frmReviewDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmReviewDashboard.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem menuItem_1 = new JMenuItem("Change Password");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new ChangePassword();
			}
		});
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Log Out");
		menuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(menuItem_2);
		
		JMenu mnChangeMyRole = new JMenu("Change My Role");
		menuBar.add(mnChangeMyRole);

		JMenuItem mntmAuthors = new JMenuItem("Author");
		mnChangeMyRole.add(mntmAuthors);
		
		JMenuItem mntmChiefEditor = new JMenuItem("Chief Editor");
		mnChangeMyRole.add(mntmChiefEditor);
		
		JMenuItem mntmEditor = new JMenuItem("Editor");
		mnChangeMyRole.add(mntmEditor);
		
		JMenuItem mntmReader = new JMenuItem("Reader");
		mnChangeMyRole.add(mntmReader);
		
		
	}

}
