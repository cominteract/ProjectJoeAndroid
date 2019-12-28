package com.andre.projjoe.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;

public class AnswerSurveyFragment extends Fragment {
	    LinearLayout surveyContainer;
	   	String[] questions = {"What is your favorite softdrink?","When do you usually buy softdrinks?"};
	   	int[] initnumber = {1,6};
	   	String[] answers = {"Coca-Cola","Pepsi","Royal","Sprite","7-up","Breakfast","Lunch","Merienda","Dinner","All of the above"};
		FragmentInteractionInterface fragmentInteractionInterface;
	    public Pass selectedPass;
	   	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    		View rootView = inflater.inflate(R.layout.fragment_answersurvey, container, false);
		   		surveyContainer = ( LinearLayout) rootView.findViewById(R.id.surveyContainer);
				Button answerSurveyOkBtn = (Button) rootView.findViewById(R.id.answerSurveyOkBtn);
				answerSurveyOkBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						shareTransaction();
						fragmentInteractionInterface.onSwitchFragment(AnswerSurveyFragment.this,DiscoverFragment.newInstance());
					}
				});
	    		for(int cnt = 0;cnt<2;cnt++)
	    			createRadioButton(initnumber[cnt],questions[cnt]);
	    		return rootView;
	   	}
		private void createRadioButton(int init,String question) {
			final TextView tv = new TextView(getActivity());
			tv.setText(question);
			tv.setTextColor(Color.parseColor("#ffffff"));
			surveyContainer.addView(tv);
			final RadioButton[] rb = new RadioButton[5];
			RadioGroup rg = new RadioGroup(getActivity()); //create the RadioGroup
			rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
			int total = init + 5;
			int cntrb = 0;
			for(int i=init; i<total; i++){
				rb[cntrb]  = new RadioButton(getActivity());
				rb[cntrb].setTextColor(Color.WHITE);
				rg.addView(rb[cntrb]); //the RadioButtons are added to the radioGroup instead of the layout
				rb[cntrb].setText(answers[i - 1]);
				cntrb++;
			}
			surveyContainer.addView(rg);//you add the whole RadioGroup to the layout
		}

		private void shareTransaction()
		{
			Transaction transaction = new Transaction(fragmentInteractionInterface.dataPassRetrieved().currentJoeUser()
					,fragmentInteractionInterface.dataPassRetrieved().randomDate(),selectedPass.passPoints.point,
					Transaction.TransactionType.TransactionReceivingPass);
			selectedPass.isLocked = false;
			fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().passList.add(selectedPass);
			if(fragmentInteractionInterface.dataPassRetrieved().getAvailablePasses().contains(selectedPass))
				fragmentInteractionInterface.dataPassRetrieved().getAvailablePasses().remove(selectedPass);
			fragmentInteractionInterface.dataPassRetrieved().addTransaction(transaction);
			Toast.makeText(getActivity(),"Congratulations! You claimed " + selectedPass.passDescription,Toast.LENGTH_LONG).show();
			fragmentInteractionInterface.onSwitchFragment(AnswerSurveyFragment.this, PassesFragment.newInstance());
		}

		public static AnswerSurveyFragment newInstance() {
			AnswerSurveyFragment fragment = new AnswerSurveyFragment();

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
