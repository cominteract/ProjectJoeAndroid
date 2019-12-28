package com.andre.projjoe.fragments;


import java.io.File;
import java.io.OutputStream;


import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.andre.projjoe.ImageFilters;
import com.andre.projjoe.R;
import com.andre.projjoe.imageManipulation.AIAsync;
import com.andre.projjoe.imageManipulation.AsyncInterface;
import com.andre.projjoe.imageManipulation.ImageInterface;
import com.andre.projjoe.imageManipulation.ImageManager;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Branch;


public class FilterPhotoFragment extends Fragment implements ImageInterface, AsyncInterface {
	File fileRet;
	Bitmap myBitmap = null;
	ImageView img;
	AIAsync aiAsync;
	static int seekBarPosition = 0;
	private Thread thread;  
	public Bitmap edited = null;
	ProgressDialog progress;
	OutputStream fOut = null;
	File filex = null;
	public Branch selectedBranch;
	ImageManager imageManager;
	View v;
	FragmentInteractionInterface fragmentInteractionInterface;
	int[] arrfilters = {R.id.effect_black,R.id.effect_boost_1,R.id.effect_boost_2,
			R.id.effect_boost_3,R.id.effect_brightness,R.id.effect_color_blue,R.id.effect_color_depth_32,
			R.id.effect_color_depth_64,R.id.effect_color_green,
			R.id.effect_color_red,R.id.effect_contrast,R.id.effect_contrast,R.id.effect_emboss,
			R.id.effect_engrave,R.id.effect_flea,R.id.effect_gamma,R.id.effect_gaussian_blue,
			R.id.effect_grayscale,R.id.effect_hue,R.id.effect_invert,R.id.effect_mean_remove,
			R.id.effect_round_corner,R.id.effect_saturation,R.id.effect_sepia,
			R.id.effect_sepia_green,R.id.effect_sepia_blue,R.id.effect_sheding_cyan,R.id.effect_sheding_green,
			R.id.effect_sheding_yellow,R.id.effect_smooth,R.id.effect_tint};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_filterphoto, container, false);
		Log.d(" On Create View ", " On Create View Filter");
		Button filterPhotoOkBtn = (Button)rootView.findViewById(R.id.filterPhotoOkBtn);
		filterPhotoOkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(selectedBranch!=null && selectedBranch.branchMerchant!=null) {
					CompletePhotoFragment completePhotoFragment = CompletePhotoFragment.newInstance();
					completePhotoFragment.myBitmap = imageManager.getBitmap();
					completePhotoFragment.selectedBranch = selectedBranch;
					fragmentInteractionInterface.onSwitchFragment(FilterPhotoFragment.this, completePhotoFragment);
				}
				else
				{
					Toast.makeText( getActivity(), " Something went wrong ", Toast.LENGTH_LONG).show();
				}
			}
		});


		ImageView filterPhotoImage = (ImageView) rootView.findViewById(R.id.filterPhotoImage);
		if(edited!=null)
			filterPhotoImage.setImageBitmap(edited);
		imageManager = new ImageManager();
		imageManager.initManager(this,filterPhotoImage);


