package com.andre.projjoe.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.models.JoeUser;

public class LeaderBoardAdapter extends BaseAdapter {
    private static LayoutInflater inflater=null;

	List<JoeUser> joeUserList;
	Activity activity;


	public LeaderBoardAdapter(Activity a, List<JoeUser> joeUserList) {
		activity = a;
		this.joeUserList = joeUserList;
		Log.d(" Ilan user ", joeUserList.size() + " ");
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return joeUserList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
        View vi=convertView;
        if(convertView==null)
        	vi = inflater.inflate(R.layout.leaderboard_adapter,null);
        
        	TextView leaderBoardFullIdTxt = (TextView) vi.findViewById(R.id.leaderBoardFullIdTxt);
        	TextView leaderBoardFullNameTxt = (TextView) vi.findViewById(R.id.leaderBoardFullNameTxt);
        	TextView leaderBoardPointsTxt = (TextView) vi.findViewById(R.id.leaderBoardPointsTxt);
        	ImageView leaderBoardImage = (ImageView) vi.findViewById(R.id.leaderBoardImage);
			leaderBoardFullIdTxt.setText(String.valueOf(position + 1)+")");
			leaderBoardFullNameTxt.setText(joeUserList.get(position).fullName);

			leaderBoardPointsTxt.setText(joeUserList.get(position).merchantPoints + " ");
			Glide.with(activity).load(joeUserList.get(position).image).into(leaderBoardImage);


		return vi;
	}

}
