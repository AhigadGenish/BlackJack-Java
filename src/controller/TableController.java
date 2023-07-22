package controller;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import model.DBManager;
import model.Player;
import model.Table;
import model.UserPlayer;
import view.PlayerComponent;
import view.TableWindow;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
// Controller ->
public class TableController{
	
	private Table table;
	protected List<TableWindow> obsrevers;
	
	public TableController(Table anyTable, List<TableWindow> tableGUIS) {
		
        this.table = anyTable;
        this.obsrevers = new ArrayList<TableWindow>();
        
        if(tableGUIS == null) {
        	return;
        
        }
        
        for(TableWindow anyWindow :  tableGUIS) {
	       
        	addWindow(anyWindow);
        }
    }
	
	// Add action listeners to the buttons in the TableGUI
	public void addWindow(TableWindow anyWindow) {
		
		this.obsrevers.add(anyWindow);
	   	anyWindow.addSeatButtonListener(new SeatButtonListener(anyWindow));
    	anyWindow.addBetButtonListener(new BetButtonListener(anyWindow));
    	anyWindow.addHitButtonListener(new HitButtonListener(anyWindow));
    	anyWindow.addStandButtonListener(new StandButtonListener(anyWindow));
    	anyWindow.addSplitButtonListener(new SplitButtonListener(anyWindow));
    	anyWindow.addDoubleButtonListener(new DoubleButtonListener(anyWindow));
    	anyWindow.addSurrenderButtonListener(new SurrenderButtonListener(anyWindow));
    	anyWindow.addCloseButtonListener(new CloseButtonListener(anyWindow));
    	
    	for(Player player : table.getPlayers()) {
    		
    		if(player != null) {
    			anyWindow.updatePlayersComponents(player);
    		}
    	}
    	
    	if(table.inRound() == true) {

    	  	anyWindow.updateDealerComponent(table.getDealer());
    	}
  
	}
	

