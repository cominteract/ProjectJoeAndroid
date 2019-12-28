package com.googleMapsAndGPS;

import android.content.Context;
import android.location.*;
import android.util.Log;


import com.andre.projjoe.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by andreinsigne on 03/11/2017.
 */

public class GoogleMapLocationDataSample {


    private int maxLocations = 15;
    private LatLng currentZoomed;
    private GoogleMapsInterface googleMapsInterface;
    List<GoogleMapLocation> googleMapLocations = new ArrayList<>();

    public void setMaxLocations(int maxLocations) {
        this.maxLocations = maxLocations;
    }

    public GoogleMapLocationDataSample(GoogleMapsInterface googleMapsInterface)
    {
        this.googleMapsInterface = googleMapsInterface;
    }


    private GoogleMapLocation getLoc(android.location.Location location)
    {
        GoogleMapLocation googleMapLocation = new GoogleMapLocation().locationDesc(location!=null? " You are here " :"Dummy Data").
                locationIcon(R.drawable.messenger_bubble_large_blue).locationLatitude(location!=null? location.getLatitude() :  14.583771)
                .locationLongitude(location!=null? location.getLongitude() : 121.059675)
                .locationName(" You are here ").locationAddress(" You are here");
        return googleMapLocation;
    }

    public void setCurrentLocation(android.location.Location location)
    {
        if(googleMapLocations!=null && googleMapLocations.size() > 0)
            googleMapLocations.clear();
        else
            googleMapLocations = new ArrayList<>();
        googleMapLocations.add(getLoc(location));
        if (currentZoomed == null || currentZoomed.equals(null)) {
            currentZoomed = new LatLng((location!=null? location.getLatitude() :  14.583771)
                    , (location!=null? location.getLongitude() : 121.059675));
        }

    }

    public void setOwnLocation(android.location.Location location)
    {
        setCurrentLocation(location);
        googleMapsInterface.onMapLocationsLoaded(googleMapLocations);
    }

    public void setPolyLocationsSample()
    {
        //"14.5839333,121.0500025"
        //"14.3478279,120.9471624"
        googleMapLocations.add(new GoogleMapLocation().locationAddress(" Ortigas ")
        .locationDesc(" JMT ").locationIcon(R.drawable.search_icon).locationLatitude(14.5839333)
                .locationLongitude(121.0500025).locationName(" Wylog "));
        googleMapLocations.add(new GoogleMapLocation().locationAddress(" Cavite ")
                .locationDesc(" Cardinal Village ").locationIcon(R.drawable.messenger_button_blue_bg_round).locationLatitude(14.3478279)
                .locationLongitude(120.9471624).locationName(" Andre's House "));
        googleMapsInterface.onMapLocationsLoaded(googleMapLocations);
    }


    public LatLng getCurrentZoomed()
    {
        return currentZoomed;
    }


    public List<GoogleMapLocation> getGoogleMapLocations() {
        Log.d(" Map Locations ", googleMapLocations.get(0).getLocationLatitude() + " Locations ");
        return googleMapLocations;
    }

    public void setGoogleMapLocation(GoogleMapLocation googleMapLocation)
    {
        this.googleMapLocations.clear();
        this.googleMapLocations.add(googleMapLocation);
    }
    public void setGoogleMapLocationWithOwn(GoogleMapLocation googleMapLocation)
    {
        this.googleMapLocations.clear();
        this.googleMapLocations.add(getLoc(null));
        this.googleMapLocations.add(googleMapLocation);
    }

    public void setGoogleMapLocations(List<GoogleMapLocation> googleMapLocations)
    {
        this.googleMapLocations.clear();
        this.googleMapLocations = googleMapLocations;
    }

    public void setRandomLocationMaps(Context context)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        for(int x = 0; x < maxLocations ; x++) {
            try {
                double lat = 65;
                double lang = 127;

                addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                String city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();

                if(addresses.size()>0) {
                    GoogleMapLocation googleMapLocation = new GoogleMapLocation()
                            .locationAddress(addresses.get(0).getLocality())
                            .locationDesc(addresses.get(0).getAddressLine(0))
                            .locationIcon(R.drawable.messenger_bubble_small_blue)
                            .locationLatitude(lat).locationLongitude(lang)
                            .locationName(addresses.get(0).getFeatureName());
                    googleMapLocations.add(googleMapLocation);

                    if (currentZoomed == null || currentZoomed.equals(null)) {
                        currentZoomed = new LatLng(lat, lang);

                    }
                }

            } catch (IOException e) {

            }
        }
        googleMapsInterface.onMapLocationsLoaded(googleMapLocations);
    }

    public void addLocationsFromNearbyCleared(GoogleMapNearby googleMapNearby)
    {
        setCurrentLocation(null);
        if(googleMapNearby!=null && googleMapNearby.getResults()!=null && googleMapNearby.getResults().size() > 0)
        {
            for(Results results : googleMapNearby.getResults()) {
                GoogleMapLocation googleMapLocation = new GoogleMapLocation().locationAddress(results.vicinity)
                        .locationName(results.name).locationLatitude(results.geometry.location.getLat())
                        .locationLongitude(results.geometry.location.getLng()).locationDesc(results.name);
                if(googleMapLocations!=null)
                    googleMapLocations.add(googleMapLocation);
            }
        }
        googleMapsInterface.onMapLocationsLoaded(googleMapLocations);
    }

    public void addLocationsFromNearby(GoogleMapNearby googleMapNearby)
    {
        if(googleMapNearby!=null && googleMapNearby.getResults()!=null && googleMapNearby.getResults().size() > 0)
        {
            for(Results results : googleMapNearby.getResults()) {
                GoogleMapLocation googleMapLocation = new GoogleMapLocation().locationAddress(results.vicinity)
                        .locationName(results.name).locationLatitude(results.geometry.location.getLat())
                        .locationLongitude(results.geometry.location.getLng()).locationDesc(results.name);
                if(googleMapLocations!=null)
                    googleMapLocations.add(googleMapLocation);
            }
        }
        googleMapsInterface.onMapLocationsLoaded(googleMapLocations);
    }


    public void setRandomLocationMapsClose(Context context, android.location.Location location)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        setCurrentLocation(location);
        long startTime = System.nanoTime();
// ... the code being measured ...

        for(int x = 0; x < maxLocations ; x++) {
            try {

                double lat = 14.347827;
                double lang = 120.9471624;

                addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                String city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();

                if(addresses.size()>0) {



                    if(!addresses.get(0).getFeatureName().equals(addresses.get(0).getCountryName())) {
                        GoogleMapLocation googleMapLocation = new GoogleMapLocation()
                                .locationAddress(addresses.get(0).getLocality())
                                .locationDesc(addresses.get(0).getAddressLine(0))
                                .locationIcon(R.drawable.messenger_bubble_small_white)
                                .locationLatitude(lat).locationLongitude(lang)
                                .locationName(addresses.get(0).getFeatureName());
                        googleMapLocations.add(googleMapLocation);
                    }
                    else
                        x -= 1;
                }

            } catch (IOException e) {

            }
        }
        long estimatedTime = System.nanoTime() - startTime;
        Log.d(" Process took ", " Process took " + estimatedTime);
        googleMapsInterface.onMapLocationsLoaded(googleMapLocations);
    }
}
