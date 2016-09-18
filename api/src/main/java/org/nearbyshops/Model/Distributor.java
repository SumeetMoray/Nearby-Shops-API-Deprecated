package org.nearbyshops.Model;


public class Distributor {

	public Distributor() {
		super();
		// TODO Auto-generated constructor stub
	}


	// Column Names

	// Table Name for Distributor
	public static final String TABLE_NAME = "DISTRIBUTOR";

	// Column names for Distributor
	public static final String DISTRIBUTOR_ID = "ID";
	public static final String DISTRIBUTOR_NAME = "DISTRIBUTOR_NAME";
	public static final String DISTRIBUTOR_PASSWORD = "DISTRIBUTOR_PASSWORD";

	// to be Implemented
	public static final String IS_ENABLED = "IS_ENABLED";
	public static final String IS_WAITLISTED = "IS_WAITLISTED";

	/*
	Enable or Disable indicates whether the account is enabled or not.
	When the staff is approving the new accounts they might put some or few accounts on hold because they might
	want to do some extra enquiry before they give them an approval. In this situation they might transfer these accounts
	on to waitlisted in order to distinctly identify them and they do not get mixed with other new accounts.
	*/


	// Create Table Statement
	public static final String createTableDistributorPostgres = "CREATE TABLE IF NOT EXISTS "
			+ Distributor.TABLE_NAME + "("
			+ " " + Distributor.DISTRIBUTOR_ID + " SERIAL PRIMARY KEY,"
			+ " " + Distributor.DISTRIBUTOR_NAME + " text UNIQUE,"
			+ " " + Distributor.DISTRIBUTOR_PASSWORD + " text,"

			+ " " + Distributor.IS_ENABLED + " boolean,"
			+ " " + Distributor.IS_WAITLISTED + " boolean"
			+ ")";



	// Instance Variables

	private int distributorID;
	private String distributorName;
	private String password;
	private Boolean isEnabled;
	private Boolean isWaitlisted;



	public Boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(Boolean enabled) {
		isEnabled = enabled;
	}

	public Boolean getWaitlisted() {
		return isWaitlisted;
	}

	public void setWaitlisted(Boolean waitlisted) {
		isWaitlisted = waitlisted;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(int distributorID) {
		this.distributorID = distributorID;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
}
