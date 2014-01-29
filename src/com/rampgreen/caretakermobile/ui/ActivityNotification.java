/*
 * Copyright 2013 Tim Roes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rampgreen.caretakermobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.EnhancedListAdapter;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;
import com.rampgreen.caretakermobile.util.StringUtils;

import java.util.ArrayList;

import de.timroes.android.listview.EnhancedListView;

public class ActivityNotification extends SherlockFragmentActivity {

	private EnhancedListAdapter mAdapter;
	private EnhancedListView mListView;
	private String calledComponent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Notification ");

		getSupportActionBar().setHomeButtonEnabled (true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			calledComponent = bundle.getString(Constants.CALLED_COMPONENT);
			if(StringUtils.notEmpty(calledComponent) && calledComponent.equalsIgnoreCase(Constants.SERVICE_GCM_INTENT)) {
				
			} else if(StringUtils.notEmpty(calledComponent) && calledComponent.equalsIgnoreCase(Constants.ACTIVITY_FRAGMENT_CHANGE_ACTIVITY)) {
				getSupportActionBar().setHomeButtonEnabled (true);
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//				getSupportActionBar().setDisplayUseLogoEnabled (true);
			} else {
				AppLog.e("called component is empty in ActivityNotification");
			}
		}

		mAdapter = new EnhancedListAdapter(this);
		mAdapter.resetItems();
		//		if(StringUtils.notEmpty(msg)) {
		//			mAdapter.add(msg);
		//		}
		mListView = (EnhancedListView)findViewById(R.id.list);
		mListView.setAdapter(mAdapter);

		// Set the callback that handles dismisses.
		mListView.setDismissCallback(new de.timroes.android.listview.EnhancedListView.OnDismissCallback() {
			/**
			 * This method will be called when the user swiped a way or deleted it via
			 * {@link de.timroes.android.listview.EnhancedListView#delete(int)}.
			 *
			 * @param listView The {@link EnhancedListView} the item has been deleted from.
			 * @param position The position of the item to delete from your adapter.
			 * @return An {@link de.timroes.android.listview.EnhancedListView.Undoable}, if you want
			 *      to give the user the possibility to undo the deletion.
			 */
			@Override
			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {

				final String item = (String) mAdapter.getItem(position);
				mAdapter.remove(position);
				return new EnhancedListView.Undoable() {
					@Override
					public void undo() {
						mAdapter.insert(position, item);
					}
				};
			}
		});

		// Show toast message on click and long click on list items.
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AppLog.showToast(ActivityNotification.this, "Clicked on item " + mAdapter.getItem(position));
			}
		});
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				AppLog.showToast(ActivityNotification.this, "Long clicked on item " + mAdapter.getItem(position));
				return true;
			}
		});

		mListView.setSwipingLayout(R.id.swiping_layout);

		//		applySettings();
		defaultSettings();

	}

	private void defaultSettings() {
		// Set the UndoStyle, the user selected.
		EnhancedListView.UndoStyle style = EnhancedListView.UndoStyle.MULTILEVEL_POPUP;
		mListView.setUndoStyle(style);

		// Enable or disable Swipe to Dismiss
		mListView.enableSwipeToDismiss();

		// Set the swipe direction
		EnhancedListView.SwipeDirection direction= EnhancedListView.SwipeDirection.BOTH; 
		mListView.setSwipeDirection(direction);

		// Enable or disable swiping layout feature
		mListView.setSwipingLayout(R.id.swiping_layout);
	}

	@Override
	protected void onStop() {
		if(mListView != null && mAdapter != null) {
			mListView.discardUndo();
			//			mAdapter.removeUndoItem();
		}
		super.onStop();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			backClickedOnActionBar();
			break;
		case R.id.action_clear_all:
			mAdapter.removeAll();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void backClickedOnActionBar() {
		// opened activity by clicking notification
		if(StringUtils.notEmpty(calledComponent) && calledComponent.equalsIgnoreCase(Constants.SERVICE_GCM_INTENT)) {
			String accessToken = (String)AppSettings.getPrefernce(this, null, AppSettings.ACCESS_TOKEN, "");
			String beanAccessToken = BeanController.getLoginBean().getAccessToken();
			User user = BeanController.getUserBean();
			ArrayList<User> userList = user.getUserList();// getting data in fragmentchangelistactivity
			// if user did logout explicitly, open login screen.
			if(StringUtils.isEmpty(beanAccessToken) || userList == null) {
			// open login activity
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			} else {
				// open home screen
				Intent intent = new Intent(getApplicationContext(), FragmentChangeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				intent.putExtra(Constants.BUNDLE_KEY_USERS, user);
				startActivity(intent);
				// to close the activity
				finish();
			}
		} else if(StringUtils.notEmpty(calledComponent) && calledComponent.equalsIgnoreCase(Constants.ACTIVITY_FRAGMENT_CHANGE_ACTIVITY)) {
			// opened activity by clicking application icon 
			onBackPressed();
		} else {
			AppLog.e("called component is empty in ActivityNotification");
		}
	}
}
