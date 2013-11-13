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
import com.rampgreen.caretakermobile.model.User;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {
		private ArrayList<User> listItem;
		private Context context;
		
		public AdapterUser(Context context) {
			this.context = context;
		}
		
		public AdapterUser(Context context, ArrayList<User> listItem) {
			this.context = context;
			this.listItem = getMenuUserList(listItem);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			//			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(((User)getItem(position)).getName());
			icon.setVisibility(View.GONE);

			return convertView;
		}

		public void setList (ArrayList<User> list) {
			listItem = getMenuUserList(list);;
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
		
		public  ArrayList<User> getMenuUserList(ArrayList<User> userList) {
			ArrayList<User> dashUserList = new ArrayList<User>();
			for (User user : userList)
			{
				if(! user.isOnDashboard()){
					dashUserList.add(user);
				}
			}
			return dashUserList; 
		}

	}