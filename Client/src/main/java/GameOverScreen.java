import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.*;

public class GameOverScreen extends JPanel {
	JLabel gameOver;
	JLabel score;
	JButton back;
	int finalScore;
	public GameOverScreen(){
		this.setBackground(Color.black);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		gBC.fill = GridBagConstraints.VERTICAL;
		
		gameOver = new JLabel("Game Over!");
		gBC.gridx = 20;
		gBC.gridy = 0;
		gameOver.setFont(new Font("Serif", Font.PLAIN, 100));
		gameOver.setForeground(Color.red);
		//gameOver.setSize(200,40);
		add(gameOver,gBC);
		
		score = new JLabel("Your Score:" + finalScore);
		gBC.gridx = 20;
		gBC.gridy = 60;
		score.setForeground(Color.green);
		score.setFont(new Font("Serif", Font.CENTER_BASELINE, 150));
		//score.setSize(400,80);
		add(score,gBC);
		
		back = new JButton("Back to Main Menu");
		gBC.gridx = 20;
		gBC.gridy = 85;
		add(back,gBC);
	}
}
