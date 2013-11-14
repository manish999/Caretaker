package com.rampgreen.caretakermobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;

public class FragmentHomeMenuAlert extends SherlockListFragment
{
	private static final String KEY_CONTENT = "TestFragment:Content123";
	private String test = "test";
	private int position = 0;

	private int[] slider_menu_icon = new int[]{R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher};
	private String[] slider_menu_text = new String[]{"<<","Self","Adam",
			"Brie",
			"Cindy",
	"Jacob"};

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
		Bundle bundle = getSherlockActivity().getIntent().getExtras();
		if(bundle != null) { 
			mFragmentCalledByMenuOption = bundle.getInt(Constants.FRAGMENT_ADD_MENU_CALLER);
			mFragmentCaller = bundle.getInt(Constants.ActivityConstants.FRAGMENT_CALLER);
			mClickedMenuDisease = bundle.getString(Constants.BUNDLE_KEY_DISEASE);
		}
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
		SampleAdapter adapter = new SampleAdapter(getActivity());
		for (int i = 0; i < 4; i++) {
			adapter.add(new SampleItem(slider_menu_text[i], slider_menu_icon[i]));
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

	public class SampleAdapter extends ArrayAdapter<SampleItem> {
		ArrayList<SampleItem> listItem;
		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			//			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			icon.setVisibility(View.GONE);

			return convertView;
		}

		public void setList (ArrayList<SampleItem> list) {

		}

	}

	private class SampleItem {
		public String tag;
		//		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			//			this.iconRes = iconRes;
		}
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
			newContent = new FragmentHomeMenuDisease();
			switchMenuContent(newContent);
		} else {
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


}
