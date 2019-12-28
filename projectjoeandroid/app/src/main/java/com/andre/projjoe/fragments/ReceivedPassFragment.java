package com.andre.projjoe.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.JoeUser;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceivedPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceivedPassFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    FragmentInteractionInterface fragmentInteractionInterface;
    Pass receivedPass;
    JoeUser selectedJoeUser;
    DataPasses dataPasses;
    public String message;
    public ReceivedPassFragment() {
        // Required empty public constructor
    }

    public static ReceivedPassFragment newInstance() {
        ReceivedPassFragment fragment = new ReceivedPassFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView receivedPassTitleTxt = (TextView)view.findViewById(R.id.receivedPassTitleTxt);
        TextView receivedPassClaimsTxt = (TextView)view.findViewById(R.id.receivedPassClaimsTxt);
        TextView receivedPassDeadlineTxt = (TextView)view.findViewById(R.id.receivedPassDeadlineTxt);
        TextView receivedPassDescTxt = (TextView)view.findViewById(R.id.receivedPassDescTxt);
        EditText receivedPassEdt = (EditText)view.findViewById(R.id.receivedPassEdt);

        if(selectedJoeUser!=null)
        {
            Transaction transaction = new Transaction(dataPasses.currentJoeUser(),dataPasses.randomDate(),receivedPass, Transaction.TransactionType.TransactionReceivingPass);
            transaction.setTransactionOtherUser(selectedJoeUser);
            dataPasses.addTransaction(transaction);
            Toast.makeText(getActivity(),"Congratulations! You claimed " + receivedPass.passDescription,Toast.LENGTH_LONG).show();
        }

        if(message!=null)
            receivedPassEdt.setText( message);
        else
            receivedPassEdt.setText( " Enjoy " + dataPasses.currentJoeUser().fullName);
        TextView receivedPassFromTxt = (TextView) view.findViewById(R.id.receivedPassFromTxt);
        receivedPassFromTxt.setText(" From : " + selectedJoeUser.fullName);
        final Button receivedPassBtn = (Button)view.findViewById(R.id.receivedPassBtn);
        receivedPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataPasses.getCurrentPassList().contains(receivedPass))
                    dataPasses.getCurrentPassList().remove(receivedPass);
                fragmentInteractionInterface.onSwitchFragment(ReceivedPassFragment.this, PassesFragment.newInstance());
            }
        });
        ImageView receivedPassImage = (ImageView)view.findViewById(R.id.receivedPassImage);

        if(receivedPass!=null)
        {
            receivedPassTitleTxt.setText(receivedPass.passMerchant.merchantName);
            receivedPassClaimsTxt.setText(receivedPass.passClaims);
            receivedPassDeadlineTxt.setText(receivedPass.passDuration);
            receivedPassDescTxt.setText(receivedPass.passDescription);
            receivedPassFromTxt.setText(" From User ");
            Glide.with(getActivity()).load(receivedPass.passMerchant.merchantImage).into(receivedPassImage);
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
}
