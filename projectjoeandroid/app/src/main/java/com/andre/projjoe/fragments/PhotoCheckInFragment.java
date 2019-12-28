package com.andre.projjoe.fragments;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cameraProcess.CameraManager;
import com.andre.projjoe.R;
import com.andre.projjoe.activities.BaseActivity;
import com.andre.projjoe.adapters.PhotoCheckInAdapter;
import com.andre.projjoe.imageManipulation.ImageManager;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class PhotoCheckInFragment extends Fragment {
	public ArrayList<HashMap<String, String>> feedList = new ArrayList<HashMap<String, String>>();
	
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

	private Uri fileUri; // file url to store image/video

	String struri;

	static String fnameorig = null;
	static String directory = null;
	private ImageView imgPreview;
	private VideoView videoPreview;
	private Button btnRecordVideo;
	private ImageView photoCheckInImageView;
	FragmentInteractionInterface fragmentInteractionInterface;
	DataPasses dataPasses;
	Bitmap bitmap;
	boolean isTransactions = false;
	static File fileRet;
	RecyclerView photoCheckInRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_photocheckin, container, false);
		photoCheckInRecyclerView = (RecyclerView) rootView.findViewById(R.id.photoCheckInRecyclerView);
		photoCheckInImageView = (ImageView) rootView.findViewById(R.id.photoCheckInImageView);

		photoCheckInImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TagPhotoFragment tagPhotoFragment = TagPhotoFragment.newInstance();

				fragmentInteractionInterface.onSwitchFragment(PhotoCheckInFragment.this,tagPhotoFragment);
			}
		});
		if(isTransactions == true || (dataPasses.getAvailablePostList()!=null && dataPasses.getAvailablePostList().size()>10))
			photoChecking();
        return rootView;
    }

    public void photoChecking()
	{
		isTransactions = true;
		if(photoCheckInRecyclerView!=null) {
			photoCheckInRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
			PhotoCheckInAdapter photoCheckInAdapter = new PhotoCheckInAdapter(getActivity(), dataPasses.getAvailablePostList(), fragmentInteractionInterface);
			photoCheckInRecyclerView.setAdapter(photoCheckInAdapter);
		}
	}

	public static PhotoCheckInFragment newInstance() {
		PhotoCheckInFragment fragment = new PhotoCheckInFragment();

		return fragment;
	}

	private void captureImage() {
//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//		struri = fileUri.toString();
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//		// start the image capture Intent
//		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof FragmentInteractionInterface) {
			fragmentInteractionInterface = (FragmentInteractionInterface) context;
			dataPasses = fragmentInteractionInterface.dataPassRetrieved();
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		fragmentInteractionInterface = null;
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == getActivity().RESULT_OK) {
				// successfully captured the image
				// display it in image view
				//previewCapturedImage();
				
				String fname = null;
				Bitmap myBitmap = null;
				if(fileRet.exists()){
					//Toast.makeText(getActivity(), "here you go", Toast.LENGTH_LONG).show();
				    myBitmap = BitmapFactory.decodeFile(fileRet.getAbsolutePath());
				    fname = fileRet.getName();
				    //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
				    //myImage.setImageBitmap(myBitmap);

				}

				
				
				



				
				
				
			} else if (resultCode == getActivity().RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getActivity(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to capture image
				Toast.makeText(getActivity(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		} else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
			if (resultCode == getActivity().RESULT_OK) {
				// video successfully recorded
				// preview the recorded video
				//previewVideo();
			} else if (resultCode == getActivity().RESULT_CANCELED) {
				// user cancelled recording
				Toast.makeText(getActivity(),
						"User cancelled video recording", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to record video
				Toast.makeText(getActivity(),
						"Sorry! Failed to record video", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	private boolean isDeviceSupportCamera() {
		if (getActivity().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}
	
	public Uri getOutputMediaFileUri(int type) {
	
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".png");
			
			fnameorig = "Pictures/Hello Camera"+"/"
					+ "IMG_" + timeStamp + ".png";
			directory = "Pictures/Hello Camera"+"/";
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}
		fileRet = mediaFile;
		return mediaFile;
	}
	
	
	public Bitmap combineImages(Bitmap c, Bitmap s, int x,int y) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
	    Bitmap cs = null; 
	 
	    int width, height = 0; 
	     
	    if(c.getWidth() > s.getWidth()) { 
	      width = c.getWidth(); 
	      height = c.getHeight() + s.getHeight(); 
	    } else { 
	      width = s.getWidth(); 
	      height = c.getHeight() + s.getHeight(); 
	    } 
	 
	    cs = Bitmap.createBitmap(width, c.getHeight(), Bitmap.Config.ARGB_8888); 
	 
	    Canvas comboImage = new Canvas(cs); 
	 
	    comboImage.drawBitmap(c, 0f, 0f, null); 
	    comboImage.drawBitmap(s, x, y, null); 
	 
	    // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location 
	    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png"; 
	 
	    OutputStream os = null; 
	    try { 
	      os = new FileOutputStream(loc + tmpImg); 
	      cs.compress(CompressFormat.PNG, 100, os); 
	    } catch(IOException e) { 
	      Log.e("combineImages", "problem combining images", e); 
	    }*/ 
	 
	    return cs; 
	  } 
	
	private void previewCapturedImage() {
		try {
			// hide video preview
			videoPreview.setVisibility(View.GONE);

			imgPreview.setVisibility(View.VISIBLE);

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			imgPreview.setImageBitmap(bitmap);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
