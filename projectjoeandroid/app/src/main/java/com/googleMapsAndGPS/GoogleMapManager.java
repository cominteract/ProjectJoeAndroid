package com.googleMapsAndGPS;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by andreinsigne on 03/11/2017.
 */

public class GoogleMapManager {

    public GoogleMapAccessor googleMapAccessor;
    public GoogleMapManager(GoogleMapAccessor googleMapAccessor1
            , GoogleMapsInterface googleMapsInterface , Context context)
    {
        googleMapAccessor = googleMapAccessor1;
        googleMapAccessor.initInterfaces(googleMapsInterface);
        googleMapAccessor.setGeocoder(context);
    }

    public void zoomIn()
    {
        CameraUpdateFactory.zoomTo(5);
    }

    public void zoomOut()
    {
        CameraUpdateFactory.zoomTo(18);
    }

    public void goToLatLng(LatLng locationLatLng)
    {
        googleMapAccessor.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLng(locationLatLng));
    }

    public void goToLocation(GoogleMapLocation googleMapLocation)
    {
        googleMapAccessor.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLng(googleMapLocation.getLocationLatLng()));
    }

    private void updateCameraProperties()
    {
        if(googleMapAccessor.isZoomed())
            CameraUpdateFactory.zoomTo(18);
        else
            CameraUpdateFactory.zoomTo(5);
        googleMapAccessor.getGoogleMap().getUiSettings().setZoomControlsEnabled(true);
        googleMapAccessor.getGoogleMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMapAccessor.animateMap();
    }

    private void updateMarkers()
    {
        if(googleMapAccessor.isMultipleLocation()) {
            Log.d(" Multiple ", " " + googleMapAccessor.getGoogleMapLocations().size());
            for(int x = 0; x < googleMapAccessor.getGoogleMapLocations().size(); x ++)
            {
//                Log.d(x +" LatLng ", " Lat : "
//                        + googleMapAccessor.getGoogleMapLocations().get(x).getLocationLatitude()  + " Lng : "
//                        + googleMapAccessor.getGoogleMapLocations().get(x).getLocationLongitude() + " Name : "
//                        + googleMapAccessor.getGoogleMapLocations().get(x).getLocationName());
                Marker marker = googleMapAccessor.getGoogleMap().addMarker(new MarkerOptions()
                        .position(googleMapAccessor.getGoogleMapLocations().get(x).getLocationLatLng())
                        .title(googleMapAccessor.getGoogleMapLocations().get(x).getLocationName()));
            }
        }
        else
        {
            Log.d(" Single ", " " + googleMapAccessor.getCurrentGoogleMapLocation().getLocationName());
            Marker marker = googleMapAccessor.getGoogleMap().addMarker(new MarkerOptions()
                    .position(googleMapAccessor.getCurrentGoogleMapLocation().getLocationLatLng()).title(googleMapAccessor.getLocationName()));
        }
    }



    public void mapLocations()
    {
        updateCameraProperties();
        updateMarkers();
    }
}
