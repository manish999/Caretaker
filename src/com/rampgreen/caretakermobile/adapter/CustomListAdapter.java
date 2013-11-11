package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
	
	private static ArrayList<ListItemDetails> itemDetailsrrayList;
	
	LayoutInflater layoutInflater;
	String[] countryName ;
	int[] countryFlag;
	Context context;
	
//	public CustomListAdapter(String[] _name , int[] _icon , Context c) {
//		// TODO Auto-generated constructor stub
//		countryName = _name;
//		countryFlag = _icon;
//		context = c;
//	}
	
	public CustomListAdapter(ArrayList<ListItemDetails> result , Context c) {
		// TODO Auto-generated constructor stub
		itemDetailsrrayList = result;
		context = c;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		//return countryName.length;
		return itemDetailsrrayList.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		//return null;
		return itemDetailsrrayList.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View row = layoutInflater.inflate(R.layout.listlayout, parent,false);
		
		TextView textview = (TextView) row.findViewById(R.id.textView1);
        ImageView imageview = (ImageView) row.findViewById(R.id.imageView1);

        textview.setText(itemDetailsrrayList.get(position).getName());
        imageview.setImageResource(itemDetailsrrayList.get(position).getImage());

        return (row);
	}

}
