package com.rampgreen.caretakermobile.model;

import java.util.ArrayList;

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
}
