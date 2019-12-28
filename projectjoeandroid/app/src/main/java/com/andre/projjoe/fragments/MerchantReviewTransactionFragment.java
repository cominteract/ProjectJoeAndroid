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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantReviewTransactionFragment extends Fragment {

    public String transactionType;
    FragmentInteractionInterface fragmentInteractionInterface;
    public static MerchantReviewTransactionFragment newInstance() {
        MerchantReviewTransactionFragment merchantReviewTransactionFragment = new MerchantReviewTransactionFragment();
        return merchantReviewTransactionFragment;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant_review_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView merchantReviewTransactionTypeTxt = view.findViewById(R.id.merchantReviewTransactionTypeTxt);
        LinearLayout merchantReviewTransactionUseContainer = view.findViewById(R.id.merchantReviewTransactionUseContainer);
        LinearLayout merchantReviewTransactionEarnContainer  = view.findViewById(R.id.merchantReviewTransactionEarnContainer);
        Button merchantReviewTransactionOkBtn = view.findViewById(R.id.merchantReviewTransactionOkBtn);
        merchantReviewTransactionOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantTransactionSuccessFragment merchantTransactionSuccessFragment = MerchantTransactionSuccessFragment.newInstance();
                fragmentInteractionInterface.onSwitchFragment(MerchantReviewTransactionFragment.this, merchantTransactionSuccessFragment);
            }
        });
        if(transactionType.equals("Use Points"))
        {
            merchantReviewTransactionTypeTxt.setText("Your customer is using points");
            merchantReviewTransactionEarnContainer.setVisibility(View.GONE);
            merchantReviewTransactionUseContainer.setVisibility(View.VISIBLE);
        }
        else if(transactionType.equals("Earn Points"))
        {
            merchantReviewTransactionTypeTxt.setText("Your customer is earning points");
            merchantReviewTransactionEarnContainer.setVisibility(View.VISIBLE);
            merchantReviewTransactionUseContainer.setVisibility(View.GONE);
        }
        else
        {
            merchantReviewTransactionTypeTxt.setText("Your customer is using pass");
            merchantReviewTransactionEarnContainer.setVisibility(View.GONE);
            merchantReviewTransactionUseContainer.setVisibility(View.VISIBLE);
        }

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
