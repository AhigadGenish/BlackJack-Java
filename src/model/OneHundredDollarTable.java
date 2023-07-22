package model;

public class OneHundredDollarTable extends Table{

	// Data Members
	private final static int minimumBet = 100;
	private static OneHundredDollarTable theTable;
	
	// Singleton Design Pattern
	private OneHundredDollarTable() {
		super(minimumBet);
		
	}

	@Override
	public int getMinimumBet() {
		return minimumBet;
	}
	
	public static synchronized Table getInstance() {
		
		if(theTable == null) {
			theTable = new OneHundredDollarTable();
		}
		
		return theTable;
	}
	


}
