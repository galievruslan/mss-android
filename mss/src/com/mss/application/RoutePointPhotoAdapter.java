package com.mss.application;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mss.application.fragments.RoutePointPhotoFragment;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.RoutePointPhoto;
import com.mss.domain.services.RoutePointService;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.utils.IterableHelpers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class RoutePointPhotoAdapter extends FragmentPagerAdapter {
	private static final String TAG = RoutePointPhotoAdapter.class.getSimpleName();
	
	private List<RoutePointPhoto> items;
	public RoutePointPhotoAdapter(Context context, FragmentManager mgr, RoutePoint routePoint) {
		super(mgr);
		
		DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		try {
			RoutePointService routePointService = new RoutePointService(helper);
			items = IterableHelpers.toList(RoutePointPhoto.class, routePointService.findPhotos(routePoint));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			items = new ArrayList<RoutePointPhoto>();
		}
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Fragment getItem(int position) {
		RoutePointPhoto routePointPhoto = items.get(position);
		return(RoutePointPhotoFragment.newInstance(routePointPhoto.getId(), routePointPhoto.getPath()));
	}

	@Override
	public long getItemId(int position) {		
		return items.get(position).getId();
	}
}