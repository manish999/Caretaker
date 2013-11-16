package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.AdapterUser;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;
import java.util.Map;
import java.util.RandomAccess;

public class FragmentHomeMenuUser extends SherlockListFragment implements  Response.Listener<JSONObject>, Response.ErrorListener
{
	static ArrayList<User> userList = new ArrayList<User>();


	private static final String KEY_CONTENT = "TestFragment:Content123";
	private AdapterUser adapter;
	private UserListProvider userListProvider;

	public static FragmentHomeMenuUser newInstance() {
		FragmentHomeMenuUser fragment = new FragmentHomeMenuUser();
		// populating the menu userlist with back button 
		userList.clear();
		userList.add(new User("<<", false, null, 0));
		ArrayList<User> userListBean = BeanController.getUserBean().getUserList();
		for (User user : userListBean)
		{
			userList.add(user);
		}
		return fragment;
	}

	/**
	 * these users will come from the database
	 */
	//	static ArrayList<User> addUsers()
	//	{
	//		if(userList.size() == 0 ) 
	//		{
	//			User user = new User("<<", false, null, 0);
	//			userList.add(user);
	//			user = new User("adam", false, null, 0);
	//			userList.add(user);
	//			user = new User("Brie", false, null, 0);
	//			userList.add(user);
	//			user = new User("Cindy", false, null, 0);
	//			userList.add(user);
	//			user = new User("Jacob", false, null, 0);
	//			userList.add(user);
	//			user = new User("Mady", false, null, 0);
	//			userList.add(user);
	//		}
	//		return userList;
	//	}

	/**
	 * remove users will be updated to the database
	 */
	private static void removeUsers(int position)
	{
		userList.remove(position);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle fd = getSherlockActivity().getIntent().getExtras();
		this.userListProvider = new UserListProvider();
		setHasOptionsMenu(true);
		
		SlidingMenu slidingMenu = ((FragmentChangeActivity)getSherlockActivity()).getSlidingMenu();
		slidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
            	userList = userListProvider.getList(UserListProvider.FOR_MENU_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, true);
        		adapter.setList(userList, AdapterUser.MENU_HOME_USER_LIST); 
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
		//		this.accountToken = (String) AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.ACCOUNT_TOKEN, "");
		//		this.profileID = (String) AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.PROFILE_ID, "");
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

		userList = userListProvider.getList(UserListProvider.FOR_MENU_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, true);
		adapter = new AdapterUser(getActivity(),userList, AdapterUser.MENU_HOME_USER_LIST);
		setListAdapter(adapter);
		//		if (savedInstanceState == null) {
		//            FragmentManager fragmentManager = getSherlockActivity().getSupportFragmentManager();
		//            FragmentTransaction transaction = fragmentManager.beginTransaction();
		//            Fragment fragmentGraph = FragmentGraph.instantiate(getSherlockActivity(), "fgraph");
		//            Fragment fragmentGraphList = FragmentGraph.instantiate(getSherlockActivity(), "fgraphList");
		//            transaction.add(R.id.frag_graph, fragmentGraph);
		//            transaction.add(R.id.frag_chartlist, fragmentGraphList);
		//            transaction.commit();
		//        }

		// it is used to get the data from fragmentsectionlist 

	}

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//		Fragment f =  getFragmentManager().findFragmentById(R.id.content_frame);
		//		if (f != null) 
		//			getFragmentManager().be ginTransaction().hide(f).commit();
	}

	//	private void updateVisitFrag(Bundle bundle) {
	//		FragmentTransaction transaction = getSherlockActivity().getSupportFragmentManager().beginTransaction();
	//		//		FragmentManager fm = getSherlockActivity().getSupportFragmentManager();
	//		FragmentGraph fragGraph = new FragmentGraph();
	//		FragmentChartList fragList = new FragmentChartList();
	//		//		Bundle args = new Bundle();
	//		//	args.putInt(ArticleFragment.ARG_POSITION, position);
	//		fragGraph.setArguments(bundle);
	//		fragList.setArguments(bundle);
	//
	//		// Replace whatever is in the fragment_container view with this fragment,
	//		// and add the transaction to the back stack so the user can navigate back
	//		transaction.replace(R.id.frag_graph, fragGraph);
	//		transaction.replace(R.id.frag_chartlist, fragList);
	//		//		transaction.addToBackStack(null);
	//
	//		// Commit the transaction
	//		transaction.commit();
	//		//    if (fm.findFragmentById(R.id.) == null) {
	//		//        fm.beginTransaction()
	//		//                .replace(placeholder, new MyListFragment(tabId),onResponseReceived tabId)
	//		//                .commit();
	//		//    }
	//	}

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
			userList = userListProvider.getList(UserListProvider.FOR_MENU_CONTENT, UserListProvider.NOT_DEFINE, UserListProvider.ADD_USER_ICON, true);
			User user = userList.get(position);
			user.setUserOnHomeScreen(true);
			
			// set the value on server
			MyRequestQueue queue = MyVolley.getRequestQueue();
			Map<String, String> paramMap = QueryHelper.createAddUserIconQuery(BeanController.getLoginBean().getAccessToken(), user.getRequestId(), "1");
			CustomRequest customRequest = new CustomRequest(Method.POST,
					Constants.URL_WEB_SERVICE, paramMap, FragmentHomeMenuUser.this, FragmentHomeMenuUser.this);
			queue.add(customRequest);

			newContent = new FragmentTabBottom();
			//			bundle.putSerializable(Constants.BUNDLE_KEY_USERS, getDashBoaredList(userList));
			//			bundle.putInt(Constants.BUNDLE_KEY_POSITION, position);
			bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentHomeMenuUser);
			newContent.setArguments(bundle);
			//			removeUsers(position);
			adapter.setList(adapter.getMenuUserList(userList, AdapterUser.MENU_HOME_USER_LIST), AdapterUser.MENU_HOME_USER_LIST);
			adapter.notifyDataSetChanged();

			if (newContent != null)
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

	// this mehod is used to switch the homecontent everytime new instanse
	private void switchToHomeContent(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		} 
	}

	private String setPosition(String users, int position) {
		char[] arr = users.toCharArray();
		if(arr[position] == '1') {
			AppLog.showToast(getSherlockActivity(), "User already added on dashboard");
		} else {
			arr[position] = '1';
		}
		AppSettings.setPreference(getSherlockActivity(), null, AppSettings.TEMP_DASHBOARD_USER, new String(arr));
		return new String(arr);

	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// home click
			adapter.setList(adapter.getMenuUserList(userList, AdapterUser.MENU_HOME_USER_LIST),AdapterUser.MENU_HOME_USER_LIST);
			adapter.notifyDataSetChanged();
			return false;
//	    case R.id.activity_menu_item:
//	        // Not implemented here
//	        return false;
//	    case R.id.fragment_menu_item:
//	        // Do Fragment menu item stuff here
//	        return true;
	    default:
	        break;
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		
	}

	@Override
	public void onResponse(JSONObject response)
	{
		
	}
}
