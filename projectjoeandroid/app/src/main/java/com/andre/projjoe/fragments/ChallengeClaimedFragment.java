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
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;

public class ChallengeClaimedFragment extends Fragment {

    FragmentInteractionInterface fragmentInteractionInterface;
    DataPasses dataPasses;
    public Pass selectedPass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challengeclaimed, container, false);
        TextView challengeClaimedTransactionCodeTxt = (TextView)rootView.findViewById(R.id.challengeClaimedTransactionCodeTxt);
        TextView challengeClaimedPassDescriptionTxt = (TextView)rootView.findViewById(R.id.challengeClaimedPassDescriptionTxt);
        TextView challengeClaimedTimeStampTxt = (TextView)rootView.findViewById(R.id.challengeClaimedTimeStampTxt);
        challengeClaimedTransactionCodeTxt.setText(challengeClaimedTransactionCodeTxt.getText().toString() + "2312348423984");
        challengeClaimedTimeStampTxt.setText(challengeClaimedTimeStampTxt.getText().toString() + "Current Date");
        if(selectedPass!=null)
            challengeClaimedPassDescriptionTxt.setText(challengeClaimedPassDescriptionTxt.getText().toString() + selectedPass.passDescription);


        rootView.findViewById(R.id.challengeClaimedOkBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transaction = new Transaction(dataPasses.currentJoeUser(),dataPasses.randomDate(),selectedPass, Transaction.TransactionType.TransactionReceivingPass);
                dataPasses.addTransaction(transaction);
                selectedPass.passType = dataPasses.passType[0];
                dataPasses.currentJoeUser().passList.add(selectedPass);
                if(dataPasses.getAvailablePasses().contains(selectedPass))
                    dataPasses.getAvailablePasses().remove(selectedPass);
                Toast.makeText(getActivity(),"Congratulations! You claimed " + selectedPass.passDescription,Toast.LENGTH_LONG).show();
                fragmentInteractionInterface.onSwitchFragment(ChallengeClaimedFragment.this, DiscoverFragment.newInstance());
            }
        });
        return rootView;
    }

    public static ChallengeClaimedFragment newInstance() {
        ChallengeClaimedFragment fragment = new ChallengeClaimedFragment();
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
