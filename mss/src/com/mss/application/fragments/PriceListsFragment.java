package com.mss.application.fragments;

import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mss.application.PriceListAdapter;
import com.mss.application.R;
import com.mss.domain.models.PriceList;

public class PriceListsFragment extends SherlockListFragment {
	private static final String TAG = PriceListsFragment.class.getSimpleName();
	
	private final Set<OnPriceListSelectedListener> mOnPriceListSelectedListeners = 
			new HashSet<PriceListsFragment.OnPriceListSelectedListener>(1);
	
	private int mLastPosition;

	public PriceListsFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_price_lists, container, false);
				
	    try {
			setListAdapter(new PriceListAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
	        @Override
	        public boolean onItemLongClick(AdapterView<?> adapter, View view,
	                int position, long id) {
	            
	        	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	            // Get the layout inflater
	            LayoutInflater inflater = getActivity().getLayoutInflater();
	            View dialogView = inflater.inflate(R.layout.dialog_price_list_info, null);
	            TextView name = (TextView) dialogView.findViewById(R.id.name_text_view);
	            
	            PriceList priceList = (PriceList) getListAdapter().getItem(position);
	            name.setText(priceList.getName());

	            // Inflate and set the layout for the dialog
	            // Pass null as the parent view because its going in the dialog layout
	        	builder.setView(dialogView)
	        		// Add action buttons
	                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
	                	@Override
	                    public void onClick(DialogInterface dialog, int id) {
	                		dialog.dismiss();
	                	}
	                });
	                   
	        	builder.create().show();
	            return true;

	        }
	    });
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnPriceListSelectedListener listener : mOnPriceListSelectedListeners) {
			listener.onPriceListSelected((PriceList) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnPriceListSelectedListener(OnPriceListSelectedListener listener) {
		mOnPriceListSelectedListeners.add(listener);
	}

	public boolean removeOnPriceListSelectedListener(OnPriceListSelectedListener listener) {
		return mOnPriceListSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnPriceListSelectedListener {
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
		void onPriceListSelected(PriceList priceList, int position, long id);
	}
}