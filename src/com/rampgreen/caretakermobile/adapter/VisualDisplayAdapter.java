package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.SensorData;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.model.VisualDisplaySettings;
import com.rampgreen.caretakermobile.ui.GraphHome;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class VisualDisplayAdapter.
 */
public class VisualDisplayAdapter extends BaseAdapter {

	/** The context. */
	private Context context;

	/** The caretakers users. */
	private ArrayList<VisualDisplaySettings> caretakersUsers;

	/** The inflater. */
	private LayoutInflater inflater;

	/** The user list provider. */
	private UserListProvider userListProvider;

	/** The token. */
	String token = BeanController.getLoginBean().getAccessToken();

	/** The holder. */
	private ViewHolder holder;;
	// private TextView accountName;
	/** The account name. */
	String accountName;

	private ArrayList<SensorData> mSensorDataList = new ArrayList<SensorData>();;

	/**
	 * Instantiates a new visual display adapter.
	 * 
	 * @param context
	 *            the context
	 * @param caretakersUsers
	 *            the caretakers users
	 */
	public VisualDisplayAdapter(Context context,
			ArrayList<VisualDisplaySettings> caretakersUsers) {
		this.context = context;
		// this.caretakersUsers = FragmentHomeMenuAlert.visualArrayList;
		this.caretakersUsers = caretakersUsers;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		userListProvider = new UserListProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		User user = userListProvider.getUser(caretakersUsers.get(position)
				.getUserID());
		VisualDisplaySettings setting = caretakersUsers.get(position);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_visual_display,
					null);
			holder = new ViewHolder();
			holder.chartLayout = (LinearLayout) convertView
					.findViewById(R.id.chart_container);

			convertView.setTag(holder);
		}
		else {
			// Get the ViewHolder back to get fast access to the TextView
			holder = (ViewHolder) convertView.getTag();
		}
		// GetSensorData(user.getUid());
		GraphHome graphHome = new GraphHome();
		if(mSensorDataList.size()>0) {
			
			// how much time would take this function 
			View view = graphHome.displayGraph(context, mSensorDataList, user.getUid(),user.getUsername(),
					setting.getBiometricID()); 
//			View view = graphHome.displayGraph(context, mSensorDataList);
			holder.chartLayout.addView(view);}
		return convertView;
	}

	/**
	 * The Class ViewHolder.
	 */
	private static class ViewHolder {

		/** The chart layout. */
		LinearLayout chartLayout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (caretakersUsers != null) {
			return caretakersUsers.size();
		} else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Sets the list.
	 * 
	 * @param caretakersUsers
	 *            the new list
	 */
	public void setList(ArrayList<VisualDisplaySettings> caretakersUsers) {
		this.caretakersUsers = caretakersUsers;
		notifyDataSetChanged();
	}
	
	/**
	 *  used to set the graph data to the list on home screen
	 * @param graphData
	 */
	public void setGraphDataList(ArrayList<SensorData> graphData) {
		this.mSensorDataList = graphData;
		notifyDataSetChanged();
	}
}
