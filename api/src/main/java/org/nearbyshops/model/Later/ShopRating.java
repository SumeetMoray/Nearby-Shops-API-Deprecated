package org.nearbyshops.model.Later;

import org.nearbyshops.model.EndUser;
import org.nearbyshops.model.Shop;

public class ShopRating {
	
	int endUserID;
	EndUser endUser;
	
	int shopID;
	Shop shop;
	
	// a value between 1 to 10 given as rating by the End User for the shop
	int rating;
	
	// a review written by the end user for the given shop
	String review;

}
