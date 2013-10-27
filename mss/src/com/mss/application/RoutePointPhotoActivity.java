package com.mss.application;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.domain.models.RoutePointPhoto;
import com.mss.domain.services.RoutePointService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

public class RoutePointPhotoActivity extends SherlockActivity {
	private static final String TAG = RoutePointPhotoActivity.class.getSimpleName();
	
	public static final String PHOTO_ID = "photo_id";
	
	private DatabaseHelper mDatabaseHelper;
	private RoutePointService mRoutePointService;
	
	ImageView mImage;
	EditText mComment;
	
	private RoutePointPhoto mRouePointPhoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_point_photo);
		
		mDatabaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		try {
			mRoutePointService = new RoutePointService(mDatabaseHelper);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
				
		mImage = (ImageView)findViewById(R.id.photo_image_view);
		mComment = (EditText)findViewById(R.id.comment_text_edit);        
        
		mRouePointPhoto = mRoutePointService.getPhotoById(getIntent().getLongExtra(PHOTO_ID, 0));
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.resetViewBeforeLoading(true)
	  	.cacheInMemory(true)
	  	.imageScaleType(ImageScaleType.EXACTLY)
	  	.build();
    
		imageLoader.displayImage("file://" + mRouePointPhoto.getPath(), mImage, options);
		mComment.setText(mRouePointPhoto.getComment());		
		
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.route_point_photo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
				Intent intent=new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
			return true;
		case R.id.menu_item_save:
			try {
				if (mRouePointPhoto != null) {
					mRoutePointService.commentPhoto(mRouePointPhoto, mComment.getText().toString());
					
					Intent intent=new Intent();
        			setResult(RESULT_OK, intent);
        			finish();
				}
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());
			}		
			return true;
	case R.id.menu_item_delete:
		try {
			if (mRouePointPhoto != null) {
				new AlertDialog.Builder(this)
	        	.setTitle(R.string.dialog_delete_confirmation_title) 
	        	.setMessage(R.string.dialog_delete_confirmation_message) 
	        	.setIcon(R.drawable.ic_action_delete)
	        	.setPositiveButton(R.string.dialog_delete_confirmation_positive_button, 
	        			new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int whichButton) { 
	        			mRoutePointService.deletePhoto(mRouePointPhoto);
	        			Intent intent=new Intent();
	        			setResult(RESULT_OK, intent);
	        			finish();
	        			dialog.dismiss();
	        		}   
	        	})
	        	.setNegativeButton(R.string.dialog_delete_confirmation_negative_button, new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int which) {
	        			dialog.dismiss();
	        		}
	        	})
	        	.create()
	        	.show();
			}
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}		
		return true;
		default:
			return false;
		}
	}

}
