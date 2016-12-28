import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ForgotPasswordMenu extends JPanel 
{
	
	private JTextField usernameField;
	private JTextField answerField;
	private boolean usernameFlag = false;
	private boolean securityFlag = false;
	private String securityQuestion, password;
	
	public boolean isUsernameFlag() 
	{
		return usernameFlag;
	}

	public void setUsernameFlag(boolean usernameFlag)
	{
		this.usernameFlag = usernameFlag;
	}

	public boolean isSecurityFlag() 
	{
		return securityFlag;
	}

	public void setSecurityFlag(boolean securityFlag) 
	{
		this.securityFlag = securityFlag;
	}

	/**
	 * Create the panel.
	 */
	public ForgotPasswordMenu() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username :");
		lblNewLabel.setBounds(163, 90, 80, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Security Question :");
		lblNewLabel_1.setBounds(125, 115, 106, 14);
		add(lblNewLabel_1);
		lblNewLabel_1.setVisible(usernameFlag);
		
		usernameField = new JTextField();
		usernameField.setBounds(310, 87, 150, 20);
		add(usernameField);
		usernameField.setColumns(10);
		
		final JLabel securityLabel = new JLabel(securityQuestion);
		securityLabel.setBounds(310, 115, 150, 14);
		add(securityLabel);
		securityLabel.setVisible(usernameFlag);
		
		JLabel forgotPasswordMenu = new JLabel("Forget Password ?");
		forgotPasswordMenu.setForeground(Color.BLUE);
		forgotPasswordMenu.setFont(new Font("Tahoma", Font.BOLD, 21));
		forgotPasswordMenu.setBounds(275, 11, 212, 26);
		add(forgotPasswordMenu);
		
		JLabel lblNewLabel_4 = new JLabel("Your Asnwer :");
		lblNewLabel_4.setBounds(146, 203, 109, 14);
		add(lblNewLabel_4);
		lblNewLabel_4.setVisible(usernameFlag);
		
		
		JLabel lblNewLabel_5 = new JLabel("Your Password is :");
		lblNewLabel_5.setBounds(125, 228, 123, 14);
		add(lblNewLabel_5);
		lblNewLabel_5.setVisible(securityFlag);
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				if(isUsernameFlag())
				{
					
				}
			}
		});
		enterButton.setBounds(196, 338, 89, 23);
		add(enterButton);
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Network.fetchUser(usernameField.getText(), new Network.UserCompletion() {
					public void whenCompleted(User user) {
						securityLabel.setText(user.getSecurityQuestion());
					}

					public void whenError(String error) {

					}
				});
			}
		});
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(424, 338, 89, 23);
		add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.goToPanel("LoginMenu");
			}
		});
		
		answerField = new JTextField();
		answerField.setBounds(310, 200, 150, 20);
		add(answerField);
		answerField.setColumns(10);
		answerField.setVisible(usernameFlag);
		
		JLabel lblThePassword = new JLabel(password);
		lblThePassword.setBounds(310, 228, 131, 14);
		add(lblThePassword);
		lblThePassword.setVisible(securityFlag);
		

	}
}
