package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Pass;

public class UseInputPasswordFragment extends Fragment {
	FragmentInteractionInterface fragmentInteractionInterface;
	public Pass selectedPass;
	public int points;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	  	View rootView = inflater.inflate(R.layout.fragment_inputpasscode, container, false);
    	  	Button inputPasswordOKBtn = (Button) rootView.findViewById(R.id.inputPasswordOKBtn);
			TextView inputPasswordCaptionTxt = (TextView)rootView.findViewById(R.id.inputPasswordCaptionTxt);
			TextView inputPasswordPassName = (TextView) rootView.findViewById(R.id.inputPasswordPassName);
			if(selectedPass==null)
			{
				rootView.findViewById(R.id.inputPasswordImage).setVisibility(View.GONE);
				inputPasswordPassName.setVisibility(View.GONE);
				inputPasswordCaptionTxt.setText(" You are about to use " + points);
			}
			else
			{

				Glide.with(getActivity()).load(selectedPass.passMerchant.merchantImage).into(((ImageView)rootView.findViewById(R.id.inputPasswordImage)));
				inputPasswordCaptionTxt.setText(" You are about to use ");
				inputPasswordPassName.setText(selectedPass.passDescription);
			}
			inputPasswordOKBtn.setOnClickListener(new OnClickListener()
    	  {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(selectedPass!=null) {
					Log.d(" Why Here ", " Why ");
					ShowQrUsingPassFragment showQrUsingPassFragment = ShowQrUsingPassFragment.newInstance();
					showQrUsingPassFragment.selectedPass = selectedPass;
					fragmentInteractionInterface.onSwitchFragment(UseInputPasswordFragment.this, showQrUsingPassFragment);
				}
				else
				{
					ShowQrUsePointsFragment showQrUsePointsFragment = ShowQrUsePointsFragment.newInstance();
					showQrUsePointsFragment.points = points;
					fragmentInteractionInterface.onSwitchFragment(UseInputPasswordFragment.this, showQrUsePointsFragment);
				}
			}
  	  	  });
    	  return rootView;
    }

	public static UseInputPasswordFragment newInstance() {
		UseInputPasswordFragment fragment = new UseInputPasswordFragment();

		return fragment;
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
