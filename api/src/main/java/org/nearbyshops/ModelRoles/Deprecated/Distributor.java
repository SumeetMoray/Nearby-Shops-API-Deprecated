package org.nearbyshops.ModelRoles.Deprecated;


import java.sql.Timestamp;

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
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";

	public static final String ABOUT = "ABOUT";
	public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";

	// to be Implemented
	public static final String IS_ENABLED = "IS_ENABLED";
	public static final String IS_WAITLISTED = "IS_WAITLISTED";

	// to be implemented
	public static final String CREATED = "CREATED";
	public static final String UPDATED = "UPDATED";

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
			+ " " + Distributor.DISTRIBUTOR_NAME + " text,"
			+ " " + Distributor.USERNAME + " text UNIQUE,"
			+ " " + Distributor.PASSWORD + " text,"

			+ " " + Distributor.ABOUT + " text,"
			+ " " + Distributor.PROFILE_IMAGE_URL + " text,"

			+ " " + Distributor.IS_ENABLED + " boolean,"
			+ " " + Distributor.IS_WAITLISTED + " boolean,"

			+ " " + Distributor.CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
			+ " " + Distributor.UPDATED + " timestamp with time zone "

			+ ")";



	// Instance Variables

	private int distributorID;
	private String distributorName;
	private String username;
	private String password;

	private String about;
	private String profileImageURL;

	private Boolean enabled;
	private Boolean waitlisted;
	private Timestamp created;
	private Timestamp updated;




	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getWaitlisted() {
		return waitlisted;
	}

	public void setWaitlisted(Boolean waitlisted) {
		this.waitlisted = waitlisted;
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


	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
}
