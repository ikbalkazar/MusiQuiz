import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import sun.awt.windows.ThemeReader;
import sun.nio.ch.Net;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ikbalkazar on 13/12/16.
 */
public class Main {
    public static User user = null;
    public static JFrame mainFrame;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 400;
    private static JPanel cards;

    public static MainMenu mainMenu;

    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cards = new JPanel(new CardLayout());

        cards.add(new SignupMenu(), "SignupMenu");
        cards.add(new ForgotPasswordMenu(), "ForgotPasswordMenu");
        cards.add(new LoginMenu(), "LoginMenu");

        mainFrame.getContentPane().add(cards);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setVisible(true);

        goToPanel("LoginMenu");
    }

    public static void loginWith(User user) {
        Main.user = user;
        mainMenu = new MainMenu();
        mainMenu.refresh();
        cards.add(mainMenu, "MainMenu");
        goToPanel("MainMenu");
    }

    public static void startGame(String challengeId, Question[] questions) {
        System.out.println("||| Starting Challenge.... " + challengeId + " : " + questions.length);
        GamePlay gamePlay = new GamePlay(challengeId, questions);
        cards.add(gamePlay, "GamePlay" + challengeId);
        goToPanel("GamePlay" + challengeId);
        for (int i = 0; i < 11; i++) {
            if (gamePlay.exited) {
                break;
            }
            try {
                if (i < 10) {
                    gamePlay.playSong(questions[i].getURL());
                }
                gamePlay.play(i, 30);
                gamePlay.mp3player.close();
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }
    }

    public static void goToPanel(final String panelIdentifier) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CardLayout cardLayout = (CardLayout)cards.getLayout();
                cardLayout.show(cards, panelIdentifier);
            }
        });
    }

    public static void testNetwork() {
        /*
        Network.getRequest("/user/joey", new Network.Completion() {
            public void whenCompleted(JSONObject jsonObject) {
                System.out.println(jsonObject);
            }
            public void whenError(String error) {
                System.err.println(error);
            }
        });

        Network.fetchUser("watson", new Network.UserCompletion() {
            public void whenCompleted(User user) {
                System.out.println("Username: " + user.getUsername());
                System.out.println("Password: " + user.getPassword());
            }

            public void whenError(String error) {
            }
        });

        Network.registerUser(new User("missme", "baker", "question?", "answer!!"), new Network.RegisterCompletion() {
            public void whenCompleted(boolean success) {
                System.err.println("Registration success: " + success);
            }
            public void whenError(String error) {
                System.err.println("ERROR: Registration error!!");
            }
        });

        Network.addFriend("watson", "joey");
        Network.addFriend("watson", "missme");

        Thread.sleep(1000);

        Network.deleteFriend("watson", "missme");

        Network.fetchFriends("watson", new Network.FriendsCompletion() {
            public void whenCompleted(String[] friends) {
                System.out.println("Got friends of watson");
                for (int i = 0; i < friends.length; i++) {
                    System.out.println(friends[i]);
                }
            }

            public void whenError(String error) {
                System.out.println("Friends fetch ERROR!!!!!....");
            }
        });

        Network.getQuestions(2, new Network.QuestionCompletion() {
            public void whenCompleted(Question[] questions) {
                System.out.println("Questions got!!");
                for (int i = 0; i < 10; i++) {
                    System.out.println("Question : " + questions[i].getURL());
                }
            }

            public void whenError(String error) {
            }
        });
*/
/*
        Network.createChallenge("watson", "joey");

        Thread.sleep(1000);

        Network.getChallenges("watson");
*/
        /*
        Network.acceptChallenge("joey", "0");

        Thread.sleep(1000);

        Network.finishChallenge("watson", "0", 7);
        Network.finishChallenge("joey", "0", 12);*/

        Network.getChallenges("watson", new Network.ChallengeCompletion() {
            public void whenCompleted(Challenge[] challenges) {
                System.out.println("Got challenges of watson");
                for (int i = 0; i < challenges.length; i++) {
                    System.out.println("Id: " + challenges[i].getId() + " CreatedAt: " + challenges[i].getCreatedAt());
                }
            }

            public void whenError(String error) {

            }
        });
    }
}
