package com.mss.application.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.mss.application.R;
import com.mss.domain.models.Order;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class OrderFragment extends SherlockFragment implements OnTabChangeListener {
	private static final String TAG = OrderFragment.class.getSimpleName();
	public static final String TAB_GENERAL = "General";
    public static final String TAB_DETAILS = "Details";
    public static final String TAB_NOTES = "Notes";
	
    private View mView;
    private TabHost mTabHost;
    private int mCurrentTab;
    
	private Order mOrder;
	private TextView mShippingDate;
	private TextView mAmount;

	/**
	 * We have to provide a public empty constructor because the framework needs it
	 */
	public OrderFragment() {

	}

	/**
	 * We prefer to instantiate our {@link Fragment Fragments} using a factory method
	 * 
	 * @param note
	 *            the {@link Note} instance
	 * @return the actual {@link NoteFragment} instance
	 */
	public static OrderFragment newInstance(Order order) {
		OrderFragment fragment = new OrderFragment();
		fragment.mOrder = order;

		return fragment;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remember to call this method to enable onCreateOptionsMenu callback

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_order, null);

        mTabHost = (TabHost) mView.findViewById(android.R.id.tabhost);
        setupTabs();
		
		if (mOrder != null) {
			
			//mShippingDate = (TextView) v.findViewById(R.id.order_shipping_date_text_view);
			mShippingDate.setText(DateFormat.getDateFormat(mView.getContext()).format(mOrder.getShippingDate()));
			//mAmount = (TextView) v.findViewById(R.id.order_amount_text_view);
			mAmount.setText(String.valueOf(mOrder.getAmount()));
		}

		return mView;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
 
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        updateTab(TAB_GENERAL, R.id.tab_general);
    }
 
    private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_GENERAL, R.string.label_tab_general, R.id.tab_general));
        mTabHost.addTab(newTab(TAB_DETAILS, R.string.label_tab_details, R.id.tab_details));
        mTabHost.addTab(newTab(TAB_NOTES, R.string.label_tab_notes, R.id.tab_notes));
    }
    
    private TabSpec newTab(String tag, int labelId, int tabContentId) {
        Log.d(TAG, "buildTab(): tag=" + tag);
 
        //View indicator = LayoutInflater.from(getActivity()).inflate(
        //       R.layout.tab,
        //        (ViewGroup) mView.findViewById(android.R.id.tabs), false);
        //((TextView) indicator.findViewById(R.id.text)).setText(labelId);
 
        TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(getString(labelId));
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }
 
    
    
    @Override
    public void onTabChanged(String tabId) {
        if (TAB_GENERAL.equals(tabId)) {
            updateTab(tabId, R.id.tab_general);
            mCurrentTab = 0;
            return;
        }
        if (TAB_DETAILS.equals(tabId)) {
            updateTab(tabId, R.id.tab_details);
            mCurrentTab = 1;
            return;
        }
        if (TAB_NOTES.equals(tabId)) {
            updateTab(tabId, R.id.tab_notes);
            mCurrentTab = 2;
            return;
        }
    }
 
    private void updateTab(String tabId, int placeholder) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tabId);
        if (fragment == null) {
            fm.beginTransaction()
                    .replace(placeholder, new OrderFragment(), tabId)
                    .commit();
        }
    }

	@Override
	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			com.actionbarsherlock.view.MenuInflater inflater) {
		inflater.inflate(R.menu.menu_order, menu);
	}

	public Order getOrder() {
		return mOrder;
	}

	public long getOrderId() {
		if (mOrder != null)
			return mOrder.getId();
		return 0l;
	}

	public void updateContent(Order order) {
		mOrder = order;
		mShippingDate.setText(DateFormat.getDateFormat(getView().getContext()).format(mOrder.getShippingDate()));
		mAmount.setText(String.valueOf(mOrder.getAmount()));
	}
}
