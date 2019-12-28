package com.andre.projjoe.fragments;

import com.andre.projjoe.activities.Login;
import com.andre.projjoe.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class First extends Fragment {
	String[] attribs = {"hello","#ffffff","#555555","16"};
	int padding[] = {5,6,4,3};
	int margin[] = {15,12,13,14};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.first, container, false);
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
    
    public boolean doSomething()
    {
    	Log.d("clicked","nice clicked");
    	return true;
    }
    public void getonClick(final Boolean doSomething,final Context c,int viewid,View cont)
	{
    	
    	/*View v = (View) cont.findViewById(viewid);
	
    	v.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d("yooo",doSomething.toString());
			}
    	});*/
	}
    
}