package com.rampgreen.caretakermobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EnhancedListAdapter extends BaseAdapter {

	Context mContext;
	private LayoutInflater inflater;
	private List<String> mItems = new ArrayList<String>();

	public EnhancedListAdapter(Context context)
	{
		this.mContext = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void resetItems() {
		mItems.clear();
		mItems = msgListFillerFromStore(mItems);
		notifyDataSetChanged();
	}

	public void remove(int position) {
		mItems.remove(position);
		updatePreference(mItems);
		notifyDataSetChanged();
	}
	
	public void removeAll() {
		mItems.clear();
		updatePreference(mItems);
		notifyDataSetChanged();
	}

	public void insert(int position, String item) {
		mItems.add(position, item);
		notifyDataSetChanged();
	}

	public void add(String item) {
		mItems.add(item);
		notifyDataSetChanged();
	}

	public void removeUndoItem()
	{
		updatePreference(mItems);
	}

	/**
	 * How many items are in the data set represented by this Adapter.
	 *
	 * @return Count of items.
	 */
	@Override
	public int getCount() {
		return mItems.size();
	}

	/**
	 * Get the data item associated with the specified position in the data set.
	 *
	 * @param position Position of the item whose data we want within the adapter's
	 *                 data set.
	 * @return The data at the specified position.
	 */
	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	/**
	 * Get the row id associated with the specified position in the list.
	 *
	 * @param position The position of the item within the adapter's data set whose row id we want.
	 * @return The id of the item at the specified position.
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Get a View that displays the data at the specified position in the data set. You can either
	 * create a View manually or inflate it from an XML layout file. When the View is inflated, the
	 * parent View (GridView, ListView...) will apply default layout parameters unless you use
	 * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
	 * to specify a root view and to prevent attachment to the root.
	 *
	 * @param position    The position of the item within the adapter's data set of the item whose view
	 *                    we want.
	 * @param convertView The old view to reuse, if possible. Note: You should check that this view
	 *                    is non-null and of an appropriate type before using. If it is not possible to convert
	 *                    this view to display the correct data, this method can create a new view.
	 *                    Heterogeneous lists can specify their number of view types, so that this View is
	 *                    always of the right type (see {@link #getViewTypeCount()} and
	 *                    {@link #getItemViewType(int)}).
	 * @param parent      The parent that this view will eventually be attached to
	 * @return A View corresponding to the data at the specified position.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.threaded_view_list_item_rec, parent, false);
			//                mListView = inflater.inflate(R.layout.list_item, parent, false);
			// Clicking the delete icon, will read the position of the item stored in
			// the tag and delete it from the list. So we don't need to generate a new
			// onClickListener every time the content of this view changes.
			View view = convertView.findViewById(R.id.relativeLayoutThreadedItem);
			view.getLayoutParams().width = LayoutParams.FILL_PARENT;
//			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//					RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//			view.setLayoutParams(lp);
			final View origView = convertView;
			//                mListView = (EnhancedListView)origView.findViewById(R.id.list);
			//                convertView.findViewById(R.id.action_delete).setOnClickListener(new View.OnClickListener() {
			//                    @Override
			//                    public void onClick(View v) {
			//                        mListView.d1elete(((ViewHolder)origView.getTag()).position);
			//                    }
			//                });21 jan  109718124  

			holder = new ViewHolder();
			assert convertView != null;
			holder.mTextView = (TextView) convertView.findViewById(R.id.TextViewMsg);
//			holder.mTextView.getLayoutParams().width = LayoutParams.FILL_PARENT;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.position = position;
		holder.mTextView.setText(mItems.get(position));

		return convertView;
	}

	private class ViewHolder {
		TextView mTextView;
		int position;
	}

	/**
	 * This method update the preference for piped notification msgs
	 * @param mItems2
	 */
	private void updatePreference(List<String> mItems2) {
		String msgItemArray = "";
		if(mItems2.size() == 0) {
			AppSettings.setPreference(mContext, null, AppSettings.STORE_NOTI_LIST, msgItemArray);
		} else {
			Iterator<String> it = mItems2.iterator();
			if (it.hasNext()) {
				msgItemArray = it.next(); 
			}
			while (it.hasNext()) {
				msgItemArray =msgItemArray + "|" + it.next();
			}
		}
		AppSettings.setPreference(mContext, null, AppSettings.STORE_NOTI_LIST, msgItemArray);
	}

	private List<String> msgListFillerFromStore(List<String> itemList) {
		String msgsSeperatedByPipe = (String)AppSettings.getPrefernce(mContext, null, AppSettings.STORE_NOTI_LIST, "");
		if(StringUtils.notEmpty(msgsSeperatedByPipe)) {
			String[] msgArray = {};
			if(msgsSeperatedByPipe.contains("|"))
				msgArray = msgsSeperatedByPipe.split("\\|");
			else {
				msgArray = new String[]{msgsSeperatedByPipe};
			}
			List<String> temp = Arrays.asList(msgArray); // could not attached directly because method return fixed size list.
			itemList.addAll(temp);
		} else {
			// code for empty msg
		}
		return itemList;
	}

}