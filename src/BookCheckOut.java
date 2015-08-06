import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class BookCheckOut
{

	private JFrame frame;
	private JTextField bookIdTextField;
	private JTextField branchIdTextField;
	private JTextField cardNumTextField;
	private JButton btnCheckOut;
	private JButton btnBack;

	/**
	 * Create the application.
	 */
	public BookCheckOut()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Book Check Out");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setBounds(63, 30, 98, 27);
		frame.getContentPane().add(lblBookId);
		
		JLabel lblBranchId = new JLabel("Branch Id");
		lblBranchId.setBounds(63, 67, 98, 27);
		frame.getContentPane().add(lblBranchId);
		
		JLabel lblCardNum = new JLabel("Card No.");
		lblCardNum.setBounds(63, 106, 98, 27);
		frame.getContentPane().add(lblCardNum);

		bookIdTextField = new JTextField();
		bookIdTextField.setBounds(179, 34, 200, 19);
		frame.getContentPane().add(bookIdTextField);
		bookIdTextField.setColumns(10);
		
		branchIdTextField= new JTextField();
		branchIdTextField.setBounds(179, 72, 200, 19);
		frame.getContentPane().add(branchIdTextField);
		branchIdTextField.setColumns(10);

		cardNumTextField= new JTextField();
		cardNumTextField.setBounds(179, 110, 200, 19);
		frame.getContentPane().add(cardNumTextField);
		cardNumTextField.setColumns(10);

		btnCheckOut = new JButton("Check out");
		btnCheckOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(cardNumTextField.getText().isEmpty() || branchIdTextField.getText().isEmpty() 
						|| bookIdTextField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "No field can be blank");
					return;
				}
				Borrower borrower = new Borrower(Integer.parseInt(cardNumTextField.getText()));
				try
				{
					if(borrower.checkOutNewBook(bookIdTextField.getText(), 
							branchIdTextField.getText()))
					{
						JOptionPane.showMessageDialog(null, "Successfully checked out the book");
					}
					else
						JOptionPane.showMessageDialog(null, borrower.returnMessage);
				}
				catch (SQLException e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				finally
				{
					Refresh();
				}
			}
		});
		btnCheckOut.setBounds(246, 171, 147, 43);
		frame.getContentPane().add(btnCheckOut);

		btnBack = new JButton("Back");
		btnBack.setBounds(63, 171, 160, 43);
		frame.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
		{
			frame.setVisible(false);
		}
	});
	}

	public void setCheckOutBookDetails(String bookId, String branchId)
	{
		bookIdTextField.setText(bookId);
		branchIdTextField.setText(branchId);
	}

	public void Refresh()
	{
		bookIdTextField.setText("");
		cardNumTextField.setText("");
		branchIdTextField.setText("");
	}
}
