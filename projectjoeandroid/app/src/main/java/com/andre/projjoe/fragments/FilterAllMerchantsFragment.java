package com.andre.projjoe.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andre.projjoe.R;
import com.andre.projjoe.adapters.FilterAllMerchantsAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class FilterAllMerchantsFragment extends Fragment {

	DataPasses dataPasses;
	FragmentInteractionInterface fragmentInteractionInterface;
	int[] icons = new int[]{R.drawable.nike, R.drawable.etude, R.drawable.jordan,
			R.drawable.easports};
	String[] titles = new String[]{"NIKE", "ETUDE", "JORDAN", "EA"};
	String[] descs = new String[]{"Good products create value", "Good products create value", "Good products create value", "Good products create value"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_allmerchants, container, false);

		final ListView allMerchantsListView = (ListView) rootView.findViewById(R.id.allMerchantsListView);

		FilterAllMerchantsAdapter adapter = new FilterAllMerchantsAdapter(this, dataPasses.getMerchantList(), fragmentInteractionInterface);
		allMerchantsListView.setAdapter(adapter);
		return rootView;
	}


	public static FilterAllMerchantsFragment newInstance() {
		FilterAllMerchantsFragment fragment = new FilterAllMerchantsFragment();

		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof FragmentInteractionInterface) {
			fragmentInteractionInterface = (FragmentInteractionInterface) context;
			dataPasses = ((FragmentInteractionInterface) context).dataPassRetrieved();
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