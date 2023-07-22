package model;

import java.util.ArrayList;
import java.util.List;

// Dealer Object
public class Dealer {
	
	// Data Members
	private final String DEALER = "Dealer";
	private String dealerName;
	private Hand dealerHand;
	private volatile boolean dealerTurn = false;

			
	// Constructor
	public Dealer(int minimumBet) {
		
		this.dealerName = DEALER + " Of Table " + minimumBet + "$";
		this.dealerHand = new Hand(0);
	
	}
	
	// Getters
	public String getDealerName() {
		return this.dealerName;
	}
	
	public Card getFirstCard() {
		return this.dealerHand.getCards().get(0);
	}
	
	public Card getSecondCard() {
		return this.dealerHand.getCards().get(1);
	}
	
	public List<Card> getDealerCards(){
		return this.dealerHand.getCards();
	}

	public synchronized boolean isDealerTurn() {
		return this.dealerTurn;
	}
	
	public int getSumOfDealerCards() {
		
		return this.getDealerHand().getSumOfPlayingCards();
	}
	
	public Hand getDealerHand() {
		return dealerHand;
	}
	
	// Setters:
	public void setDealerHand(Hand newHand) {
		this.dealerHand = newHand;
	}
	
	public synchronized void setDealerTurn(boolean turn) {
		this.dealerTurn = turn;
	}
	
	

	
			
}
