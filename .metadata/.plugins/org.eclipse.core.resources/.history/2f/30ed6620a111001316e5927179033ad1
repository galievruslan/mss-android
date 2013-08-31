package com.mss.application;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.mss.application.R;
import com.mss.domain.models.OrderPickedUpItem;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OrderItemPickupActivity extends SherlockFragmentActivity implements LoaderCallbacks<OrderPickedUpItem> {

	private static final String TAG = OrderItemPickupActivity.class.getSimpleName();
	public static final String KEY_ID = "id";
	public static final String KEY_ORDER_PICKUP_ITEM_ID = "order_pickup_item_id";
		
	public static final int LOADER_ID_ORDER_PICKEDUP_ITEM = 0;
	
	static final int PICK_UNIT_OF_MEASURE_REQUEST = 1;

	private long mOrderPickupItemId;
	private long mProductUnitOfMeasureId;
	private int mSelectedCount;
	
	private OrderPickedUpItem mOrderPickedUpItem;
	private TextView mDescription;
	private TextView mPrice;
	private TextView mCount;
	private TextView mAmount;
	private EditText mUnitOfMeasure;
	
	private Button mOneButton;
	private Button mTwoButton;
	private Button mThreeButton;
	private Button mFourButton;
	private Button mFiveButton;
	private Button mSixButton;
	private Button mSevenButton;
	private Button mEightButton;
	private Button mNineButton;
	private Button mNilButton;
	private Button mDelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_item_pickup);

		mOrderPickupItemId = getIntent().getLongExtra(KEY_ORDER_PICKUP_ITEM_ID, RoutePointActivity.ROUTE_POINT_ID_NEW);
		
		mDescription = (TextView) findViewById(R.id.description_text_view);
		mPrice = (TextView) findViewById(R.id.price_text_view);
		mCount = (TextView) findViewById(R.id.count_text_view);
		mAmount = (TextView) findViewById(R.id.amount_text_view);
		mUnitOfMeasure = (EditText) findViewById(R.id.uom_edit_text);
		mUnitOfMeasure.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent activity = new Intent(getApplicationContext(), ProductUomsActivity.class);
				activity.putExtra("product_id", mOrderPickedUpItem.getId());
		    	startActivityForResult(activity, PICK_UNIT_OF_MEASURE_REQUEST);
			}
        });
		
		mOneButton = (Button) findViewById(R.id.button_one);
		mTwoButton = (Button) findViewById(R.id.button_two);
		mThreeButton = (Button) findViewById(R.id.button_three);
		mFourButton = (Button) findViewById(R.id.button_four);
		mFiveButton = (Button) findViewById(R.id.button_five);
		mSixButton = (Button) findViewById(R.id.button_six);
		mSevenButton = (Button) findViewById(R.id.button_seven);
		mEightButton = (Button) findViewById(R.id.button_eight);
		mNineButton = (Button) findViewById(R.id.button_nine);
		mNilButton = (Button) findViewById(R.id.button_nill);
		mDelButton = (Button) findViewById(R.id.button_delete);
		
		mOneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(1);
            }
        });		
		mTwoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(2);
            }
        });		
		mThreeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(3);
            }
        });
		mFourButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(4);
            }
        });
		mFiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(5);
            }
        });
		mSixButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(6);
            }
        });		
		mSevenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(7);
            }
        });		
		mEightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(8);
            }
        });
		mNineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(9);
            }
        });
		mNilButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddDigit(0);
            }
        });
		mDelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeleteDigit();
            }
        });
		
		getSupportLoaderManager().initLoader(LOADER_ID_ORDER_PICKEDUP_ITEM, null, this);
		
		// Let's show the application icon as the Up button
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void AddDigit(int digit) {
		String stringCount = String.valueOf(mSelectedCount);
		if (stringCount.length() == 5)
			return;
		
		stringCount += String.valueOf(digit);
		mSelectedCount = Integer.parseInt(stringCount);
		
		getSupportLoaderManager().restartLoader(LOADER_ID_ORDER_PICKEDUP_ITEM, null, this);
	}
	
	private void DeleteDigit() {
		String stringCount = String.valueOf(mSelectedCount);
		
		if (stringCount.length() > 0)
			stringCount = stringCount.substring(0, stringCount.length() - 1);
		
		if (stringCount.length() == 0)
			mSelectedCount = 0;
		else 
			mSelectedCount = Integer.parseInt(stringCount);
		
		getSupportLoaderManager().restartLoader(LOADER_ID_ORDER_PICKEDUP_ITEM, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_order_item_pickup, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_UNIT_OF_MEASURE_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	mProductUnitOfMeasureId = data.getLongExtra("product_uom_id", 0l);	    
	        	m
	        	
	        	getSupportLoaderManager().restartLoader(LOADER_ID_ORDER_PICKEDUP_ITEM, null, this);
	        }
	    } 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_item_save:
			Intent intent=new Intent();
		    intent.putExtra("order_pickup_item_id", mOrderPickupItemId);
		    intent.putExtra("order_pickup_item_uom_id", mOrderPickedUpItem.getUoMId());
		    intent.putExtra("order_pickup_item_count", mOrderPickedUpItem.getCount());
		    setResult(RESULT_OK, intent);
		    finish();
		    return true;
		default:
			return false;
		}
	}

	@Override
	public Loader<OrderPickedUpItem> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_ID_ORDER_PICKEDUP_ITEM:

			try {
				return new OrderPickedUpItemLoader(this, mOrderPickupItemId);
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<OrderPickedUpItem> loader, OrderPickedUpItem data) {
		mOrderPickedUpItem = data;
				
		if (mOrderPickedUpItem != null) {
			try {
				mSelectedCount = mOrderPickedUpItem.getCount();
				mProductUnitOfMeasureId = mOrderPickedUpItem.getProductUoMId();
				
				mDescription.setText(mOrderPickedUpItem.getName());
				mPrice.setText(mOrderPickedUpItem.getPrice().toString());
				mCount.setText(String.valueOf(mSelectedCount));
				mAmount.setText(mOrderPickedUpItem.getAmount().toString());
				mUnitOfMeasure.setText(mOrderPickedUpItem.getUoMName());				
			} catch (Throwable e) {
				e.printStackTrace();
			}		
		}
	}

	@Override
	public void onLoaderReset(Loader<OrderPickedUpItem> loader) {
		mOrderPickedUpItem = null;
	}
}