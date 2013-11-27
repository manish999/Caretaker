package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.TextDisplaySettings;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;

import java.util.ArrayList;

public class TextDisplayAdapter extends BaseAdapter {
	
	private ArrayList<TextDisplaySettings> caretakersUsers;
	private LayoutInflater inflater;
	private UserListProvider userListProvider;

	public TextDisplayAdapter(Context context, ArrayList<TextDisplaySettings> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		userListProvider = new UserListProvider();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null) {
			convertView = inflater.inflate(R.layout.threaded_view_list_item_rec, null);
			holder = new ViewHolder();
			holder.accountName = (TextView)convertView.findViewById(R.id.TextViewMsg); 
			//			holder.imageIcon= (ImageView)convertView.findViewById(R.id.grid_item_image);
			//			holder.imageIcon.setImageResource(R.drawable.user);
			convertView.setTag(holder);
		}
		else {
			// Get the ViewHolder back to get fast access to the TextView
			holder = (ViewHolder) convertView.getTag();
		}
		User user = userListProvider.getUser(caretakersUsers.get(position).getUserID());
		TextDisplaySettings setting = caretakersUsers.get(position);
		holder.accountName.setText(user.getUsername()+ "'s " + setting.getBiometricID());
//		holder.accountName.setText("UerID: "+user.getUid() + " UserName : "+user.getUsername()+ 
//				" BiometricId: "+setting.getBiometricID()+ " Unique ID : "+setting.getUniqueID());
		//		holder.accountName.getPaint().setFakeBoldText(true);
		return convertView;
	}

	public static class ViewHolder {
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

	public void setList(ArrayList<TextDisplaySettings> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		notifyDataSetChanged();
	}
}
