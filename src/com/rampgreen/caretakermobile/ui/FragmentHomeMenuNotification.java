package com.rampgreen.caretakermobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.AdapterUser;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;

public class FragmentHomeMenuNotification extends SherlockListFragment
{
	private static final String KEY_CONTENT = "TestFragment:Content123";
	private UserListProvider userListProvider;
	private ArrayList<User> userList;
	private AdapterUser adapter;
	private int mFragmentCalledByMenuOption;
	private int mFragmentCaller;
	private String mClickedMenuDisease;

	public static FragmentHomeMenuNotification newInstance() {
		FragmentHomeMenuNotification fragment = new FragmentHomeMenuNotification();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getSherlockActivity().getIntent().getExtras();
		this.userListProvider = new UserListProvider();
		if(bundle != null) { 
			mFragmentCalledByMenuOption = bundle.getInt(Constants.FRAGMENT_ADD_MENU_CALLER);
			mFragmentCaller = bundle.getInt(Constants.ActivityConstants.FRAGMENT_CALLER);
			mClickedMenuDisease = bundle.getString(Constants.BUNDLE_KEY_DISEASE);
		}
		
		SlidingMenu slidingMenu = ((FragmentChangeActivity)getSherlockActivity()).getSlidingMenu();
		slidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
            	userList = userListProvider.getList(UserListProvider.FOR_MENU_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.NOTIFICATION, true);
        		adapter.setList(userList, AdapterUser.MENU_NOTIFICATION_USER_LIST); 
        		adapter.notifyDataSetChanged();
                AppLog.logToast(getSherlockActivity(), "onOpen");
            }
        });
		slidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
            	AppLog.logToast(getSherlockActivity(), "onOpened");
            }
        });
		slidingMenu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
            	AppLog.logToast(getSherlockActivity(), "onClose");
            }
        });
		slidingMenu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
            	AppLog.logToast(getSherlockActivity(), "onClosed");
            }
        });
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRetainInstance(true);
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState != null ) {
			//			whichTab  = savedInstanceState.getInt(Constants.TAB_TYPE);
			//			profileID  = savedInstanceState.getString(Constants.ID_PROFILE);
			//			accountToken  = savedInstanceState.getString(Constants.ID_ACCOUNT);
		}
		
		userList = userListProvider.getList(UserListProvider.FOR_MENU_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.NOTIFICATION, true);
		adapter = new AdapterUser(getActivity(),userList, AdapterUser.MENU_NOTIFICATION_USER_LIST);
		setListAdapter(adapter);
		
//		SampleAdapter adapter = new SampleAdapter(getActivity());
//		for (int i = 0; i < 4; i++) {
//			adapter.add(new SampleItem(slider_menu_text[i], slider_menu_icon[i]));
//		}
//		setListAdapter(adapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//		Fragment f =  getFragmentManager().findFragmentById(R.id.content_frame);
		//		if (f != null) 
		//			getFragmentManager().be ginTransaction().hide(f).commit();
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		Bundle bundle = new Bundle();
		AppLog.logToast(getSherlockActivity(), position+"");
		// get total users on dashboared
		//		String	totalUser = (String)AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.TEMP_TOTAL_USER, "0");
		//		int totuser= Integer.parseInt(totalUser);

		String	dashUser = (String)AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.TEMP_DASHBOARD_USER, "00000");
		//pop all fragments from backstack on click sliding menu
		//		getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

		if(position == 0) {
			newContent = new FragmentMenuColor();
			switchMenuContent(newContent);
		} else {
			// on click slider menu close and  notification is to be set via web service and toast shown that notification has been set.
			userList = userListProvider.getList(UserListProvider.FOR_MENU_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.NOTIFICATION, true);
			User user = userList.get(position);
			user.setNotification(true);
			
			AppLog.logToast(getSherlockActivity(), position+"");
			
			newContent = new FragmentTabBottom();
//			bundle.putInt(Constants.BUNDLE_KEY_POSITION, position);
//			bundle.putInt(Constants.FRAGMENT_ADD_MENU_CALLER, mFragmentCalledByMenuOption);
//			bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentChangeActivity);
//			bundle.putString(Constants.BUNDLE_KEY_DISEASE, mClickedMenuDisease);
//			newContent.setArguments(bundle);
			switchToHomeContent(newContent);
		}

	}

	// the meat of switching the above fragment
	private void switchMenuContent(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchMenuFragment(fragment);
		} 
	}

	// this method is used to switch the homecontent every time new instance
	private void switchToHomeContent(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		} 
	}


}
