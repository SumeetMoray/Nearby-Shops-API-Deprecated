package org.nearbyshops.database;

import org.nearbyshops.Service.DistributorService;
import org.nearbyshops.Service.ItemCategoryService;
import org.nearbyshops.Service.ItemService;
import org.nearbyshops.Service.ShopItemService;
import org.nearbyshops.Service.ShopService;
import org.nearbyshops.model.Later.AbstractCategoryService;


public class Globals {
	
	//public static ArrayList<ItemCategory> list = new ArrayList<ItemCategory>();
	
	public static ItemCategoryService itemCategoryService = new ItemCategoryService();
	
	public static ItemService itemService = new ItemService();
	
	public static DistributorService distributorService = new DistributorService();
	
	public static ShopService shopService = new ShopService();
	
	public static ShopItemService shopItemService = new ShopItemService();
	
	public static AbstractCategoryService abstractCategoryService = new AbstractCategoryService();
	
}
