package com.rampgreen.caretakermobile.model;

import java.util.ArrayList;
import java.util.List;



public class UserNotificationEntity {

	public String Name;
	public List<GroupItemEntity> GroupItemCollection;

	public UserNotificationEntity()
	{
		GroupItemCollection = new ArrayList<GroupItemEntity>();
	}

	public class GroupItemEntity
	{
		public String Name;
	}

}
