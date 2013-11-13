package com.rampgreen.caretakermobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.AdapterUser;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;

public class FragmentHomeMenuUser extends SherlockListFragment
{
	static ArrayList<User> userList = new ArrayList<User>();


	private static final String KEY_CONTENT = "TestFragment:Content123";
	private AdapterUser adapter;

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
		adapter = new AdapterUser(getActivity(),userList);
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
	public void onSaveInstanceState(Bundle outState) {
		//		outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
		//		outState.putInt(Constants.TAB_TYPE, whichTab);
		//		outState.putString(Constants.ID_PROFILE, profileID);
		//		outState.putString(Constants.ID_ACCOUNT, accountToken);
		//		outState.putString(Constants.TYPE_TITLE, Constants.IMPRESSIONS);
		//		this.accountToken = (String) AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.ACCOUNT_TOKEN, "");
		//		this.profileID = (String) AppSettings.getPrefernce(getSherlockActivity(), null, AppSettings.PROFILE_ID, "");
		//		super.onSaveInstanceState(outState);
		//		outState.putInt("mColorRes", mColorRes);
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
			ArrayList<User> arrayList = adapter.getMenuUserList(userList);
			User user = arrayList.get(position);
			user.setOnDashboard(true);

			newContent = new FragmentTabBottom();
			bundle.putSerializable(Constants.BUNDLE_KEY_USERS, getDashBoaredList(userList));
			bundle.putInt(Constants.BUNDLE_KEY_POSITION, position);
			newContent.setArguments(bundle);
			//			removeUsers(position);
			adapter.setList(adapter.getMenuUserList(arrayList));
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


}
