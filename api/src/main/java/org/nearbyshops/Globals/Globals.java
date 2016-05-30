package org.nearbyshops.Globals;

import org.nearbyshops.DAOs.*;


public class Globals {
	
	//public static ArrayList<ItemCategory> list = new ArrayList<ItemCategory>();
	
	public static ItemCategoryService itemCategoryService = new ItemCategoryService();
	
	public static ItemService itemService = new ItemService();
	
	public static DistributorService distributorService = new DistributorService();
	
	public static ShopService shopService = new ShopService();
	
	public static ShopItemService shopItemService = new ShopItemService();

	public static EndUserService endUserService = new EndUserService();
	
}
