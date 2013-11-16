package com.rampgreen.caretakermobile.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.User;

public class UsersCaretakersImageAdapter extends BaseAdapter {
	private Context context;
	private final List<User> caretakersUsers;

	public UsersCaretakersImageAdapter(Context context, List<User> caretakersUsers) {
		this.context = context;
		this.caretakersUsers = caretakersUsers;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from activity_users_caretakers.xml
			gridView = inflater.inflate(R.layout.activity_users_caretakers,
					null);

			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(caretakersUsers.get(position).getUsername());

			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);

			//String user = caretakersUsers[position];
			imageView.setImageResource(R.drawable.user);
		} else {
			gridView = (View) convertView;
		}

		return gridView;
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

}
