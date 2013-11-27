package com.rampgreen.caretakermobile.model;

import java.util.ArrayList;
import java.util.List;

public class UserListProvider
{
	public static final int NOT_DEFINE = -1;

	public static final int FOR_HOME_CONTENT = 1;
	public static final int FOR_MENU_CONTENT = 2;

	public static final int DISEASE_GSR = 10;
	public static final int DISEASE_HEART_RATE = 11;
	public static final int DISEASE_ACCELEROMETER = 12;
	public static final int DISEASE_TEMPRATURE = 13;

	public static final int ADD_USER_ICON = 21;
	public static final int NOTIFICATION = 22;
	public static final int TEXT_DISPLAY = 23;
	public static final int VISUAL_DISPLAY = 24;

	ArrayList<User> userBeanList;

	public ArrayList<User> getList(int contentFor, int diseaseType, int menuType, boolean requiredBackasFirstItem) {
		ArrayList<User> userList;
		switch (contentFor) {
		case FOR_HOME_CONTENT:
			userList = getContentList(diseaseType, menuType);			
			break;

		case FOR_MENU_CONTENT:
			userList = getMenuList(diseaseType, menuType);
			if(requiredBackasFirstItem) {
				// get the index, if already updated no need to update.
				if(userList.size() != 0 && ((User)userList.get(0)).getUsername().equalsIgnoreCase("<<")) {

				} else {
					User user = new User();
					user.setUsername("<<");
					user.setUserOnHomeScreen(false);
					userList.add(0,user);
				}
			}
			break;

		default:
			throw new IllegalArgumentException("parameter must be UserListProvider's constants");
		}
		return userList;
	}

	public ArrayList<User> getTextDisplayList() {
		// get the content data for each disease for textdisplay 
		ArrayList<User> tempList = new ArrayList<User>();
		List<User> userList = getContentList(DISEASE_GSR, TEXT_DISPLAY);
		tempList.addAll(userList);
		userList = getContentList(DISEASE_HEART_RATE, TEXT_DISPLAY);
		tempList.addAll(userList);
		userList = getContentList(DISEASE_ACCELEROMETER, TEXT_DISPLAY);
		tempList.addAll(userList);
		userList = getContentList(DISEASE_TEMPRATURE, TEXT_DISPLAY);
		tempList.addAll(userList);
		return tempList;
	}

	public ArrayList<User> getVisualDisplayList() {
		// get the content data for each disease for textdisplay 
		ArrayList<User> tempList = new ArrayList<User>();
		List<User> userList = getContentList(DISEASE_GSR, VISUAL_DISPLAY);
		tempList.addAll(userList);
		userList = getContentList(DISEASE_HEART_RATE, VISUAL_DISPLAY);
		tempList.addAll(userList);
		userList = getContentList(DISEASE_ACCELEROMETER, VISUAL_DISPLAY);
		tempList.addAll(userList);
		userList = getContentList(DISEASE_TEMPRATURE, VISUAL_DISPLAY);
		tempList.addAll(userList);
		return tempList;
	}

	private ArrayList<User> getContentList(int diseaseType, int menuType)
	{
		ArrayList<User> allUser = getUserBeanList();
		ArrayList<User> menuUserList = getMenuList(diseaseType, menuType);
		ArrayList<User> containerUserList = new ArrayList<User>();
		for (User user : allUser)
		{
			if(! menuUserList.contains(user)) {
				containerUserList.add(user);
			}
		}
		return containerUserList;
	}

