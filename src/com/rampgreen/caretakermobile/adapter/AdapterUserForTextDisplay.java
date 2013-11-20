//package com.rampgreen.caretakermobile.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.rampgreen.caretakermobile.R;
//import com.rampgreen.caretakermobile.model.TextDisplaySettings;
//import com.rampgreen.caretakermobile.model.User;
//
//import java.util.ArrayList;
//
//public class AdapterUserForTextDisplay extends BaseAdapter {
//	public static final int MENU_HOME_USER_LIST = 300;
//	public static final int MENU_NOTIFICATION_USER_LIST = 301;
//	public static final int MENU_HOME_TEXT_DISPLAY_USER_LIST = 302;
//	public static final int MENU_VISUAL_DISPLAY_USER_LIST = 303;
//	
//	private ArrayList<TextDisplaySettings> listItem;
//	private Context context;
//
//	public AdapterUserForTextDisplay(Context context) {
//		this.context = context;
//	}
//
//	public AdapterUserForTextDisplay(Context context, ArrayList<TextDisplaySettings> listItem, int menuType) {
//		this.context = context;
//		this.listItem = getMenuUserList(listItem, menuType);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			convertView = LayoutInflater.from(context).inflate(R.layout.row, null);
//		}
//		ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
//		//			icon.setImageResource(getItem(position).iconRes);
//		TextView title = (TextView) convertView.findViewById(R.id.row_title);
//		title.setText(((User)getItem(position)).getUsername());
//		icon.setVisibility(View.GONE);
//
//		return convertView;
//	}
//
//	public void setList (ArrayList<TextDisplaySettings> list, int menuType) {
//		listItem = getMenuUserList(list, menuType);;
//	}
//
//	@Override
//	public int getCount()
//	{
//
//		return listItem.size();
//	}
//
//	@Override
//	public Object getItem(int arg0)
//	{
//		return listItem.get(arg0);
//	};
//
//	@Override
//	public long getItemId(int position)
//	{
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	public  ArrayList<User> getMenuUserList(ArrayList<User> userList, int menuType) {
//		ArrayList<User> dashUserList = new ArrayList<User>();
//		switch (menuType) {
//		
//		case MENU_HOME_USER_LIST:
//			for (User user : userList)
//			{
//				if(! user.isUserOnHomeScreen()){
//					dashUserList.add(user);
//				}
//			}
//			break;
//			
//		case MENU_NOTIFICATION_USER_LIST:
//			for (User user : userList)
//			{
//				if(! user.isNotification()){
//					dashUserList.add(user);
//				}
//			}
//			break;
//			
//		case MENU_HOME_TEXT_DISPLAY_USER_LIST:
//			dashUserList = userList;
////			for (User user : userList)
////			{
////				if(! user.isUserOnHomeScreen()){
////					dashUserList.add(user);
////				}
////			}
//			break;
//			
//		case MENU_VISUAL_DISPLAY_USER_LIST:
//			dashUserList = userList;
////			for (User user : userList)
////			{
////				if(! user.isUserOnHomeScreen()){
////					dashUserList.add(user);
////				}
////			}
//			break;
//
//		default:
//			break;
//		}
//
//
//		return dashUserList; 
//	}
//
//}