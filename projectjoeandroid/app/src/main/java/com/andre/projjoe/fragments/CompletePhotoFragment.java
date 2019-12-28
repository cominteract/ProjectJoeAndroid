package com.andre.projjoe.fragments;

import java.io.File;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cameraProcess.CameraConstants;
import com.andre.projjoe.R;
import com.andre.projjoe.imageManipulation.AIAsync;
import com.andre.projjoe.imageManipulation.AsyncInterface;
import com.andre.projjoe.imageManipulation.ImageInterface;
import com.andre.projjoe.imageManipulation.ImageManager;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Branch;
import com.andre.projjoe.models.DataPasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CompletePhotoFragment extends Fragment implements AsyncInterface, ImageInterface{
	String filename,path,directory;
	Bitmap myBitmap = null;
	Bitmap overlayBitmap = null;
	ImageManager imageManager;
	ImageView img = null;
	File fileRet;
	FragmentInteractionInterface fragmentInteractionInterface;
	DataPasses dataPasses;
	Branch selectedBranch;
	AIAsync aiAsync;
	boolean alreadyImaged = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_completephoto, container, false);
        
//		if(getActivity().getIntent().getExtras()!=null)
//		{
//			filename = (getActivity().getIntent().getExtras().getString("filename"));
//			path = (getActivity().getIntent().getExtras().getString("path"));
//			directory = getActivity().getIntent().getExtras().getString("directory");
//
//		}
//		fileRet = new File(Environment.getExternalStorageDirectory(), filename);
//
//
//
//		if(fileRet.exists()){
//
//		    myBitmap = BitmapFactory.decodeFile(fileRet.getAbsolutePath());
//		    Toast.makeText(getActivity(), "there is here", Toast.LENGTH_LONG).show();
//		    //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
//		    //myImage.setImageBitmap(myBitmap);
//			img = (ImageView) rootView.findViewById(R.id.imagepic);
//			img.setImageBitmap(myBitmap);
//
//		}
//		final String[] attribs = {"Congratulations", "You have successfully done a photo check "};
		Button completePhotoOkBtn = (Button) rootView.findViewById(R.id.completePhotoOkBtn);
		completePhotoOkBtn.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(alreadyImaged) {
					final Dialog dialog = new Dialog(getActivity());
					dialog.setContentView(R.layout.dialog_photocheckinsuccess);
					dialog.setTitle("Congratulations");

					Button successPhotoCheckInOkBtn = (Button) dialog.findViewById(R.id.successPhotoCheckInOkBtn);
					successPhotoCheckInOkBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							fragmentInteractionInterface.onSwitchFragment(CompletePhotoFragment.this, PhotoCheckInFragment.newInstance());

						}
					});
					dialog.show();
				}
				else
				{

				}
				
			}

    });
		ImageView completePhotoImage = (ImageView) rootView.findViewById(R.id.completePhotoImage);

		if(myBitmap!=null)
			completePhotoImage.setImageBitmap(myBitmap);
		imageManager = new ImageManager();
		imageManager.initManager(this,completePhotoImage);
		if(myBitmap!=null) {
			Glide.with(this)
					.asBitmap()
					.load(selectedBranch.branchMerchant.merchantImage)
					.into(new SimpleTarget<Bitmap>() {
						@Override
						public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
							overlayBitmap = resource;
							if(imageManager!=null && !imageManager.isImageProcessing() && overlayBitmap!=null) {
								Log.d(" Starting nigga "," Starting nigga ");
								//overlayBitmap = Bitmap.createBitmap(50,50,overlayBitmap.getConfig());
								startBackground();

							}
							else
							{
								Log.d(" Dont do that nigga "," dont do that nigga ");

							}
						}
					});

		}
		else
		{
			Log.d(" Dont do that nigga "," dont do that nigga ");

		}
        return rootView;
    }


    private void startBackground()
	{
		aiAsync = new AIAsync(this);
		aiAsync.execute("");

	}

	public static CompletePhotoFragment newInstance() {
		CompletePhotoFragment fragment = new CompletePhotoFragment();

		return fragment;
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
	public void onUpdated(String result) {
		imageManager.updateImage();
		if(imageManager.getBitmap()!=null)
		{
			imageManager.saveBitmap(CameraConstants.MEDIA_TYPE_IMAGE,"proj_joe_edited",imageManager.getBitmap());
		}
		alreadyImaged = true;
	}

	@Override
	public String onBackground() {
		overlayBitmap = Bitmap.createScaledBitmap(overlayBitmap, 30, 30, false);
		imageManager.combineImagesWithTextBelow(myBitmap, overlayBitmap, selectedBranch.branchAddress);
		return null;
	}

	@Override
	public void imageChanged() {


	}

	@Override
	public void imageChangedWithCurrentFilter(String filter) {

	}
}
