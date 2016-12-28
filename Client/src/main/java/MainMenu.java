/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import sun.security.jgss.spnego.NegTokenInit;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static java.lang.Thread.sleep;


/**
 *
 * @author Şamil
 */
public class MainMenu extends JPanel {
    
    JButton refresh;
    JPanel friends;
    JPanel waitingChallenges;
    JPanel matchHistory;
    JPanel activeGames;
    JComboBox friendsCombo;
    JLabel selectedFriend;
    String[] friendNames;
    JButton deleteFriend;
    JButton challengeFriend;
    JTextArea addFriendText;
    JButton addFriend;
    JComboBox activeGamesBox;
    JButton acceptChallenge;
    JButton rejectChallenge;
    JComboBox waitingChallengesBox;
    JButton startGame;
    JTable matchHistoryTable;
    JScrollPane pane;
    int friendCount;
    ArrayList<Challenge> actives, waitings, finishedOnes;
    
    
    public void createFriendsPanel(){
        
        friends = new JPanel();
        friends.setPreferredSize(new Dimension( 300 , 200 ));
        
        friends.setLayout(null);
        
        JLabel panelName = new JLabel("FRIENDS");
        
        panelName.setBounds(130,-30,100,100);
        
        friends.add( panelName );

        friendsCombo = new JComboBox( friendNames );
        friendsCombo.addActionListener (new ComboListener());
        
        friendsCombo.setBounds( 50,50,200,30 );
        
        selectedFriend = new JLabel("");
        
        deleteFriend = new JButton("Delete");
        challengeFriend = new JButton("ChallengeFriend");

        challengeFriend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String friend = friendNames[friendsCombo.getSelectedIndex()];
                Network.createChallenge(Main.user.getUsername(), friend);
            }
        });

        deleteFriend.addActionListener(new DeleteListener());
        
        deleteFriend.setBounds(20,90,120,40);
        challengeFriend.setBounds(150,90,130,40);
        
        deleteFriend.setEnabled(false);
        challengeFriend.setEnabled(false);
        
        addFriendText = new JTextArea("Enter Username");
        addFriend = new JButton("Add Friend");
        addFriend.addActionListener( new AddListener() );
        addFriendText.setBounds( 30, 150 , 130 , 20 );
        addFriend.setBounds( 170 , 150 , 100 ,20);
        
        friends.add( friendsCombo );
        friends.add( selectedFriend );
        friends.add( deleteFriend );
        friends.add( challengeFriend );
        friends.add( addFriendText );
        friends.add( addFriend );
        
        
        
    }

    private String didWin(Challenge challenge) {
        if (challenge.getSenderScore() == challenge.getReceiverScore()) {
            return "TIE";
        } else {
            String winner;
            if (challenge.getReceiverScore() > challenge.getSenderScore()) {
                winner = challenge.getReceiver();
            } else {
                winner = challenge.getSender();
            }

            if (winner.equals(Main.user.getUsername())) {
                return "WIN";
            } else {
                return "LOSE";
            }
        }
    }

    public void createMatchHistoryPanel(ArrayList<Challenge> challenges){
        matchHistory = new JPanel();
        matchHistory.setPreferredSize(new Dimension( 300 , 200 ));
        
        JLabel panelName = new JLabel("MATCH HISTORY");
        
        panelName.setBounds( 90 , -30 , 120 , 50 );
        
        String[] columnNames = {"Opponent" , "Date" , "Status"};
        
        String[][] data = new String[challenges.size()][3];
        for (int i = 0; i < challenges.size(); i++) {
            data[i] = new String[3];
            data[i][0] = challenges.get(i).getSender();
            if (data[i][0].equals(Main.user.getUsername())) {
                data[i][0] = challenges.get(i).getReceiver();
            }
            data[i][1] = challenges.get(i).getCreatedAt();
            data[i][2] = didWin(challenges.get(i));
        }
        
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        
        matchHistoryTable = new JTable( model );
        matchHistoryTable.setEnabled(false);
        
        
        
        
        matchHistoryTable.setPreferredScrollableViewportSize(new Dimension(250,63));
        matchHistoryTable.setFillsViewportHeight(true);
        
        pane = new JScrollPane( matchHistoryTable );
        pane.setVisible(true);
        
        //pane.setViewportView( matchHistoryTable );
        //pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        
        matchHistory.add(panelName);
       
        matchHistory.add( pane );
        
    }
    
    public void createActiveGamesPanel(final ArrayList<Challenge> challenges){
        activeGames = new JPanel();
        activeGames.setPreferredSize(new Dimension( 300 , 200 ));        
        
        activeGames.setLayout(null);
        
        JLabel panelName = new JLabel("ACTIVE GAMES");
        panelName.setBounds(120,-30,100,100);

        String[] chNames = new String[challenges.size() + 1];
        chNames[0] = "Select a challenge...";
        for (int i = 0; i < challenges.size(); i++) {
            String other = challenges.get(i).getSender();
            if (other.equals(Main.user.getUsername())) {
                other = challenges.get(i).getReceiver();
            }
            chNames[i + 1] = other;
        }
        activeGamesBox = new JComboBox( chNames );
        
        activeGamesBox.setBounds( 40 , 60 , 220 , 40 );
        activeGamesBox.addActionListener(new ActiveGamesListener());
        
        startGame = new JButton("Start");
        startGame.setEnabled(false);
        startGame.setBounds( 100 , 120 , 100 , 40 );

        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int chosen = activeGamesBox.getSelectedIndex() - 1;
                final String challengeId = challenges.get(chosen).getId();
                Network.getQuestions(challengeId, new Network.QuestionCompletion() {
                    public void whenCompleted(Question[] questions) {
                        Main.startGame(challengeId, questions);
                    }

                    public void whenError(String error) {

                    }
                });
            }
        });
        
        activeGames.add( panelName );
        activeGames.add( activeGamesBox );
        activeGames.add( startGame );
        
    }
    
    public void createWaitingChallengesPanel(final ArrayList<Challenge> challenges){
        waitingChallenges = new JPanel();
        waitingChallenges.setPreferredSize(new Dimension( 300 , 200 ));
        
        waitingChallenges.setLayout(null);
        
        JLabel panelName = new JLabel("WAITING CHALLENGES");
        panelName.setBounds(90,-30,140,100);

        final String[] challengeNames = new String[challenges.size() + 1];
        challengeNames[0] = "Select a challenge...";
        for (int i = 0; i < challenges.size(); i++) {
            challengeNames[i + 1] = challenges.get(i).getSender();
        }

        waitingChallengesBox = new JComboBox( challengeNames );
        
        
        waitingChallengesBox.setBounds( 40 , 60 , 220 , 40 );
        waitingChallengesBox.addActionListener(new ChallengeListener());
    
        acceptChallenge = new JButton("Accept");
        rejectChallenge = new JButton("Reject");
        
        acceptChallenge.setEnabled(false);
        rejectChallenge.setEnabled(false);
        
        acceptChallenge.setBounds( 50 , 120 , 90 , 40 );
        rejectChallenge.setBounds( 160 , 120 , 90 , 40 );
        
        waitingChallenges.add( panelName );
        waitingChallenges.add( waitingChallengesBox );
        waitingChallenges.add( acceptChallenge );
        waitingChallenges.add( rejectChallenge );

        acceptChallenge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int chosen = waitingChallengesBox.getSelectedIndex() - 1;
                String challengeId = challenges.get(chosen).getId();
                Network.acceptChallenge(Main.user.getUsername(), challengeId);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                          Thread.sleep(1000);
                        } catch (Exception e) {

                        }
                        refresh();
                    }
                });
            }
        });

        JButton refresh = new JButton("Refresh");
        refresh.setBounds( 220 , 0 , 80 , 20);
        refresh.addActionListener( new refreshListener() );
        waitingChallenges.add(refresh);
    }
    
    public MainMenu() {
        setBackground (Color.cyan);
        setPreferredSize( new Dimension( 700 , 400 ) );

        friendNames = new String[1];
        friendNames[0] = "Select a friend...";
        waitings = new ArrayList<Challenge>();
        actives = new ArrayList<Challenge>();
        finishedOnes = new ArrayList<Challenge>();

        refreshUI();
    }

    public void clearUI() {
        remove(friends);
        remove(waitingChallenges);
        remove(matchHistory);
        remove(activeGames);

        repaint();
        revalidate();
    }

    public void refreshUI() {
        createFriendsPanel();
        add( friends );
        createWaitingChallengesPanel(waitings);
        createMatchHistoryPanel(finishedOnes);
        createActiveGamesPanel(actives);
        add( waitingChallenges );
        add( matchHistory );
        add( activeGames );

        repaint();
        revalidate();
    }

    public void refresh() {
        if (Main.user == null) {
            return;
        }

        Network.fetchFriends(Main.user.getUsername(), new Network.FriendsCompletion() {
            public void whenCompleted(String[] friendNamesGot) {
                System.out.println("Got friends of " + Main.user.getUsername() + " => " + friendNamesGot.length);
                friendNames = new String[friendNamesGot.length + 1];
                friendNames[0] = "Select a friend...";
                for (int i = 0; i < friendNamesGot.length; i++) {
                    friendNames[i + 1] = friendNamesGot[i];
                }
                friendCount = friendNamesGot.length;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        clearUI();
                        refreshUI();
                    }
                });
            }

            public void whenError(String error) {

            }
        });

        Network.getChallenges(Main.user.getUsername(), new Network.ChallengeCompletion() {
            public void whenCompleted(Challenge[] challenges) {
                waitings.clear();
                actives.clear();
                finishedOnes.clear();
                for (int i = 0; i < challenges.length; i++) {
                    if (challenges[i].getStatus() == 0 && !challenges[i].getSender().equals(Main.user.getUsername())) {
                        waitings.add(challenges[i]);
                    } else if (challenges[i].getStatus() == 1) {
                        actives.add(challenges[i]);
                    } else if (challenges[i].getStatus() == 2) {
                        finishedOnes.add(challenges[i]);
                    }
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        clearUI();
                        refreshUI();
                    }
                });
            }

            public void whenError(String error) {

            }
        });
    }

    private class refreshListener implements ActionListener{

        public void actionPerformed ( ActionEvent event ){
            refresh();
        }

    }
    
    private class ComboListener implements ActionListener{
      public void actionPerformed (ActionEvent event)
      {
         selectedFriend.setText( friendNames[friendsCombo.getSelectedIndex()] );

         if ( friendsCombo.getSelectedIndex() != 0 ){
            deleteFriend.setEnabled(true);
            challengeFriend.setEnabled(true);
         }else{
            deleteFriend.setEnabled(false);
            challengeFriend.setEnabled(false);   
         }
      }
    }
    
    private class ChallengeListener implements ActionListener{
    
        public void actionPerformed( ActionEvent event ){
         
            if ( waitingChallengesBox.getSelectedIndex() != 0 ){
                acceptChallenge.setEnabled(true);
                rejectChallenge.setEnabled(true);
            } else {
                acceptChallenge.setEnabled(false);
                rejectChallenge.setEnabled(false);
            }
        }
    
    }
    
    private class ActiveGamesListener implements ActionListener{
    
        public void actionPerformed( ActionEvent event ){
         
            if ( activeGamesBox.getSelectedIndex() != 0 ){
                startGame.setEnabled(true);
            }else{
                startGame.setEnabled(false);
            }
        }
    
    }
    
    public class DeleteListener implements ActionListener{
     
        public void actionPerformed( ActionEvent event ){
            int index = friendsCombo.getSelectedIndex();
            Network.deleteFriend(Main.user.getUsername(), friendNames[index]);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    refresh();
                }
            });
        }
        
    }
    
    public class AddListener implements ActionListener{
     
        public void actionPerformed( ActionEvent event ){
            Network.addFriend(Main.user.getUsername(), addFriendText.getText());
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    refresh();
                }
            });
        }
        
    }
    
}
