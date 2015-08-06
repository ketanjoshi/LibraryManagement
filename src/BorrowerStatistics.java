import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class BorrowerStatistics
{

	private JFrame frame;
	private JButton btnPayFine;

	private double checkInFine;
	private double checkOutFine;
	private double finePaidSoFar;

	/**
	 * Create the application.
	 */
	public BorrowerStatistics()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Borrower Statistics");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setBounds(52, 27, 100, 25);
		frame.getContentPane().add(lblCardNumber);

		final JTextField cardNumTextField = new JTextField();
		cardNumTextField.setBounds(205, 27, 150, 25);
		frame.getContentPane().add(cardNumTextField);
		cardNumTextField.setColumns(10);

		JLabel lblTotalCheckOuts = new JLabel("Total Check Out Books");
		lblTotalCheckOuts.setBounds(52, 65, 173, 25);
		frame.getContentPane().add(lblTotalCheckOuts);

		final JLabel lblTotalCheckOutValue = new JLabel("");
		lblTotalCheckOutValue.setBounds(250, 65, 105, 25);
		frame.getContentPane().add(lblTotalCheckOutValue);

		JLabel lblTotalFine = new JLabel("Fine Paid So Far");
		lblTotalFine.setBounds(52, 105, 175, 25);
		frame.getContentPane().add(lblTotalFine);

		final JLabel lblTotalFineValue = new JLabel("");
		lblTotalFineValue.setBounds(250, 105, 105, 25);
		frame.getContentPane().add(lblTotalFineValue);

		JLabel lblCheckInFine = new JLabel("Fine: Checked-In books");
		lblCheckInFine.setBounds(52, 140, 173, 25);
		frame.getContentPane().add(lblCheckInFine);

		final JLabel lblCheckInFineValue = new JLabel("");
		lblCheckInFineValue.setBounds(250, 140, 105, 25);
		frame.getContentPane().add(lblCheckInFineValue);

		JLabel lblCheckOutFine = new JLabel("Fine: Checked-Out books");
		lblCheckOutFine.setBounds(52, 175, 180, 25);
		frame.getContentPane().add(lblCheckOutFine);

		final JLabel lblCheckOutFineValue = new JLabel("");
		lblCheckOutFineValue.setBounds(250, 175, 105, 25);
		frame.getContentPane().add(lblCheckOutFineValue);

		JButton btnDisplay = new JButton("Display");
		btnDisplay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(cardNumTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Card number cannot be left blank.");
				else
				{
					Borrower borrower = new Borrower(Integer.parseInt(cardNumTextField.getText()));
					double[] stats = borrower.getBorrowerStats();
					if(stats == null)
						JOptionPane.showMessageDialog(null, borrower.returnMessage);
					else
					{
						finePaidSoFar = stats[1];
						checkInFine = stats[2];
						checkOutFine = stats[3];
	
						lblTotalCheckOutValue.setText(": " + String.valueOf((int)stats[0]));
						lblTotalFineValue.setText(": $" + String.valueOf(finePaidSoFar));
						lblCheckInFineValue.setText(": $" + String.valueOf(checkInFine));
						lblCheckOutFineValue.setText(": $" + String.valueOf(checkOutFine));
	
						if(checkInFine != 0.0)
							btnPayFine.setEnabled(true);
					}
				}
			}
		});
		btnDisplay.setBounds(154, 220, 100, 38);
		frame.getContentPane().add(btnDisplay);

		btnPayFine = new JButton("Pay Fine");
		btnPayFine.setEnabled(false);
		btnPayFine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Borrower borrower = new Borrower(Integer.parseInt(cardNumTextField.getText()));
				try
				{
					borrower.payFineAmount();
					JOptionPane.showMessageDialog(null, borrower.returnMessage);
					lblTotalFineValue.setText(": $" + String.valueOf(finePaidSoFar + checkInFine));
					finePaidSoFar += checkInFine;
					checkInFine = 0;
					lblCheckInFineValue.setText(": $0.0");
					btnPayFine.setEnabled(false);
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnPayFine.setBounds(266, 220, 100, 38);
		frame.getContentPane().add(btnPayFine);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(52, 220, 90, 38);
		frame.getContentPane().add(btnBack);
	}
}
