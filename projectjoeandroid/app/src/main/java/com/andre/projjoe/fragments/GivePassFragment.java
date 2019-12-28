package com.andre.projjoe.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.JoeUser;
import com.andre.projjoe.models.Pass;

public class GivePassFragment extends Fragment {

	JoeUser selectedJoeUser;
	Pass selectedPass;
	DataPasses dataPasses;
	TextView givePassClaimsTxt,givePassDeadlineTxt,givePassDescTxt,givePassTitleTxt;
  	ArrayList<String> friendNames = new ArrayList<String>();
  	FragmentInteractionInterface fragmentInteractionInterface;
  	Spinner givePassFriendsSpinner;
	EditText givePassMessageEdt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	  	View rootView = inflater.inflate(R.layout.fragment_givepass, container, false);

			givePassTitleTxt = (TextView) rootView.findViewById(R.id.givePassTitleTxt);
			givePassDescTxt = (TextView) rootView.findViewById(R.id.givePassDescTxt);
			givePassDeadlineTxt = (TextView) rootView.findViewById(R.id.givePassDeadlineTxt);
			givePassClaimsTxt = (TextView) rootView.findViewById(R.id.givePassClaimsTxt);
			givePassFriendsSpinner = (Spinner)rootView.findViewById(R.id.givePassFriendsSpinner);
			givePassMessageEdt = (EditText) rootView.findViewById(R.id.givePassMessageEdt);
			if(dataPasses!=null && dataPasses.currentJoeUser().friendList!=null)
			{
				for(JoeUser joeUser : dataPasses.currentJoeUser().friendList)
				{
					friendNames.add(joeUser.fullName);
				}
			}


			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.filter_adapter, friendNames);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			givePassFriendsSpinner.setAdapter(dataAdapter);
			givePassFriendsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					selectedJoeUser = dataPasses.currentJoeUser().friendList.get(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			if(selectedPass!=null)
			{
				givePassTitleTxt.setText(selectedPass.passMerchant.merchantName);
				givePassDescTxt.setText(selectedPass.passDescription);
				givePassClaimsTxt.setText(selectedPass.passClaims);
				givePassDeadlineTxt.setText(selectedPass.passDuration);
			}
			rootView.findViewById(R.id.givePassOkBtn).setOnClickListener(new OnClickListener()
    	  	{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(selectedJoeUser!=null && givePassMessageEdt.getText().toString().length() > 5) {
						GiveInputPasswordFragment giveInputPasswordFragment = GiveInputPasswordFragment.newInstance();
						giveInputPasswordFragment.selectedJoeUser = selectedJoeUser;
						giveInputPasswordFragment.selectedPass = selectedPass;
						giveInputPasswordFragment.message = givePassMessageEdt.getText().toString();
						fragmentInteractionInterface.onSwitchFragment(GivePassFragment.this, giveInputPasswordFragment
						);
					}

				}
   			 });


    	  
  
   	 

    	  	return rootView;
    }


    

	public static GivePassFragment newInstance() {
		GivePassFragment fragment = new GivePassFragment();

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
}
