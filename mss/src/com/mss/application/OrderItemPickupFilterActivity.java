package com.mss.application;

import java.util.HashSet;
import java.util.Set;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.Category;
import com.mss.domain.services.CategoryService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class OrderItemPickupFilterActivity extends SherlockFragmentActivity {
	private static final String TAG = OrderItemPickupFilterActivity.class.getSimpleName();
	
	static final int CATEGORIES_REQUEST = 0;
	
	private DatabaseHelper mHelper;
    private CategoryService mCategoryService;
	
	private CheckBox mInOrder;
	private CheckBox mInStock;
	private EditText mCategories;
	
	private boolean mShowInOrderOnly = false;
	private boolean mShowInStockOnly = false;
	private long[] mCategoriesIds = new long[0];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_item_pickup_filter);
		
		mHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mCategoryService = new CategoryService(mHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		mInOrder = (CheckBox)findViewById(R.id.in_order_filter_check_box);
		mInStock = (CheckBox)findViewById(R.id.in_stock_filter_check_box);
		mCategories = (EditText)findViewById(R.id.categories_filter_text_edit);
		
		Long[] categoriesIds = OrderEditContext.getSelectedCategories().toArray(new Long[0]);
		mCategoriesIds = new long[categoriesIds.length];
		for (int i = 0; i < categoriesIds.length; i++) {
			mCategoriesIds[i] = categoriesIds[i];
		}
		
		mShowInOrderOnly = OrderEditContext.getInOrder();
		mShowInStockOnly = OrderEditContext.getInStock();
		initActivityValues();
		
		mCategories.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {				
				Intent filterActivity = new Intent(getApplicationContext(), CategoriesActivity.class);
				filterActivity.putExtra("categories", mCategoriesIds);
				startActivityForResult(filterActivity, CATEGORIES_REQUEST);
			}
        });
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putBoolean("in_order", mShowInOrderOnly);
		savedInstanceState.putBoolean("in_stock", mShowInStockOnly);
		savedInstanceState.putLongArray("categories_ids", mCategoriesIds);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mShowInOrderOnly = savedInstanceState.getBoolean("in_order");
		mShowInStockOnly = savedInstanceState.getBoolean("in_stock");
		mCategoriesIds = savedInstanceState.getLongArray("categories_ids");
		initActivityValues();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_order_item_pickup_filter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_item_save:
			OrderEditContext.setInOrder(mInOrder.isChecked());
			OrderEditContext.setInStock(mInStock.isChecked());
			Set<Long> categoriesIdsSet = new HashSet<Long>();
			for (long categoryId : mCategoriesIds) {
				if (!categoriesIdsSet.contains(categoryId)) {
					categoriesIdsSet.add(categoryId);
				}
			}
			OrderEditContext.setSelectedCategories(categoriesIdsSet);
			Intent intent=new Intent();
    	    setResult(RESULT_OK, intent);
    	    finish();
			return true;
		default:
			return false;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CATEGORIES_REQUEST) {
	    	if (resultCode == RESULT_OK) {
	    		mCategoriesIds = data.getLongArrayExtra("categories");
	    		initActivityValues();
	    	}
	    } 
	}
	
	private void initActivityValues(){
		mInOrder.setChecked(mShowInOrderOnly);
		mInStock.setChecked(mShowInStockOnly);
		Category[] categories = IterableHelpers.toArray(Category.class, mCategoryService.getCategoriesByIds(mCategoriesIds));
		String selectedCategories = "";
		for (int i = 0; i < categories.length; i++) {
			selectedCategories += categories[i].getName();
			if (i < categories.length - 1) {
				selectedCategories += ", ";
			}
		}
		
		mCategories.setText(selectedCategories);
	}
}
