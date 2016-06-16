package org.nearbyshops.Model;


import org.nearbyshops.ModelStats.ItemStats;

import java.sql.Timestamp;

public class Item {

	int itemID;
	
	String itemName;
	String itemDescription;
	String itemImageURL;
	
	//technically it is the name of the manufacturer 
	// Typically its the name of the manufacturer
	
	// Only required for JDBC
	int itemCategoryID;


	ItemStats itemStats;




	// recently added
	String quantityUnit;
	Timestamp dateTimeCreated;
	String itemDescriptionLong;





	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public Timestamp getDateTimeCreated() {
		return dateTimeCreated;
	}

	public void setDateTimeCreated(Timestamp dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}

	public String getItemDescriptionLong() {
		return itemDescriptionLong;
	}

	public void setItemDescriptionLong(String itemDescriptionLong) {
		this.itemDescriptionLong = itemDescriptionLong;
	}

	public ItemStats getItemStats() {
		return itemStats;
	}

	public void setItemStats(ItemStats itemStats) {
		this.itemStats = itemStats;
	}

	public int getItemCategoryID() {
		return itemCategoryID;
	}



	public void setItemCategoryID(int itemCategoryID) {
		this.itemCategoryID = itemCategoryID;
	}



	ItemCategory itemCategory;



	public ItemCategory getItemCategory() {
		return itemCategory;
	}



	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}


	
	//No-args constructor

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	// getters and Setter methods

	public int getItemID() {
		return itemID;
	}



	public void setItemID(int itemID) {
		this.itemID = itemID;
	}



	public String getItemDescription() {
		return itemDescription;
	}



	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}



	public String getItemImageURL() {
		return itemImageURL;
	}



	public void setItemImageURL(String itemImageURL) {
		this.itemImageURL = itemImageURL;
	}


	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
