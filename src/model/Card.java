package model;

public class Card {

    // Data Members
    private int value;
    private String iconPath;
    private boolean isAce;

    
    // Constructor
    public Card(int newValue, boolean isAceCard, String newIconPath) {

        this.value = newValue;
        this.isAce = isAceCard;
        this.iconPath = newIconPath;
    }

    // Getters:
	public int getValue() {
		return value;
	}

	public String getIconPath() {
		return iconPath;
	}
	
	public boolean isAce() {
		return isAce;
	}
	
	// Setters
	public void setValue(int anyValue) {
		this.value = anyValue;
	}

	public void setIconPath(String anyIconPath) {
		this.iconPath = anyIconPath;
	}

	public void setAce(boolean isAceCard) {
		this.isAce = isAceCard;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof Card) {
			// Casting
			Card otherCard = (Card)o;
			return otherCard.getValue() == this.getValue();
		}
		
		return false;
	}

}
