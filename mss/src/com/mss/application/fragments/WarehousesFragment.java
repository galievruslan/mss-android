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
import com.mss.application.R;
import com.mss.application.WarehouseAdapter;
import com.mss.domain.models.Warehouse;

public class WarehousesFragment extends SherlockListFragment {
	private static final String TAG = WarehousesFragment.class.getSimpleName();
	
	private final Set<OnWarehouseSelectedListener> mOnWarehouseSelectedListeners = 
			new HashSet<WarehousesFragment.OnWarehouseSelectedListener>(1);
	
	private int mLastPosition;

	public WarehousesFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_warehouse_list, container, false);
				
	    try {
			setListAdapter(new WarehouseAdapter(v.getContext()));
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
	            View dialogView = inflater.inflate(R.layout.dialog_warehouse_info, null);
	            TextView name = (TextView) dialogView.findViewById(R.id.name_text_view);
	            TextView address = (TextView) dialogView.findViewById(R.id.address_text_view);
	            
	            Warehouse warehouse = (Warehouse) getListAdapter().getItem(position);
	            name.setText(warehouse.getName());
	            address.setText(warehouse.getAddress());

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

		for (OnWarehouseSelectedListener listener : mOnWarehouseSelectedListeners) {
			listener.onWarehouseSelected((Warehouse) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnWarehouseSelectedListener(OnWarehouseSelectedListener listener) {
		mOnWarehouseSelectedListeners.add(listener);
	}

	public boolean removeOnWarehouseSelectedListener(OnWarehouseSelectedListener listener) {
		return mOnWarehouseSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnWarehouseSelectedListener {
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
		void onWarehouseSelected(Warehouse warehouse, int position, long id);
	}
}