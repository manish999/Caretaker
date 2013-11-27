package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.rampgreen.caretakermobile.MyRequestQueue;
import com.rampgreen.caretakermobile.MyVolley;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.AdapterUser;
import com.rampgreen.caretakermobile.model.BaseDeleteSettings;
import com.rampgreen.caretakermobile.model.BeanController;
import com.rampgreen.caretakermobile.model.ListHolder;
import com.rampgreen.caretakermobile.model.TextDisplaySettings;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserListProvider;
import com.rampgreen.caretakermobile.model.VisualDisplaySettings;
import com.rampgreen.caretakermobile.network.CustomRequest;
import com.rampgreen.caretakermobile.network.QueryHelper;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;
import java.util.Map;

public class FragmentHomeMenuAlert extends SherlockListFragment implements  Response.Listener<JSONObject>, Response.ErrorListener
{
	private static final String KEY_CONTENT = "TestFragment:Content123";
	private UserListProvider userListProvider;
	private ArrayList<?> userList;
	private AdapterUser adapter;
	private int mFragmentCalledByMenuOption;
	private int mFragmentCaller;
	private String mClickedMenuDisease;

	public static FragmentHomeMenuAlert newInstance() {
		FragmentHomeMenuAlert fragment = new FragmentHomeMenuAlert();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
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
				int menuType = AdapterUser.MENU_HOME_TEXT_DISPLAY_USER_LIST;
				userList = getUserListByDisease(mClickedMenuDisease);
				if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_DISPLAY){
					menuType = AdapterUser.MENU_HOME_TEXT_DISPLAY_USER_LIST;
				} else if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_VISUALEXPLORER) {
					menuType = AdapterUser.MENU_VISUAL_DISPLAY_USER_LIST;
				}
				adapter.setList(userList, menuType); 
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
		adapter = new AdapterUser(getActivity());
		int menuType = AdapterUser.MENU_HOME_TEXT_DISPLAY_USER_LIST;
		if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_DISPLAY){
			menuType = AdapterUser.MENU_HOME_TEXT_DISPLAY_USER_LIST;
			userList = getUserListByDisease(mClickedMenuDisease);
			adapter.setList(userList, menuType);
		} else if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_VISUALEXPLORER) {
			menuType = AdapterUser.MENU_VISUAL_DISPLAY_USER_LIST;
			userList = getUserListByDisease(mClickedMenuDisease);
			adapter.setList(userList, menuType);
		}

		
		
		
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
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		Bundle bundle = new Bundle();
		AppLog.logToast(getSherlockActivity(), position+"");
		if(position == 0) {
			newContent = new FragmentHomeMenuDisease();
			switchMenuContent(newContent);
		} else {
			Object obj = userList.get(position);
			userList = getUserListByDisease(mClickedMenuDisease);
			if(obj instanceof TextDisplaySettings) {
				TextDisplaySettings user = (TextDisplaySettings)obj;
				setArgumentForUser(user, mClickedMenuDisease, true);
				// need send update server too
				setValuesOnWebServer(user, mClickedMenuDisease, true);

			} else if(obj instanceof VisualDisplaySettings) {
				VisualDisplaySettings user =(VisualDisplaySettings)obj;
				setArgumentForUser(user, mClickedMenuDisease, true);
				// need send update server too
				setValuesOnWebServer(user, mClickedMenuDisease, true);
			}
						
			

			AppLog.logToast(getSherlockActivity(), position+"");
			// on click slider menu close and  notification is to be set via web service and toast shown that notification has been set.
			newContent = new FragmentTabBottom();
			bundle.putInt(Constants.BUNDLE_KEY_POSITION, position);
			bundle.putInt(Constants.FRAGMENT_ADD_MENU_CALLER, mFragmentCalledByMenuOption);
			bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentChangeActivity);
			bundle.putString(Constants.BUNDLE_KEY_DISEASE, mClickedMenuDisease);
			
			newContent.setArguments(bundle);
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

	private ArrayList<?> getUserListByDisease(String diseaseType) {
		ArrayList<TextDisplaySettings> userList = null;
		int menuType = mFragmentCalledByMenuOption;
		if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_DISPLAY) {
			menuType = UserListProvider.TEXT_DISPLAY;
			return userListProvider.getFilterUserbyTextDisplayUserID(diseaseType);
			
		} else if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_VISUALEXPLORER){
			menuType = UserListProvider.VISUAL_DISPLAY;
			return userListProvider.getFilterUserbyVisualDisplayUserID(diseaseType);
		}
		return userList;
	}

	private void setArgumentForUser(Object user, String diseaseType, boolean setterValue) {
		
		if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_DISPLAY) {
			ListHolder.getTextDisplaySettingList().add((TextDisplaySettings)user);
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
//				user.setGsrTextDisplay(setterValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
//				user.setHeartRateTextDisplay(setterValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
//				user.setAccelerometerTextDisplay(setterValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
//				user.setTempratureTextDisplay(setterValue);

			}
		} else if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_VISUALEXPLORER){
			ListHolder.getVisualDisplaySettingsList().add((VisualDisplaySettings)user);
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
//				user.setGsrVisualDisplay(setterValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
//				user.setHeartRateVisualDisplay(setterValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
//				user.setAccelerometerVisualDisplay(setterValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
//				user.setTempratureVisualDisplay(setterValue);
			}
		}
	}

	private void setValuesOnWebServer(BaseDeleteSettings setting, String diseaseType, boolean setterValue) {
		int menuType;
		String biometricValue = setterValue ? "1" : "0";
		
		// set the value on server
		MyRequestQueue queue = MyVolley.getRequestQueue();
		Map<String, String> paramMap = null;
		
		String rquestId = userListProvider.getUser(setting.getUserID()).getRequestId();
		if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_DISPLAY) {
			TextDisplaySettings textDisplaySettings = (TextDisplaySettings)setting;
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
				paramMap = QueryHelper.createAddTextDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "1", biometricValue);
				
			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
				paramMap = QueryHelper.createAddTextDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "2", biometricValue);
				
			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
				paramMap = QueryHelper.createAddTextDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "3", biometricValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
				paramMap = QueryHelper.createAddTextDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "4", biometricValue);

			}
		} else if(mFragmentCalledByMenuOption == Constants.ADD_TEXT_VISUALEXPLORER){
			VisualDisplaySettings visualDisplaySettings = (VisualDisplaySettings)setting;
			if (diseaseType.equalsIgnoreCase(Constants.DISEASE_GSR)) {
				paramMap = QueryHelper.createAddVisualDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "1", biometricValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_HEART_RATE))	{
				paramMap = QueryHelper.createAddVisualDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "2", biometricValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_ACCELEROMETER))	{
				paramMap = QueryHelper.createAddVisualDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "3", biometricValue);

			} else if(diseaseType.equalsIgnoreCase(Constants.DISEASE_TEMPRATURE))	{
				paramMap = QueryHelper.createAddVisualDisplayQuery(BeanController.getLoginBean().getAccessToken(), rquestId, "4", biometricValue);
			}
		}
		
		if(paramMap ==null) {
			throw new NullPointerException("netwrok parameter are null, check method FragmentHomeMenuAlert's setValuesOnWebServer");
		}
		
		CustomRequest customRequest = new CustomRequest(Method.POST,
				Constants.URL_WEB_SERVICE, paramMap, FragmentHomeMenuAlert.this, FragmentHomeMenuAlert.this);
		queue.add(customRequest);
	}

	@Override
	public void onErrorResponse(VolleyError error)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(JSONObject response)
	{
		// TODO Auto-generated method stub
		
	}


}
