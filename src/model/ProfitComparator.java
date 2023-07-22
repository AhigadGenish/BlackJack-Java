package model;

import java.util.Comparator;
import java.util.Map;

// Strategy Design Pattern
class ProfitComparator implements Comparator<Map<String, String>> {
	
    @Override
    public int compare(Map<String, String> user1, Map<String, String> user2) {
        int profit1 = Integer.parseInt(user1.get("Total profit"));
        int profit2 = Integer.parseInt(user2.get("Total profit"));
        return Integer.compare(profit2, profit1); // Sort in descending order
    }
}
