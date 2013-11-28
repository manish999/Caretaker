package com.rampgreen.caretakermobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UsersCaretakersImageAdapter;
import com.rampgreen.caretakermobile.model.UserCaretakerBean;
import com.rampgreen.caretakermobile.util.AppLog;

import java.util.ArrayList;

public class FragmentUserCaretaker extends SherlockFragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	private GridView gridView;
	private ArrayList<UserCaretakerBean> users;
	private UsersCaretakersImageAdapter usersCaretakersImageAdapter;

	public static FragmentUserCaretaker newInstance() {
		return new FragmentUserCaretaker();
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.grid, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gridView = (GridView) getView().findViewById(R.id.users_caretakers);
		usersCaretakersImageAdapter = new UsersCaretakersImageAdapter(
				getSherlockActivity(), users);
		gridView.setAdapter(usersCaretakersImageAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Bundle bundle = new Bundle();
				Intent intent = new Intent();
				bundle.putString("title", users.get(position).getUserName());
				intent.setClass(getSherlockActivity(), HomeActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

				AppLog.logToast(getSherlockActivity(), "posi" + position);

			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);

	}

	public void refreshAdapter(ArrayList<UserCaretakerBean> users) {
		this.users = users;
		if(usersCaretakersImageAdapter != null)
			usersCaretakersImageAdapter.setList(users);

	}

}