package com.googleMapsAndGPS;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by andreinsigne on 30/10/2017.
 */


public class GoogleMapLocation {

    private LatLng locationLatLng;
    private String locationName;
    private String locationDesc;
    private String locationAddress;
    private int locationIcon;
    private double locationLongitude;
    private double locationLatitude;
    private Marker marker;
    //private LatLng location;
    public final static String kGoogleLocationsName = "google_locations_name";
    public final static String kGoogleLocationsDesc = "google_locations_desc";
    public final static String kGoogleLocationsIcon = "google_locations_icon";
    public final static String kGoogleLocationsAddress = "google_locations_address";
    public final static String kGoogleLocationsLatitude = "google_locations_latitude";
    public final static String kGoogleLocationsLongitude = "google_locations_longitude";

    public GoogleMapLocation(HashMap<String,String> map)
    {
        locationName = map.get(kGoogleLocationsName);
        locationDesc = map.get(kGoogleLocationsDesc);
        locationIcon = Integer.valueOf(map.get(kGoogleLocationsIcon));
        locationAddress = map.get(kGoogleLocationsAddress);
        locationLatitude = Double.valueOf(map.get(kGoogleLocationsLatitude));
        locationLongitude = Double.valueOf(map.get(kGoogleLocationsLongitude));
        locationLatLng = new LatLng(locationLatitude,locationLongitude);
    }


    public GoogleMapLocation()
    {
        locationLatLng = new LatLng(locationLatitude,locationLongitude);
    }



    public GoogleMapLocation locationName(String locationNameParam)
    {
        locationName = locationNameParam;
        return this;
    }

    public GoogleMapLocation locationDesc(String locationDescParam)
    {
        locationDesc = locationDescParam;
        return this;
    }
    public GoogleMapLocation locationLatitude(Double locationLatitudeParam)
    {
        locationLatitude = locationLatitudeParam;

        return this;
    }
    public GoogleMapLocation locationLongitude(Double locationLongitudeParam)
    {
        locationLongitude = locationLongitudeParam;
        return this;
    }
    public GoogleMapLocation locationIcon(int locationIconParam)
    {
        locationIcon = locationIconParam;
        return this;
    }

    public GoogleMapLocation locationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
        return this;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public LatLng getLocationLatLng() {

        if(locationLatLng.longitude == 0.0 && locationLatLng.latitude == 0.0) {
            locationLatLng = new LatLng(locationLatitude, locationLongitude);
        }
        return locationLatLng;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public int getLocationIcon() {
        return locationIcon;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public String getLocationName() {
        return locationName;
    }
    //public String
}
