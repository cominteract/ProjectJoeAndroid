package com.andre.projjoe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.fragments.AnswerSurveyFragment;
import com.andre.projjoe.fragments.ChallengeClaimedFragment;
import com.andre.projjoe.fragments.ChallengeDetailsFragment;
import com.andre.projjoe.fragments.CompletePhotoFragment;
import com.andre.projjoe.fragments.DashboardFragment;
import com.andre.projjoe.fragments.DiscoverFragment;
import com.andre.projjoe.fragments.FilterAllMerchantsFragment;
import com.andre.projjoe.fragments.FilterIndustryFragment;
import com.andre.projjoe.fragments.FilterPhotoFragment;
import com.andre.projjoe.fragments.GiveInputPasswordFragment;
import com.andre.projjoe.fragments.GivePassFragment;
import com.andre.projjoe.fragments.GivePassSuccessFragment;
import com.andre.projjoe.fragments.GivePointsFragment;
import com.andre.projjoe.fragments.GivePointsSuccessFragment;
import com.andre.projjoe.fragments.GrabSuccessDetailsFragment;
import com.andre.projjoe.fragments.JoeUserFriendsFragment;
import com.andre.projjoe.fragments.JoeUserLogoutFragment;
import com.andre.projjoe.fragments.JoeUserProfileFragment;
import com.andre.projjoe.fragments.JoeUserSettingsFragment;
import com.andre.projjoe.fragments.MerchantDetailsFragment;
import com.andre.projjoe.fragments.PassDetailsFragment;
import com.andre.projjoe.fragments.PassesFragment;
import com.andre.projjoe.fragments.PhotoCheckInFragment;
import com.andre.projjoe.fragments.PointsFragment;
import com.andre.projjoe.fragments.ReceivedPassFragment;
import com.andre.projjoe.fragments.ShowQrEarnedPtsFragment;
import com.andre.projjoe.fragments.ShowQrUsePointsFragment;
import com.andre.projjoe.fragments.ShowQrUsingPassFragment;
import com.andre.projjoe.fragments.JoeUserTransactionsFragment;
import com.andre.projjoe.fragments.TagPhotoFragment;
import com.andre.projjoe.fragments.UseInputPasswordFragment;
import com.andre.projjoe.fragments.UsingPointsFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class MainNavigation extends BaseActivity implements FragmentInteractionInterface {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Fragment fragmentPos1;
    Fragment fragmentPos2;
    Fragment fragmentPos3;
    Fragment fragmentPos4;
    Fragment fragmentPos5;
    private int selectedPosition;
    Toolbar toolbar;
    boolean transactionsLoaded = false;
    ImageView mainNavigationSearch;
    DataPasses dataPasses = new DataPasses(this);
    private String[] toolbarTitle = new String[]{"My Passes","Discover","Photo Check In", "My Points","Dashboard"};
    private ViewPager mViewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mainNavigationSearch = (ImageView)findViewById(R.id.mainNavigationSearch);
        mainNavigationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainNavigation.this, Search.class);
                startActivity(i);
            }
        });
        setSupportActionBar(toolbar);
        setupInitialFragments();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.navigationViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            // optional
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            // optional
            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
                if(position==1) {
                    mViewPager.setBackgroundColor(Color.parseColor("#50527a"));
                    toolbar.setBackgroundColor(Color.parseColor("#50527a"));
                }
                else {
                    mViewPager.setBackgroundColor(Color.parseColor("#1fb4dc"));
                    toolbar.setBackgroundColor(Color.parseColor("#1fb4dc"));
                }
                if(position<5)
                    toolbar.setTitle(toolbarTitle[position]);
            }

            // optional
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutNavigation);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.passes_tab_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.discover_tab_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.photos_tab_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.points_tab_icon);
        tabLayout.getTabAt(4).setIcon(R.drawable.dashboard_tab_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwitchFragment(Fragment currentFragment, Fragment destinationFragment) {
        if(transactionsLoaded) {
            mSectionsPagerAdapter.switchFragment(currentFragment, destinationFragment);
            setupTabIcons();
        }
        else Toast.makeText(this," Still loading ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSwitchFragmentWithPosition(Fragment currentFragment, Fragment destinationFragment, int position) {

    }

    @Override
    public DataPasses dataPassRetrieved() {
        return dataPasses;
    }

    @Override
    public void updatedCurrentPass() {
        if(fragmentPos1 instanceof PassesFragment)
            ((PassesFragment) fragmentPos1).updateCurrentPasses();

    }

    @Override
    public void onTransactionsRetrieved() {
        transactionsLoaded = true;
        if(fragmentPos3 instanceof PhotoCheckInFragment)
            ((PhotoCheckInFragment)fragmentPos3).photoChecking();
    }

    @Override
    public void onLogout() {
        finish();
    }
    private void setupInitialFragments()
    {
        fragmentPos1 = PassesFragment.newInstance();
        fragmentPos2 = DiscoverFragment.newInstance();
        fragmentPos3 = PhotoCheckInFragment.newInstance();
        fragmentPos4 = PointsFragment.newInstance();
        fragmentPos5 = DashboardFragment.newInstance();
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        FragmentManager fm;
        public void switchFragment(Fragment currentFragment, Fragment destinationFragment)
        {
            fm.beginTransaction().remove(currentFragment).commitNow();
            if(destinationFragment instanceof PassDetailsFragment && fragmentPos1.equals(currentFragment))
                fragmentPos1 = (PassDetailsFragment)destinationFragment;
            else if(destinationFragment instanceof PassDetailsFragment && fragmentPos2.equals(currentFragment))
                fragmentPos2 = (PassDetailsFragment)destinationFragment;
            else if(destinationFragment instanceof UseInputPasswordFragment && currentFragment.equals(fragmentPos1))
                fragmentPos1 = (UseInputPasswordFragment) destinationFragment;
            else if(destinationFragment instanceof UseInputPasswordFragment && currentFragment.equals(fragmentPos4))
                fragmentPos4 = (UseInputPasswordFragment) destinationFragment;
            else if(destinationFragment instanceof ShowQrUsingPassFragment)
                fragmentPos1 = (ShowQrUsingPassFragment) destinationFragment;
            else if (destinationFragment instanceof PassesFragment)
                fragmentPos1 = (PassesFragment) destinationFragment;
            else if(destinationFragment instanceof GivePassFragment)
                fragmentPos1 = (GivePassFragment) destinationFragment;
            else if(destinationFragment instanceof GiveInputPasswordFragment && currentFragment.equals(fragmentPos1))
                fragmentPos1 = (GiveInputPasswordFragment) destinationFragment;
            else if(destinationFragment instanceof GiveInputPasswordFragment && currentFragment.equals(fragmentPos4))
                fragmentPos4 = (GiveInputPasswordFragment) destinationFragment;
            else if(destinationFragment instanceof ReceivedPassFragment)
                fragmentPos1 = (ReceivedPassFragment) destinationFragment;
            else if(destinationFragment instanceof GivePassSuccessFragment)
                fragmentPos1 = (GivePassSuccessFragment) destinationFragment;
            else if(destinationFragment instanceof DiscoverFragment)
                fragmentPos2 = (DiscoverFragment) destinationFragment;
            else if(destinationFragment instanceof AnswerSurveyFragment)
                fragmentPos2 = (AnswerSurveyFragment) destinationFragment;
            else if(destinationFragment instanceof GrabSuccessDetailsFragment)
                fragmentPos2 = (GrabSuccessDetailsFragment) destinationFragment;
            else if(destinationFragment instanceof FilterIndustryFragment)
                fragmentPos2 = (FilterIndustryFragment) destinationFragment;
            else if(destinationFragment instanceof FilterAllMerchantsFragment)
                fragmentPos2 = (FilterAllMerchantsFragment) destinationFragment;
            else if(destinationFragment instanceof MerchantDetailsFragment)
                fragmentPos2 = (MerchantDetailsFragment) destinationFragment;
            else if(destinationFragment instanceof ChallengeClaimedFragment)
                fragmentPos2 = (ChallengeClaimedFragment) destinationFragment;
            else if(destinationFragment instanceof ChallengeDetailsFragment)
                fragmentPos2 = (ChallengeDetailsFragment) destinationFragment;
            else if(destinationFragment instanceof PhotoCheckInFragment)
                fragmentPos3 = (PhotoCheckInFragment) destinationFragment;
            else if(destinationFragment instanceof CompletePhotoFragment)
                fragmentPos3 = (CompletePhotoFragment) destinationFragment;
            else if(destinationFragment instanceof FilterPhotoFragment)
                fragmentPos3 = (FilterPhotoFragment) destinationFragment;
            else if(destinationFragment instanceof TagPhotoFragment)
                fragmentPos3 = (TagPhotoFragment) destinationFragment;
            else if(destinationFragment instanceof GivePointsFragment)
                fragmentPos4 = (GivePointsFragment) destinationFragment;
            else if(destinationFragment instanceof UsingPointsFragment)
                fragmentPos4 = (UsingPointsFragment) destinationFragment;
            else if(destinationFragment instanceof PointsFragment)
                fragmentPos4 = (PointsFragment) destinationFragment;
            else if(destinationFragment instanceof ShowQrEarnedPtsFragment)
                fragmentPos4 = (ShowQrEarnedPtsFragment) destinationFragment;
            else if(destinationFragment instanceof ShowQrUsePointsFragment)
                fragmentPos4 = (ShowQrUsePointsFragment) destinationFragment;
            else if(destinationFragment instanceof GivePointsSuccessFragment)
                fragmentPos4 = (GivePointsSuccessFragment) destinationFragment;
            else if(destinationFragment instanceof JoeUserProfileFragment)
                fragmentPos5 = (JoeUserProfileFragment) destinationFragment;
            else if(destinationFragment instanceof DashboardFragment)
                fragmentPos5 = (DashboardFragment) destinationFragment;
            else if(destinationFragment instanceof JoeUserTransactionsFragment)
                fragmentPos5 = (JoeUserTransactionsFragment) destinationFragment;
            else if(destinationFragment instanceof JoeUserFriendsFragment)
                fragmentPos5 = (JoeUserFriendsFragment) destinationFragment;
            else if(destinationFragment instanceof JoeUserSettingsFragment)
                fragmentPos5 = (JoeUserSettingsFragment) destinationFragment;
            else if(destinationFragment instanceof JoeUserLogoutFragment)
                fragmentPos5 = (JoeUserLogoutFragment) destinationFragment;
            notifyDataSetChanged();
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return fragmentPos1;
            else if(position == 1)
                return fragmentPos2;
            else if(position == 2)
                return fragmentPos3;
            else if(position == 3)
                return fragmentPos4;
            else return fragmentPos5;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    public void onBackPressed() {
        if(canGoBack())
        super.onBackPressed();
        else
        {
            if(selectedPosition==0)
                onSwitchFragment(fragmentPos1, PassesFragment.newInstance());
            else if(selectedPosition==1)
                onSwitchFragment(fragmentPos2, DiscoverFragment.newInstance());
            else if(selectedPosition==2)
                onSwitchFragment(fragmentPos3, PhotoCheckInFragment.newInstance());
            else if(selectedPosition==3)
                onSwitchFragment(fragmentPos4, PointsFragment.newInstance());
            else if(selectedPosition==4) {
                onSwitchFragment(fragmentPos5, DashboardFragment.newInstance());
            }
        }
    }

    private boolean canGoBack()
    {
        return (fragmentPos1 instanceof  PassesFragment
                && fragmentPos2 instanceof  DiscoverFragment
                && fragmentPos3 instanceof PhotoCheckInFragment
                && fragmentPos4 instanceof  PointsFragment
                && fragmentPos5 instanceof  DashboardFragment);
    }

    @Override
    public void cameraPictureTakenSuccess() {
        super.cameraPictureTakenSuccess();
        if(fragmentPos3 instanceof TagPhotoFragment)
        {
           ((TagPhotoFragment) fragmentPos3).handleCapturePhoto();
        }
    }
}
