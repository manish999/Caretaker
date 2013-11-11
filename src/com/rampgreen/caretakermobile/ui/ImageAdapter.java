package com.rampgreen.caretakermobile.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.rampgreen.caretakermobile.R;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	// Keep all Images in array
	public Integer[] mThumbIds = {
			R.drawable.pic_1, R.drawable.pic_1,
			R.drawable.pic_1, R.drawable.pic_1,
			R.drawable.pic_1, R.drawable.pic_1,
			R.drawable.pic_1, R.drawable.pic_1,	
			R.drawable.pic_1, R.drawable.pic_1,	
			R.drawable.pic_1, R.drawable.pic_1

	};

	// Constructor
	public ImageAdapter(Context c){
		mContext = c;
	}
	
	public ImageAdapter(Context c, int totalUserOnDashboared){
		mContext = c;
		mThumbIds = new Integer[totalUserOnDashboared];
		for (int i = 0; i < totalUserOnDashboared; i++)
		{
			mThumbIds[i] = R.drawable.user;
		}
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(mThumbIds[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
		return imageView;
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
