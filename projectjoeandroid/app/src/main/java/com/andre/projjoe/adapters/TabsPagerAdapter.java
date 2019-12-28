package com.andre.projjoe.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andre.projjoe.fragments.Fifth;
import com.andre.projjoe.fragments.First;
import com.andre.projjoe.fragments.Fourth;
import com.andre.projjoe.fragments.Second;
import com.andre.projjoe.fragments.Third;

public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new First();
        case 1:
            // Games fragment activity
            return new Second();
        case 2:
            // Movies fragment activity
            return new Third();
        case 3:
            // Movies fragment activity
            return new Fourth();
        case 4:
            // Movies fragment activity
            return new Fifth();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
 
}