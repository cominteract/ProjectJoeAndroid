package com.andre.projjoe.activities;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.andre.projjoe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements OnMapReadyCallback{
	
	

  LatLng locll;
  LatLng KIEL = new LatLng(53.551, 9.993);
  String branchname = null;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    


    


    /*Marker kiel = map.addMarker(new MarkerOptions()
        .position(KIEL)
        .title("Kiel")
        .snippet("Kiel is cool")
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.ic_launcher)));*/


  }


	@Override
	public void onMapReady(GoogleMap googleMap) {
		if(getIntent().getExtras()!=null)
		{
			if(getIntent().getExtras().getString("lat")!=null)
			{
				locll = new LatLng(Double.valueOf(getIntent().getExtras().getString("lat")),Double.valueOf(getIntent().getExtras().getString("long")));
				branchname = getIntent().getExtras().getString("branch");
				Marker location = googleMap.addMarker(new MarkerOptions().position(locll)
						.title(branchname));
			}
			else if(getIntent().getExtras().getStringArrayList("latlist")!=null)
			{

				for(int cntlat = 0;cntlat<getIntent().getExtras().getStringArrayList("latlist").size();cntlat++)
				{
					locll = new LatLng(Double.valueOf(getIntent().getExtras().getStringArrayList("latlist").get(cntlat)),Double.valueOf(getIntent().getExtras().getStringArrayList("longlist").get(cntlat)));
					branchname = getIntent().getExtras().getStringArrayList("branchlist").get(cntlat);
					Marker location = googleMap.addMarker(new MarkerOptions().position(locll)
							.title(branchname));
				}
			}
			// Move the camera instantly to hamburg with a zoom of 15.
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locll, 15));

			// Zoom in, animating the camera.
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		}
	}
}