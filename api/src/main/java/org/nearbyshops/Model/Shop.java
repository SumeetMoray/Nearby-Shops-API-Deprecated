package org.nearbyshops.Model;


import java.sql.Timestamp;

public class Shop {

	// real time variables
	double distance;


	// normal variables

	int shopID;
	
	String shopName;

	// the radius of the circle considering shop location as its center.
	//This is the distance upto which shop can deliver its items
	double deliveryRange;

	// latitude and longitude for storing the location of the shop
	double latCenter;
	double lonCenter;

	// bounding coordinates for the shop generated using shop center coordinates and delivery range.
	double latMax;
	double lonMax;
	double latMin;
	double lonMin;


	// delivery charger per order
	double deliveryCharges;

	int distributorID;
	
	String imagePath;


	// added recently
	String shopAddress;
	String city;
	long pincode;
	String landmark;
	int billAmountForFreeDelivery;
	String customerHelplineNumber;
	String deliveryHelplineNumber;
	String shortDescription;
	String longDescription;
	Timestamp dateTimeStarted;
	boolean isOpen;





	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}



	public double getDeliveryRange() {
		return deliveryRange;
	}

	public void setDeliveryRange(double deliveryRange) {
		this.deliveryRange = deliveryRange;
	}

	public double getLatCenter() {
		return latCenter;
	}

	public void setLatCenter(double latCenter) {
		this.latCenter = latCenter;
	}

	public double getLonCenter() {
		return lonCenter;
	}

	public void setLonCenter(double lonCenter) {
		this.lonCenter = lonCenter;
	}

	public double getLatMax() {
		return latMax;
	}

	public void setLatMax(double latMax) {
		this.latMax = latMax;
	}

	public double getLonMax() {
		return lonMax;
	}

	public void setLonMax(double lonMax) {
		this.lonMax = lonMax;
	}

	public double getLatMin() {
		return latMin;
	}

	public void setLatMin(double latMin) {
		this.latMin = latMin;
	}

	public double getLonMin() {
		return lonMin;
	}

	public void setLonMin(double lonMin) {
		this.lonMin = lonMin;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getDistributorID() {
		return distributorID;
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


	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public int getBillAmountForFreeDelivery() {
		return billAmountForFreeDelivery;
	}

	public void setBillAmountForFreeDelivery(int billAmountForFreeDelivery) {
		this.billAmountForFreeDelivery = billAmountForFreeDelivery;
	}

	public String getCustomerHelplineNumber() {
		return customerHelplineNumber;
	}

	public void setCustomerHelplineNumber(String customerHelplineNumber) {
		this.customerHelplineNumber = customerHelplineNumber;
	}

	public String getDeliveryHelplineNumber() {
		return deliveryHelplineNumber;
	}

	public void setDeliveryHelplineNumber(String deliveryHelplineNumber) {
		this.deliveryHelplineNumber = deliveryHelplineNumber;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Timestamp getDateTimeStarted() {
		return dateTimeStarted;
	}

	public void setDateTimeStarted(Timestamp dateTimeStarted) {
		this.dateTimeStarted = dateTimeStarted;
	}

	public boolean getisOpen() {
		return isOpen;
	}

	public void setisOpen(boolean open) {
		isOpen = open;
	}
}
