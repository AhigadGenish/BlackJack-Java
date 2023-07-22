package model;

import java.util.Comparator;
import java.util.Map;

// Strategy Design Pattern

class WinsComparator implements Comparator<Map<String, String>> {
	
    @Override
    public int compare(Map<String, String> user1, Map<String, String> user2) {
        int wins1 = Integer.parseInt(user1.get("Number of wins"));
        int wins2 = Integer.parseInt(user2.get("Number of wins"));
        return Integer.compare(wins2, wins1); // Sort in descending order
    }
}
