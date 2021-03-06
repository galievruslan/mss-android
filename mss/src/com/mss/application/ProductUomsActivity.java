package com.mss.application;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.mss.application.fragments.ProductUomsFragment;
import com.mss.application.fragments.ProductUomsFragment.OnProductUnitOfMeasureSelectedListener;
import com.mss.domain.models.ProductUnitOfMeasure;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

public class ProductUomsActivity extends SherlockFragmentActivity implements OnProductUnitOfMeasureSelectedListener, Callback, LoaderCallbacks<List<ProductUnitOfMeasure>> {

	private static final String TAG = ProductUomsActivity.class.getSimpleName();
	private static final int LOADER_ID_PRODUCT_UOMS = 0;
	
	private Long mProductId;
	ProductUnitsOfMeasuresAdapter mProductUnitOfMeasuresAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_uoms);
		try {
			mProductUnitOfMeasuresAdapter = new ProductUnitsOfMeasuresAdapter(this);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		mProductId = getIntent().getLongExtra("product_id", 0l);
				
		ProductUomsFragment fragmentProductUoms = getProductUomsFragmentFragment();
		fragmentProductUoms.addOnProductUnitOfMeasureSelectedListener(this);
		
		getSupportLoaderManager().initLoader(LOADER_ID_PRODUCT_UOMS, null, this);
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onProductUnitOfMeasureSelected(ProductUnitOfMeasure productUnitOfMeasure, int position, long id) {
		Intent intent=new Intent();
	    intent.putExtra("product_uom_id", productUnitOfMeasure.getId());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
	protected ProductUomsFragment getProductUomsFragmentFragment() {
		return (ProductUomsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_uom_list);
	}
	
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		// Note that we handle Edit and Delete items here, even if they were
		// added by the NoteFragment.

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return false;
		}
	}

	@Override
	public Loader<List<ProductUnitOfMeasure>> onCreateLoader(int id, Bundle arg1) {
		switch (id) {
		case LOADER_ID_PRODUCT_UOMS:
			try {
				return new ProductUnitsOfMeasuresLoader(this, mProductId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<List<ProductUnitOfMeasure>> loader, List<ProductUnitOfMeasure> data) {
		switch (loader.getId()) {
		case LOADER_ID_PRODUCT_UOMS:
			mProductUnitOfMeasuresAdapter.swapData(data);
			getProductUomsFragmentFragment().setListAdapter(mProductUnitOfMeasuresAdapter);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLoaderReset(Loader<List<ProductUnitOfMeasure>> arg0) {
		mProductUnitOfMeasuresAdapter.swapData(null);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode,
		com.actionbarsherlock.view.Menu menu) {
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode,
		com.actionbarsherlock.view.Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, com.actionbarsherlock.view.MenuItem item) {
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {	}
}
