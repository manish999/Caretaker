package com.rampgreen.caretakermobile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.adapter.UsersCaretakersImageAdapter;
import com.rampgreen.caretakermobile.model.User;
import com.rampgreen.caretakermobile.model.UserCaretakerBean;

import java.util.ArrayList;
import java.util.List;

public class FragmentUserCaretaker extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	int fragVal;
	GridView gridView;
	static ArrayList<UserCaretakerBean> users;
	static Activity activity;
	UsersCaretakersImageAdapter usersCaretakersImageAdapter;

	public static FragmentUserCaretaker newInstance(
			ArrayList<UserCaretakerBean> user, int pos) {
		FragmentUserCaretaker fragment = new FragmentUserCaretaker();
		users = user;
		Bundle args = new Bundle();
		args.putInt("val", pos);
		fragment.setArguments(args);
		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
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
				getActivity(), users);
		gridView.setAdapter(usersCaretakersImageAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						((TextView) v.findViewById(R.id.grid_item_label))
								.getText(), Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);

	}

	public void refreshAdapter(ArrayList<UserCaretakerBean> users) {
//		usersCaretakersImageAdapter = new UsersCaretakersImageAdapter(
//				getActivity(), users);
		if(usersCaretakersImageAdapter != null)
			usersCaretakersImageAdapter.setList(users);

	}

}