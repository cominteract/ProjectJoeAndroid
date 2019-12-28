package com.andre.projjoe.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.andre.projjoe.R;
import com.andre.projjoe.fragments.DashboardFragment;
import com.andre.projjoe.fragments.JoeUserFriendsFragment;
import com.andre.projjoe.fragments.JoeUserLogoutFragment;
import com.andre.projjoe.fragments.JoeUserProfileFragment;
import com.andre.projjoe.fragments.JoeUserSettingsFragment;
import com.andre.projjoe.fragments.JoeUserTransactionsFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;

public class DashboardAdapter extends BaseAdapter {
    private Context mContext;
    int height;
    int width;
    int startwidth;
    FragmentInteractionInterface fragmentInteractionInterface;
    DashboardFragment dashboardFragment;
    public Integer[] mThumbIds = {
            R.drawable.profile, R.drawable.transaction,
            R.drawable.connection, R.drawable.settings,
            R.drawable.logout,R.drawable.blank
    };
 
    // Constructor
    public DashboardAdapter(Context c, FragmentInteractionInterface fragmentInteractionInterface, DashboardFragment dashboardFragment){
        this.fragmentInteractionInterface = fragmentInteractionInterface;
        this.dashboardFragment = dashboardFragment;
        mContext = c;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        startwidth = width/3 + 6;

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
        final ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundResource(mThumbIds[position]);

        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setAdjustViewBounds(true);
        GridView.LayoutParams layoutParams;
        layoutParams = new GridView.LayoutParams(startwidth,160);

        imageView.setLayoutParams(layoutParams);
        imageView.setTag(Integer.valueOf(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)imageView.getTag() == 0)
                    fragmentInteractionInterface.onSwitchFragment(dashboardFragment, JoeUserProfileFragment.newInstance());
                else if((int)imageView.getTag() == 1)
                    fragmentInteractionInterface.onSwitchFragment(dashboardFragment, JoeUserTransactionsFragment.newInstance());
                else if((int)imageView.getTag() == 2)
                    fragmentInteractionInterface.onSwitchFragment(dashboardFragment, JoeUserFriendsFragment.newInstance());
                else if((int)imageView.getTag() == 3)
                    fragmentInteractionInterface.onSwitchFragment(dashboardFragment, JoeUserSettingsFragment.newInstance());
                else if((int)imageView.getTag() == 4)
                    fragmentInteractionInterface.onSwitchFragment(dashboardFragment, JoeUserLogoutFragment.newInstance());
                else if((int)imageView.getTag() == 5)
                    fragmentInteractionInterface.onSwitchFragment(dashboardFragment, JoeUserProfileFragment.newInstance());
            }
        });

        return imageView;
    }
 
}