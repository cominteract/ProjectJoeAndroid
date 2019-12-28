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
import com.andre.projjoe.models.DataPasses;

public class MerchantEmployeeRegisterFragment extends Fragment {


    DataPasses dataPasses;
    public String transactionType;
    FragmentInteractionInterface fragmentInteractionInterface;
    public static MerchantEmployeeRegisterFragment newInstance() {
        MerchantEmployeeRegisterFragment fragment = new MerchantEmployeeRegisterFragment();

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
        return inflater.inflate(R.layout.fragment_merchant_employee_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button merchantEmployeeRegisterLoginBtn = view.findViewById(R.id.merchantEmployeeRegisterLoginBtn);
        merchantEmployeeRegisterLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantScanFragment merchantScanFragment = MerchantScanFragment.newInstance();
                merchantScanFragment.transactionType = transactionType;
                fragmentInteractionInterface.onSwitchFragment(MerchantEmployeeRegisterFragment.this, merchantScanFragment);

            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionInterface) {
            fragmentInteractionInterface = (FragmentInteractionInterface) context;
            fragmentInteractionInterface.dataPassRetrieved();

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
