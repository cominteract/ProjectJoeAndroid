package com.sources.methods;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddTextView {
	
	public TextView getTextView(String[] attribs,int padding[],int margin[],Context c,int id)
	{
		
		TextView tview = new TextView(c);
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
	

	
}
