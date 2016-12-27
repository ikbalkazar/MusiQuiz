import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class ForgotPasswordMenu {

 private JFrame frame;
 private JTextField textField;
 private JTextField textField_1;
/*
 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     ForgotPasswordMenu window = new ForgotPasswordMenu();
     window.frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }
*/
 /**
  * Create the application.
  */
 public ForgotPasswordMenu() {
  initialize();
 }

 /**
  * Initialize the contents of the frame.
  */
 private void initialize() {
  frame = new JFrame();
  frame.setBounds(100, 100, 450, 300);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.getContentPane().setLayout(null);
  
  JLabel lblUsername = new JLabel("Username :");
  lblUsername.setBounds(64, 63, 92, 14);
  frame.getContentPane().add(lblUsername);
  
  JLabel lblSecurityQuestion = new JLabel("Security Question :");
  lblSecurityQuestion.setBounds(64, 88, 125, 14);
  frame.getContentPane().add(lblSecurityQuestion);
  
  textField = new JTextField();
  textField.setBounds(194, 60, 176, 20);
  frame.getContentPane().add(textField);
  textField.setColumns(10);
  
  textField_1 = new JTextField();
  textField_1.setBounds(194, 139, 172, 20);
  frame.getContentPane().add(textField_1);
  textField_1.setColumns(10);
  
  JLabel lblNewLabel = new JLabel("Own question\r\n");
  lblNewLabel.setBounds(199, 88, 225, 14);
  frame.getContentPane().add(lblNewLabel);
  
  JLabel lblYourAnswer = new JLabel("Your Answer :");
  lblYourAnswer.setBounds(64, 142, 111, 14);
  frame.getContentPane().add(lblYourAnswer);
  
  JLabel lblYourPasswordIs = new JLabel("Your Password is :");
  lblYourPasswordIs.setBounds(64, 167, 111, 14);
  frame.getContentPane().add(lblYourPasswordIs);
  
  JLabel lblNewLabel_1 = new JLabel("the password\r\n");
  lblNewLabel_1.setBounds(194, 170, 211, 14);
  frame.getContentPane().add(lblNewLabel_1);
  
  JLabel lblForgotPassword = new JLabel("FORGOT PASSWORD");
  lblForgotPassword.setForeground(Color.BLUE);
  lblForgotPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
  lblForgotPassword.setBounds(140, 11, 217, 14);
  frame.getContentPane().add(lblForgotPassword);
  
  JButton btnBack = new JButton("Back");
  btnBack.setBounds(181, 227, 89, 23);
  frame.getContentPane().add(btnBack);
 }
}