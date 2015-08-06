import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class BorrowerManagement
{

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public BorrowerManagement()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Borrower Management");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		JButton btnBorrowerStats = new JButton("Borrower Stats");
		btnBorrowerStats.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BorrowerStatistics();
			}
		});
		btnBorrowerStats.setBounds(34, 41, 165, 67);
		frame.getContentPane().add(btnBorrowerStats);

		JButton btnNewBorrower = new JButton("New Borrower");
		btnNewBorrower.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				new AddBorrower();
			}
		});
		btnNewBorrower.setBounds(240, 41, 165, 67);
		frame.getContentPane().add(btnNewBorrower);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(133, 162, 165, 67);
		frame.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
			}
		});
	}
}
