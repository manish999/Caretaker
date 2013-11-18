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

import java.util.ArrayList;
import java.util.List;

public class FragmentUserCaretaker extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";  
	GridView gridView;
	static ArrayList<User> users;
	static Activity activity;
	UsersCaretakersImageAdapter usersCaretakersImageAdapter;

	public static FragmentUserCaretaker newInstance(String content,ArrayList<User> user) {
		FragmentUserCaretaker fragment = new FragmentUserCaretaker();
		users= user;
		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
		//            mContent = savedInstanceState.getString(KEY_CONTENT);
		//        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.grid, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		gridView = (GridView) getActivity().findViewById(R.id.users_caretakers);
		usersCaretakersImageAdapter = new UsersCaretakersImageAdapter(getActivity(), users);
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



		//super.onActivityCreated(savedInstanceState);

		/* if (activity != null) {

    	   mGridAdapter = new GridAdapter(activity, gridItems);
    	   if (mGridView != null) {
    	    mGridView.setAdapter(mGridAdapter);
    	   }

    	   mGridView.setOnItemClickListener(new OnItemClickListener() {
    	    @Override
    	    public void onItemClick(AdapterView parent, View view,
    	      int position, long id) {
    	     onGridItemClick((GridView) parent, view, position, id);
    	    }
    	   });
    	  }	
		 */

		/*
      	String[] users={"ABC","XYZ"};
      	getActivity().setContentView(R.layout.grid);
    	gridView = (GridView) getActivity().findViewById(R.id.users_caretakers);
		//System.out.println("Users count" + users.length);
		gridView.setAdapter(new UsersCaretakersImageAdapter(getActivity(), users));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						((TextView) v.findViewById(R.id.grid_item_label))
								.getText(), Toast.LENGTH_SHORT).show();

			}
		});
		 */


	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}    

	public void refreshAdapter(ArrayList< User> users) {
		usersCaretakersImageAdapter.setList(users);
	}

}