package com.andre.projjoe.adapters;


/**
 * Created by Dre on 5/30/2014.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.models.Post;

public class MerchPhotosAdapter extends BaseAdapter {
    private static LayoutInflater inflater=null;

    private List<Post> postList;
    Activity activity;


    public MerchPhotosAdapter(Activity a, List<Post> postList) {
        activity = a;
        this.postList = postList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return this.postList.size();
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
            vi = inflater.inflate(R.layout.merchphotos_adapter,null);
        /*for(int cntpass = 0;cntpass<4;cntpass++)
        {
        	HashMap<String,Integer> passtype = new HashMap<String,Integer>();
        	passtype.put("passtype", cntpass);
        	drawablelist.add(passtype);
        }
        */

      
        TextView merchPhotosBranchesTagTxt = (TextView) vi.findViewById(R.id.merchPhotosBranchesTagTxt);
        ImageView merchantDetailsImage = (ImageView) vi.findViewById(R.id.merchPhotosImage);
        Glide.with(activity).load(postList.get(position).postImage).into(merchantDetailsImage);
        merchPhotosBranchesTagTxt.setText(postList.get(position).postCaption);

        
        vi.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			}
        });
        		return vi;
    }
}
