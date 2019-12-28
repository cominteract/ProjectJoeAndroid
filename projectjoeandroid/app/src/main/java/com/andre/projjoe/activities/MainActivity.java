package com.andre.projjoe.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.andre.projjoe.R;
import com.andre.projjoe.adapters.TabsPagerAdapter;
import com.andre.projjoe.fragments.PassesFragment;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titless
    private String[] tabs = { "First", "Second", "Third", "Fourth", "Fifth" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

    }







}