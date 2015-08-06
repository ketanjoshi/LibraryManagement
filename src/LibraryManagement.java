import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibraryManagement
{

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public LibraryManagement()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Library Management");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
		JLabel lblLibraryManagement = new JLabel("Library Management Module");
		lblLibraryManagement.setBounds(22, 22, 402, 47);
		frame.getContentPane().add(lblLibraryManagement);
		
		JButton btnBookSearch = new JButton("Book Search");
		btnBookSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BookSearch();
			}
		});
		btnBookSearch.setBounds(12, 79, 196, 61);
		frame.getContentPane().add(btnBookSearch);

		JButton btnBorrowerManagement = new JButton("Borrower Management");
		btnBorrowerManagement.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BorrowerManagement();
			}
		});
		btnBorrowerManagement.setBounds(230, 79, 204, 61);
		frame.getContentPane().add(btnBorrowerManagement);
		
		JButton btnCheckIn = new JButton("Check In");
		btnCheckIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BookCheckIn();
			}
		});
		btnCheckIn.setBounds(12, 182, 196, 61);
		frame.getContentPane().add(btnCheckIn);
		
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BookCheckOut();
			}
		});
		btnCheckOut.setBounds(230, 182, 208, 61);
		frame.getContentPane().add(btnCheckOut);
	}
}
