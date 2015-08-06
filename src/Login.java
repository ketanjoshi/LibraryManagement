import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Login {

	private JFrame frame;
	private JTextField txtUser;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					DatabaseWrapper.attachShutDownHook();
					Login window = new Login();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Login");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JLabel lblUserName = new JLabel("Username");
		lblUserName.setBounds(72, 63, 121, 34);
		frame.getContentPane().add(lblUserName);
		
		txtUser = new JTextField();
		txtUser.setText("");
		txtUser.setBounds(211, 68, 162, 25);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(72, 118, 121, 34);
		frame.getContentPane().add(lblPass);
		
		txtPass = new JPasswordField();
		txtPass.setText("");
		txtPass.setBounds(211, 123, 162, 25);
		frame.getContentPane().add(txtPass);
		txtPass.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String user = txtUser.getText();
				String pass = new String(txtPass.getPassword());
				if(DatabaseWrapper.validateUser(user, pass))
				{
					frame.setVisible(false);
					new LibraryManagement();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid username or password. Try again.");
					refresh();
				}
			}

			private void refresh() {
				txtUser.setText("");
				txtPass.setText("");
			}
		});
		btnLogin.setBounds(146, 189, 144, 44);
		frame.getContentPane().add(btnLogin);
	}
}
