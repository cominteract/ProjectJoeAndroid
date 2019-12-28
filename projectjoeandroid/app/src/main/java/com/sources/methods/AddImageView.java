package com.sources.methods;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ImageView;

public class AddImageView {
	public ImageView getImageView(String[] attribs,int padding[],int margin[],Context c,int id)
	{
		
		ImageView iview = new ImageView(c);
		iview.setBackgroundColor(Color.parseColor(attribs[2]));
		iview.setId(id);
		//(left, top, right, bottom)
		iview.setPadding(padding[0], padding[1], padding[2], padding[3]);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		//(left, top, right, bottom)
		lp.setMargins(margin[0],margin[1],margin[2],margin[3]);
		iview.setLayoutParams(lp);
		return iview;
	}
}
