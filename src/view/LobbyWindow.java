package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import controller.TableController;
import model.DBManager;
import model.Player;
import model.User;
import model.UserPlayer;


public class LobbyWindow extends JFrame{

    // Data Members
    private final Player thePlayer;
    private final TableController tcOneHundred;
    private final TableController tcFifty;
    private final TableController tcFive;
	private JPanel panel;

    private static final String TABLESTRING100 = "100$ Table";
    private static final String TABLESTRING50 = "50$ Table";
    private static final String TABLESTRING5 = "5$ Table";
    private JLabel playerNameField;
    private final SoundPlayer chipSoundPlayer = new SoundPlayer("resources/sounds/chipsBuySound.wav");
    private final SoundPlayer seatSoundPlayer = new SoundPlayer("resources/sounds/seatSound.wav");

    // Constructor
    public LobbyWindow(Player newPlayer, TableController nTcOneHundred,TableController nTcFifty,TableController nTcFive) {
    	
        this.thePlayer = newPlayer;
        this.tcOneHundred = nTcOneHundred;
        this.tcFifty = nTcFifty;
        this.tcFive = nTcFive;
        this.createAndShowGUI();
        
        // Update User is connected in DB
        updateUserConnectInDB(true);
    }


    // Update user connect in DB
    private void updateUserConnectInDB(boolean connect) {
    	
         if (thePlayer instanceof UserPlayer) {
 			
 			User user = ((UserPlayer)thePlayer).getUser();
 			DBManager.setconnectedToUser(user, connect);
 		}
	}


    public void createAndShowGUI() {
    	
        setTitle("Lobby Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the panel with a BorderLayout
        // Load and draw the background image
        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon backgroundIcon = new ImageIcon("resources/lobbyWindow/lobbyBG.png");
                Image image = backgroundIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        this.panel.setLayout(new BorderLayout());

        // Create a label for the player's name and total money
        playerNameField = new JLabel("Name: " + thePlayer.getPlayerName() + " | Money: " + thePlayer.getTotalMoney() + "$");
        playerNameField.setForeground(Color.BLACK);

//        playerNameField.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameField.setFont(new Font("Arial", Font.BOLD, 14));
        this.panel.add(playerNameField, BorderLayout.PAGE_START);

        // Add components to the panel
        this.panel.add(createTableButtonsPanel(), BorderLayout.CENTER);
        this.panel.add(createActionButtonPanel(), BorderLayout.SOUTH);

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Handle the close event here
                updateUserConnectInDB(false);
            }
        };
        this.addWindowListener(windowListener);

        // Set the panel as the content pane of the frame
        this.setContentPane(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private JPanel createTableButtonsPanel() {
    	
        JPanel tableButtonsPanel = new JPanel();
        tableButtonsPanel.setOpaque(false);
        tableButtonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        tableButtonsPanel.add(createTableButton(TABLESTRING100));
        tableButtonsPanel.add(createTableButton(TABLESTRING50));
        tableButtonsPanel.add(createTableButton(TABLESTRING5));
        
        return tableButtonsPanel;
    }

    private JButton createTableButton(String buttonText) {

        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.GRAY);
        button.addActionListener(e -> {

            // Handle button click event
            TableWindow nTw = new TableWindow(thePlayer);
            switch (buttonText) {
                case TABLESTRING100 -> {
                    tcOneHundred.addWindow(nTw);
                    tcOneHundred.getTable().setTableController(tcOneHundred);
                }
                case TABLESTRING50 -> {
                    tcFifty.addWindow(nTw);
                    tcFifty.getTable().setTableController(tcFifty);
                }
                case TABLESTRING5 -> {
                    tcFive.addWindow(nTw);
                    tcFive.getTable().setTableController(tcFive);
                }
            }
            setVisible(false);
            seatSoundPlayer.play();
        });
        return button;
    }

    private JPanel createActionButtonPanel() {
    	
        JPanel actionButtonPanel = new JPanel();
        actionButtonPanel.setOpaque(false);
        actionButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        actionButtonPanel.add(createActionButton());
        return actionButtonPanel;
    }

    private JButton createActionButton() {
    	
        JButton button = new JButton("Add Money");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(131, 34, 34));
        button.addActionListener(e -> {
        	
           // Handle button click event

            if (thePlayer instanceof UserPlayer) {
                addMoneyDialog();
            } else {
                JOptionPane.showMessageDialog(null, "Guest players cannot add money :(");
            }
        });
        return button;
    }
    public void addMoneyDialog() {
    	
        // Create a panel to hold the slider and label
        JPanel addMoneyPanel = new JPanel();
        addMoneyPanel.setLayout(new BoxLayout(addMoneyPanel, BoxLayout.Y_AXIS));

        // Create a slider for selecting the amount to add
        JSlider amountSlider = new JSlider(JSlider.HORIZONTAL, 0, 10000, 0);
        amountSlider.setMajorTickSpacing(1000);
        amountSlider.setMinorTickSpacing(50);
       // amountSlider.setPaintTicks(true);
        //amountSlider.setPaintLabels(true);

        // Set the UI of the slider to use a custom knob image
        ImageIcon knobIcon = new ImageIcon("resources/lobbyWindow/chip.png");

        amountSlider.setUI(new CustomSliderUI(knobIcon));

        // Create a label to show the selected amount
        JLabel amountLabel = new JLabel("Amount: " + amountSlider.getValue());
        amountLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add a change listener to update the label when the slider value changes
        amountSlider.addChangeListener(e -> amountLabel.setText("Amount: " + amountSlider.getValue()));

        // Add the slider and label to the panel
        addMoneyPanel.add(amountSlider);
        addMoneyPanel.add(amountLabel);

        // Show the panel and handle the button press
        int result = JOptionPane.showConfirmDialog(this, addMoneyPanel, "Add Money", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the OK button is pressed
        if (result == JOptionPane.OK_OPTION) {
            // Update player money in DB and update the UI
            thePlayer.setTotalMoney(thePlayer.getTotalMoney() + (int)amountSlider.getValue());
            DBManager.updateUserValues(((UserPlayer) thePlayer).getUser());
            this.playerNameField.setText("Name: " + thePlayer.getPlayerName() + " | Money: " + thePlayer.getTotalMoney() + "$");
            this.playerNameField.repaint();
            
            if(amountSlider.getValue() > 0) {
            	chipSoundPlayer.play();
            }
        }
    }



}
