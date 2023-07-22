package model;

public class EndHandRoundState implements HandState {
	
	// Data Members
	private Player thePlayer;
	private Hand currentHand;
	private static final String CANTPLAY = "Can't Play";
	
	// Constructor
	public EndHandRoundState(Player newPlayer, Hand newHand) {
		
		this.thePlayer = newPlayer;
		this.currentHand = newHand;
	}

	@Override
	public String split() {
	
		return CANTPLAY;
	}

	@Override
	public String surrender() {
		return CANTPLAY;
		
	}

	@Override
	public String stand() {
		return CANTPLAY;
	}

	@Override
	public String hit() {
		return CANTPLAY;
		
	}

	@Override
	public String doubleDown() {
		return CANTPLAY;
	}

	
}
