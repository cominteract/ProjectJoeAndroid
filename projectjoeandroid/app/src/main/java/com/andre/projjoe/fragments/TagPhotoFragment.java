package com.andre.projjoe.fragments;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.cameraProcess.CameraInterface;
import com.cameraProcess.CameraManager;
import com.andre.projjoe.R;
import com.andre.projjoe.activities.BaseActivity;
import com.andre.projjoe.adapters.PhotoLocAdapter;
import com.andre.projjoe.imageManipulation.AsyncInterface;
import com.andre.projjoe.imageManipulation.ImageInterface;
import com.andre.projjoe.imageManipulation.ImageManager;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;


public class TagPhotoFragment extends Fragment implements ImageInterface, AsyncInterface {
	File fileRet;
	FragmentInteractionInterface fragmentInteractionInterface;
	DataPasses dataPasses;
	public Bitmap selectedBitmap;
	CameraManager cameraManager;
	ImageView tagPhotoImageView;
	PhotoLocAdapter photoLocAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tagphoto, container, false);

		RecyclerView tagPhotoRecyclerView = (RecyclerView) rootView.findViewById(R.id.tagPhotoRecyclerView);
		tagPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		tagPhotoImageView = (ImageView) rootView.findViewById(R.id.tagPhotoImageView);
		photoLocAdapter = new PhotoLocAdapter(getActivity(),dataPasses.getBranchList(),fragmentInteractionInterface, TagPhotoFragment.this);
		tagPhotoRecyclerView.setAdapter(photoLocAdapter);
		cameraManager = new CameraManager();
		cameraManager.initManager((BaseActivity)getActivity());
		cameraManager.setImageDirectory("proj_joe");
		cameraManager.captureImage();
//		if(getActivity().getIntent().getExtras()!=null)
//		{
//			filename = (getActivity().getIntent().getExtras().getString("filename"));
//			path = (getActivity().getIntent().getExtras().getString("path"));
//			Log.d("correct", getActivity().getIntent().getExtras().getString("filename"));
//
//		}
//		Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
//                R.drawable.jollibee_icon);
//
//		if(filename!=null)
//			fileRet = new File(Environment.getExternalStorageDirectory(),  filename);
//		if(fileRet.exists()){
//
//		    myBitmap = BitmapFactory.decodeFile(fileRet.getAbsolutePath());
//
//		    //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
//		    //myImage.setImageBitmap(myBitmap);
//
//		}
//		ImageView img = (ImageView) rootView.findViewById(R.id.imagepic);
//		img.setImageBitmap(myBitmap);
//		ImageManager imageManager = new ImageManager();
//		ImageView tagPhotoImage = (ImageView) rootView.findViewById(R.id.tagPhotoImage);
//		Drawable myIcon = getResources().getDrawable( R.drawable.bg2 );
//		DisplayMetrics displayMetrics = new DisplayMetrics();
//		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//		int height = displayMetrics.heightPixels;
//		int width = displayMetrics.widthPixels;
//		tagPhotoImage.setImageBitmap(ImageManager.drawableToBitmap(myIcon,width,height/3));
//		imageManager.initManager(this,tagPhotoImage);
        return rootView;
    }

	public static TagPhotoFragment newInstance() {
		TagPhotoFragment fragment = new TagPhotoFragment();

		return fragment;
	}

	public void handleCapturePhoto()
	{
		if(cameraManager!=null && cameraManager.getCapturedImage()!=null)
		{
			selectedBitmap = cameraManager.getCapturedImage();
			tagPhotoImageView.setImageBitmap(cameraManager.getCapturedImage());
			photoLocAdapter.setSelectedBitmap(selectedBitmap);

		}
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
	public void imageChanged() {

	}

	@Override
	public void imageChangedWithCurrentFilter(String filter) {

	}

	@Override
	public void onUpdated(String result) {

	}

	@Override
	public String onBackground() {
		return null;
	}


}
