package model;

public class FiftyDollarTable extends Table {

	private final static int minimumBet = 50;
	private static FiftyDollarTable theTable;
	
	// Singleton Design Pattern
	private FiftyDollarTable() {
		super(minimumBet);
		
	}

	@Override
	public int getMinimumBet() {
		return minimumBet;
	}
	
	public static synchronized Table getInstance() {
		
		if(theTable == null) {
			theTable = new FiftyDollarTable();
		}
		
		return theTable;
	}
}
