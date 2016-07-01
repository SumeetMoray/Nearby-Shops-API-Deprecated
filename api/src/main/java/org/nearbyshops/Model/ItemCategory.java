package org.nearbyshops.Model;


public class ItemCategory {


	int itemCategoryID;
	
	String categoryName;
	String categoryDescription;
	
	int parentCategoryID;
	boolean isLeafNode;

	String imagePath;

	// recently added
	boolean isAbstractNode;
	String descriptionShort;


	//no-args Constructor
	public ItemCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Getters and Setters


	public Boolean getisAbstractNode() {
		return isAbstractNode;
	}

	public void setisAbstractNode(Boolean abstractNode) {
		isAbstractNode = abstractNode;
	}

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public int getItemCategoryID() {
		return itemCategoryID;
	}

	public void setItemCategoryID(int itemCategoryID) {
		this.itemCategoryID = itemCategoryID;
	}

	public int getParentCategoryID() {
		return parentCategoryID;
	}

	public void setParentCategoryID(int parentCategoryID) {
		this.parentCategoryID = parentCategoryID;
	}

	public boolean getIsLeafNode() {
		return isLeafNode;
	}

	public void setIsLeafNode(boolean isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
