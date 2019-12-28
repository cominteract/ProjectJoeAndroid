package com.andre.projjoe.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantDailyTimeRecordSuccess extends Fragment {

    FragmentInteractionInterface fragmentInteractionInterface;
    public Bitmap selectedBitmap;
    public static MerchantDailyTimeRecordSuccess newInstance() {
        MerchantDailyTimeRecordSuccess merchantDailyTimeRecordSuccess = new MerchantDailyTimeRecordSuccess();
        return merchantDailyTimeRecordSuccess;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant_daily_time_record_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button merchantDTRSuccessOkBtn = view.findViewById(R.id.merchantDTRSuccessOkBtn);
        if(selectedBitmap!=null)
        {
            ImageView merchantDTRSuccessImage = view.findViewById(R.id.merchantDTRSuccessImage);
            merchantDTRSuccessImage.setImageBitmap(selectedBitmap);
        }
        merchantDTRSuccessOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInteractionInterface.onSwitchFragment(MerchantDailyTimeRecordSuccess.this, null);
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

}
