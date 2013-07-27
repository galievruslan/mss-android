package com.mss.application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Customer;
import com.mss.domain.services.CustomerService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class CustomersActivity extends Activity {

	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customers);
		
		try {
			CustomerService customerService = new CustomerService(getHelper());
			Customer[] values;
			
			values = IterableHelpers.toArray(Customer.class, customerService.Customers());
			CustomersAdapter adapter = new CustomersAdapter(this, values);
			
			mListView = (ListView) findViewById(R.id.list);
		    mListView.setAdapter(adapter);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customers, menu);
		return true;
	}

	private DatabaseHelper databaseHelper = null;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}
}