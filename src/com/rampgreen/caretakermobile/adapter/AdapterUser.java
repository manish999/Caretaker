package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.TextDisplaySettings;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {
	public static final int MENU_HOME_USER_LIST = 300;
	public static final int MENU_NOTIFICATION_USER_LIST = 301;
	public static final int MENU_HOME_TEXT_DISPLAY_USER_LIST = 302;
	public static final int MENU_VISUAL_DISPLAY_USER_LIST = 303;

	private ArrayList<String> listItem;
	private Context context;

	public AdapterUser(Context context) {
		this.context = context;
	}

	//	public AdapterUser(Context context, ArrayList<User> listItem, int menuType) {
	//		this.context = context;
	//		
	//		this.listItem = getMenuUserList(listItem, menuType);
	//	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.row, null);
		}
		ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
		//			icon.setImageResource(getItem(position).iconRes);
		TextView title = (TextView) convertView.findViewById(R.id.row_title);
		title.setText(listItem.get(position));
		icon.setVisibility(View.GONE);

		return convertView;
	}

	@SuppressWarnings("unchecked")
	public void setList (ArrayList<?> list, int menuType) {
		if(list != null && list.size() != 0 ) {
			Object obj = list.get(0);
			if(obj instanceof User) {
				ArrayList<User> userlist = getMenuUserList(list, menuType);
				listItem = getNamesFromUserList(userlist);
			} else  if(obj instanceof TextDisplaySettings) {
				ArrayList<TextDisplaySettings> textDisplaySettingsList  = (ArrayList<TextDisplaySettings>)list;
				listItem = getNamesFromTextDisplayList(textDisplaySettingsList);
			}
		}
	}

	@Override
	public int getCount()
	{

		return listItem.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return listItem.get(arg0);
	};

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("unchecked")
	public  ArrayList<User> getMenuUserList(ArrayList<?> list, int menuType) {
		ArrayList<User> userList = (ArrayList<User>)list;
		ArrayList<User> dashUserList = new ArrayList<User>();
		switch (menuType) {

		case MENU_HOME_USER_LIST:
			for (User user : userList)
			{
				if(! user.isUserOnHomeScreen()){
					dashUserList.add(user);
				}
			}
			break;

		case MENU_NOTIFICATION_USER_LIST:
			for (User user : userList)
			{
				if(! user.isNotification()){
					dashUserList.add(user);
				}
			}
			break;

		case MENU_HOME_TEXT_DISPLAY_USER_LIST:
			dashUserList = userList;
			//			for (User user : userList)
			//			{
			//				if(! user.isUserOnHomeScreen()){
			//					dashUserList.add(user);
			//				}
			//			}
			break;

		case MENU_VISUAL_DISPLAY_USER_LIST:
			dashUserList = userList;
			//			for (User user : userList)
			//			{
			//				if(! user.isUserOnHomeScreen()){
			//					dashUserList.add(user);
			//				}
			//			}
			break;

		default:
			break;
		}


		return dashUserList; 
	}
	
	private  ArrayList<String> getNamesFromUserList(ArrayList<User> userList) {
		ArrayList<String> menuNameList = new ArrayList<String>();
		for (User user : userList)
		{
			menuNameList.add(user.getUsername());
		}
		return menuNameList; 
	}
	
	private  ArrayList<String> getNamesFromTextDisplayList(ArrayList<TextDisplaySettings> userList) {
		ArrayList<String> menuNameList = new ArrayList<String>();
		for (TextDisplaySettings setting : userList)
		{
			menuNameList.add(setting.getUserID());
		}
		return menuNameList; 
	}

}