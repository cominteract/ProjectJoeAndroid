package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.JoeUser;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;

public class GivePassSuccessFragment extends Fragment {
	public Pass selectedPass;
	public JoeUser selectedJoeUser;
	public String message;
	DataPasses dataPasses;
	FragmentInteractionInterface fragmentInteractionInterface;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			  View rootView = inflater.inflate(R.layout.fragment_givepasssuccess, container, false);

				TextView passSuccessUser = (TextView)rootView.findViewById(R.id.passSuccessUser);
				TextView passSuccessDetails = (TextView)rootView.findViewById(R.id.passSuccessDetailsTxt);
				if(selectedPass!=null && selectedJoeUser!=null)
				{
					passSuccessUser.setText(selectedJoeUser.fullName);
					passSuccessDetails.setText(selectedPass.passDescription);



				}
	//
	//    	  tvtitle.setText(getActivity().getIntent().getExtras().getString("passname") + getActivity().getIntent().getExtras().getString("passdesc"));
	//    	  frname.setText(" " + getActivity().getIntent().getExtras().getString("frname"));
				rootView.findViewById(R.id.passSuccessOkBtn).setOnClickListener(new OnClickListener()
			  	{
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ReceivedPassFragment receivedPassFragment = ReceivedPassFragment.newInstance();
						receivedPassFragment.receivedPass = selectedPass;
						receivedPassFragment.selectedJoeUser = selectedJoeUser;

						Transaction transaction = new Transaction(selectedJoeUser,dataPasses.randomDate(),selectedPass, Transaction.TransactionType.TransactionGivingPass);
						transaction.setTransactionOtherUser(dataPasses.currentJoeUser());
						dataPasses.addTransaction(transaction);

						Toast.makeText(getActivity(),"Congratulations! You have given " + selectedPass.passDescription,Toast.LENGTH_LONG).show();
						receivedPassFragment.message = message;
						fragmentInteractionInterface.onSwitchFragment(GivePassSuccessFragment.this,
								receivedPassFragment);
					}
				});

				return rootView;
    }

	public static GivePassSuccessFragment newInstance() {
		GivePassSuccessFragment fragment = new GivePassSuccessFragment();

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
