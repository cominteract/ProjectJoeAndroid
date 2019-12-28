package com.sources.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class OnClickClass {
	
	public Boolean somethingDone;
	public void getOnClick(View v,Boolean doSomething)
	{
		v.setOnClickListener(onClickListener);
		somethingDone = doSomething;
	}
	private OnClickListener onClickListener = new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	         switch(v.getId()){
	             case 32:
	                  //DO something
	             break;
	             case 111:
	                  //DO something
	             break;
	             case 123:
	                  //DO something
	             break;
	         }

	   }
	};
}
