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
import com.mss.application.ProductUnitsOfMeasuresAdapter;
import com.mss.application.R;
import com.mss.domain.models.ProductUnitOfMeasure;

public class ProductUomsFragment extends SherlockListFragment {
	private static final String TAG = ProductUomsFragment.class.getSimpleName();
	
	private final Set<OnProductUnitOfMeasureSelectedListener> mOnProductUnitOfMeasureSelectedListeners = 
			new HashSet<ProductUomsFragment.OnProductUnitOfMeasureSelectedListener>(1);
	
	private int mLastPosition;

	public ProductUomsFragment() {
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ListView listView = getListView();
		listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_uom_list, container, false);
				
	    try {
			setListAdapter(new ProductUnitsOfMeasuresAdapter(v.getContext()));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}

	    return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		setLastClickedPosition(position);

		for (OnProductUnitOfMeasureSelectedListener listener : mOnProductUnitOfMeasureSelectedListeners) {
			listener.onProductUnitOfMeasureSelected((ProductUnitOfMeasure) getListAdapter().getItem(position), position, id);
		}
	}

	private void setLastClickedPosition(int position) {
		mLastPosition = position;
	}

	public int getLastClickedPosition() {
		return mLastPosition;
	}

	public void addOnProductUnitOfMeasureSelectedListener(OnProductUnitOfMeasureSelectedListener listener) {
		mOnProductUnitOfMeasureSelectedListeners.add(listener);
	}

	public boolean removeOnProductUnitOfMeasureSelectedListener(OnProductUnitOfMeasureSelectedListener listener) {
		return mOnProductUnitOfMeasureSelectedListeners.remove(listener);
	}
	
	/**
	 * Implement this interface if you want to handle events when the user selects a note from the list
	 */
	public interface OnProductUnitOfMeasureSelectedListener {
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
		void onProductUnitOfMeasureSelected(ProductUnitOfMeasure productUnitOfMeasure, int position, long id);
	}
}