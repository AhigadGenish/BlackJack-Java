package model;

import java.util.*;

import javax.swing.JOptionPane;

public class UserPlayer extends Player{

	// Data Members
	private User user;
	
	public User getUser() {
		return this.user;
	}
	
	public UserPlayer(User newUser) {
		this.user = newUser;
	}

	@Override
	public void setTotalMoney(int newAmount) {
		this.user.setTotalAmount(newAmount);
		
	}

	
	@Override
	public int getTotalMoney() {
		return this.user.getTotalAmount();
	}



	@Override
	public String seat(List<Player> places) {
		
		// Ask for place:
		String userInput = JOptionPane.showInputDialog("Insert place between 1-4");
		if(userInput == null || userInput.isBlank() == true || userInput.matches("[0-9]*") == false) {
			JOptionPane.showMessageDialog(null, "Invalid input");
			return null;
		}
		
		int place = Integer.parseInt(userInput) - 1;
		
		if(place < 0 || place > 3) {
			JOptionPane.showMessageDialog(null, "Invalid input");
			return null;
		}
		
		if(places.get(place) != null) {
			return "Occupied";
		}
		// Vacant so seat
		places.set(place, this);
		this.seatIndex = place;
		return "Seated at " + (place + 1) + " Place";
	}

	@Override
	public String getPlayerName() {
		return this.user.getName();
	}
	


}
