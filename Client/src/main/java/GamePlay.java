
import com.sun.deploy.panel.ExceptionListDialog;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Şamil
 */
public class GamePlay extends JPanel {
    private String challengeId;
    private Question[] questionsArray;
    JLabel[] questionNo;
    JButton[] exitButton;
    JLabel[] timeLabel;
    JLabel[] questionLabel;
    JLabel[] scoreLabel;
    JLabel resultLabel;
    JButton[] button1,button2,button3,button4;
    JPanel[] panels;
    CardLayout L;
    int NO;
    int score;
    
    String[] questions;
    String[] choices;
    int[] correctChoices;
    int remainingTime;
    boolean[] answered;
    GamePlay myself;
    boolean exited;
    
    public void setQuestions(){
     
        questions = new String[10];
        choices = new String[4];
        correctChoices = new int[10];
        answered = new boolean[10];
        
        choices[0] = "choice0";
        choices[1] = "choice1";
        choices[2] = "choice2";
        choices[3] = "choice3";
        
        for ( int i=0 ; i<10 ; i++ ){
            questions[i] = "Question Text " + (i+1) + "......................... ?";
            correctChoices[i] = i%4;
            answered[i] = false;
        }
        
        NO = 0;
        score = 0;
        
    }
    
    
    public void waitSomeTime( int x ){
        for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(x);stop>System.nanoTime();) {}
    }
    
    public void play( int q , int time ) throws InterruptedException{
        
        if ( q == 10 ){
            setGameOverPanel();
            L.show( this , "Game Over" );
            repaint();
            revalidate();
            return;
        }
        
        if ( answered[q] == true )
            return;
        
        L.show( this, ""+q );
        repaint();
        revalidate();
        
        System.out.println("questionNumber : " + q + "      score : " + score +  "    time : " + time);
        
        panels[q].remove(timeLabel[q]);
        panels[q].remove(questionLabel[q]);
        panels[q].remove(scoreLabel[q]);
        
        timeLabel[q] = new JLabel("Remaining Time : " + time + "''");
        timeLabel[q].setFont(new Font("Serif", Font.PLAIN, 18));
        timeLabel[q].setBounds(450,30,200,30);
        
        scoreLabel[q] = new JLabel( "Your Score : " + score );
        scoreLabel[q].setFont(new Font("Serif", Font.PLAIN, 18));
        scoreLabel[q].setBounds(550,200,120,40);
        
        panels[q].add(timeLabel[q]);
        panels[q].add(questionLabel[q]);
        panels[q].add(scoreLabel[q]);
        
        panels[q].repaint();
        panels[q].revalidate();
        
        repaint();
        revalidate();
        
        if ( time > 0 && answered[q] == false ){
            
            waitSomeTime(1);
            
            play( q, time-1  );
        }
        
    }
    
    public void setGameOverPanel(){ 
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont( new Font("Serif", Font.PLAIN , 80) );
        gameOverLabel.setBounds( 150 , 50 , 400 , 100 );
        panels[10].add( gameOverLabel );

        JLabel finalScoreLabel = new JLabel("Your Score : " + score);
        finalScoreLabel.setFont( new Font("Serif", Font.PLAIN , 65) );
        finalScoreLabel.setBounds( 150 , 200 , 500 , 100 );
        panels[10].add( finalScoreLabel );
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300 , 310 , 100 , 40);
        panels[10].add( exitButton );

        exitButton.addActionListener(new ExitListener());
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            exited = true;
            Network.finishChallenge(Main.user.getUsername(), challengeId, score);
            Main.goToPanel("MainMenu");
        }
    }
    
    public GamePlay(String challengeId, Question[] questions){
        this.challengeId = challengeId;
        this.questionsArray = questions;

        setQuestions();
        setLayout( new CardLayout() );

        exited = false;
        panels = new JPanel[11];
        questionNo = new JLabel[10];
        timeLabel = new JLabel[10];
        questionLabel = new JLabel[10];
        scoreLabel = new JLabel[10];
        button1 = new JButton[10];
        button2 = new JButton[10];
        button3 = new JButton[10];
        button4 = new JButton[10];
        exitButton = new JButton[10];
        
        setVisible(true);
        
        for ( int i=0 ; i<10 ; i++ ){
         
            panels[i] = new JPanel();
            panels[i].setLayout(null);
            panels[i].setBackground (Color.cyan);
            panels[i].setPreferredSize( new Dimension( 700 , 400 ) );
            panels[i].setVisible(true);
                
            questionNo[i] = new JLabel("Question : " + (i+1));
            questionNo[i].setBounds(60,30,100,30);
            questionNo[i].setFont(new Font("Serif", Font.PLAIN, 18));
            
            timeLabel[i] = new JLabel("Remaining Time : " + 45 + "''");
            timeLabel[i].setFont(new Font("Serif", Font.PLAIN, 18));
            timeLabel[i].setBounds(450,30,200,30);
        
            questionLabel[i] = new JLabel( questions[i] );
            questionLabel[i].setFont(new Font("Serif", Font.PLAIN, 25));
            questionLabel[i].setBounds(150,80,400,40);
        
            scoreLabel[i] = new JLabel( "Your Score : " + score );
            scoreLabel[i].setFont(new Font("Serif", Font.PLAIN, 18));
            scoreLabel[i].setBounds(550,200,120,40);
        
            button1[i] = new JButton( choices[0] );
            button1[i].setFont(new Font("Serif", Font.PLAIN, 25));
            button1[i].setBounds( 200 , 150 , 300 , 40 );
        
            button2[i] = new JButton( choices[1] );
            button2[i].setFont(new Font("Serif", Font.PLAIN, 25));
            button2[i].setBounds( 200 , 200 , 300 , 40 );
        
            button3[i] = new JButton( choices[2] );
            button3[i].setFont(new Font("Serif", Font.PLAIN, 25));
            button3[i].setBounds( 200 , 250 , 300 , 40 );
        
            button4[i] = new JButton( choices[3] );
            button4[i].setFont(new Font("Serif", Font.PLAIN, 25));
            button4[i].setBounds( 200 , 300 , 300 , 40 );
        
            button1[i].addActionListener( new choiceListener() );
            button2[i].addActionListener( new choiceListener() );
            button3[i].addActionListener( new choiceListener() );
            button4[i].addActionListener( new choiceListener() );
        
            exitButton[i] = new JButton("Exit");
            exitButton[i].setFont(new Font("Serif", Font.PLAIN, 15));
            exitButton[i].setBounds( 30 , 350 , 80 , 30 );
            exitButton[i].addActionListener(new ExitListener());
            
            panels[i].add(questionNo[i]);
            panels[i].add(timeLabel[i]);
            panels[i].add(questionLabel[i]);
            panels[i].add(scoreLabel[i]);
            panels[i].add(button1[i]);
            panels[i].add(button2[i]);
            panels[i].add(button3[i]);
            panels[i].add(button4[i]);
            panels[i].add(exitButton[i]);
        
            add( panels[i] , "" + i);
            
        }
        
        
        panels[10] = new JPanel();
        panels[10].setLayout(null);
        panels[10].setBackground( Color.GREEN );
        
        add( panels[10] , "Game Over" );
        
        L = (CardLayout)this.getLayout();
        
        L.show(this,"0");
        
        myself = this;
        
    }
    
    public void addResult() throws InterruptedException{
     
        panels[NO].add(resultLabel);
        panels[NO].repaint();
        panels[NO].revalidate();
        
    }
    
    public void removeResult() throws InterruptedException{
    
        panels[NO].remove(resultLabel);
        panels[NO].repaint();
        panels[NO].revalidate();
        
    }
    
    public class choiceListener implements ActionListener{
        
        public void actionPerformed( ActionEvent e ){
            
            String answer = e.getActionCommand();
            
            answered[NO]=true;
            
            System.out.println("answered " + NO + " true");
            
            if ( answer.equals( "choice"+correctChoices[NO] ) ){
                
                System.out.println("Correct Answer!");
                
                resultLabel = new JLabel("Correct!");
                resultLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                resultLabel.setBounds(300 , 310 , 200 , 100 );
                score++;
            }else{
                
                System.out.println("Wrong Answer!");
                
                resultLabel = new JLabel("Wrong!!");
                resultLabel.setFont(new Font("Serif", Font.PLAIN, 30));
                resultLabel.setBounds(300 , 310 , 200 , 100 );
            }
            
            try {
                addResult();
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            NO++;
            
        }
    
    }
    
}
