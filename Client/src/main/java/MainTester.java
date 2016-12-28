/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Thread.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author Şamil
 */
public class MainTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        
        MainMenu MainMenuPanel = new MainMenu();
        GamePlay GamePlayPanel = new GamePlay("1", new Question[2]);
        
        frame.add(GamePlayPanel);
        frame.pack();
        frame.setVisible(true);
        
       
        for ( int i=0 ; i<11 ; i++  ){
            
            System.out.println("----------------------------------------------------\nNew Question\n---------------------------------------------");
            
            sleep(1000);
            
            GamePlayPanel.play(i,45);
        }
        
    }
    
}
