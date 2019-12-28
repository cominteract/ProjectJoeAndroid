package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class PointsFragment extends Fragment {

    DataPasses dataPasses;
    FragmentInteractionInterface fragmentInteractionInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_points, container, false);
        
        Button pointsEarnBtn = (Button) rootView.findViewById(R.id.pointsEarnBtn);
        Button pointsGiveBtn = (Button) rootView.findViewById(R.id.pointsGiveBtn);
        Button pointsUseBtn = (Button) rootView.findViewById(R.id.pointsUseBtn);
        if(dataPasses.currentJoeUser()!=null) {
            ((TextView) rootView.findViewById(R.id.pointsEarnedTxt)).setText(dataPasses.currentJoeUser().earnedPoints+ "");
            ((TextView) rootView.findViewById(R.id.pointsReceivedTxt)).setText(dataPasses.currentJoeUser().receivedPoints+ "");
            ((TextView) rootView.findViewById(R.id.pointsGivenTxt)).setText(dataPasses.currentJoeUser().givenPoints+ "");
            ((TextView) rootView.findViewById(R.id.pointsUsedTxt)).setText(dataPasses.currentJoeUser().usedPoints+ "");
            ((TextView) rootView.findViewById(R.id.pointsCurrentTxt)).setText(dataPasses.currentJoeUser().currentPoints + "");
        }
        pointsEarnBtn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                fragmentInteractionInterface.onSwitchFragment(PointsFragment.this,ShowQrEarnedPtsFragment.newInstance());
//				Intent i = new Intent(getActivity(),MainTabs.class);
//				i.putExtra("points", "earned");
//				getActivity().startActivity(i);
			}
        });

        pointsUseBtn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
                fragmentInteractionInterface.onSwitchFragment(PointsFragment.this,UsingPointsFragment.newInstance());
				// TODO Auto-generated method stub
//				Intent i = new Intent(getActivity(),MainTabs.class);
//				i.putExtra("points", "use");
//				getActivity().startActivity(i);
			}
        });

        pointsGiveBtn.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
                fragmentInteractionInterface.onSwitchFragment(PointsFragment.this,GivePointsFragment.newInstance());
				// TODO Auto-generated method stub
//				Intent i = new Intent(getActivity(),MainTabs.class);
//				i.putExtra("points", "give");
//				getActivity().startActivity(i);
			}
        });
        
        return rootView;
    }

	public static PointsFragment newInstance() {
		PointsFragment fragment = new PointsFragment();

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