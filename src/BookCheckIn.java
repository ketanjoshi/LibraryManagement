import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class BookCheckIn
{

	private JFrame frame;
	private JTextField bookIdTextField;
	private JTextField cardNumTextField;
	private JTextField borrowerTextField;
	private JButton btnCheckIn;
	private JButton btnBack;
	private JTable table;

	/**
	 * Create the application.
	 */
	public BookCheckIn()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Book Check In");
		frame.setBounds(100, 100, 782, 384);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

		KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				ShowSearchResult();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		};

		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setBounds(62, 12, 98, 27);
		frame.getContentPane().add(lblBookId);

		JLabel lblborrower = new JLabel("Borrower");
		lblborrower.setBounds(62, 50, 98, 27);
		frame.getContentPane().add(lblborrower);

		JLabel lblCardNum = new JLabel("Card No.");
		lblCardNum.setBounds(62, 88, 98, 27);
		frame.getContentPane().add(lblCardNum);

		bookIdTextField = new JTextField();
		bookIdTextField.setBounds(153, 20, 300, 19);
		frame.getContentPane().add(bookIdTextField);
		bookIdTextField.setColumns(10);
		bookIdTextField.addKeyListener(keyListener);

		borrowerTextField= new JTextField();
		borrowerTextField.setBounds(153, 58, 300, 19);
		frame.getContentPane().add(borrowerTextField);
		borrowerTextField.setColumns(10);
		borrowerTextField.addKeyListener(keyListener);

		cardNumTextField= new JTextField();
		cardNumTextField.setBounds(153, 96, 300, 19);
		frame.getContentPane().add(cardNumTextField);
		cardNumTextField.setColumns(10);
		cardNumTextField.addKeyListener(keyListener);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShowSearchResult();
			}
		});
		btnSearch.setBounds(641, 20, 117, 27);
		frame.getContentPane().add(btnSearch);

		btnCheckIn = new JButton("Check In");
		btnCheckIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(table.getSelectedRowCount() <= 0)
					JOptionPane.showMessageDialog(null, "No record selected.");
				else
				{
					Borrower borrower = new Borrower();
					try
					{
						borrower.checkInBook(table.getValueAt(table.getSelectedRow(), 1).toString(),
								table.getValueAt(table.getSelectedRow(), 2).toString(),
								table.getValueAt(table.getSelectedRow(), 3).toString());
					}
					catch (SQLException e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					JOptionPane.showMessageDialog(null, borrower.returnMessage + "\n" +
							BookManagement.returnMessage);
					BookManagement.returnMessage = "";
				}
				ShowSearchResult();
			}
		});
		btnCheckIn.setBounds(641, 58, 117, 27);
		frame.getContentPane().add(btnCheckIn);

		btnBack = new JButton("Back");
		btnBack.setBounds(641, 96, 117, 27);
		frame.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e)
		{
			frame.setVisible(false);
		}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVisible(true);
		scrollPane.setBounds(22, 156, 736, 134);
		table = new JTable();
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane);

		String[] columnNames = { "Card No", "Loan_ID", "Book_ID", "BranchID", "Title" };
		Object[][] data = { { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" },
				{ "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);

	}

	private void ShowSearchResult() {
		BookLoanData bookDetails = new BookLoanData(bookIdTextField.getText(),
				cardNumTextField.getText(), borrowerTextField.getText());
		DefaultTableModel tableModel = SearchBook.returnLoanedBooks(bookDetails);
		if(tableModel == null)
			JOptionPane.showMessageDialog(null, SearchBook.returnMessage);
		else
		table.setModel(tableModel);
	}

}
