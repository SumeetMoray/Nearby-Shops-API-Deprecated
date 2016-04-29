package org.nearbyshops.model.Later;

import org.nearbyshops.model.EndUser;
import org.nearbyshops.model.Item;

public class ItemRating {
	
	int endUserID;
	EndUser endUser;
	
	int itemID;
	Item item;
	
	// a value between 1 to 10 given by the end user for the end user for the item as a rating 
	int itemRating;
	
	// a review written by the end user for the the item
	String itemReview;
	
}