	// Listener for the Bet button
    public class BetButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	
    	public BetButtonListener(TableWindow theWidnow) {
    		
    		this.theWindow = theWidnow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
           // Get the bet amount from the TableGUI
            Player currentPlayer = theWindow.getPlayer(); // Get the current player from the Table model
            if(currentPlayer.getSeatIndex() == -1) {
            	notifyToSpecificWindow("Please seat first", currentPlayer);
            	return;
            }
            table.placeBet(currentPlayer); // Place the bet in the Table model
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange(currentPlayer);
        }
    }

    // Listener for the Hit button
    public class HitButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	
    	public HitButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer || currentPlayer.getSeatIndex() == -1) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.hit(currentPlayer); // Player hits in the Table model
            theWindow.updateMessage(message);
            
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange(currentPlayer);
        }
    }

    // Listener for the Stand button
    public class StandButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	
    	public StandButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer || currentPlayer.getSeatIndex() == -1) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.stand(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange(currentPlayer);
        }
    }
    // Listener for the Split button
    public class SplitButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	
    	public SplitButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer || currentPlayer.getSeatIndex() == -1) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.split(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange(currentPlayer);
        }
    }
    // Listener for the Double button
    public class DoubleButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	
    	public DoubleButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
      
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            if(this.theWindow.getPlayer() != currentPlayer || currentPlayer.getSeatIndex() == -1) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.doubleDown(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange(currentPlayer);
        }
    }
    // Listener for the Surrender button
    public class SurrenderButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	
    	public SurrenderButtonListener(TableWindow theWindow) {
    		
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = table.getCurrentPlayer(); // Get the current player from the Table model
            
            if(this.theWindow.getPlayer() != currentPlayer || currentPlayer.getSeatIndex() == -1) {
            	theWindow.updateMessage("Its not your turn");
            	return;
            }
            
            String message = table.surrender(currentPlayer); // Player stands in the Table model
            theWindow.updateMessage(message);
            
            // Notify all players of the updated game state
            notifyPlayersOfGameStateChange(currentPlayer);
        }
    }
    // Listener for the Seat button
    public class SeatButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	private boolean isSeat = false;
    	
    	public SeatButtonListener(TableWindow theWindow) {
    		this.theWindow = theWindow;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            Player currentPlayer = theWindow.getPlayer(); // Get the current player from the Table model
            String message = "";

            if(this.isSeat == false) {
            	
            	
            	message = table.addPlayer(currentPlayer);
            	
            	// If seated
            	if(message.indexOf("Seated") != -1) {
            		subscribePlayerComponent(currentPlayer);
            		this.isSeat = true;
            	}
            	
            }
            else {
            	
            	this.isSeat = false;
            	int seatBefore = currentPlayer.getSeatIndex();
            	message = table.removePlayer(currentPlayer);
            	unSubscribePlayerComponent(currentPlayer, seatBefore);

            }
            
            theWindow.updateMessage(message);
 
        }

    }
    
    // Close Button Listener class
    public class CloseButtonListener implements ActionListener {
    	
    	private TableWindow theWindow;
    	private JButton seatOrUpButton;
    	
    	public WindowListener windowListener = new WindowAdapter(){
        
            @Override
            public void windowClosing(WindowEvent e) {
                // Handle the close event here
                performCloseAction();
            }

			private void performCloseAction() {
				
				// If seat
				if(theWindow.getPlayer().getSeatIndex() != -1) {
	
					// Handle DB that this player disconnect
					updateDisconnectInDB(theWindow.getPlayer());
					seatOrUpButton.doClick();
				}
			}

		

			
        };
        
    	public CloseButtonListener(TableWindow newTableWindow) {
    		this.theWindow = newTableWindow;
			this.seatOrUpButton = newTableWindow.getSeatButton();
		}
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			this.windowListener.windowClosing(null);
			
		}
    	
    }
    
    // Update disconnect
	private void updateDisconnectInDB(Player anyPlayer) {
		
		if(anyPlayer instanceof UserPlayer) {
			// Update disconnect and money
			DBManager.setconnectedToUser(((UserPlayer)anyPlayer).getUser(), false);
			DBManager.updateUserValues(((UserPlayer)anyPlayer).getUser());
		}
		
		
	}
	
    // Notify all players of the game state change
    private void notifyPlayersOfGameStateChange(Player anyPlayer) {
    	
    	 for(TableWindow anyWindow : obsrevers) {
    		
			 anyWindow.onPropertyChanged(anyPlayer);
			 anyWindow.revalidate();
			 anyWindow.repaint();
		 }
    }
	
	public void startGame()  {
	
		updateDealerLabel();
		this.table.afterBetting();
		
		if(this.table.anyPlayerBet() == true){
			
			
			this.createDealerComponent();
			this.updateDealerComponent();

		}
		
		
	 
        Thread playerThread = new Thread(() -> {
        	table.playersTurn();
   
        });

        playerThread.start();

        // Wait for the player's turn to complete before proceeding with the dealer's turn
        try {
        	// Join
			playerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(table.anyPlayerAlive() == true) {
        	
        	try {
              	// Sleep and Notify that dealer turn now
        		Thread.sleep(1000);
        		this.notifyMessageViaController("Dealer Turn");
        		
        		
      		} catch (InterruptedException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
        	
        	table.turnOfDealer();
        }
		
	
		
		table.updateMoneyOfPlayers();
	
		table.finishRound();
		
		try {
	        	// Sleep for 4 seconds
				Thread.sleep(4000);
			} 
		  catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		this.cleanAllTables();
	}
	
	private void updateDealerLabel() {
		
		for(TableWindow tableWindow : this.obsrevers) {
			tableWindow.updateDealerLabel(this.table);
		}
		
	}

	private void createDealerComponent() {
		
		for(TableWindow tableWindow : this.obsrevers) {
			tableWindow.createNewDealerComponent();
		}
		
	}

	private void cleanAllTables() {
		
	
		// Clean player components
		for(TableWindow windowTable : this.obsrevers) {

			windowTable.clearHands();

		}
		
		try {
	        	// Sleep for 1 second
				Thread.sleep(1000);
		
		} 
		catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		  
		// Clean Dealer component
		for(TableWindow windowTable : this.obsrevers) {
			windowTable.removeDealerComponent();
		}
		try {
	        	// Sleep for 1 second
				Thread.sleep(1000);
			} 
		  catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		this.notifyToTimerLabel("Waiting for bet");
	}

	public void updateDealerComponent() {
	
		for(TableWindow anyWindow : obsrevers) {
	
			 anyWindow.updateDealerComponent(this.table.getDealer());
		 }

		
		 
	}
	// Notify message to all windows
	public void notifyMessageViaController(String message) {
    	
    	for(TableWindow tableWindow : this.obsrevers) {
			 tableWindow.updateMessage(message);
		 }
    }
	// Notify message to specific window
	public void notifyToSpecificWindow(String message, Player anyPlayer) {
    	
    	for(TableWindow tableWindow : this.obsrevers) {
    		if(tableWindow.getPlayer() == anyPlayer) {
			  tableWindow.updateMessage(message);
    		}
		 }
    }
	// Notify message to Timer label
	public void notifyToTimerLabel(String message) {
		
		for(TableWindow tableWindow : this.obsrevers) {
    		tableWindow.getTimerLabel().setText(message);
    	}
		
	}
    
    // Update player label
	public void updatePlayerLabel(Player anyPlayer) {
		
		for(TableWindow tableWindow : this.obsrevers) {
    		if(tableWindow.getPlayer() == anyPlayer) {
			  tableWindow.updatePlayerLabel();
    		}
		 }
	}

    public void subscribePlayerComponent(Player anyPlayer) {
    	
    	int seat = anyPlayer.getSeatIndex();
    	
    	for(TableWindow tableWindow : this.obsrevers) {
    		
    		PlayerComponent anyPlayerComponent = tableWindow.getPlayersComponent().get(seat);
    		anyPlayerComponent.setSeat(true);
    		anyPlayerComponent.updateComponents(anyPlayer);
    		tableWindow.getTheLayeredPane().repaint();

    	}
    	
    }
    public void unSubscribePlayerComponent(Player anyPlayer,  int seatBefore) {
    	
    	 

    	for(TableWindow tableWindow : this.obsrevers) {
 
    		PlayerComponent anyPlayerComponent = tableWindow.getPlayersComponent().get(seatBefore);
    		tableWindow.clearPlayerComponent(anyPlayerComponent);
    		anyPlayerComponent.setSeat(false);
  
    		tableWindow.getTheLayeredPane().repaint();
    	}
    	  
    }
    
    public Table getTable() {
    	return this.table;
    }


}
