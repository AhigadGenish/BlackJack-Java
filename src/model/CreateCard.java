package model;

import java.io.*;
import java.util.*;


public class CreateCard {

    // Data Members
    private static List<String> icons;
    private static CreateCard theInstance;

    // Private Constructor - Singleton Design Pattern
    private CreateCard() {
        // Initialize icons list and fill the list.
        icons = new ArrayList<String>();
        fillIconsList();
    }

    // Fill the icons list from the resources.
    private static void fillIconsList() {


         // Get icons path, and fill the list.

          String cardsFolderPath = "resources/cards"; 
          File cardsFolder = new File(cardsFolderPath);
          File[] pngIcons = cardsFolder.listFiles();

          // Fill the list with png icon paths
          for(File icon : pngIcons){
             icons.add(icon.getName());
          }
 
    }

    // Generate card randomly
    public static Card genarateCard(){

        if(theInstance == null){
            theInstance = new CreateCard();
        }
        
        Random rand = new Random();

        // Get Icon
        int index = rand.nextInt(0,52);
        String icon = icons.get(index);

        // Check if ace
        boolean isAce = icon.indexOf("ace") != -1;

        // Parse and retrieve value from the png format name which is : value_name_color
        String[] parts = icon.split("_");
        int value = Integer.parseInt(parts[0]);

        return new Card(value, isAce, icon);
    }

}
