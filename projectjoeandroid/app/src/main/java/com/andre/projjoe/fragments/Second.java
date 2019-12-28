package com.andre.projjoe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andre.projjoe.R;
import com.andre.projjoe.activities.Login;

public class Second extends Fragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.second, container, false);
        ImageView go = (ImageView) rootView.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), Login.class);
				startActivity(i);
			}
		});
        return rootView;
    }
}