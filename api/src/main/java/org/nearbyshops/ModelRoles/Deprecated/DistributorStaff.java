package org.nearbyshops.ModelRoles.Deprecated;


public class DistributorStaff {

	public DistributorStaff() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Column Names

	// Table Name for Distributor
	public static final String TABLE_NAME = "DISTRIBUTOR_STAFF";

	// Column names for Distributor
	public static final String DISTRIBUTOR_STAFF_ID = "DISTRIBUTOR_STAFF_ID";
	public static final String NAME = "NAME_ADMIN";
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";

	public static final String ABOUT = "ABOUT";
	public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";

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
	public static final String createTableDistributorStaffPostgres = "CREATE TABLE IF NOT EXISTS "
			+ DistributorStaff.TABLE_NAME + "("
			+ " " + DistributorStaff.DISTRIBUTOR_STAFF_ID + " SERIAL PRIMARY KEY,"
			+ " " + DistributorStaff.NAME + " text,"
			+ " " + DistributorStaff.USERNAME + " text UNIQUE,"
			+ " " + DistributorStaff.PASSWORD + " text,"

			+ " " + DistributorStaff.ABOUT + " text,"
			+ " " + DistributorStaff.PROFILE_IMAGE_URL + " text,"

			+ " " + DistributorStaff.IS_ENABLED + " boolean,"
			+ " " + DistributorStaff.IS_WAITLISTED + " boolean"
			+ ")";



	// Instance Variables

	private int distributorStaffID;
	private String name;
	private String username;
	private String password;

	private String about;
	private String profileImageURL;

	private Boolean isEnabled;
	private Boolean isWaitlisted;



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

	public int getDistributorStaffID() {
		return distributorStaffID;
	}

	public void setDistributorStaffID(int distributorStaffID) {
		this.distributorStaffID = distributorStaffID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
