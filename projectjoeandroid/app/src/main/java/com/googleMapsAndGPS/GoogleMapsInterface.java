package com.googleMapsAndGPS;

import android.location.*;

import java.util.List;

/**
 * Created by andreinsigne on 03/11/2017.
 */

public interface GoogleMapsInterface {
    public void onCameraMove(GoogleMapLocation googleMapLocation);
    public void onMarkerClick(GoogleMapLocation googleMapLocation);
    public void onMapLongClick(GoogleMapLocation googleMapLocation);
    public void onInfoMarkerClick(GoogleMapLocation googleMapLocation);
    public void onInfoMarkerLongClick(GoogleMapLocation googleMapLocation);
    public void onInfoMarkerClose(GoogleMapLocation googleMapLocation);
    public void onMapLocationsLoaded(List<GoogleMapLocation> googleMapLocations);


    public void onLocationLoaded(android.location.Location location);

    public void onNearby(GoogleMapNearby googleMapNearby);
    public void onChangeType(String type);
}
