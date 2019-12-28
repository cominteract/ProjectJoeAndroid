package com.andre.projjoe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;


public class MerchantEarnPointsTransaction extends Fragment {

    public String transactionType;
    FragmentInteractionInterface fragmentInteractionInterface;
    public MerchantEarnPointsTransaction() {
        // Required empty public constructor
    }

    public static MerchantEarnPointsTransaction newInstance() {
        MerchantEarnPointsTransaction fragment = new MerchantEarnPointsTransaction();
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
        return inflater.inflate(R.layout.fragment_merchant_earn_points_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button merchantEarnPointsProceedBtn = view.findViewById(R.id.merchantEarnPointsProceedBtn);
        merchantEarnPointsProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantReviewTransactionFragment merchantReviewTransactionFragment = MerchantReviewTransactionFragment.newInstance();
                merchantReviewTransactionFragment.transactionType = transactionType;
                fragmentInteractionInterface.onSwitchFragment(MerchantEarnPointsTransaction.this, merchantReviewTransactionFragment);
            }
        });
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
    // TODO: Rename method, update argument and hook method into UI event

}
