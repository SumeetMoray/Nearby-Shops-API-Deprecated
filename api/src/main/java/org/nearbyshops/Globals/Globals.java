package org.nearbyshops.Globals;

import org.nearbyshops.DAOs.DistributorService;
import org.nearbyshops.DAOs.ItemCategoryService;
import org.nearbyshops.DAOs.ItemService;
import org.nearbyshops.DAOs.ShopItemService;
import org.nearbyshops.DAOs.ShopService;
import org.nearbyshops.Model.Later.AbstractCategoryService;


public class Globals {
	
	//public static ArrayList<ItemCategory> list = new ArrayList<ItemCategory>();
	
	public static ItemCategoryService itemCategoryService = new ItemCategoryService();
	
	public static ItemService itemService = new ItemService();
	
	public static DistributorService distributorService = new DistributorService();
	
	public static ShopService shopService = new ShopService();
	
	public static ShopItemService shopItemService = new ShopItemService();
	
	public static AbstractCategoryService abstractCategoryService = new AbstractCategoryService();
	
}
