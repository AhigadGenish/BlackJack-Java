package model;

public class AfterSplitHandOneState implements HandState {

	// Data Members
	private Player thePlayer;
	private Hand currentHand;
	private boolean afterHit = false;
	
	// Constructor
	public AfterSplitHandOneState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}

	@Override
	public String split() {
		
		return "Hand1 : Can't split twice";

	}

	@Override
	public String surrender() {
		
		return "Hand1 : Can't surrender";
	}

	@Override
	public String stand() {
 
		// Update State
		this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand2());
		return "Hand1 : Stand";
	}

	@Override
	public String hit() {
	
		if(this.currentHand.getSumOfPlayingCards() > 21) {
			
			return "Hand1 : Can't hit";
		}
		
		// Get More Card
		this.currentHand.getMoreCard();
		this.afterHit = true;
		
		// If done
		if(this.currentHand.getSumOfPlayingCards() >= 21) {
			
			// Update State
			this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand2());
			return this.thePlayer.getPlayerName() + " Hand2 : ";
		}
		
		// Else, stay in current state
		return "Hand1 : Hitted";
		
	}

	@Override
	public String doubleDown() {
		
		if(this.afterHit == true || this.enoughMoneyForSplitOrDouble() == false) {
		
			return "Hand1 : Can't Double";
		}

		// Update total money
		this.updateTotalMoneyAfterSplitOrDouble();
		
		// Get More Card
		this.currentHand.getMoreCard();
		
		// Update State
		this.thePlayer.setTheState(this.thePlayer.getAfterSplitHand2());
		
		return "Hand1 : Double down";
		
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

}
