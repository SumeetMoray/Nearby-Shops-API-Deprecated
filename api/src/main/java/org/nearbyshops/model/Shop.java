package org.nearbyshops.model;


public class Shop {

	int shopID;
	
	//List<ShopItem> shopItems;
	
	//@ManyToOne
	//@JoinColumn(name="DISTRIBUTOR")
	//Distributor distributor;
	
	String shopName;
	
	// the radius of the circle considering shop location as its center. 
	//This is the distance upto which shop can deliver its items
	double radiusOfService;
	
	// the average of all the ratings given by the end users
	// the average rating is updated whenever a user rates a shop
	double averageRating;
	
	// latitude and longitude for storing the location of the shop
	double latitude;
	double longitude;
	
	// delivery charger per order 
	double deliveryCharges;
	
	// column created for JDBC
	int distributorID;

	
	//int dateOfStart;
	
	
	String imagePath;
	
	
	
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getDistributorID() {
		return distributorID;
	}

	public double getRadiusOfService() {
		return radiusOfService;
	}

	public void setRadiusOfService(double radiusOfService) {
		this.radiusOfService = radiusOfService;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public void setDistributorID(int distributorID) {
		this.distributorID = distributorID;
	}
	
	


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Shop(Distributor distributor, String shopName, int radiusOfService, int averageRating, double latitude,
			double longitude, int deliveryCharges) {
		
		
		super();
	
		this.shopName = shopName;
		this.radiusOfService = radiusOfService;
		this.averageRating = averageRating;
		this.latitude = latitude;
		this.longitude = longitude;
		this.deliveryCharges = deliveryCharges;
	}

	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
