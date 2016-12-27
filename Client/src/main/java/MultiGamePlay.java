import java.awt.Color;


import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MultiGamePlay extends JPanel{
	Thread thread;
	JLabel questionNum;
	JLabel remainingTime;
	JLabel question;
	JButton choiceButton;
	JButton exitButton;
	int remainingTimeInt;
	int questionNumInt = 1;
	int score;
	JLabel opponent;
	JLabel currentScore;
	String scoreDisplay;
	URL myURL;
	URLConnection myURLConnection;
	
	public MultiGamePlay(){
		thread = new Thread();
		try {
		    myURL = new URL("https://p.scdn.co/mp3-preview/0e590269a3b328d150f63dec29607461cc80c74b?cid=8897482848704f2a8f8d7c79726a70d4");
		    myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
		} 
		catch (MalformedURLException e) { 
		    // new URL() failed
		    // ...
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
		}
		this.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		this.setBackground(Color.CYAN);
		//gBC.fill = GridBagConstraints.HORIZONTAL;
		//questionNumInt = 1;
		remainingTimeInt = 60;
		
		questionNum = new JLabel( "Question : " + questionNumInt );
		gBC.weightx = 50;
        gBC.gridx = 10;
        gBC.gridy = 0;
        add(questionNum, gBC);
		
        remainingTime = new JLabel( "Remaining Time: " + remainingTimeInt + "\"");
        gBC.weightx = 50;
        gBC.gridx = 80;
        gBC.gridy = 0;
        add(remainingTime, gBC);
		
		question = new JLabel(" What is the name of the playing song? ");
		gBC.weightx = 50;
        gBC.gridx = 20;
        gBC.gridy = 20;
		add(question, gBC);
		
		MouseListener mouseListenerChoice = new ChoiceButtonSubclass();
		for(int i = 0; i < 4; i++){
			choiceButton = new JButton("choice" + (i+1));
			choiceButton.addMouseListener( mouseListenerChoice );
			gBC.weightx = 30;
	        gBC.gridx = 20;
	        gBC.gridy = 32+ i*10;
	        choiceButton.setBackground(Color.white);
	        choiceButton.setForeground(Color.green);
			add(choiceButton, gBC);
		}
		
		
		opponent = new JLabel("Opponent:" + "\n" +"FriendX");
		gBC.weightx = 5;
        gBC.gridx = 80;
        gBC.gridy = 42;
		add(opponent,gBC);
		
		if( questionNumInt == 1 )
			scoreDisplay = "0";
		else
			scoreDisplay = score + "/" + ( questionNumInt-1 );
		currentScore = new JLabel("Score: " + scoreDisplay );
		gBC.weightx = 5;
        gBC.gridx = 80;
        gBC.gridy = 62;
		add(currentScore,gBC);
		
		exitButton = new JButton("Exit");
		MouseListener mouseListenerExit = new ExitButtonSubclass();
		exitButton.addMouseListener( mouseListenerExit  );
		gBC.weightx = 5;
        gBC.gridx = 5;
        gBC.gridy = 100;
		add(exitButton, gBC);
		
	}
	long start = System.currentTimeMillis();;
	
	
	public void decreaseRemainingTime() throws InterruptedException{
		while(remainingTimeInt > 0){
			thread.sleep(1000);
			remainingTimeInt = remainingTimeInt - 1;
		}
		return;
	}

	public class ExitButtonSubclass extends JButton implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			Main.mainFrame.remove(this);
			Main.goToPanel(new MainMenu());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0){
		}
		
		
	}
	public class ChoiceButtonSubclass extends JButton implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			if(mouseEvent.getComponent().getBackground() == Color.BLUE)
				mouseEvent.getComponent().setBackground(Color.white);
			else
			mouseEvent.getComponent().setBackground(Color.BLUE);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			return;
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			return;
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			return;
		}

		@Override
		public void mouseReleased(MouseEvent arg0){
			return;
		}	
	}
}