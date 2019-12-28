package com.andre.projjoe.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.andre.projjoe.R;

public class FilterAdapter extends BaseAdapter {
    private Context mContext;
 
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.industry_arts_icon, R.drawable.industry_beauty_icon,
            R.drawable.industry_cars_icon, R.drawable.industry_entertainment_icon,
            R.drawable.industry_fashion_icon,R.drawable.industry_food_icon,
            R.drawable.industry_health_icon,R.drawable.industry_travel_icon,
            R.drawable.industry_lifestyle_icon
    };
 
    // Constructor
    public FilterAdapter(Context c){
        mContext = c;
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
 
}