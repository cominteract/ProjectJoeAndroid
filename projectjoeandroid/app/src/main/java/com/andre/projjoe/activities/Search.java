package com.andre.projjoe.activities;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.andre.projjoe.R;
import com.andre.projjoe.adapters.SearchAdapter;

public class Search extends AppCompatActivity {
	
	public ArrayList<HashMap<String, String>> feedList = new ArrayList<HashMap<String, String>>();
	
	int[] icons = new int[] { R.drawable.twinhearts, R.drawable.umbrella, R.drawable.heartpouch,
	        R.drawable.heartarrow, R.drawable.phoneheart, R.drawable.handheart };
	
    String[] descs = new String[] { "20% OFF on all Alex Merchandise Deal Pass", "30% OFF on all Alex Merchandise Deal Pass", "Free wristlet on Alexandria Jewelry Free Pass",
	        "Alex Jone's Infowars Shop Lifestyle", "Alex and Ani Rye Jewels Fashion", "Alex Rio Connection" };
    
    EditText searchEdt;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		searchEdt= (EditText) findViewById(R.id.searchEdt);

		
		final ListView searchListView = (ListView) findViewById(R.id.searchListView);
	    for(int cnt = 0;cnt<descs.length;cnt++)
	    {
	    	HashMap<String, String> map = new HashMap<String, String>();
	    	map.put("descs",descs[cnt]);
	    	map.put("icons",String.valueOf(icons[cnt]));
	    	feedList.add(map);
	    }
	    SearchAdapter adapter = new SearchAdapter(Search.this,feedList);
		searchListView.setAdapter(adapter);
	    handleIntent(getIntent());
	    
	}	
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Handling intent data
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			/**
			 * Use this query to display search results like
			 * 1. Getting the data from SQLite and showing in listview
			 * 2. Making webrequest and displaying the data
			 * For now we just display the query only
			 */
			searchEdt.setText("Search Query: " + query);

		}

	}

}
