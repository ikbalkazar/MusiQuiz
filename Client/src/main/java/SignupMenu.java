
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
public class SignupMenu extends JPanel implements ActionListener{
    public JLabel UserName,Password,ConfirmPassword,Question,Answer;
    public JTextField Name,QuestionField,AnswerField;
    public JButton backButton,createAccountButton;
    public JPasswordField PasswordField,ConfirmPasswordField;
    public SignupMenu(){
      setSize(Main.WIDTH, Main.HEIGHT);
      setLayout(null);

      UserName=new JLabel("Username:");
      Password=new JLabel("Password:");
      ConfirmPassword=new JLabel("Confirm Password:");
      Question=new JLabel("Write your own security question:");
      Answer=new JLabel("Your answer:");
      
      backButton=new JButton("Back");
      createAccountButton=new JButton("Create Account");
      
      Name=new JTextField();
      PasswordField=new JPasswordField(30);
      ConfirmPasswordField=new JPasswordField(30);
      QuestionField=new JTextField();
      AnswerField=new JTextField();
      
      UserName.setBounds(60,30,120,30);
      Password.setBounds(60,70,120,30);
      ConfirmPassword.setBounds(60,110,120,30);
      Question.setBounds(60,150,220,30);
      Answer.setBounds(60,190,120,30);
      
      Name.setBounds(270,30,130,30);
      PasswordField.setBounds(270,70,130,30);
      ConfirmPasswordField.setBounds(270,110,130,30);
      QuestionField.setBounds(270,150,130,30);
      AnswerField.setBounds(270,190,130,30);
      
      backButton.setBounds(60,250,130,50);
      backButton.addActionListener(this);
      createAccountButton.setBounds(330,250,130,50);
      createAccountButton.addActionListener(this);
      PasswordField.addActionListener(this);
      ConfirmPasswordField.addActionListener(this);

      backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Main.goToPanel("LoginMenu");
        }
      });

      add(backButton);
      add(createAccountButton);
      add(Name);
      add(PasswordField);
      add(ConfirmPasswordField);
      add(QuestionField);
      add(AnswerField);
      add(UserName);
      add(Password);
      add(ConfirmPassword);
      add(Question);
      add(Answer);
    
  }
  //ERROR !!!!!!!!!!!!!!!!!!
    public void actionPerformed(ActionEvent e){
    if(e.getSource()==createAccountButton){
      char[] password = PasswordField.getPassword();
      char[] confirmPassword = ConfirmPasswordField.getPassword();
      String Text1=new String(password);
      String Text2=new String(confirmPassword);
      System.out.println(PasswordField.getPassword());
      System.out.println(ConfirmPasswordField.getPassword());
      if(Text1.equals(Text2)){
        final User newUser = new User(Name.getText(), Text1, QuestionField.getText(), AnswerField.getText());
        Network.registerUser(newUser, new Network.RegisterCompletion() {
          public void whenCompleted(boolean success) {
            if (success) {
              JOptionPane.showMessageDialog(null, "You're signed up");
              Main.loginWith(newUser);
            } else {
              JOptionPane.showMessageDialog(null, "Password does not match");
            }
          }

          public void whenError(String error) {
            JOptionPane.showMessageDialog(null, "Password does not match");
          }
        });

      }
      else {
        JOptionPane.showMessageDialog(null, "Password does not match");
      }
  }
   
}
}
