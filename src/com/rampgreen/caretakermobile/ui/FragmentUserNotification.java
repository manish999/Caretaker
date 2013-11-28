package com.rampgreen.caretakermobile.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UserNotificationExpandableListAdapter;
import com.rampgreen.caretakermobile.model.UserNotificationEntity;
import com.rampgreen.caretakermobile.model.UserNotificationEntity.GroupItemEntity;

public class FragmentUserNotification extends SherlockFragment implements
Response.Listener<JSONObject>, Response.ErrorListener{
	
	private ExpandableListView mExpandableListView;
	private List<UserNotificationEntity> mGroupCollection;
	
	public FragmentUserNotification() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.usernotification, null); 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		prepareResource();
		initPage();	
	}
	
	private void prepareResource() {

		mGroupCollection = new ArrayList<UserNotificationEntity>();
		
		UserNotificationEntity ge = new UserNotificationEntity();
		GroupItemEntity gi = ge.new GroupItemEntity();
		
		ge.Name = "GSR";		
		gi.Name = "heart rate is now 130/min";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);
		
		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Heart Rate";		
		gi.Name = "Heart Rate: 130 /min Normal Range: 60-80 /min";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);
		
		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Accelerometer";
		gi.Name = "Accelerometer";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);
		
		ge = new UserNotificationEntity();
		gi = ge.new GroupItemEntity();
		ge.Name = "Temperature";
		gi.Name = "Temprature";
		ge.GroupItemCollection.add(gi);
		mGroupCollection.add(ge);
		

		/*for (int i = 1; i < 6; i++) {
			GroupEntity ge = new GroupEntity();
			ge.Name = "Group" + i;

			for (int j = 1; j < 5; j++) {
				GroupItemEntity gi = ge.new GroupItemEntity();
				gi.Name = "Child" + j;
				ge.GroupItemCollection.add(gi);
			}

			mGroupCollection.add(ge);
		}*/

	}

	private void initPage() {
		mExpandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
		/*mExpandableListView.setDividerHeight(2);*/
		/*mExpandableListView.setChildDivider(getResources().getDrawable(R.color.gray));*/
		UserNotificationExpandableListAdapter adapter = new UserNotificationExpandableListAdapter(getActivity(),
				mExpandableListView, mGroupCollection);

		mExpandableListView.setAdapter(adapter);
	}		
	

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		
	}

	

}
