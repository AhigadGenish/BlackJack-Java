package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Card;
import model.Dealer;

// Dealer Component UI
public class DealerComponent extends JLabel {
	
	// Data Members
	private JLayeredPane thePane;    
	private JPanel dealerPanel;
	
	private boolean showCard = false;
	
	private static int dealerX = 500;
	private static int dealerY = 60;
	
	// Constructor
    public DealerComponent(JLayeredPane anyPane) {
    	
        this.thePane = anyPane;
        this.createDealerPanel();

    }
    
   private void createDealerPanel() {
    	
	   dealerPanel = new JPanel();
	   dealerPanel.setLayout(null);
	   dealerPanel.setOpaque(false);
	   dealerPanel.setBounds(0, 0, 1200, 800);
       thePane.add(dealerPanel, new Integer(1));
    }
    
    // Update Dealer Component
    public void updateComponent(Dealer dealer) {

        int i = 0;

        for (Card card : dealer.getDealerHand().getCards()) {
        	
            // Display cards
            String iconPath = "resources/cards/" + card.getIconPath();
            ImageIcon cardIcon = new ImageIcon(iconPath);
            JLabel cardIconLabel = new JLabel(cardIcon);
            cardIconLabel.setBounds(dealerX - 15*i + 25, dealerY + 60, cardIcon.getIconWidth(), cardIcon.getIconHeight());
            cardIconLabel.setForeground(Color.WHITE);
            this.dealerPanel.add(cardIconLabel, new Integer(1));
           
            i += 1;
         
            if(dealer.isDealerTurn() == false) {
            	break;
            }
          
        }
        
        if(dealer.getDealerHand().getSumOfPlayingCards() >= 17 && dealer.isDealerTurn() == true && this.showCard == false) {
        	
        	this.showCard = true;
        	 // Display sum of cards
            String sum = dealer.getDealerHand().hasBlackJack() ? "BlackJack!" : Integer.toString(dealer.getDealerHand().getSumOfCards()) + "/" + Integer.toString(dealer.getDealerHand().getSumOfCardsWithAce());
            JLabel sumOfCards = new JLabel(sum);
            
	       	// It's dealer turn so Display sum of cards
	        sumOfCards.setBounds(dealerX + 25, dealerY + 180 , 80, 10);
	        sumOfCards.setForeground(Color.WHITE);
	        this.dealerPanel.add(sumOfCards, new Integer(1));
        }
    
    }
    
    // Clear Dealer Component
    public void clearDealerComponent() {
    	
    	this.showCard = false;
    	this.thePane.remove(dealerPanel);
    	this.thePane.repaint();
    
    }
}
