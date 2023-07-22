package view;

import java.util.regex.Pattern;
import java.awt.*;
import javax.swing.*;

import controller.*;
import model.*;

// Main Window
public class MainWindow extends JFrame {
	
	// Data Members
    private static final String backgroundIcon = "resources/mainWindow/blackJackMain.png";
	private static final SoundPlayer roomSoundPlayer = new SoundPlayer("resources/sounds/casinoAudienceSound.wav");
    
	// Constructor
    public MainWindow() {
    	
    	DBManager.resetConnectedToFalse();
    	
        setTitle("Blackjack main menu");
        
        // Initialize Tables:
        
        Table oneHundredNewTable = OneHundredDollarTable.getInstance();
        Table fiftynewTable = FiftyDollarTable.getInstance();
        Table fiveNewTable = FiveDollarTable.getInstance();
        
        
        TableController nTcOneHundred = new TableController(oneHundredNewTable, null);
        TableController nTFifty = new TableController(fiftynewTable, null);
        TableController nTcFive = new TableController(fiveNewTable, null);
        
        oneHundredNewTable.setTableController(nTcOneHundred);
        fiftynewTable.setTableController(nTFifty);
        fiveNewTable.setTableController(nTcFive);
        
        
        
        
        // Fronted:
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));

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
        add(mainPanel);

        // Create buttons for sign up, log in, play as guest, and leader board
        JButton signUpButton = new JButton("Sign up");
        JButton logInButton = new JButton("Log in");
        JButton playAsGuestButton = new JButton("Play as guest");
        JButton leaderboardButton = new JButton("Leader board");
		JButton muteButton = new JButton("Play Or Pause Music");

        // Set the location of the buttons using setBounds
        int buffer = 380;
        signUpButton.setBounds(buffer, 500, 120, 30);
        logInButton.setBounds(buffer + 150, 500, 120, 30);
        playAsGuestButton.setBounds(buffer + 300, 500, 120, 30);
        leaderboardButton.setBounds(buffer + 150, 600, 120, 30);
		muteButton.setBounds(150, 100 , 160, 30);

        // Add buttons to the mainPanel
        mainPanel.add(signUpButton);
        mainPanel.add(logInButton);
        mainPanel.add(playAsGuestButton);
        mainPanel.add(leaderboardButton);
		mainPanel.add(muteButton);
        
    	
        // Add ActionListener to signUpButton
        signUpButton.addActionListener(e -> {
            signUp(nTcOneHundred, nTFifty, nTcFive); // Call your desired function for sign up
        });

        // Add ActionListener to logInButton
        logInButton.addActionListener(e -> {
            logIn(nTcOneHundred, nTFifty, nTcFive); // Call your desired function for log in
        });

        // Add ActionListener to playAsGuestButton
        playAsGuestButton.addActionListener(e -> {
            playAsGuest(nTcOneHundred, nTFifty, nTcFive); // Call your desired function for playing as guest
        });

        // Add ActionListener to leaderboardButton
        leaderboardButton.addActionListener(e -> {
        	leaderboard(); // Call your desired function for displaying leaderboard
        });

		// Add ActionListener to muteButton
		muteButton.addActionListener(e -> {
			
			roomSoundPlayer.playOrMute();
				});

        pack();
        setVisible(true);
        

        
    }
    
    public static boolean validPassword(String password) {
        // Password regex: length >= 5
        String regex = ".{5,}";
        return Pattern.matches(regex, password);
    }
    
    public static boolean validName(String name) {
        // Name regex: length >= 5, contains only letters and numbers
        String regex = "[a-zA-Z0-9]{5,}";
        return Pattern.matches(regex, name);
    }

	private void signUp(TableController nTcOneHundred,TableController nTFifty,TableController nTcFive) {
	    // Create a panel to hold the sign-up form components
	    JPanel signUpPanel = new JPanel();
	    signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));

	    // Create labels and text fields for name and password
	    JLabel nameLabel = new JLabel("Name:");
	    JTextField nameTextField = new JTextField(20);

	    JLabel passwordLabel = new JLabel("Password:");
	    JPasswordField passwordField = new JPasswordField(20);

	    // Add the components to the sign-up panel
	    signUpPanel.add(nameLabel);
	    signUpPanel.add(nameTextField);
	    signUpPanel.add(passwordLabel);
	    signUpPanel.add(passwordField);

	    // Show the sign-up panel and handle the button press
	    int result = JOptionPane.showConfirmDialog(this, signUpPanel, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    // If the OK button is pressed
	    if (result == JOptionPane.OK_OPTION) {
	    	
	        String name = nameTextField.getText();
	        String password = new String(passwordField.getPassword());

	        // Validate the name and password using existing functions
	        boolean isNameValid = validName(name);
	        boolean isPasswordValid = validPassword(password);

	        if (!isNameValid || !isPasswordValid) {
	        	
	            JOptionPane.showMessageDialog(this, "Invalid name or password! \n -Name must be at least 5 characters long and contain only letters and numbers, \n -Password must be at least 5 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            // Name and password are valid, save them to variables or process them further
	            // For example, you can save them as instance variables in the MainWindow class
	        	DBManager userDB = new DBManager();
	            if( userDB.isUserInDB(name) == true ) {
	            	
	            	 JOptionPane.showMessageDialog(this, "This user name already exist, please enter diffarent name ","Error", JOptionPane.ERROR_MESSAGE);
	            }
	            else {
	            	UserPlayer newPlayer = new UserPlayer(userDB.addNewUserToDB(name, password));
	            	Thread thread = new Thread(() -> {
	            	    LobbyWindow guestLobby = new LobbyWindow(newPlayer, nTcOneHundred, nTFifty, nTcFive);
	            	    // Any other code you want to execute in the thread
	            	});

	            	thread.start();  // Start the thread
	            }
	            
	        }
	    }
    
	}

	private void logIn(TableController nTcOneHundred,TableController nTFifty,TableController nTcFive) {
		
	    // Create a panel to hold the login form components
	    JPanel loginPanel = new JPanel();
	    loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

	    // Create labels and text fields for name and password
	    JLabel nameLabel = new JLabel("Name:");
	    JTextField nameTextField = new JTextField(20);

	    JLabel passwordLabel = new JLabel("Password:");
	    JPasswordField passwordField = new JPasswordField(20);

	    // Add the components to the login panel
	    loginPanel.add(nameLabel);
	    loginPanel.add(nameTextField);
	    loginPanel.add(passwordLabel);
	    loginPanel.add(passwordField);

	    // Show the login panel and handle the button press
	    int result = JOptionPane.showConfirmDialog(this, loginPanel, "Log In", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    // If the OK button is pressed
	    if (result == JOptionPane.OK_OPTION) {
	        String name = nameTextField.getText();
	        String password = new String(passwordField.getPassword());

	        // Check if the user exists in the userDB using isExistUser method
	        DBManager userDB = new DBManager();
	        boolean isUserExist = userDB.isUserInDB(name);
	    
	        if (userDB.validUserPassword(name, password) == false) {
	            JOptionPane.showMessageDialog(this, "Invalid user name or password", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        else if( userDB.getUserconnected(name) == true){
	        	
	        	JOptionPane.showMessageDialog(this, "user is already connected", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        else {
	            // User exists, proceed with the login
	        	User logInUser = userDB.getUserFromDB(name, password);
	            UserPlayer loggedInPlayer = new UserPlayer(logInUser);
	            
	        	Thread thread = new Thread(() -> {
	        		LobbyWindow loggedInLobby = new LobbyWindow(loggedInPlayer, nTcOneHundred, nTFifty, nTcFive);
	        	    
	        	});

	        	thread.start();  // Start the thread
	        }
	    }
	}

	private void playAsGuest(TableController nTcOneHundred,TableController nTFifty,TableController nTcFive) {
		
		GuestPlayer newGuestPlayer = new GuestPlayer();
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
    		LobbyWindow guestLobby = new LobbyWindow(newGuestPlayer,nTcOneHundred,nTFifty,nTcFive);
            }
        });

	}

	private void leaderboard() {
		LeaderboardWindow newLeaderboardWindow = LeaderboardWindow.getInstance();
	}
}
