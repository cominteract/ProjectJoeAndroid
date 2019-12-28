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
import android.widget.TextView;

import com.cameraProcess.CameraManager;
import com.andre.projjoe.R;
import com.andre.projjoe.activities.BaseActivity;
import com.andre.projjoe.listeners.FragmentInteractionInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantDailyTimeRecord extends Fragment {

    FragmentInteractionInterface fragmentInteractionInterface;
    CameraManager cameraManager;
    public static MerchantDailyTimeRecord newInstance() {
        MerchantDailyTimeRecord merchantDailyTimeRecord = new MerchantDailyTimeRecord();
        return merchantDailyTimeRecord;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_merchant_daily_time_record, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button merchantReviewPointsOkBtn = view.findViewById(R.id.merchantReviewPointsOkBtn);
        merchantReviewPointsOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraManager = new CameraManager();
                cameraManager.initManager((BaseActivity)getActivity());
                cameraManager.setImageDirectory("proj_joe_merchants_dailytimerecord");
                cameraManager.captureImage();
                //fragmentInteractionInterface.onSwitchFragment(MerchantDailyTimeRecord.this,MerchantDailyTimeRecordSuccess.newInstance());
            }
        });
    }

    public void handleDailyTimeRecord()
    {
        if(cameraManager!=null && cameraManager.getCapturedImage()!=null)
        {
            MerchantDailyTimeRecordSuccess merchantDailyTimeRecordSuccess = MerchantDailyTimeRecordSuccess.newInstance();
            merchantDailyTimeRecordSuccess.selectedBitmap = cameraManager.getCapturedImage();
            fragmentInteractionInterface.onSwitchFragment(MerchantDailyTimeRecord.this,merchantDailyTimeRecordSuccess);
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
