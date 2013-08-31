package com.mss.application.fragments;

import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.application.MainMenuAdapter;
import com.mss.application.R;

public class MainMenuFragment extends SherlockListFragment {
	private static final String TAG = MainMenuFragment.class.getSimpleName();
	
	private final Set<OnMenuSelectedListener> mOnMenuSelectedListeners = 
			new HashSet<MainMenuFragment.OnMenuSelectedListener>(1);
	
	private int mLastPosition;

	public MainMenuFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_menu_list, container, false);
				
	    try {
			setListAdapter(new MainMenuAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnMenuSelectedListener listener : mOnMenuSelectedListeners) {
			listener.onMenuSelected((MainMenuAdapter.MenuItem) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnMenuSelectedListener(OnMenuSelectedListener listener) {
		mOnMenuSelectedListeners.add(listener);
	}

	public boolean removeOnMenuSelectedListener(OnMenuSelectedListener listener) {
		return mOnMenuSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnMenuSelectedListener {
		/**
		 * Called when the user selects a note entry on the list
		 * 
		 * @param n
		 *            {@link Note} instance
		 * @param position
		 *            its position on the list
		 * @param id
		 *            its id
		 */
		void onMenuSelected(MainMenuAdapter.MenuItem menuItem, int position, long id);
	}
}