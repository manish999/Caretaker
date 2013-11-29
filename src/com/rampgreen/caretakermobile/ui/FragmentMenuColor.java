package com.rampgreen.caretakermobile.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.Constants;

import java.util.ArrayList;

/**
 * This calss is used to implement the Menu bar as listfragment.
 * 
 * @author Manish Pathak
 * 
 */
public class FragmentMenuColor extends SherlockFragment
{

	private int[] slider_menu_icon = new int[] {
			R.drawable.user_slider,
			R.drawable.notification_slider,
			R.drawable.visual_slider,
			R.drawable.text_slider};

	private String[] slider_menu_text = new String[] { 
			"Add User Icon",
			"Add Alert Notification", 
			"Add Visual Display" , 
			"Add Text Display"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.account_exp_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		String[] colors = new String[] { "mnaish", "pathak" };// getResources().getStringArray(R.array.color_names);
		// ArrayAdapter<String> ad = new
		// ArrayAdapter<String>(getActivity(),R.layout.list_sliding_menu,colors);
		// setListAdapter(ad);
		// ArrayAdapter<String> colorAdapter = new
		// ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		// setListAdapter(colorAdapter);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		for (int i = 0; i < 4; i++) {
			adapter.add(new SampleItem(slider_menu_text[i], slider_menu_icon[i]));
		}

		ListView listView = (ListView) getActivity()
				.findViewById(R.id.listView);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id)
			{

				//				String acName = (String) ad.getGroup(groupPosition);

				Fragment newContent = null;
				Bundle bundle = new Bundle();
				// pop all fragments from backstack on click sliding menu
				getActivity().getSupportFragmentManager().popBackStack(null,
						FragmentManager.POP_BACK_STACK_INCLUSIVE);
				switch (position) {
				case 0:
					newContent = FragmentHomeMenuUser.newInstance();
					bundle.putInt(Constants.FRAGMENT_ADD_MENU_CALLER, Constants.ADD_USER);
					bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentHomeMenuDisease);
					newContent.setArguments(bundle);
					break;
				case 1:
					newContent = FragmentHomeMenuNotification.newInstance();
					bundle.putInt(Constants.FRAGMENT_ADD_MENU_CALLER, Constants.ADD_NOTIFICATION);
					bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentHomeMenuDisease);
					newContent.setArguments(bundle);
					break;
				case 2:
					// direct call fragment.
					newContent = FragmentHomeMenuDisease.newInstance();
					bundle.putInt(Constants.FRAGMENT_ADD_MENU_CALLER, Constants.ADD_TEXT_VISUALEXPLORER);
					bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentHomeMenuDisease);
					newContent.setArguments(bundle);
					break;
				case 3:
					newContent = FragmentHomeMenuDisease.newInstance();
					bundle.putInt(Constants.FRAGMENT_ADD_MENU_CALLER, Constants.ADD_TEXT_DISPLAY);
					bundle.putInt(Constants.ActivityConstants.FRAGMENT_CALLER, Constants.ActivityConstants.FragmentHomeMenuDisease);
					newContent.setArguments(bundle);
					break;
				}
				if (newContent != null)
					switchMenuContent(newContent);



			}
		});
	}

	private class SampleItem
	{
		public String tag;
		public int iconRes;

		public SampleItem(String tag, int iconRes)
		{
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem>
	{
		ArrayList<SampleItem> listItem;

		public SampleAdapter(Context context)
		{
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.slider_row, null);
			}
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

		public void setList(ArrayList<SampleItem> list)
		{

		}

	}

	// the meat of switching the above fragment
	private void switchMenuContent(Fragment fragment)
	{
		if (getActivity() == null)
			return;

		if (getActivity() instanceof FragmentChangeActivity)
		{
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchMenuFragment(fragment);
		}
	}

	public void init()
	{



		// We will be adding children on a thread, and then update the ListView
		//		adapter = new ExpandableListAdapter(getSherlockActivity(),
		//				new ArrayList<String>(), new ArrayList<Account>());
		//		Profile profile = new Profile();
		// profile.setProfileName("a");
		// profilesList.add(profile);
		// profile = new Profile();
		// profile.setProfileName("b");
		// profilesList.add(profile);
		// profile = new Profile();
		// profile.setProfileName("c");
		// profilesList.add(profile);
		// profilesList.add(profile);

		//		Account account = new Account();
		//		account.setAccountName("Add User Icon");
		//		account.setProfiles(profilesList);
		//		accountList.add(account);
		//		account = new Account();
		//		account.setAccountName("Add Alert Notification");
		//		account.setProfiles(profilesList);
		//		accountList.add(account);
		//		account = new Account();
		//		account.setAccountName("Add Visual Display");
		//		account.setProfiles(profilesList);
		//		accountList.add(account);
		//		account = new Account();
		//		account.setAccountName("Add Text Display");
		//		account.setProfiles(profilesList);
		//		accountList.add(account);

		//		adapter.addItem(accountList);
		//		// Set this blank adapter to the list view
		//		listView.setAdapter(adapter);
		//		setListAdapter(adapter)
		// setHeader((R.string.profiles), false, false, false);
	}
}
