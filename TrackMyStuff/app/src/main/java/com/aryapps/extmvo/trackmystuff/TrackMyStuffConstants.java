package com.aryapps.extmvo.trackmystuff;

public interface TrackMyStuffConstants {
	
	public static final String POSITION = "position";
    public static final String ARTICLE_NAME = "articleName";
    public static final String CONTAINER = "container";
    public static final boolean LOG=false;
    public static final String FILE_LOCATION="/mnt/sdcard/TrackMyStuffData/trackInfo.xls";
    public static final String EMAIL_CONTENT="Please find attached Track My Stuff Data!!";
    public static final String EMAIL_SUBJECT="TrackMyStuff Data";
    public static final String TOAST_NO_EMAIL_CONFIGURED="Please configure an Email Id by going to the Settings option";
    public static final String TOAST_MANDATORY_FIELDS = "Article Name and Container are mandatory fields, please fill the mandatory fields";
    public static final String DATA_SAVED_SUCCESS = "Data Saved Successfully";
    public static final String DUPLICATE_DATA = "Article Name already exists please modify the name, and try again or edit the already present data";
    public static final String DATE_QUERY = "date=?";
    public static final String SELECT_QUERY ="SELECT  * FROM ";
    public static final String DATE_FORMAT ="dd-MMM-yy";
    public static final String EMAIL_DATA_SAVED_SUCCESS ="Email and user data saved sucessfully";
    public static final String EMAIL_DATA_SAVED_FAIL ="Email and user data saved unsuccessful";
    public static final String EMAIL_USER_NAME_MANDATORY ="Please enter values for Name and Email";





}
