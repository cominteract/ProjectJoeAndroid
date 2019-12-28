package com.sources.methods;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

	private SharedPreferences sharedPrefs;
	public void setShared(String[] keys, String[] values, String[] keyslong, Long[] valueslong, Context c)
	{
		sharedPrefs = c.getSharedPreferences("contextpref",c.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        
        for(int cntval = 0;cntval<keys.length;cntval++)
        {
        	editor.putString(keys[cntval],values[cntval]);
        }
        for(int cntval = 0;cntval<keyslong.length;cntval++)
        {
        	editor.putLong(keyslong[cntval],valueslong[cntval]);
        }
        editor.commit();
	}
	
	public String[] getSharedString(String[] keys, String[] values, Context c)
	{
		String[] outputstring = null;
		sharedPrefs = c.getSharedPreferences("contextpref",c.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        for(int cntval = 0;cntval<keys.length;cntval++)
        {
        	if(sharedPrefs.contains(keys[cntval]))
        		outputstring[cntval] = values[cntval];
        }

        
        return outputstring;
	}
	
	public Long[] getSharedLong(String[] keyslong, Long[] valueslong, Context c)
	{
		sharedPrefs = c.getSharedPreferences("contextpref",c.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
		Long[] outputlong = null;
		for(int cntval = 0;cntval<keyslong.length;cntval++)
		{
			if(sharedPrefs.contains(keyslong[cntval]))
				outputlong[cntval] = valueslong[cntval];	
		}
		return outputlong;
	}
	
}
