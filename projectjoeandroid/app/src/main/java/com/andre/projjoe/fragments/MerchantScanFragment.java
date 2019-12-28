package com.andre.projjoe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class MerchantScanFragment extends Fragment {

    FragmentInteractionInterface fragmentInteractionInterface;
    public String transactionType;
    private DecoratedBarcodeView barcodeView;
    public MerchantScanFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MerchantScanFragment newInstance() {
        MerchantScanFragment fragment = new MerchantScanFragment();

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

        return inflater.inflate(R.layout.fragment_merchant_scan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IntentIntegrator.forSupportFragment(this);
        Button merchantScanOkBtn = view.findViewById(R.id.merchantScanOkBtn);
        merchantScanOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transactionType.equals("Use Points"))
                {
                    MerchantReviewTransactionFragment merchantReviewTransactionFragment = MerchantReviewTransactionFragment.newInstance();
                    merchantReviewTransactionFragment.transactionType = transactionType;
                    fragmentInteractionInterface.onSwitchFragment(MerchantScanFragment.this, merchantReviewTransactionFragment);
                }
                else if(transactionType.equals("Earn Points"))
                {
                    MerchantEarnPointsTransaction merchantEarnPointsTransaction = MerchantEarnPointsTransaction.newInstance();
                    merchantEarnPointsTransaction.transactionType = transactionType;
                    fragmentInteractionInterface.onSwitchFragment(MerchantScanFragment.this,merchantEarnPointsTransaction);
                }
                else
                {
                    MerchantReviewTransactionFragment merchantReviewTransactionFragment = MerchantReviewTransactionFragment.newInstance();
                    merchantReviewTransactionFragment.transactionType = transactionType;
                    fragmentInteractionInterface.onSwitchFragment(MerchantScanFragment.this, merchantReviewTransactionFragment);
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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
