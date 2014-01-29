package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;

public class DisplayCalendarView  extends BaseAdapter{

	private String[] days;
	private LayoutInflater inflater;
	
	public DisplayCalendarView(Context context,
			String[] days) {
		this.days = days;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	@Override
	public int getCount() {
		if (days != null) {
			return days.length;
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.calendartext, null);
			holder = new ViewHolder();
			holder.SensorData = (TextView) convertView
					.findViewById(R.id.text);		
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			holder = (ViewHolder) convertView.getTag();
		}	
		
		Spanned spanned = Html.fromHtml(DisplayTextValue(days[position]));
		
		holder.SensorData.setText(spanned);
		return convertView;
	}

	private String DisplayTextValue(String string) {
		String BiometricName = "<span><span><span><h1 >" + string  + "</h1>"
				+ "<b><font color=\"green\"> GSR</font></b> : "				
				+ "2.36 Ohms" 
				+ "<br><b><font color=\"blue\"> Pulse</font></b> : 125"
				+ " Beats/min"
				+ "<br><b><font color=\"green\"> HR</font></b>      : 524"				
				+ " Beats/min"
				+ "<br><b><font color=\"blue\"> Ambient</font></b> : 36"			
				+ " Celsius"
				+ "<br><b><font color=\"green\"> Skin</font></b>    : 45"				
				+ " Celsius";
		return BiometricName;
	}

	public static class ViewHolder {
		TextView SensorData;		
	}

}
