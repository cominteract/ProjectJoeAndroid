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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;


public class JoeUserLogoutFragment extends Fragment {

    FragmentInteractionInterface fragmentInteractionInterface;
    DataPasses dataPasses;
    public JoeUserLogoutFragment() {
        // Required empty public constructor
    }
    public static JoeUserLogoutFragment newInstance() {
        JoeUserLogoutFragment fragment = new JoeUserLogoutFragment();

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
        return inflater.inflate(R.layout.fragment_joe_user_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView joeUserLogoutImage = (ImageView)view.findViewById(R.id.joeUserLogoutImage);
        Glide.with(getActivity()).load(dataPasses.currentJoeUser().image).apply(RequestOptions.circleCropTransform()).into(joeUserLogoutImage);
        view.findViewById(R.id.joeUserLogoutOkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInteractionInterface.onLogout();
            }
        });
        view.findViewById(R.id.joeUserLogoutCancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInteractionInterface.onSwitchFragment(JoeUserLogoutFragment.this, DashboardFragment.newInstance());
            }
        });
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
