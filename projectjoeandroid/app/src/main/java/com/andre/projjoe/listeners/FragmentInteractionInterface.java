package com.andre.projjoe.listeners;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.andre.projjoe.models.DataPasses;

/**
 * Created by Dre on 2/21/2018.
 */

public interface FragmentInteractionInterface {
    public void onSwitchFragment(Fragment currentFragment, Fragment destinationFragment);
    public void onSwitchFragmentWithPosition(Fragment currentFragment, Fragment destinationFragment, int position);
    public DataPasses dataPassRetrieved();
    public void updatedCurrentPass();
    public void onTransactionsRetrieved();
    public void onLogout();
}
