package com.googleMapsAndGPS;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;


import com.andre.projjoe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by andreinsigne on 03/11/2017.
 */

public class GoogleMapAccessor {
    private GoogleMap googleMap;
    private GoogleMapLocation currentGoogleMapLocation;

    private boolean isZoomed = false;
    Geocoder geocoder;
    GoogleMapLocationDataSample googleMapLocationDataSample;
    public GoogleMapAccessor(GoogleMap googleMap1, boolean isZoomed1, GoogleMapLocation googleMapLocation1)
    {
        googleMap = googleMap1;
        isZoomed = isZoomed1;
        currentGoogleMapLocation = googleMapLocation1;
    }

    public void setGeocoder(Context context)
    {
        geocoder = new Geocoder(context, Locale.getDefault());
    }



    public GoogleMapAccessor(GoogleMap googleMap1, boolean isZoomed1, GoogleMapLocationDataSample googleMapLocationDataSample)
    {
        googleMap = googleMap1;
        isZoomed = isZoomed1;
        this.googleMapLocationDataSample = googleMapLocationDataSample;

        if(isMultipleLocation()) {
            currentGoogleMapLocation = this.googleMapLocationDataSample.googleMapLocations.get(0);
        }
        else
        {
            if(this.googleMapLocationDataSample.googleMapLocations.size()>0)
            currentGoogleMapLocation = this.googleMapLocationDataSample.googleMapLocations.get(0);
        }
    }

    public void setGoogleMapLocations(List<GoogleMapLocation> googleMapLocationList) {
        this.googleMapLocationDataSample.googleMapLocations = googleMapLocationList;
    }

    public List<GoogleMapLocation> getGoogleMapLocations() {
        return this.googleMapLocationDataSample.googleMapLocations;
    }

    public GoogleMapLocation getCurrentGoogleMapLocation() {
        currentGoogleMapLocation = this.googleMapLocationDataSample.googleMapLocations.get(0);
        return currentGoogleMapLocation;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public String getLocationName()
    {
        return currentGoogleMapLocation.getLocationName();
    }

    public LatLng getLocationLatLng()
    {
        return getCurrentGoogleMapLocation().getLocationLatLng();
    }

    public boolean isMultipleLocation()
    {
        return this.googleMapLocationDataSample.googleMapLocations!=null && this.googleMapLocationDataSample.googleMapLocations.size()>1;
    }

    private GoogleMapLocation getGoogleMapLocationFromMarker(Marker marker)
    {
        for(int n = 0;n<this.googleMapLocationDataSample.googleMapLocations.size();n++)
        {
            if(marker.getTitle().equals(this.googleMapLocationDataSample.googleMapLocations.get(n).getLocationName()))
            {
                currentGoogleMapLocation = this.googleMapLocationDataSample.googleMapLocations.get(n);
            }
        }
        return currentGoogleMapLocation;
    }

    public void initInterfaces(final GoogleMapsInterface googleMapsInterface1)
    {
        getGoogleMap().setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                    googleMapsInterface1.onCameraMove(currentGoogleMapLocation);
            }
        });
        getGoogleMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                    googleMapsInterface1.onInfoMarkerClick(getGoogleMapLocationFromMarker(marker));
            }
        });
        getGoogleMap().setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                googleMapsInterface1.onInfoMarkerClose(getGoogleMapLocationFromMarker(marker));
            }
        });
        getGoogleMap().setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if(isMultipleLocation() && addresses.size()>0) {
                        currentGoogleMapLocation = new GoogleMapLocation()
                                .locationDesc(addresses.get(0).getAddressLine(0))
                                .locationIcon(R.drawable.allmerchants_ea_icon)
                                .locationLatitude(latLng.latitude).locationLongitude(latLng.longitude)
                                .locationName(addresses.get(0).getFeatureName());
                        GoogleMapAccessor.this.googleMapLocationDataSample.googleMapLocations.add(currentGoogleMapLocation);
                        googleMapsInterface1.onMapLongClick(currentGoogleMapLocation);
                        animateMap();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        getGoogleMap().setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                googleMapsInterface1.onInfoMarkerLongClick(getGoogleMapLocationFromMarker(marker));
            }
        });
        getGoogleMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                googleMapsInterface1.onMarkerClick(getGoogleMapLocationFromMarker(marker));

                //Log.d( " Marker ", marker.getPosition().latitude + " LATLNG " + marker.getPosition().longitude );
                return false;
            }
        });
    }

    public void animateMap()
    {
        if(!isZoomed()) {
            getGoogleMap().animateCamera(CameraUpdateFactory.newLatLngZoom(currentGoogleMapLocation.getLocationLatLng(), 11.0f));
            getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(currentGoogleMapLocation.getLocationLatLng(), 11.0f));

        }
        else
        {
            getGoogleMap().animateCamera(CameraUpdateFactory.newLatLngZoom(currentGoogleMapLocation.getLocationLatLng(), 19.0f));
            getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(currentGoogleMapLocation.getLocationLatLng(), 19.0f));
        }
    }



    public boolean isZoomed() {
        return isZoomed;
    }
}
