package model;

public class FiveDollarTable extends Table {

	private final static int minimumBet = 5;
	private static FiveDollarTable theTable;
	
	// Singleton Design Pattern
	private FiveDollarTable() {
		super(minimumBet);
		
	}

	@Override
	public int getMinimumBet() {
		return minimumBet;
	}
	
	
	public static synchronized Table getInstance() {
		
		if(theTable == null) {
			theTable = new FiveDollarTable();
		}
		
		return theTable;
	}
	
}
