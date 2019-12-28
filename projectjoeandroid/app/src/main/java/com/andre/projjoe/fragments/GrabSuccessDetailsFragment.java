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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;

public class GrabSuccessDetailsFragment extends Fragment{
	TextView grabSuccessDescTxt,grabSuccessPriceTxt;
	ImageView grabSuccessImage;
	Button grabSuccessBtnOk,grabSuccessBtnBack;
	Pass grabbedPass;

	DataPasses dataPasses;
	FragmentInteractionInterface fragmentInteractionInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_grabsuccessdetails, container, false);
		grabSuccessDescTxt = (TextView) rootView.findViewById(R.id.grabSuccessDescTxt);
		grabSuccessPriceTxt = (TextView) rootView.findViewById(R.id.grabSuccessPriceTxt);
		grabSuccessImage = (ImageView) rootView.findViewById(R.id.grabSuccessImage);
		if(grabbedPass!=null) {
			Glide.with(getActivity()).load(grabbedPass.passMerchant.merchantImage).into(grabSuccessImage);
			grabSuccessDescTxt.setText(grabbedPass.passDescription);
			grabSuccessPriceTxt.setText(grabbedPass.passPrice + " PHP");
		}
//    	if(getActivity().getIntent().getExtras()!=null)
//    	{
//    		desc.setText(getActivity().getIntent().getExtras().getString("desc"));
//    		icon.setBackgroundResource(Integer.valueOf(getActivity().getIntent().getExtras().getString("icon")));
//    	}

		grabSuccessBtnOk = (Button) rootView.findViewById(R.id.grabSuccessBtnOk);
		grabSuccessBtnOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(grabbedPass!=null) {

					grabbedPass.passClaimCount -= 1;
					grabbedPass.passClaims = grabbedPass.passClaimCount + " claims";
					dataPasses.getCurrentPassList().add(grabbedPass);

					Transaction transaction = new Transaction(dataPasses.currentJoeUser(), dataPasses.randomDate(),grabbedPass, Transaction.TransactionType.TransactionReceivingPass);
					dataPasses.addTransaction(transaction);
					Toast.makeText(getActivity(),"Congratulations! You claimed " + grabbedPass.passDescription,Toast.LENGTH_LONG).show();
					fragmentInteractionInterface.updatedCurrentPass();
				}
				fragmentInteractionInterface.onSwitchFragment(GrabSuccessDetailsFragment.this,DiscoverFragment.newInstance());
			}
		});
		grabSuccessBtnBack = (Button) rootView.findViewById(R.id.grabSuccessBtnBack);
		grabSuccessBtnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PassDetailsFragment passDetailsFragment = PassDetailsFragment.newInstance();
				passDetailsFragment.fromDiscover = true;
				passDetailsFragment.selectedPass = grabbedPass;

				fragmentInteractionInterface.onSwitchFragment(GrabSuccessDetailsFragment.this,passDetailsFragment);
			}
		});
    	
    	return rootView;
    }
	public static GrabSuccessDetailsFragment newInstance() {
		GrabSuccessDetailsFragment fragment = new GrabSuccessDetailsFragment();

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
