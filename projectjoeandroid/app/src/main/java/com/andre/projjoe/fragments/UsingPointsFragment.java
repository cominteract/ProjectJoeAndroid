package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class UsingPointsFragment extends Fragment {
	FragmentInteractionInterface fragmentInteractionInterface;
	DataPasses dataPasses;
	EditText usingPointsEdt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	  	View rootView = inflater.inflate(R.layout.fragment_usingpoints, container, false);
			TextView usingCurrentPointsTxt = (TextView) rootView.findViewById(R.id.usingCurrentPointsTxt);
			usingCurrentPointsTxt.setText(dataPasses.currentJoeUser().currentPoints + "");
    	  	Button usingOkBtn = (Button) rootView.findViewById(R.id.usingOkBtn);
			usingPointsEdt = (EditText) rootView.findViewById(R.id.usingPointsEdt);
			usingOkBtn.setOnClickListener(new OnClickListener()
			  {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int points = Integer.valueOf(usingPointsEdt.getText().toString());
					UseInputPasswordFragment useInputPasswordFragment = UseInputPasswordFragment.newInstance();
					useInputPasswordFragment.points = points;
					fragmentInteractionInterface.onSwitchFragment(UsingPointsFragment.this,useInputPasswordFragment);

				}
			  });
    	  return rootView;
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
	public static UsingPointsFragment newInstance() {
		UsingPointsFragment fragment = new UsingPointsFragment();

		return fragment;
	}
}
