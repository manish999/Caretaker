package com.rampgreen.caretakermobile.util;


/**
 * It is used to define all the application constants. 
 * @author Manish Pathak
 *
 */
public class Constants {
	public static final String TAG = "Caretaker";
	//	public static final String URL_WEB_SERVICE = "http://10.15.5.11/mwservice/index.php";
	public static final String URL_WEB_SERVICE = "http://121.240.116.173/mwservice/index.php";
	//	public static final String URL_WEB_SERVICE = "http://121.240.116.173/mwservice_dev/index.php";
	public static final String LOGIN_ACTION = "";

	public static final String LOGIN_EMAIL = "loginmail";
	public static final String BUNDLE_KEY_POSITION = "position";
	public static final String BUNDLE_KEY_USERS = "userbundle";
	
	public static final String BUNDLE_KEY_DISEASE = "bundledisease";
	public static final String DISEASE_GSR = "GSR";//"bundleGSR";
	public static final String DISEASE_HEART_RATE = "Heart Rate";
	public static final String DISEASE_ACCELEROMETER = "Accelerometer";
	public static final String DISEASE_TEMPRATURE = "Temperature";
	
	public static final String FRAGMENT_ADD_MENU_CALLER = "fragmainmenucaller";
	
	public static final int ADD_USER = 101;
	public static final int ADD_NOTIFICATION = 102;
	public static final int ADD_TEXT_DISPLAY = 103;
	public static final int ADD_TEXT_VISUALEXPLORER = 104;
	
	public static class ActivityConstants 
	{
		public static final String FRAGMENT_CALLER = "fragcaller";
		
		public static final int FragmentMenuColor = 1001;
		public static final int FragmentHomeMenuUser = 1002;
		public static final int FragmentHomeMenuAlert = 1003;
		public static final int FragmentHomeMenuText = 1004;
		public static final int FragmentHomeMenuVisual= 1005;
		public static final int FragmentHomeMenuDisease= 1006;
		public static final int FragmentTabBottom= 1007;
		public static final int FragmentChangeActivity= 1008;
		public static final int nothing = -1;
	}
}
