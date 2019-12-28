package com.andre.projjoe.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andre.projjoe.R;
import com.andre.projjoe.adapters.DashboardAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class DashboardFragment extends Fragment {

    FragmentInteractionInterface fragmentInteractionInterface;
    DataPasses dataPasses;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_filterindustry, container, false);
        GridView filterIndustryGridView = (GridView) rootView.findViewById(R.id.filterIndustryGridView);
        filterIndustryGridView.setBackgroundColor(Color.parseColor("#1fb4dc"));
        // Instance of DashboardAdapter Class
        filterIndustryGridView.setAdapter(new DashboardAdapter(getActivity(), fragmentInteractionInterface, DashboardFragment.this));
	 
        return rootView;
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();

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