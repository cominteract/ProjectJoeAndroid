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
import com.andre.projjoe.models.Pass;

public class ChallengeDetailsFragment extends Fragment{

	FragmentInteractionInterface fragmentInteractionInterface;
	DataPasses dataPasses;
	public Pass selectedPass;
	boolean isConditionSatisfied = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    		View rootView = inflater.inflate(R.layout.fragment_challengedetails, container, false);
    		TextView challengeDetailsCurrentPointsTxt = (TextView) rootView.findViewById(R.id.challengeDetailsCurrentPointsTxt);
    		TextView challengeDetailsPointsCaptionTxt = (TextView) rootView.findViewById(R.id.challengeDetailsPointsCaptionTxt);
    		TextView challengeDetailsTxt = (TextView)rootView.findViewById(R.id.challengeDetailsTxt);
    		rootView.findViewById(R.id.challengeDetailsClaimBtn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ChallengeClaimedFragment challengeClaimedFragment = ChallengeClaimedFragment.newInstance();
					challengeClaimedFragment.selectedPass = selectedPass;


					fragmentInteractionInterface.onSwitchFragment(ChallengeDetailsFragment.this, challengeClaimedFragment);;
				}
			});
			challengeDetailsTxt.setText("1) This is based on the total of points you have earned \n" +
    				"2) This does not include point give to you.\n" +
    				"3) Constantly update your status in meeting requirement by connecting to the internet\n" +
    				"4) Upon meeting the requirement. 'Claim' button will appear below to get your mystery award" +
    				"5) An internet connection is required to claim the mystery reward");
			if(selectedPass!=null)
			{
				challengeDetailsCurrentPointsTxt.setText(challengeDetailsCurrentPointsTxt.getText().toString()
						+ dataPasses.currentJoeUser().currentPoints);
				challengeDetailsPointsCaptionTxt.setText(challengeDetailsPointsCaptionTxt.getText().toString()
						+ selectedPass.passPoints.point);
			}

//    		TextView tvc = (TextView) rootView.findViewById(R.id.tvchallenge);
//    		TextView tvd = (TextView) rootView.findViewById(R.id.tvchallengedesc);
//    		TextView tvmp = (TextView) rootView.findViewById(R.id.tvmypoints);
//    		TextView tvt = (TextView) rootView.findViewById(R.id.tvtitle);
//    		Button btnclaim = (Button) rootView.findViewById(R.id.btnclaim);
//    		btnclaim.setOnClickListener(new OnClickListener()
//    		{
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//
//				}
//
//
//    		});
//    		tvt.setText("Win a mystery reward");
//    		tvc.setText("Earning a total of 1000 Joe points");
//    		tvmp.setText("My points:" + 1000);
//    		tvd.setText("1) This is based on the total of points you have earned \n" +
//    				"2) This does not include point give to you.\n" +
//    				"3) Constantly update your status in meeting requirement by connecting to the internet\n" +
//    				"4) Upon meeting the requirement. 'Claim' button will appear below to get your mystery award" +
//    				"5) An internet connection is required to claim the mystery reward");
    		return rootView;
    }

	public static ChallengeDetailsFragment newInstance() {
		ChallengeDetailsFragment fragment = new ChallengeDetailsFragment();
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
