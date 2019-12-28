package com.andre.projjoe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre.projjoe.R;

public class SendEmail extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.sendemail, container, false);
        Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"cominteract@gmail.com"});		  
		email.putExtra(Intent.EXTRA_SUBJECT, "Referral from Project Joe");
		email.putExtra(Intent.EXTRA_TEXT, "I recommend you to check out this awesome project here And hey its on me use this referral code to get a free 10% discount Referral:09126TGKJ");
		email.setType("message/rfc822");
		getActivity().startActivity(Intent.createChooser(email, "Choose an Email client :"));
		rootView.findViewById(R.id.btnok).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

			}
		});
		return rootView;
    }
	
}
