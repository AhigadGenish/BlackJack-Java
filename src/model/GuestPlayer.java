package model;

import java.util.*;

public class GuestPlayer extends Player {

	// Data Members
	private final String GUEST = "Guest";
	private String guestName;
	private int guestMoney;
	private static Integer numberOfGuests = 0;
	
	// Constructor
	public GuestPlayer() {
		
		this.guestMoney = 1000;
		numberOfGuests += 1;
		this.guestName = GUEST + numberOfGuests.toString();
		
	}

	// Getters
	public String getGuestName() {
		return guestName;
	}
	
	@Override
	public int getTotalMoney() {
		return this.guestMoney;
	}
	
	public static int getNumberOfGuests() {
		return numberOfGuests;
	}
	
	// Setters

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	@Override
	public void setTotalMoney(int newAmount) {
		this.guestMoney = newAmount;
		
	}

	
	public static void setNumberOfGuests(int numberOfGuests) {
		GuestPlayer.numberOfGuests = numberOfGuests;
	}


	@Override
	public String seat(List<Player> places) {
		
		for(int i = 0; i < places.size(); i++) {
			
			if(places.get(i) == null) {
				
				// Vacant, so seat
				places.set(i, this);
				this.seatIndex = i;
				return "Seated at " + (i + 1) + " Place";
			}
		}
		
		return "No Available Seats";
	}

	@Override
	public String getPlayerName() {
		
		return this.getGuestName();
	}




}
