import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class dummy2 {

	private JFrame frame;
	private final JPanel panel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dummy2 window = new dummy2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public dummy2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 685, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
		);
		
		JScrollPane scrPaneCriticism = new JScrollPane();
		scrPaneCriticism.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lblAnswerToCriticism = new JLabel("Answer to Criticism");
		lblAnswerToCriticism.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnswerToCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblCriticism = new JLabel("Criticism");
		lblCriticism.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblYourAnswer = new JLabel("Your Answer");
		lblYourAnswer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JScrollPane scrPaneAnswer = new JScrollPane();
		scrPaneAnswer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGap(250)
							.addComponent(lblAnswerToCriticism, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
							.addGap(250))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrPaneAnswer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
								.addComponent(lblYourAnswer, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrPaneCriticism, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
								.addComponent(lblCriticism, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(280)
					.addComponent(btnSubmit, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
					.addGap(280))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAnswerToCriticism, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(25))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCriticism, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrPaneCriticism, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblYourAnswer, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(scrPaneAnswer, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(37))
		);
		
		JEditorPane editPaneAnswer = new JEditorPane();
		scrPaneAnswer.setViewportView(editPaneAnswer);
		
		JEditorPane editPaneCriticsm = new JEditorPane();
		editPaneCriticsm.setEditable(false);
		scrPaneCriticism.setViewportView(editPaneCriticsm);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
}