//		String filename = null,path,directory = null;
		LinearLayout getfilter = (LinearLayout) rootView.findViewById(R.id.getfilter);
		
		for(int cnt = 0;cnt<getfilter.getChildCount();cnt++)
		{
			ImageView rv = (ImageView)rootView.findViewById(arrfilters[cnt]);
			rv.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {

						FilterPhotoFragment.this.v = v;
					// TODO Auto-generated method stub
					startBackground();

				}
				
			
			});
		}



			


		Button filterPhotoChangeContrastBtn = (Button) rootView.findViewById(R.id.filterPhotoChangeContrastBtn);
		filterPhotoChangeContrastBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopup(v);
			}
			
		});
		
		Button filterPhotoChangeBrightnessBtn = (Button) rootView.findViewById(R.id.filterPhotoChangeBrightnessBtn);
		filterPhotoChangeBrightnessBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopup(v);
			}
			
		});


        return rootView;
    }

	private void startBackground()
	{
		if(imageManager.isImageProcessing() == false) {
			aiAsync = new AIAsync(FilterPhotoFragment.this);
			aiAsync.execute("");
		}
		else
			Toast.makeText(getActivity(), " Can't do this yet ", Toast.LENGTH_LONG).show();
	}

	public static FilterPhotoFragment newInstance() {
		FilterPhotoFragment fragment = new FilterPhotoFragment();

		return fragment;
	}

    private void showPopup(final View anchorView)  {
        // Inflate the popup_layout.xml
    	 LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    	 View popupView = inflater.inflate(R.layout.dialog_filterrange, null);
    	 PopupWindow popupWindow = new PopupWindow(popupView, 
    	                           LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	 SeekBar filterRangeSeekBar = (SeekBar) popupView.findViewById(R.id.filterRangeSeekBar); // make seekbar object
			filterRangeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
         {
        	    @Override
        	    public void onProgressChanged(SeekBar seekBar, int progress,
        	    		boolean fromUser) {
        	    	// TODO Auto-generated method stub
        	    	// change progress text label with current seekbar value
        	    	seekBarPosition = progress;

        	    }
        	    
        	    @Override
        	    public void onStartTrackingTouch(SeekBar seekBar) {
        	    	// TODO Auto-generated method stub
        
        	    	
        	    }
        	    
        	    @Override
        	    public void onStopTrackingTouch(SeekBar seekBar) {
        	    	// TODO Auto-generated method stub
        	
        	    } 
         });
         // set seekbar listener.
		Button filterRangeOkBtn = (Button) popupView.findViewById(R.id.filterRangeOkBtn);
		filterRangeOkBtn.setOnClickListener(new OnClickListener()
         {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(anchorView.getId()==R.id.filterPhotoChangeContrastBtn)
//					edited = (createContrast(myBitmap,seekBarPosition));
//				else if(anchorView.getId()==R.id.filterPhotoChangeBrightnessBtn)
//					edited = (doBrightness(myBitmap,seekBarPosition));
//				else
//					edited = (doBrightness(myBitmap,seekBarPosition));
				final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Image ...", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
        
                    @Override
        
                    public void run() {
                        try {
                        	img.setImageBitmap(edited);
                            Thread.sleep(5000);
                        } catch (Exception e) {

                        }
                        ringProgressDialog.dismiss();
        
                    }
        
                }).start();
				
			
			}
         });
        // Creating the PopupWindow
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside 
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, 
        		location[0] + 100, location[1] + anchorView.getHeight()+200);
        // Getting a reference to Close button, and close the popup when clicked.

    }
    
    public void buttonClicked(View v){
    	
    	
    	



//        else if(v.getId() == R.id.effect_highlight)
//            saveBitmap(imgFilter.applyHighlightEffect(myBitmap), "effect_highlight");
        if(v.getId() == R.id.effect_black)
        	imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBlack,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_boost_1)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBoost,new int[]{1,1,3,4});
        else if(v.getId() == R.id.effect_boost_2)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBoost,new int[]{3,2,5,6});
        else if(v.getId() == R.id.effect_boost_3)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBoost,new int[]{5,3,7,8});
        else if(v.getId() == R.id.effect_brightness)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBright,new int[]{3,2,5,2});
        else if(v.getId() == R.id.effect_color_red)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoRed,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_color_green)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoGreen,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_color_blue)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBlue,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_color_depth_64)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoDecreaseColorDepth,new int[]{3,5,4,3});
        else if(v.getId() == R.id.effect_color_depth_32)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoDecreaseColorDepth,new int[]{6,8,7,6});
        else if(v.getId() == R.id.effect_contrast)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoContrast,new int[]{4,4,5,2});
        else if(v.getId() == R.id.effect_emboss)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoEmboss,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_engrave)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoEngrave,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_flea)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoFleaEffect,new int[]{3,4,5,2});
        else  if(v.getId() == R.id.effect_gaussian_blue)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoGaussianBlur,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_gamma)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoGamma,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_grayscale)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoGreyscale,new int[]{3,4,5,2});
        else  if(v.getId() == R.id.effect_hue)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoHueFilterEffect,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_invert)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoInvertEffect,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_mean_remove)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoMeanRemovalEffect,new int[]{3,4,5,2});
//        else if(v.getId() == R.id.effect_reflaction)
//            saveBitmap(imgFilter.applyReflection(myBitmap),"effect_reflaction");
        else if(v.getId() == R.id.effect_round_corner)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoRoundCornerEffect,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_saturation)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoSaturation,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_sepia)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoSepiaToning,new int[]{9,4,5,2});
        else if(v.getId() == R.id.effect_sepia_green)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoSepiaToning,new int[]{3,9,2,1});
        else if(v.getId() == R.id.effect_sepia_blue)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoSepiaToning,new int[]{3,4,9,2});
        else if(v.getId() == R.id.effect_smooth)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoSmooth,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_sheding_cyan)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoBlue,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_sheding_yellow)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoDark,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_sheding_green)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoGreen,new int[]{3,4,5,2});
        else if(v.getId() == R.id.effect_tint)
			imageManager.useFilter(ImageManager.PhotoFilter.kPhotoTint,new int[]{3,4,5,2});
//        else if(v.getId() == R.id.effect_watermark)
//            saveBitmap(imgFilter.applyWaterMarkEffect(myBitmap, "project joe", 200, 200, Color.GREEN, 80, 24, false),"effect_watermark");

    }



	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof FragmentInteractionInterface) {
			fragmentInteractionInterface = (FragmentInteractionInterface) context;

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
		imageManager.updateImage();
	}

	@Override
	public String onBackground() {
    	if(FilterPhotoFragment.this.v!=null)
			buttonClicked(FilterPhotoFragment.this.v);
		return null;
	}
}
