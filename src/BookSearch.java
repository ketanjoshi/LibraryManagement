import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class BookSearch
{

	private JFrame frame;
	private JTextField bookIdTextField;
	private JTextField titleTextField;
	private JTextField authorTextField;
	private JTable table;

	/**
	 * Create the application.
	 */
	public BookSearch()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Book Search");
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
				ShowSearchBookResult();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		};

		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setBounds(62, 12, 98, 27);
		frame.getContentPane().add(lblBookId);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(62, 50, 98, 27);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(62, 88, 98, 27);
		frame.getContentPane().add(lblAuthor);
		
		bookIdTextField = new JTextField();
		bookIdTextField.setBounds(153, 20, 300, 19);
		frame.getContentPane().add(bookIdTextField);
		bookIdTextField.setColumns(10);
		bookIdTextField.addKeyListener(keyListener);
		
		titleTextField= new JTextField();
		titleTextField.setBounds(153, 58, 300, 19);
		frame.getContentPane().add(titleTextField);
		titleTextField.setColumns(10);
		titleTextField.addKeyListener(keyListener);
		
		authorTextField= new JTextField();
		authorTextField.setBounds(153, 96, 300, 19);
		frame.getContentPane().add(authorTextField);
		authorTextField.setColumns(10);
		authorTextField.addKeyListener(keyListener);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShowSearchBookResult();
			}
		});
		btnSearch.setBounds(641, 20, 117, 27);
		frame.getContentPane().add(btnSearch);

		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(table.getSelectedRowCount() <= 0)
					JOptionPane.showMessageDialog(null, "No record selected.");
				else
				{
					if(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 5).toString()) == 0)
					{
						JOptionPane.showMessageDialog(null, "No book available in the selected branch.");
					}
					else
					{
						BookCheckOut bc = new BookCheckOut();
						bc.setCheckOutBookDetails(table.getValueAt(table.getSelectedRow(), 0).toString(), 
								table.getValueAt(table.getSelectedRow(), 3).toString());
					}
				}
				ShowSearchBookResult();
			}
		});
		btnCheckOut.setBounds(641, 58, 117, 27);
		frame.getContentPane().add(btnCheckOut);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(641, 96, 117, 27);
		frame.getContentPane().add(btnBack);

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		scrollPane.setVisible(true);
		scrollPane.setBounds(22, 156, 736, 134);
		table = new JTable();
		scrollPane.setViewportView(table);

		String[] columnNames = { "Book_ID", "Title", "Author",
				"BranchID", "Copies", "Available" };
		Object[][] data = { { "", "", "", "" }, { "", "", "", "" },
				{ "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" },
				{ "", "", "", "" }, { "", "", "", "" } };
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	}

	private void ShowSearchBookResult() {
		SearchBookQueryParams bookDetails = new SearchBookQueryParams(bookIdTextField.getText(),
				titleTextField.getText(), authorTextField.getText());
		DefaultTableModel tableModel = SearchBook.returnAvailability(bookDetails);
		if(tableModel == null)
			JOptionPane.showMessageDialog(null, SearchBook.returnMessage);
		else
		table.setModel(tableModel);
	}
}
