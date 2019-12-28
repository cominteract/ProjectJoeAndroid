package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.JoeUser;
import com.andre.projjoe.models.Pass;

public class GiveInputPasswordFragment extends Fragment {
	FragmentInteractionInterface fragmentInteractionInterface;
	public JoeUser selectedJoeUser;
	public int points = - 1;
	public Pass selectedPass;
	public String message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	  	View rootView = inflater.inflate(R.layout.fragment_inputpasscode, container, false);
    	  	Button inputPasswordOKBtn = (Button) rootView.findViewById(R.id.inputPasswordOKBtn);
			TextView inputPasswordCaptionTxt = (TextView) rootView.findViewById(R.id.inputPasswordCaptionTxt);
			TextView inputPasswordPassName = (TextView) rootView.findViewById(R.id.inputPasswordPassName);

			if(selectedJoeUser!=null && selectedPass!=null)
			{
				inputPasswordPassName.setText(selectedPass.passDescription);
				inputPasswordCaptionTxt.setText("You are about to give " + selectedJoeUser.fullName);
			}
			if(selectedJoeUser!=null && points>0)
			{
				inputPasswordPassName.setText(points + " points ");
				inputPasswordCaptionTxt.setText("You are about to give " + selectedJoeUser.fullName);
			}

			ImageView inputPasswordView = (ImageView) rootView.findViewById(R.id.inputPasswordImage);
			inputPasswordView.setVisibility(View.GONE);
			inputPasswordOKBtn.setOnClickListener(new OnClickListener()
    	  	{
				@Override
				public void onClick(View v) {

					if(points>=0) {
						GivePointsSuccessFragment givePointsSuccessFragment = GivePointsSuccessFragment.newInstance();
						givePointsSuccessFragment.selectedJoeUser = selectedJoeUser;
						givePointsSuccessFragment.points = points;
						fragmentInteractionInterface.onSwitchFragment(GiveInputPasswordFragment.this, givePointsSuccessFragment);
					}
					else
					{
						GivePassSuccessFragment givePassSuccessFragment = GivePassSuccessFragment.newInstance();
						givePassSuccessFragment.selectedPass = selectedPass;
						givePassSuccessFragment.selectedJoeUser = selectedJoeUser;
						givePassSuccessFragment.message = message;
						fragmentInteractionInterface.onSwitchFragment(GiveInputPasswordFragment.this, givePassSuccessFragment);
					}
				}
		  	});
    	  return rootView;
    }
	public static GiveInputPasswordFragment newInstance() {
		GiveInputPasswordFragment fragment = new GiveInputPasswordFragment();

		return fragment;
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
}
