package model;

import javax.swing.JOptionPane;

public class FirstChoiceState implements HandState{

	// Data Members
	private Player thePlayer;
	private Hand currentHand;
	
	// Constructor
	public FirstChoiceState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}

	// Helper method for check enough money for split or double
	private boolean enoughMoneyForSplitOrDouble() {
		
		return this.thePlayer.getTotalMoney() >= 2*this.currentHand.getBetMoney();
	}
	
	// Help method for update money after split or double
	private void updateTotalMoneyAfterSplitOrDouble() {
		// Update total money
		this.thePlayer.setTotalMoney(this.thePlayer.getTotalMoney() - this.currentHand.getBetMoney());
		
	}
	
	@Override
	public String split() {
		
		if(this.currentHand.canSplit() == false || enoughMoneyForSplitOrDouble() == false) {
			
			return "Can't split";
		}
		
		// Update total money
		updateTotalMoneyAfterSplitOrDouble();
		
		// Update Hands
		this.thePlayer.setHands(this.currentHand.createNewHandsAfterSplit());
		
		// Update States
		this.thePlayer.setAfterSplitHand1(new AfterSplitHandOneState(this.thePlayer, this.thePlayer.getHands().get(0)));
		this.thePlayer.setAfterSplitHand2(new AfterSplitHandTwoState(this.thePlayer, this.thePlayer.getHands().get(1)));
		
		// Check if BlackJack in any new hand
		if(this.thePlayer.getHands().get(0).hasBlackJack() == true) {
			this.thePlayer.setAfterSplitHand1(this.thePlayer.getAfterSplitHand2());
		}
	
		if(this.thePlayer.getHands().get(1).hasBlackJack() == true) {
			this.thePlayer.setAfterSplitHand2(new EndHandRoundState(this.thePlayer, this.thePlayer.getHands().get(1)));
		}
		
		// Update Player State to first hand playing
		this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand1());
		return "Splited";
	}

	@Override
	public String surrender() {
		
		// Update money
		this.thePlayer.setTotalMoney(this.thePlayer.getTotalMoney() + (this.currentHand.getBetMoney()/2));
		this.currentHand.setBetMoney(0);
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
		return "Surrender";
	}

	@Override
	public String stand() {
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
		return "Stand";
	}

	@Override
	public String hit() {
		
		if(this.currentHand.hasBlackJack() == true) {
			
			return "Can't hit";
		}
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// If done
		if(this.currentHand.getSumOfPlayingCards() > 21) {
			
				// Update State
				this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
				this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
				return "Busted";
				
		}
		else if (this.currentHand.getSumOfPlayingCards() == 21) {
			
			// Update State
			this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
			this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
			
		}
		else {
			// Update State
			this.thePlayer.setSecondChoice(new SecondChoiceState(this.thePlayer, this.currentHand));
			this.thePlayer.setTheState(this.thePlayer.getSecondChoice());
		}
		
		return "Hitted";
		
	}

	@Override
	public String doubleDown() {

		if(this.enoughMoneyForSplitOrDouble() == false) {
			return "Can't, No enough money for double";
		}
		
		// Update total money
		this.updateTotalMoneyAfterSplitOrDouble();
		this.currentHand.setBetMoney(2 * this.currentHand.getBetMoney());
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// Update State
		this.thePlayer.setEndOfRoundHand(new EndHandRoundState(this.thePlayer, this.currentHand));
		this.thePlayer.setTheState(this.thePlayer.getEndOfRoundHand());
		if(this.currentHand.getSumOfPlayingCards() > 21) {
			return "Busted";
		}
		else {
			return "Double down";
		}
		
	}

}
