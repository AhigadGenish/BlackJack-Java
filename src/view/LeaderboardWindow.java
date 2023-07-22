package view;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import model.DBManager;

import java.awt.*;

// Leader Board Window
public class LeaderboardWindow extends JFrame {

    private static LeaderboardWindow instance;
    private JTextArea outputArea;
    private JButton totalWinsButton;
    private JButton totalProfitButton;
    private DBManager usersDB;

    private static String backgroundIcon = "resources/leaderBoard/leaderBoardImage.png";

    private LeaderboardWindow() {
        usersDB = new DBManager();
    }

    private void printTotalProfitLeaderBoard() {
    	
        String printStr = "            TOTAL PROFIT LEADER BOARD: \n";
        
        ArrayList<Map<String, String>> allUsers = usersDB.sortUsersByProfit();
        
        for (int i = 0; i < allUsers.size(); i++) {
            printStr += ((i+1) + ") Name: " + allUsers.get(i).get("Name"));
            printStr += (", Number of wins: " + allUsers.get(i).get("Number of wins"));
            printStr += (", Total profit: " + allUsers.get(i).get("Total profit"));
            printStr += "\n";
        }
        
        outputArea.setText(printStr);
    }

    private void printTotalWinsLeaderBoard() {
    	
        String printStr = "            TOTAL WINS LEADER BOARD: \n";
        ArrayList<Map<String, String>> allUsers = usersDB.sortUsersByWins();
        
        for (int i = 0; i < allUsers.size(); i++) {
            printStr += ((i+1) + ") Name: " + allUsers.get(i).get("Name"));
            printStr += (", Number of wins: " + allUsers.get(i).get("Number of wins"));
            printStr += (", Total profit: " + allUsers.get(i).get("Total profit"));
            printStr += "\n";
        }
        outputArea.setText(printStr);
    }

    public static LeaderboardWindow getInstance() {
    	
        if (instance == null) {
            instance = new LeaderboardWindow();
        }
        initialize();
        return instance;
    }

    private static void initialize() {
        instance.setTitle("Leaderboard");
        instance.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instance.setPreferredSize(new Dimension(800, 600));

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon icon = new ImageIcon(backgroundIcon);
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null); // Set layout manager to null for manual positioning
        instance.setContentPane(mainPanel);

        // Create the output area on the right side
        instance.outputArea = new TransparentTextArea();
        instance.outputArea.setEditable(false);
        instance.outputArea.setBounds(200, 150, 600, 230);
        instance.outputArea.setForeground(Color.WHITE); // Set text color to white
        mainPanel.add(instance.outputArea);

        // Create buttons for total wins and total profit leaderboards
        instance.totalWinsButton = new JButton("<html>Show total wins<br>leader board</html>");
        instance.totalWinsButton.setBounds(125, 50, 150, 50);
        mainPanel.add(instance.totalWinsButton);

        instance.totalProfitButton = new JButton("<html>Show total profit<br>leader board</html>");
        instance.totalProfitButton.setBounds(500, 50, 150, 50);
        mainPanel.add(instance.totalProfitButton);

        // Add action listeners to the buttons
        instance.totalWinsButton.addActionListener(e -> {
            instance.printTotalWinsLeaderBoard();
        });
        
        instance.totalProfitButton.addActionListener(e -> {
            instance.printTotalProfitLeaderBoard();
        });
        
        instance.pack();
        instance.setVisible(true);
    }
}

