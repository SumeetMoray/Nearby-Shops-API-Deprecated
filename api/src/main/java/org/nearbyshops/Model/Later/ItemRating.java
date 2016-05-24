package org.nearbyshops.Model.Later;

import org.nearbyshops.Model.EndUser;
import org.nearbyshops.Model.Item;

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
