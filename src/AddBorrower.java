import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddBorrower
{

	private JFrame frame;
	private JTextField fnameTextField;
	private JTextField lnameTextField;
	private JTextField addressTextField;
	private JTextField phoneTextField;
	private JButton btnAddBorrower;
	private JButton btnBack;

	/**
	 * Create the application.
	 */
	public AddBorrower()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Add Borrower");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(63, 30, 98, 27);
		frame.getContentPane().add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(63, 70, 98, 27);
		frame.getContentPane().add(lblLastName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(63, 110, 98, 27);
		frame.getContentPane().add(lblAddress);
		
		JLabel lblPhone = new JLabel("Phone No.");
		lblPhone.setBounds(63, 150, 98, 27);
		frame.getContentPane().add(lblPhone);
		
		fnameTextField = new JTextField();
		fnameTextField.setBounds(179, 34, 218, 19);
		frame.getContentPane().add(fnameTextField);
		fnameTextField.setColumns(10);
		
		lnameTextField = new JTextField();
		lnameTextField.setBounds(179, 74, 218, 19);
		frame.getContentPane().add(lnameTextField);
		lnameTextField.setColumns(10);
		
		addressTextField = new JTextField();
		addressTextField.setBounds(180, 114, 217, 19);
		frame.getContentPane().add(addressTextField);
		addressTextField.setColumns(10);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(179, 154, 218, 19);
		frame.getContentPane().add(phoneTextField);
		phoneTextField.setColumns(10);
		
		btnAddBorrower = new JButton("Add Borrower");
		btnAddBorrower.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Add the borrower to the databse
				String fname = fnameTextField.getText();
				String lname = lnameTextField.getText();
				String address = addressTextField.getText();
				String phone = phoneTextField.getText();
				if(Validate())
				{
					Borrower borrower = new Borrower(fname, lname, address, phone);
					borrower.addToDb();
					JOptionPane.showMessageDialog(null, borrower.returnMessage);
					refresh();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No field can be blank");
				}
			}

			private void refresh() {
				fnameTextField.setText("");
				lnameTextField.setText("");
				addressTextField.setText("");
				phoneTextField.setText("");
			}

			private boolean Validate()
			{
				if(fnameTextField.getText().isEmpty())
					return false;
				if(lnameTextField.getText().isEmpty())
					return false;
				if(addressTextField.getText().isEmpty())
					return false;
				if(phoneTextField.getText().isEmpty())
					return false;
				else
					return true;
			}
		});
		btnAddBorrower.setBounds(250, 204, 147, 45);
		frame.getContentPane().add(btnAddBorrower);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(63, 204, 147, 45);
		frame.getContentPane().add(btnBack);
	}

}
