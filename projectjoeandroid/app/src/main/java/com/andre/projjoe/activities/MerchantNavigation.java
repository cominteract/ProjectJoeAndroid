package com.andre.projjoe.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.andre.projjoe.R;
import com.andre.projjoe.fragments.MerchantDailyTimeRecord;
import com.andre.projjoe.fragments.MerchantDailyTimeRecordSuccess;
import com.andre.projjoe.fragments.MerchantEarnPointsTransaction;
import com.andre.projjoe.fragments.MerchantEmployeeRegisterFragment;
import com.andre.projjoe.fragments.MerchantTransactionSuccessFragment;
import com.andre.projjoe.fragments.MerchantReportFragment;
import com.andre.projjoe.fragments.MerchantReviewTransactionFragment;
import com.andre.projjoe.fragments.MerchantScanFragment;
import com.andre.projjoe.fragments.MerchantTransactionHistoryFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class MerchantNavigation extends BaseActivity implements FragmentInteractionInterface {
    private String[] transactionTypes = new String[]{"Use Pass","Earn Points","Use Points"};
    Fragment currentFragment;
    DataPasses dataPasses = new DataPasses();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_navigation);

        ImageView merchantNavigationDailyTimeImage = findViewById(R.id.merchantNavigationDailyTimeImage);
        ImageView merchantNavigationDisplayImage = findViewById(R.id.merchantNavigationDisplayImage);
        ImageView merchantNavigationTransactImage = findViewById(R.id.merchantNavigationTransactImage);
        ImageView merchantNavigationReportImage = findViewById(R.id.merchantNavigationReportImage);
        ImageView merchantNavigationHistoryImage = findViewById(R.id.merchantNavigationHistoryImage);
        Button merchantNavigationSettingsBtn = findViewById(R.id.merchantNavigationSettingsBtn);
        Button merchantNavigationLogoutBtn = findViewById(R.id.merchantNavigationLogoutBtn);
        merchantNavigationDailyTimeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantDailyTimeRecord merchantDailyTimeRecord = MerchantDailyTimeRecord.newInstance();
                currentFragment = merchantDailyTimeRecord;
                onSwitchFragment(null,merchantDailyTimeRecord);

            }
        });
        merchantNavigationTransactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantEmployeeRegisterFragment merchantEmployeeRegisterFragment = MerchantEmployeeRegisterFragment.newInstance();
                merchantEmployeeRegisterFragment.transactionType = transactionTypes[DataPasses.randInt(0,2)];
                currentFragment = merchantEmployeeRegisterFragment;
                onSwitchFragment(null,merchantEmployeeRegisterFragment);
            }
        });
        merchantNavigationHistoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantTransactionHistoryFragment merchantTransactionHistoryFragment = MerchantTransactionHistoryFragment.newInstance();

                currentFragment = merchantTransactionHistoryFragment;
                onSwitchFragment(null, merchantTransactionHistoryFragment);
            }
        });
        merchantNavigationReportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantReportFragment merchantReportFragment = MerchantReportFragment.newInstance();
                currentFragment = merchantReportFragment;
                onSwitchFragment(null,merchantReportFragment);
            }
        });
        merchantNavigationDisplayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantReportFragment merchantReportFragment = MerchantReportFragment.newInstance();
                currentFragment = merchantReportFragment;
                onSwitchFragment(null,merchantReportFragment);
            }
        });




    }

    @Override
    public void onSwitchFragment(Fragment currentFragment, Fragment destinationFragment) {
        findViewById(R.id.merchantNavigationBottomContainer).setVisibility(View.GONE);
        findViewById(R.id.merchantNavigationContainer).setVisibility(View.GONE);
        findViewById(R.id.merchantNavigationFrame).setVisibility(View.VISIBLE);
        if(currentFragment!=null) {
            this.currentFragment = currentFragment;
            getSupportFragmentManager().beginTransaction().remove(currentFragment);
        }

        if(destinationFragment!=null) {
            if (destinationFragment instanceof MerchantDailyTimeRecord) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantDailyTimeRecord) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantEmployeeRegisterFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantEmployeeRegisterFragment) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantTransactionHistoryFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantTransactionHistoryFragment) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantReportFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantReportFragment) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantDailyTimeRecordSuccess) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantDailyTimeRecordSuccess) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantEarnPointsTransaction) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantEarnPointsTransaction) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantReviewTransactionFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantReviewTransactionFragment) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantScanFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantScanFragment) destinationFragment).commit();
            }
            if (destinationFragment instanceof MerchantTransactionSuccessFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.merchantNavigationFrame, (MerchantTransactionSuccessFragment) destinationFragment).commit();
            }
        }
        else
        {
            findViewById(R.id.merchantNavigationBottomContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.merchantNavigationContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.merchantNavigationFrame).setVisibility(View.GONE);
        }

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

    }

    @Override
    public void onTransactionsRetrieved() {

    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onBackPressed() {
        if(findViewById(R.id.merchantNavigationFrame).getVisibility() == View.VISIBLE)
        {
            for(Fragment fragment:getSupportFragmentManager().getFragments()){

                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            findViewById(R.id.merchantNavigationBottomContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.merchantNavigationContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.merchantNavigationFrame).setVisibility(View.GONE);
        }
        else
            super.onBackPressed();
    }

    @Override
    public void cameraPictureTakenSuccess() {
        super.cameraPictureTakenSuccess();
        if(currentFragment instanceof MerchantDailyTimeRecord)
        {
            ((MerchantDailyTimeRecord) currentFragment).handleDailyTimeRecord();
        }
    }
}
