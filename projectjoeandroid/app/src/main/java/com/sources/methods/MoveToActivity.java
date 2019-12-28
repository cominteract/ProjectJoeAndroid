package com.sources.methods;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

public class MoveToActivity {
	public void moveNext(Context from, Class to, String[] extraindex, String[] extravalues)
	{
		
		Intent i = new Intent(from,to);
		
		int total = extraindex.length;
		
		for(int l = 0;l < total; l++)
		{
			i.putExtra(extraindex[l], extravalues[l]);
		}
		
		from.startActivity(i);	
	}
	
	public void moveNextArray(Context from, Class to, String extraindex, ArrayList<String> extravalues)
	{
		
		Intent i = new Intent(from,to);
		i.putStringArrayListExtra(extraindex, extravalues);
		from.startActivity(i);	
	}
	
	
	
}
