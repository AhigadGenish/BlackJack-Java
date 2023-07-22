package model;
import java.util.*;

public class Hand {
	
	// Data Members
	private List<Card> cards;
	private int betMoney;
	private int sumOfCardsWithAce;
	private int sumOfCards;
	private boolean canSplit = false;
	
	private HandState theState;
	
	// Constructor
	public Hand(int newBetMoney){
		
		this.initializeHand(newBetMoney);
	}

	private void initializeHand(int newBetMoney) {
		
		// Create 2 cards
		this.cards = new ArrayList<Card>();
		Card firstCard = CreateCard.genarateCard();
		Card secondCard = CreateCard.genarateCard();
		
		this.cards.add(firstCard);
		this.cards.add(secondCard);
		
		// Set Money
		this.betMoney = newBetMoney;
		
		// Update sum of cards
		this.setSumOfCard();
		this.setSumOfCardsWithAce();
		
		// Check if can split
		this.canSplit = firstCard.equals(secondCard);
	}
	
	// Check for BlackJack
	public boolean hasBlackJack() {
		
		return this.getSumOfCardsWithAce() == 21 && this.getCards().size() == 2;
	}



	// Getters
	public List<Card> getCards() {
		return this.cards;
	}

	public int getBetMoney() {
		return this.betMoney;
	}
	
	public int getSumOfCardsWithAce() {
		return sumOfCardsWithAce;
	}
	
	public int getSumOfCards() {
		return sumOfCards;
	}
	
	public HandState getState() {
		return this.theState;
	}
	
	public boolean canSplit() {
		return this.canSplit;
	}
	
	// Setters
	public void setCards(List<Card> cards) {
		this.cards = cards;
		this.setSumOfCard();
		this.setSumOfCardsWithAce();
	}

	public void setBetMoney(int anyBetMoney) {
		this.betMoney = anyBetMoney;
	}

	public void setHandState(HandState anyHandState) {
		this.theState = anyHandState;
	}
	
	private void setSumOfCardsWithAce() {
		
		this.sumOfCardsWithAce = 0;
		
		boolean seenAce = false;
		// Sum all the cards, include case of Ace
		for(Card anyCard: this.cards) {
			
			this.sumOfCardsWithAce += anyCard.getValue();
			
			if(anyCard.isAce() == true && seenAce == false) {
				
				this.sumOfCardsWithAce += 10;
				seenAce = true;
			}
		   
		}
	}

	private void setSumOfCard() {
		
		this.sumOfCards = 0;
		
		// Sum all the cards, ignore case of Ace
		for(Card anyCard: this.cards) {
			this.sumOfCards += anyCard.getValue();
		}
	}
	
	// Get one more card, set the sum respectively.
	public void getMoreCard() {
		
		Card newCard = CreateCard.genarateCard();
		this.cards.add(newCard);
		
		this.setSumOfCard();
		this.setSumOfCardsWithAce();
	}
	
	public List<Hand> createNewHandsAfterSplit() {
		
		// Split
		Card firstCardHand1 = this.getCards().get(0);
		Card firstCardHand2 = this.getCards().get(1);
		
		// Create new Hand
		Hand secondHand = new Hand(this.getBetMoney());
		
		Card secondCardHand1 = secondHand.getCards().get(0);
		Card secondCardHand2 = secondHand.getCards().get(1);
		
		List<Card> cardsHand1 = new ArrayList<Card>();
		cardsHand1.add(firstCardHand1);
		cardsHand1.add(secondCardHand1);
		
		List<Card> cardsHand2 = new ArrayList<Card>();
		cardsHand2.add(firstCardHand2);
		cardsHand2.add(secondCardHand2);
		
		// Set the new cards of first hand and second hand
		this.setCards(cardsHand1);
		secondHand.setCards(cardsHand2);
	
		
		List<Hand> newHands = new ArrayList<Hand>();
		newHands.add(this);
		newHands.add(secondHand);
		
		return newHands;

	}
	
	// Get the sum of best cards
	public int getSumOfPlayingCards() {
		
		if(this.getSumOfCardsWithAce() > 21) {
			return this.getSumOfCards();
		}
		return this.getSumOfCardsWithAce();
	}


	
}
