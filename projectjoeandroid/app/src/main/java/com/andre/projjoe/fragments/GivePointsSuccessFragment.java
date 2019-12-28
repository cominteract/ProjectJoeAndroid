package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.JoeUser;
import com.andre.projjoe.models.Points;
import com.andre.projjoe.models.Transaction;

public class GivePointsSuccessFragment extends Fragment {
	FragmentInteractionInterface fragmentInteractionInterface;
	DataPasses dataPasses;
	public JoeUser selectedJoeUser;
	public int points;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_givepointssuccess, container, false);
		Button successGivePointsOkBtn = (Button) rootView.findViewById(R.id.successGivePointsOkBtn);
		TextView successGivePointsCurrentTxt = (TextView) rootView.findViewById(R.id.successGivePointsCurrentTxt);
		TextView successGivePointsCaptionGivenTxt = (TextView)rootView.findViewById(R.id.successGivePointsCaptionGivenTxt);
		selectedJoeUser.currentPoints += points;
		selectedJoeUser.receivedPoints += points;
		dataPasses.currentJoeUser().currentPoints -= points;
		dataPasses.currentJoeUser().givenPoints += points;
		if(selectedJoeUser!=null) {
			Transaction transaction = new Transaction(selectedJoeUser, dataPasses.randomDate(), points, Transaction.TransactionType.TransactionGivingPoints);
			transaction.setTransactionOtherUser(dataPasses.currentJoeUser());
			dataPasses.addTransaction(transaction);
		}
		if(selectedJoeUser!=null) {
			successGivePointsCaptionGivenTxt.setText("You have successfully given " + points + " points to " + selectedJoeUser.fullName);
			successGivePointsCurrentTxt.setText(String.valueOf(dataPasses.currentJoeUser().currentPoints));
		}

		successGivePointsOkBtn.setOnClickListener(new OnClickListener()
    	  {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity()," Give " + points + " points successfully", Toast.LENGTH_LONG).show();
				fragmentInteractionInterface.onSwitchFragment(GivePointsSuccessFragment.this, PointsFragment.newInstance());
			}
  	  	  });
    	  return rootView;
    }

	public static GivePointsSuccessFragment newInstance() {
		GivePointsSuccessFragment fragment = new GivePointsSuccessFragment();

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
