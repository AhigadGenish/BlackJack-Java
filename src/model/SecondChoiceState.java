package model;

public class SecondChoiceState implements HandState {

	// Data Members
	private Player thePlayer;
	private Hand currentHand;
	
	// Constructor
	public SecondChoiceState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}


	@Override
	public String split() {

		return "Cannot split";
	}

	@Override
	public String surrender() {
		return "Cannot Surrender";
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
		
		if(this.currentHand.getSumOfPlayingCards() >= 21) {
	
			return "Can't Hit";
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
		// Else stay in the same state

		return "Hitted";
	}

	@Override
	public String doubleDown() {

		return "Cannot Double";
	}

}