	private ArrayList<User> getMenuList(int diseaseType, int menuType)
	{
		ArrayList<User> userList = null;
		if(menuType == ADD_USER_ICON) {
			userList = getMenuListForAddUsericon();
		} else if(menuType == NOTIFICATION) {
			userList = getMenuListForNotification();
			//			if(diseaseType == DISEASE_GSR) {
			//				userList = getMenuListForGsrNotification();
			//			} else if(diseaseType == DISEASE_HEART_RATE){
			//				userList = getMenuListForHeartNotification();
			//			}else if(diseaseType == DISEASE_ACCELEROMETER){
			//				userList = getMenuListForAccelNotification();
			//			}else if(diseaseType == DISEASE_TEMPRATURE){
			//				userList = getMenuListForTempNotification();
			//			}
		} else if(menuType == TEXT_DISPLAY) {
			if(diseaseType == DISEASE_GSR) {
				userList = getMenuListForGsrTextDisplay();
			} else if(diseaseType == DISEASE_HEART_RATE){
				userList = getMenuListForHeartTextDisplay();
			}else if(diseaseType == DISEASE_ACCELEROMETER){
				userList = getMenuListForAccelTextDisplay();
			}else if(diseaseType == DISEASE_TEMPRATURE){
				userList = getMenuListForTempTextDisplay();
			}
		} else if(menuType == VISUAL_DISPLAY) {
			if(diseaseType == DISEASE_GSR) {
				userList = getMenuListForGsrVisualDisplay();
			} else if(diseaseType == DISEASE_HEART_RATE){
				userList = getMenuListForHeartVisualDisplay();
			}else if(diseaseType == DISEASE_ACCELEROMETER){
				userList = getMenuListForAccelVisualDisplay();
			}else if(diseaseType == DISEASE_TEMPRATURE){
				userList = getMenuListForTempVisualDisplay();
			}
		}

		if(userList == null)
			throw new NullPointerException("Method getMenuList() of UserListProvider is returning null arraylist, check it disease type = "+diseaseType +"menutype = "+menuType ); 
		return userList;
	}

	private ArrayList<User> getList(int diseaseType, int menuType) {
		ArrayList<User> userList;
		switch (menuType) {
		case NOTIFICATION:
			userList = getList(diseaseType);
			break;

		case TEXT_DISPLAY:
			userList = getList(diseaseType);
			break;
		case VISUAL_DISPLAY:
			userList = getList(diseaseType);
			break;

		default:
			throw new IllegalArgumentException("parameter must be UserListProvider's constants");
		}
		return userList; 
	}

	private ArrayList<User> getList(int diseaseType) {
		ArrayList<User> userList = new ArrayList<User>();
		switch (diseaseType) {
		case DISEASE_GSR:
			getUserBeanList();
			break;

		case DISEASE_HEART_RATE:

			break;
		case DISEASE_ACCELEROMETER:

			break;

		case DISEASE_TEMPRATURE:
			break;

		default:
			throw new IllegalArgumentException("parameter must be UserListProvider's constants");
		}
		return userList;
	}

	public ArrayList<User> getUserBeanList()
	{
		if(userBeanList ==null)
			userBeanList = BeanController.getUserBean().getUserList(); 
		return userBeanList;
	}

	public void setUserBeanList(ArrayList<User> userBeanList)
	{
		this.userBeanList = userBeanList;
	}

