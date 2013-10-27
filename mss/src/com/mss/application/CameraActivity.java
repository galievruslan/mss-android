package com.mss.application;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity  extends Activity {
	private static final String TAG = CameraActivity.class.getSimpleName();
	
	public static final String IMAGE_PATH = "image_path";
	
	private SurfaceView preview=null;
	private SurfaceHolder previewHolder=null;
	private Camera camera=null;
	private boolean inPreview=false;
	private boolean cameraConfigured=false;
	  
	private boolean mIsFlashOn = false;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_camera);

	    preview=(SurfaceView)findViewById(R.id.preview);
	    previewHolder=preview.getHolder();
	    previewHolder.addCallback(surfaceCallback);
	    
	    ImageView takePhoto = (ImageView)findViewById(R.id.take_photo_image_view);
	    takePhoto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				 if (inPreview) {
					 camera.takePicture(null, null, photoCallback);
				 	inPreview=false;
				 }				
			}
		});
	    
	    ImageView flashSwitcher = (ImageView)findViewById(R.id.flash_switcher_image_view);
	    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
	    	flashSwitcher.setOnClickListener(new OnClickListener() {			
	    		@Override
	    		public void onClick(View v) {	    	
	    			ImageView flashSwitcher = (ImageView)v;
	    			
	    			if (mIsFlashOn) {
	    				mIsFlashOn = false;
	    				flashSwitcher.setImageResource(R.drawable.ic_no_flash);
	    			} else {
	    				mIsFlashOn = true;
	    				flashSwitcher.setImageResource(R.drawable.ic_flash);
	    				
	    				if (inPreview) {
	    					camera.stopPreview();
	    				}

	    				camera.release();
	    				camera=null;
	    				inPreview=false;
	    				
	    				camera=Camera.open();
	    				startPreview();
	    			}
	    		}
			});
	    } else {
	    	flashSwitcher.setVisibility(View.GONE);
	    }
	    
	    
	    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
	    	previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	    
	}

	@Override
	public void onResume() {
		super.onResume();
	    
		if (camera == null) {
			camera=Camera.open();
		}

		startPreview();
	}

	@Override
	public void onPause() {
		if (inPreview) {
	    	camera.stopPreview();
	    }

	    camera.release();
	    camera=null;
	    inPreview=false;

	    super.onPause();
	}

	private Camera.Size getBestPreviewSize(int width, int height,
											Camera.Parameters parameters) {
		Camera.Size result = null;

	    for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
	    	if (size.width <= width && size.height <= height) {
	    		if (result == null) {
	    			result=size;
	    		}
	    		else {
	    			int resultArea=result.width * result.height;
	    			int newArea=size.width * size.height;

	    			if (newArea > resultArea) {
	    				result = size;
	    			}
	    		}
	    	}
	    }

	    return(result);
	}

	private Camera.Size getPictureSize(Camera.Parameters parameters) {
	    Camera.Size result = null;

	    for (Camera.Size size : parameters.getSupportedPictureSizes()) {
	    	if (result == null) {
	    		result = size;
	    	}
	    	else {
	    		int resultArea = result.width * result.height;
	    		int newArea = size.width * size.height;

	    		if (newArea > resultArea) {
	    			result = size;
	    		}
	    	}
	    }

	    return(result);
	}

	private void initPreview(int width, int height) {
	    if (camera != null && previewHolder.getSurface() != null) {
	    	try {
	    		camera.setPreviewDisplay(previewHolder);
	    	}
	    	catch (Throwable t) {
	    		Log.e("PreviewDemo-surfaceCallback",
	    				"Exception in setPreviewDisplay()", t);
	    		Toast.makeText(CameraActivity.this, t.getMessage(),
	    				Toast.LENGTH_LONG).show();
	    	}

	    	if (!cameraConfigured) {
	    		Camera.Parameters parameters = camera.getParameters();
	    		Camera.Size size = getBestPreviewSize(width, height, parameters);
	    		Camera.Size pictureSize = getPictureSize(parameters);

	    		if (size != null && pictureSize != null) {
	    			if (mIsFlashOn)
	    				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
	        	
	    			parameters.setPreviewSize(size.width, size.height);
	    			parameters.setPictureSize(pictureSize.width,
	    					pictureSize.height);
	    			parameters.setPictureFormat(ImageFormat.JPEG);
	    			camera.setParameters(parameters);
	    			cameraConfigured=true;
	    		}
	    	}
	    }
	}

	private void startPreview() {
	    if (cameraConfigured && camera != null) {
	    	camera.startPreview();
	    	inPreview=true;
	    }
	}

	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
	    public void surfaceCreated(SurfaceHolder holder) {
	    	
	    	//setCameraDisplayOrientation(getWindowManager(), CameraInfo.CAMERA_FACING_BACK, camera);
	    }

	    public void surfaceChanged(SurfaceHolder holder, int format,
	                               int width, int height) {
	    	initPreview(width, height);
	    	startPreview();
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	// no-op
	    }
	};

	Camera.PictureCallback photoCallback=new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, (data != null) ? data.length : 0);

                Configuration configuration = getResources().getConfiguration(); 
                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // Notice that width and height are reversed
                    Bitmap scaled = Bitmap.createScaledBitmap(bm, screenHeight, screenWidth, true);
                    int w = scaled.getWidth();
                    int h = scaled.getHeight();
                    // Setting post rotate to 90
                    Matrix mtx = new Matrix();
                    mtx.postRotate(90);
                    // Rotating Bitmap
                    bm = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);
                }
				
				File storage = new File(Environment.getExternalStoragePublicDirectory(
				        Environment.DIRECTORY_PICTURES), "mss");
			
				if (!storage.exists()) {
					storage.mkdir();
				}
					
				File photo=
						new File(storage.getPath() + "/" + String.valueOf(UUID.randomUUID()) + ".jpeg");
					
				FileOutputStream out = new FileOutputStream(photo.getPath());
				bm.compress(CompressFormat.JPEG, 100, out);
			    out.close();
							    				
				Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			    mediaScanIntent.setData(Uri.fromFile(photo));
			    sendBroadcast(mediaScanIntent);		
			    
			    camera.stopPreview();
				camera.release();
				camera = null;
				
				Intent intent=new Intent();
			    intent.putExtra(IMAGE_PATH, photo.getPath());
			    setResult(RESULT_OK, intent);
				finish();
			}
			catch (java.io.IOException e) {
				Log.e(TAG, "Exception in photoCallback", e);
			}
		}
	};
}