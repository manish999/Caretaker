package com.rampgreen.caretakermobile.ui;

import java.util.ArrayList;
import java.util.List;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UserNotificationExpandableListAdapter;
import com.rampgreen.caretakermobile.model.UserNotificationEntity;
import com.rampgreen.caretakermobile.model.UserNotificationEntity.GroupItemEntity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class UserNotification extends Activity {

	private ExpandableListView mExpandableListView;
	private List<UserNotificationEntity> mGroupCollection;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usernotification);
		Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();

        String title = myBundle.getString("title");
        setTitle(title);
        
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
		mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		/*mExpandableListView.setDividerHeight(2);*/
		/*mExpandableListView.setChildDivider(getResources().getDrawable(R.color.gray));*/
		UserNotificationExpandableListAdapter adapter = new UserNotificationExpandableListAdapter(this,
				mExpandableListView, mGroupCollection);

		mExpandableListView.setAdapter(adapter);
	}

}
