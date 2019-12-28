package com.sources.methods;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;

public class AddButton {
	
	public Button getButton(String[] attribs,int padding[],int margin[],Context c,int id)
	{
		Button tview = new Button(c);
		tview.setText(attribs[0]);
		tview.setTextColor(Color.parseColor(attribs[1]));
		tview.setBackgroundColor(Color.parseColor(attribs[2]));
		tview.setTextSize(Integer.valueOf(attribs[3]));
		tview.setId(id);
		//(left, top, right, bottom)
		tview.setPadding(padding[0], padding[1], padding[2], padding[3]);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		//(left, top, right, bottom)
		lp.setMargins(margin[0],margin[1],margin[2],margin[3]);
		tview.setLayoutParams(lp);
		return tview;
	}
	public Button[] getButtons(int size,String[] attribs,int padding[],int margin[],Context c,int id)
	{
		Button[] tview = new Button[size];
		for(int x = 0;x<size;x++)
		{
			tview[x] = new Button(c);
			tview[x].setText(attribs[0]);
			tview[x].setTextColor(Color.parseColor(attribs[1]));
			tview[x].setBackgroundColor(Color.parseColor(attribs[2]));
			tview[x].setTextSize(Integer.valueOf(attribs[3]));
			tview[x].setId(id);
			//(left, top, right, bottom)
			tview[x].setPadding(padding[0], padding[1], padding[2], padding[3]);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			//(left, top, right, bottom)
			lp.setMargins(margin[0],margin[1],margin[2],margin[3]);
			tview[x].setLayoutParams(lp);
		}
		return tview;
	}
	
	public Button[] initButton(int size,int[] ids, String[] attribs, View v)
	{
		Button[] tview = new Button[size];
		for(int x = 0;x<size;x++)
		{
			tview[x] = (Button) v.findViewById(ids[x]);
			Log.d(attribs[x],tview[x].getText().toString());
		}
		return tview;
	}
	public String[] getAllText(int size,int[] ids, String[] attribs, View v)
	{
		Button[] tview = new Button[size];
		String[] stringholder = new String[size];
		for(int x = 0;x<size;x++)
		{
			tview[x] = (Button) v.findViewById(ids[x]);
			
			stringholder[x] = tview[x].getText().toString();
		}
		return stringholder;
	}
	
	public Button[] getAllButtons(int size,View v)
	{
		Button[] tview = new Button[20];
		int childcount = ((ViewGroup) v).getChildCount();
		int tvcount = 0;
		for (int i=0; i < childcount; i++){
		      View vs = ((ViewGroup) v).getChildAt(i);
		      if(vs instanceof Button)
		      {
		    	  tview[tvcount]  = (Button) vs;
		      }
		      tvcount++;
		}
		return tview;
	}
	
	public int[] getAllIds(int size,View v)
	{
		Button[] tview = new Button[20];
		int[] ids = new int[20];
		int childcount = ((ViewGroup) v).getChildCount();
		int tvcount = 0;
		for (int i=0; i < childcount; i++){
		      View vs = ((ViewGroup) v).getChildAt(i);
		      if(vs instanceof Button)
		      {
		    	  tview[tvcount]  = (Button) vs;
		    	  ids[tvcount] = vs.getId();
		      }
		      tvcount++;
		}
		return ids;
	}
}