	private ArrayList<User> getMenuListForGsrNotification () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isGsrNotification()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForHeartNotification () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isHeartRateNotification()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForAccelNotification () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isAccelerometerNotification()) {
				tempList.add(user);
			}
		}
		return tempList;
	}
	private ArrayList<User> getMenuListForTempNotification () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isTempratureNotification()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForGsrTextDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isGsrTextDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForHeartTextDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isHeartRateTextDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForAccelTextDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isAccelerometerTextDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}
	private ArrayList<User> getMenuListForTempTextDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isTempratureTextDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForGsrVisualDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isGsrVisualDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForHeartVisualDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isHeartRateVisualDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForAccelVisualDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isAccelerometerVisualDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}
	private ArrayList<User> getMenuListForTempVisualDisplay () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isTempratureVisualDisplay()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForAddUsericon () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isUserOnHomeScreen()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	private ArrayList<User> getMenuListForNotification () {
		ArrayList<User> userList = BeanController.getUserBean().getUserList();
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : userList)
		{
			if(! user.isNotification()) {
				tempList.add(user);
			}
		}
		return tempList;
	}

	public User getUser(String userID) {
		ArrayList<User> userList = getUserBeanList();
		for (User user : userList)
		{
			if(user.getUid().equalsIgnoreCase(userID)) {
				return user;
			}
		}
		throw new IllegalArgumentException("either userlist is empty with userID : "+userID+"or User is not in Userlist by given userId.");
	}

	private ArrayList<TextDisplaySettings> getUserbyBiometricID(String bioMetricId) {
		ArrayList<TextDisplaySettings> settingList = ListHolder.getTextDisplaySettingList();
		ArrayList<TextDisplaySettings> tempList = new ArrayList<TextDisplaySettings>();
		for (TextDisplaySettings setting : settingList)
		{
			if(setting.getBiometricID().equalsIgnoreCase(bioMetricId)) {
				tempList.add(setting);
			}
		}
		return tempList;
	}

	/**
	 * it will filter the user's by TextDisplaySetting's userid from MainUserLIst 
	 * @param bioMetricId
	 * @return
	 */
	public ArrayList<TextDisplaySettings> getFilterUserbyTextDisplayUserID(String bioMetricId) {
		ArrayList<TextDisplaySettings> settingList = getUserbyBiometricID(bioMetricId);
		ArrayList<User> userList = getUserBeanList();
		ArrayList<TextDisplaySettings> tempList = new ArrayList<TextDisplaySettings>();
		tempList.add(new TextDisplaySettings("-1", "<<", "-1"));
		for (User user : userList)
		{
			boolean ispresent = false;
			TextDisplaySettings tempSetting;
			for (TextDisplaySettings setting : settingList)
			{
				//Final : get the all userlist whose id match from mainlist and is not in settinglist.
				// if userID is available in mainlist continue 
				// else throw exception not present in mail list
				// else if userID not available
				if(user.getUid().equalsIgnoreCase(setting.getUserID())) {
					ispresent = true;
					break;
					//					tempList.add(setting);
				} else {
					tempSetting = setting;
					ispresent = false;
				}
			}

			if(ispresent == false) {
				// add userId to TextDisplaySetting as USEr
				TextDisplaySettings textDisplaySettings = new TextDisplaySettings(user.getUid(), bioMetricId, "filled by webserivice");
				tempList.add(textDisplaySettings);
			}
		}
		//		for (TextDisplaySettings setting : settingList)
		//		{
		//			//Final : get the all userlist whose id match from mainlist and is not in settinglist.
		//			// if userID is available in mainlist continue 
		//			// else throw exception not present in mail list
		//			// else if userID not available
		//			if(isAvalableInMainList(setting.getUserID())) {
		//				tempList.add(setting);
		//			}
		//		}
		return tempList;
	}

	/**
	 * this method check userID presence in MainUserList
	 * @param userID
	 * @return return true if present otherwise false.
	 */
	private boolean isAvalableInMainList(String userID) {
		ArrayList<User> userList = getUserBeanList();
		for (User user : userList)
		{
			if (user.getUid().equalsIgnoreCase(userID))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isAvalableInTextDisplayList(String userID) {
		ArrayList<TextDisplaySettings> userList = ListHolder.getTextDisplaySettingList();
		for (TextDisplaySettings user : userList)
		{
			if (user.getUserID().equalsIgnoreCase(userID))
			{
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<VisualDisplaySettings> getUserbyBiometricIDForVisual(String bioMetricId) {
		ArrayList<VisualDisplaySettings> settingList = ListHolder.getVisualDisplaySettingsList();
		ArrayList<VisualDisplaySettings> tempList = new ArrayList<VisualDisplaySettings>();
		for (VisualDisplaySettings setting : settingList)
		{
			if(setting.getBiometricID().equalsIgnoreCase(bioMetricId)) {
				tempList.add(setting);
			}
		}
		return tempList;
	}

	/**
	 * it will filter the user's by VisualDisplaySettings's userid from MainUserLIst 
	 * @param bioMetricId
	 * @return
	 */
	public ArrayList<VisualDisplaySettings> getFilterUserbyVisualDisplayUserID(String bioMetricId) {
		ArrayList<VisualDisplaySettings> settingList = getUserbyBiometricIDForVisual(bioMetricId);
		ArrayList<User> userList = getUserBeanList();
		ArrayList<VisualDisplaySettings> tempList = new ArrayList<VisualDisplaySettings>();
		tempList.add(new VisualDisplaySettings("-1", "<<", "-1"));
		for (User user : userList)
		{
			boolean ispresent = false;
			VisualDisplaySettings tempSetting;
			for (VisualDisplaySettings setting : settingList)
			{
				//Final : get the all userlist whose id match from mainlist and is not in settinglist.
				// if userID is available in mainlist continue 
				// else throw exception not present in mail list
				// else if userID not available
				if(user.getUid().equalsIgnoreCase(setting.getUserID())) {
					ispresent = true;
					break;
					//					tempList.add(setting);
				} else {
					tempSetting = setting;
					ispresent = false;
				}
			}

			if(ispresent == false) {
				// add userId to TextDisplaySetting as USEr
				VisualDisplaySettings textDisplaySettings = new VisualDisplaySettings(user.getUid(), bioMetricId, "filled by webserivice");
				tempList.add(textDisplaySettings);
			}
		}
		return tempList;
	}
}