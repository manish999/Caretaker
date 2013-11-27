package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.VisualDisplaySettings;
import com.rampgreen.caretakermobile.model.UserListProvider;


import java.util.ArrayList;

public class VisualDisplayAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<VisualDisplaySettings> caretakersUsers;
	private LayoutInflater inflater;
	private UserListProvider userListProvider;

	public VisualDisplayAdapter(Context context, ArrayList<VisualDisplaySettings> caretakersUsers) {
		this.context = context;
//		this.caretakersUsers = FragmentHomeMenuAlert.visualArrayList;
		this.caretakersUsers = caretakersUsers;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		userListProvider = new UserListProvider();
	}

	//	public View getView(int position, View convertView, ViewGroup parent) {
	//		if (convertView == null) {
	//			convertView = LayoutInflater.from(context).inflate(R.layout.activity_users_caretakers, null);
	//		}	// get layout from activity_users_caretakers.xml
	//			gridView = inflater.inflate(R.layout.activity_users_caretakers,
	//					null);
	//			// set value into textview
	//			TextView textView = (TextView) gridView
	//					.findViewById(R.id.grid_item_label);
	//			textView.setText(caretakersUsers.get(position).getUsername());
	//			// set image based on selected text
	//			ImageView imageView = (ImageView) gridView
	//					.findViewById(R.id.grid_item_image);
	//			//String user = caretakersUsers[position];
	//			imageView.setImageResource(R.drawable.user);
	//		} else {
	//			gridView = (View) convertView;
	//		}
	//
	//		return gridView;
	//	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null) {
			convertView = inflater.inflate(R.layout.list_item_visual_display, null);
			holder = new ViewHolder();
//			holder.accountName = (TextView)convertView.findViewById(R.id.TextViewMsg); 
			holder.imageIcon= (ImageView)convertView.findViewById(R.id.imgview_homeContentChartDisplay);
//			holder.imageIcon.setImageResource(R.drawable.user);
			convertView.setTag(holder);
		}
		else {
			// Get the ViewHolder back to get fast access to the TextView
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.accountName.setText(caretakersUsers.get(position).getUsername());
//		holder.accountName.getPaint().setFakeBoldText(true);
		return convertView;
	}

	private static class ViewHolder {
		TextView accountName;
		ImageView imageIcon;
	}

	@Override
	public int getCount() {
		if(caretakersUsers != null){
			return caretakersUsers.size();
		}else{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void setList(ArrayList<VisualDisplaySettings> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		notifyDataSetChanged();
	}

}
