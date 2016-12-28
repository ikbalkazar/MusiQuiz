
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMenu extends JPanel {
	private JTextField userNameField,passwordField;
	//private String Password,Username;
	private boolean wrongPassword = true;

	public boolean isWrongPassword() {
		return wrongPassword;
	}

	public void setWrongPassword(boolean wrongPassword) {
		this.wrongPassword = wrongPassword;
	}

	/**
	 * Create the panel.
	 */
	public LoginMenu() {
		setLayout(null);
		
		JButton forgotPasswordButton = new JButton("Forget Password");
		forgotPasswordButton.setForeground(Color.RED);
		forgotPasswordButton.setBounds(281, 350, 173, 23);
		add(forgotPasswordButton);

		forgotPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.goToPanel("ForgotPasswordMenu");
			}
		});
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.goToPanel("SignupMenu");
			}
		});

		signUpButton.setForeground(Color.RED);
		signUpButton.setBounds(91, 268, 117, 37);
		add(signUpButton);
		
		JButton loginButton = new JButton("Log in");
		loginButton.setForeground(Color.RED);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String username = userNameField.getText();
				Network.fetchUser(username, new Network.UserCompletion() {
					public void whenCompleted(User user) {
						if (user != null && user.getPassword().equals(passwordField.getText())) {
							JOptionPane.showMessageDialog(null, "Login Successful...");
							Main.loginWith(user);
						} else {
							JOptionPane.showMessageDialog(null, "Failed to login");
						}
					}

					public void whenError(String error) {
						JOptionPane.showMessageDialog(null, "Failed to login");
					}
				});
			}
		});

		loginButton.setBounds(494, 268, 117, 37);
		add(loginButton);
		
		JLabel userNameLabel = new JLabel("Username :");
		userNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		userNameLabel.setBounds(145, 89, 89, 14);
		add(userNameLabel);
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordLabel.setBounds(145, 133, 89, 14);
		add(passwordLabel);
		
		userNameField = new JTextField();
		userNameField.setBounds(282, 86, 205, 20);
		add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(282, 130, 205, 20);
		add(passwordField);
		passwordField.setColumns(10);
		
		JLabel headLine = new JLabel("MUSIC QUIZ");
		headLine.setForeground(Color.BLUE);
		headLine.setFont(new Font("Tahoma", Font.BOLD, 21));
		headLine.setBounds(290, 27, 197, 23);
		add(headLine);
		
		JLabel signUpLabel = new JLabel("New User?");
		signUpLabel.setBounds(107, 246, 77, 14);
		add(signUpLabel);
		
		JLabel loginLabel = new JLabel("Already have an account?");
		loginLabel.setBounds(494, 246, 135, 14);
		add(loginLabel);
		
		JLabel warningLabel = new JLabel("Wrong Username or Password!");
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(281, 200, 191, 14);
		add(warningLabel);
		warningLabel.setVisible(false);

	}
}
