package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.UserCaretakerBean;

import java.util.ArrayList;

public class UsersCaretakersImageAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<UserCaretakerBean> caretakersUsers;
	private LayoutInflater inflater;

	public UsersCaretakersImageAdapter(Context context, ArrayList<UserCaretakerBean> caretakersUsers) {
		this.context = context;
		this.caretakersUsers = caretakersUsers;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderUsersCaretakers holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.icon, null);
			holder = new ViewHolderUsersCaretakers();
			holder.icontext = (TextView) convertView.findViewById(R.id.icon_text);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon_image);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
			holder.imageView.setLayoutParams(layoutParams);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderUsersCaretakers) convertView.getTag();
		}
		holder.icontext.setText(caretakersUsers.get(position).getUserName());
		holder.icontext.getPaint().setFakeBoldText(true);
		return convertView;
	}

	static class ViewHolderUsersCaretakers {
		TextView icontext;
		ImageView imageView;
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

	public void setList(ArrayList<UserCaretakerBean> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		notifyDataSetChanged();
	}
}
