package com.andre.projjoe.activities;

import com.andre.projjoe.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Sharethis extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharethis);

	    
	    
//	    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
//        .setLink("https://developers.facebook.com/android")
//        .build();

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);


	}
	@Override
	protected void onResume() {
	    super.onResume();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);

	}

	@Override
	public void onPause() {
	    super.onPause();

	}

	@Override
	public void onDestroy() {
	    super.onDestroy();

	}
}
