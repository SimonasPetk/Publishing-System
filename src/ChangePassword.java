import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class ChangePassword {

	private JFrame frmChangePassword;
	private JPasswordField pwdOldPassword;
	private JPasswordField pwdNewPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword window = new ChangePassword();
					window.frmChangePassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChangePassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChangePassword = new JFrame();
		frmChangePassword.setTitle("Change Password");
		frmChangePassword.setBounds(100, 100, 450, 300);
		frmChangePassword.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblChangeYourPassword = new JLabel("Change Your Password");
		lblChangeYourPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangeYourPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnChange = new JButton("Change");
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		pwdOldPassword = new JPasswordField();
		pwdOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		pwdNewPassword = new JPasswordField();
		pwdNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(frmChangePassword.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(110)
					.addComponent(lblChangeYourPassword, GroupLayout.PREFERRED_SIZE, 214, Short.MAX_VALUE)
					.addGap(110))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(160)
					.addComponent(btnChange, GroupLayout.PREFERRED_SIZE, 114, Short.MAX_VALUE)
					.addGap(160))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pwdNewPassword, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
							.addGap(42))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(pwdOldPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
								.addComponent(lblNewPassword, Alignment.LEADING)
								.addComponent(lblCurrentPassword, Alignment.LEADING))
							.addGap(42))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChangeYourPassword)
					.addGap(10)
					.addComponent(lblCurrentPassword)
					.addGap(10)
					.addComponent(pwdOldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblNewPassword)
					.addGap(10)
					.addComponent(pwdNewPassword, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnChange)
					.addContainerGap(64, Short.MAX_VALUE))
		);
		frmChangePassword.getContentPane().setLayout(groupLayout);
		frmChangePassword.setVisible(true);
	}
}
