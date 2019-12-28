package com.andre.projjoe.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.JoeUser;

public class GivePointsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
	EditText givePointsEdt,givePointsMessageEdt;
	TextView givePointsTitleTxt,givePointsCurrentPointsTxt;
	DataPasses dataPasses;
	List<String> friendNames = new ArrayList<>();
	FragmentInteractionInterface fragmentInteractionInterface;
	Button givePointsOKBtn;
	Spinner givePointsSpinner;
	JoeUser selectedJoeUser;
	View rootView;

	boolean pointsKeyboard = false;
	private static boolean isAttached = false;
	public static GivePointsFragment newInstance() {
		GivePointsFragment fragment = new GivePointsFragment();
		return fragment;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			  	rootView = inflater.inflate(R.layout.fragment_givepoints, container, false);

			  	givePointsEdt = (EditText) rootView.findViewById(R.id.givePointsEdt);
			  	givePointsMessageEdt = (EditText) rootView.findViewById(R.id.givePointsMessageEdt);
				givePointsCurrentPointsTxt = (TextView) rootView.findViewById(R.id.givePointsCurrentPointsTxt);
				givePointsCurrentPointsTxt.setText("Points: " + dataPasses.currentJoeUser().currentPoints);
				givePointsTitleTxt = (TextView) rootView.findViewById(R.id.givePointsTitleTxt);
			  	givePointsOKBtn = (Button) rootView.findViewById(R.id.givePointsOKBtn);
			  	givePointsOKBtn.setOnClickListener(new OnClickListener()
					{
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(givePointsEdt.getText().toString().length() > 0) {
							GiveInputPasswordFragment giveInputPasswordFragment = GiveInputPasswordFragment.newInstance();
							giveInputPasswordFragment.selectedJoeUser = selectedJoeUser;
							giveInputPasswordFragment.points = Integer.valueOf(givePointsEdt.getText().toString());
							fragmentInteractionInterface.onSwitchFragment(GivePointsFragment.this, giveInputPasswordFragment);
						}
		//				Intent i = new Intent(getActivity(),MainTabs.class);
		//				i.putExtra("points", "givepasscode");
		//				getActivity().startActivity(i);
						}
				});

				if(dataPasses!=null && dataPasses.currentJoeUser().friendList!=null && dataPasses.currentJoeUser().friendList.size()>0)
				{
					givePointsSpinner = (Spinner) rootView.findViewById(R.id.givePointsSpinner);
					givePointsSpinner.setOnItemSelectedListener(this);
					for (JoeUser joeUser : dataPasses.currentJoeUser().friendList)
					{
						friendNames.add(joeUser.fullName);
					}
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.filter_adapter, friendNames);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					// attaching data adapter to spinner
					givePointsSpinner.setAdapter(dataAdapter);
				}

				givePointsMessageEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							pointsKeyboard = false; // Instead of your Toast
					}
				});
				givePointsEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							pointsKeyboard = true; // Instead of your Toast
					}
				});
				rootView.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
				return rootView;
    }

	private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			// navigation bar height
			if(isAdded()) {
				int navigationBarHeight = 0;
				int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
				if (resourceId > 0) {
					navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
				}

				// status bar height
				int statusBarHeight = 0;
				resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
				if (resourceId > 0) {
					statusBarHeight = getResources().getDimensionPixelSize(resourceId);
				}

				// display window size for the app layout
				Rect rect = new Rect();
				getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

				// screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
				int keyboardHeight = rootView.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + rect.height());

				if (keyboardHeight <= 0) {
					onHideKeyboard();
				} else {
					onShowKeyboard();
				}
			}
		}
	};


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		selectedJoeUser = dataPasses.currentJoeUser().friendList.get(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	private void onShowKeyboard()
	{

		givePointsSpinner.setVisibility(View.GONE);
		givePointsTitleTxt.setVisibility(View.GONE);


		if(pointsKeyboard) {
			Log.d(" Shown ", " Shown Points");
			givePointsMessageEdt.setVisibility(View.GONE);
			givePointsEdt.setVisibility(View.VISIBLE);
		}
		else
		{
			Log.d(" Hidden ", " Hidden Points");
			givePointsMessageEdt.setVisibility(View.VISIBLE);
			givePointsEdt.setVisibility(View.GONE);
		}
		givePointsOKBtn.setVisibility(View.GONE);
	}
	private void onHideKeyboard()
	{

		givePointsSpinner.setVisibility(View.VISIBLE);
		givePointsTitleTxt.setVisibility(View.VISIBLE);

		givePointsEdt.setVisibility(View.VISIBLE);
		givePointsMessageEdt.setVisibility(View.VISIBLE);
		givePointsOKBtn.setVisibility(View.VISIBLE);

	}



    private void onNoTypingDone()
	{
		givePointsSpinner.setVisibility(View.GONE);
		givePointsTitleTxt.setVisibility(View.VISIBLE);

		givePointsEdt.setVisibility(View.VISIBLE);
		givePointsMessageEdt.setVisibility(View.VISIBLE);
		givePointsOKBtn.setVisibility(View.VISIBLE);
	}

    private void onTypeMessage()
	{
		givePointsSpinner.setVisibility(View.GONE);
		givePointsTitleTxt.setVisibility(View.GONE);

		givePointsEdt.setVisibility(View.GONE);
		givePointsMessageEdt.setVisibility(View.VISIBLE);
		givePointsOKBtn.setVisibility(View.GONE);
	}
	private void onTypePoints()
	{
		givePointsSpinner.setVisibility(View.GONE);
		givePointsTitleTxt.setVisibility(View.GONE);

		givePointsEdt.setVisibility(View.VISIBLE);
		givePointsMessageEdt.setVisibility(View.GONE);
		givePointsOKBtn.setVisibility(View.GONE);
	}
	private void onChoosingFriends()
	{

		givePointsSpinner.setVisibility(View.VISIBLE);
		givePointsTitleTxt.setVisibility(View.GONE);

		givePointsEdt.setVisibility(View.GONE);
		givePointsMessageEdt.setVisibility(View.GONE);
		givePointsOKBtn.setVisibility(View.GONE);
	}


    

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		isAttached = true;
		if (context instanceof FragmentInteractionInterface) {
			fragmentInteractionInterface = (FragmentInteractionInterface) context;
			dataPasses = ((FragmentInteractionInterface) context).dataPassRetrieved();

		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		isAttached = false;
		if(rootView!=null)
			rootView.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
		fragmentInteractionInterface = null;
	}
}
