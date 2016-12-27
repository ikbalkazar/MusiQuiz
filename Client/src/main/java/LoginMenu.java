import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMenu {

 private JFrame frmLogin;
 private JTextField userNameTextField;
 private JTextField passwordTextField;

 /**
  * Launch the application.
  */
 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     LoginMenu window = new LoginMenu();
     window.frmLogin.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 /**
  * Create the application.
  */
 public LoginMenu() {
  initialize();
 }

 /**
  * Initialize the contents of the frame.
  */
 private void initialize() 
 {
  
  frmLogin = new JFrame();
  frmLogin.setTitle("Login");
  frmLogin.setBounds(100, 100, 450, 300);
  frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frmLogin.getContentPane().setLayout(null);
  
  JButton signUpButton = new JButton("Sign Up");
  signUpButton.addActionListener(new ActionListener() 
  {
   public void actionPerformed(ActionEvent e) 
   {
    
   }
  });
  signUpButton.setForeground(Color.RED);
  signUpButton.setBounds(38, 147, 102, 31);
  frmLogin.getContentPane().add(signUpButton);
  
  JButton forgotPasswordButton = new JButton("Forgot Password");
  forgotPasswordButton.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
   }
  });
  forgotPasswordButton.setForeground(Color.RED);
  forgotPasswordButton.setBounds(140, 200, 150, 23);
  frmLogin.getContentPane().add(forgotPasswordButton);
  
  JButton loginButton = new JButton("Login");
  loginButton.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
   }
  });
  loginButton.setForeground(Color.RED);
  loginButton.setBounds(293, 147, 102, 31);
  frmLogin.getContentPane().add(loginButton);
  
  userNameTextField = new JTextField();
  userNameTextField.setBounds(178, 38, 89, 20);
  frmLogin.getContentPane().add(userNameTextField);
  userNameTextField.setColumns(10);
  
  passwordTextField = new JPasswordField();
  passwordTextField.setBounds(178, 69, 89, 20);
  frmLogin.getContentPane().add(passwordTextField);
  passwordTextField.setColumns(10);
  
  JLabel lblNewLabel = new JLabel("User Name :");
  lblNewLabel.setForeground(Color.BLACK);
  lblNewLabel.setBounds(38, 41, 74, 14);
  frmLogin.getContentPane().add(lblNewLabel);
  
  JLabel lblNewLabel_1 = new JLabel("Password :");
  lblNewLabel_1.setBounds(38, 72, 74, 14);
  frmLogin.getContentPane().add(lblNewLabel_1);
  
  JLabel lblNewLabel_2 = new JLabel("         New User ?");
  lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
  lblNewLabel_2.setBounds(38, 132, 102, 14);
  frmLogin.getContentPane().add(lblNewLabel_2);
  
  JLabel lblNewLabel_3 = new JLabel("      Already have an account ?");
  lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
  lblNewLabel_3.setBounds(274, 132, 137, 14);
  frmLogin.getContentPane().add(lblNewLabel_3);
  
  JLabel lblNewLabel_4 = new JLabel("MUSIC QUIZ");
  lblNewLabel_4.setForeground(Color.BLUE);
  lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 17));
  lblNewLabel_4.setBounds(166, 0, 150, 27);
  frmLogin.getContentPane().add(lblNewLabel_4);
 }
}