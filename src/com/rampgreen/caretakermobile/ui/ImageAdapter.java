package com.rampgreen.caretakermobile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.User;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<User> mUserList;
	private LayoutInflater inflater;

	// Keep all Images in array
	//	public Integer[] mThumbIds = {
	//			R.drawable.pic_1, R.drawable.pic_1,
	//			R.drawable.pic_1, R.drawable.pic_1,
	//			R.drawable.pic_1, R.drawable.pic_1,
	//			R.drawable.pic_1, R.drawable.pic_1,	
	//			R.drawable.pic_1, R.drawable.pic_1,	
	//			R.drawable.pic_1, R.drawable.pic_1
	//
	//	};

	// Constructor
	public ImageAdapter(Context c){
		mContext = c;
		inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	//	public ImageAdapter(Context c, int totalUserOnDashboared){
	//		mContext = c;
	//		mThumbIds = new Integer[totalUserOnDashboared];
	//		for (int i = 0; i < totalUserOnDashboared; i++)
	//		{
	//			mThumbIds[i] = R.drawable.user;
	//		}
	//	}

	public ImageAdapter(Context c, ArrayList<User> user){
		mContext = c;
		mUserList = getDashBoaredList(user);
		inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mUserList.size();
	}

	@Override
	public Object getItem(int position) {
		return mUserList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		//		ImageView imageView = new ImageView(mContext);
		//		imageView.setImageResource(mUserList.get(position).getImageResId());
		//		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		//		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
		//		return imageView;

		// TODO Auto-generated method stub
		View v;
		if(convertView==null){
			LayoutInflater li = inflater;
			v = li.inflate(R.layout.icon, null);
			//			v.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
			TextView tv = (TextView)v.findViewById(R.id.icon_text);
			tv.setText(mUserList.get(position).getName());
			ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
			iv.setLayoutParams(layoutParams);
			//			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//			iv.setImageResource(mUserList.get(position).getImageResId());
		}
		else
		{
			v = convertView;
		}
		return v;

	}

	private ArrayList<User> getDashBoaredList(ArrayList<User> userList) {
		ArrayList<User> dashUserList = new ArrayList<User>();
		for (User user : userList)
		{
			if(user.isOnDashboard()){
				dashUserList.add(user);
			}
		}
		return dashUserList; 
	}

	//	public View getView(int position, View convertView, ViewGroup parent) {
	//
	//		// A ViewHolder keeps references to children views to avoid unneccessary calls
	//		// to findViewById() on each row.
	//		ViewHolder holder;
	//
	//		// When convertView is not null, we can reuse it directly, there is no need
	//		// to reinflate it. We only inflate a new View when the convertView supplied
	//		// by ListView is null.
	//		//		View convertView=convertView;
	//
	//		if(convertView==null)
	//		{
	//			convertView = inflater.inflate(R.layout.userview, null);
	//			holder = new ViewHolder();
	//
	//			holder.txtNumber = (TextView)convertView.findViewById(R.id.text_user); // title
	//			holder.imageView= (ImageView)convertView.findViewById(R.id.image_user); // artist name
	//			convertView.setTag(holder);
	//		}
	//		else {
	//			// Get the ViewHolder back to get fast access to the TextView
	//			holder = (ViewHolder) convertView.getTag();
	//		}
	//
	//		holder.txtNumber.setText("manish");
	//		holder.txtNumber.getPaint().setFakeBoldText(true);
	//		holder.imageView.setImageResource(mThumbIds[position]);
	//		holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	//		holder.imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
	////		if(smsInbox.read.equalsIgnoreCase("1")) {
	////			holder.txtNewStar.setVisibility(View.INVISIBLE);
	////		}else {
	////			holder.txtNewStar.setVisibility(View.VISIBLE);
	////		}
	//
	//		return convertView;
	//	
	////		ImageView imageView = new ImageView(mContext);
	////		imageView.setImageResource(mThumbIds[position]);
	////		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	////		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
	////		return imageView;
	//	}
	//


}
