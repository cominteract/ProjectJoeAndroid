package com.andre.projjoe.adapters;

/**
 * Created by Dre on 5/30/2014.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andre.projjoe.R;

public class SearchAdapter extends BaseAdapter {
    private static LayoutInflater inflater=null;
    private final ArrayList<HashMap<String,String>> values;
    private final ArrayList<HashMap<String,String>> data;

    Activity activity;
    public SearchAdapter(Activity a, ArrayList<HashMap<String,String>> values) {
        activity = a;
        data = values;
        this.values = values;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.search_adapter,null);
        /*for(int cntpass = 0;cntpass<4;cntpass++)
        {
        	HashMap<String,Integer> passtype = new HashMap<String,Integer>();
        	passtype.put("passtype", cntpass);
        	drawablelist.add(passtype);
        }
        */

      
        TextView tvdesc = (TextView) vi.findViewById(R.id.searchdesc);
        ImageView imgicon = (ImageView) vi.findViewById(R.id.searchicon);

        tvdesc.setText(values.get(position).get("descs"));
        imgicon.setBackgroundResource(Integer.valueOf(values.get(position).get("icons")));
        
        
        vi.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*TextView tvpage = (TextView) v.findViewById(R.id.tvpage);
				TextView tvbm = (TextView) v.findViewById(R.id.tvbmtitle);		
				TextView tvholder = (TextView) v.findViewById(R.id.tvholder);	
				Intent i = new Intent(activity,BookMarks.class);
				i.putExtra("book", tvbm.getText().toString());	
				String page = tvpage.getText().toString().replace("Page - ", "");
				String[] arrstr = tvholder.getText().toString().split("==");	
				i.putExtra("page", page.trim());
				i.putExtra("title", tvbm.getText().toString());
				i.putExtra("getfontsz", arrstr[0]);
				i.putExtra("getfontad", arrstr[1]);
				i.putExtra("getwlen", arrstr[2]);
				i.putExtra("numpages", Integer.parseInt(arrstr[3].trim()));
				//Toast.makeText(activity, arrstr[0] + " == " + arrstr[1] + " == " + arrstr[2] + " == " + arrstr[3] , Toast.LENGTH_LONG).show();
				activity.startActivity(i);	*/
				
				
			}
        });
        		return vi;
            }
        }
