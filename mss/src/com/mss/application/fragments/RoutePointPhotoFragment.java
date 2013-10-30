package com.mss.application.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.mss.application.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RoutePointPhotoFragment extends SherlockFragment {
	private static final String KEY_ID="id";
	private static final String KEY_URI="uri";

	public static RoutePointPhotoFragment newInstance(Long id, String uri) {
		RoutePointPhotoFragment frag=new RoutePointPhotoFragment();
		Bundle args=new Bundle();

		args.putLong(KEY_ID, id);
		args.putString(KEY_URI, uri);		
		frag.setArguments(args);

		return(frag);
	}

	public long getRoutePointPhotoId() {
		return getArguments().getLong(KEY_ID);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.item_layout_photo, container, false);
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		final String uri = "file://" + getArguments().getString(KEY_URI);
		  
		ImageView imageView = (ImageView) result.findViewById(R.id.image);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.resetViewBeforeLoading(true)
		  	.cacheInMemory(true)
            .cacheOnDisc(true)
		  	.imageScaleType(ImageScaleType.EXACTLY)
		  	.build();
        
		imageLoader.displayImage(uri, imageView, options);
		return(result);
	}
}