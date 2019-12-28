package com.googleMapsAndGPS;


/**
 * Created by Dre on 2/15/2018.
 */



import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;


import com.andre.projjoe.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
     * Created by systemadministrator on 09/10/2016.
     */
    public class GoogleMapApiManager {

        Context context;
        public GoogleMapApiManager(Context context)
        {
            this.context = context;
        }

        public void startNearbyType(final GoogleMapsInterface googleMapsInterface, GoogleMapLocation origin, String type, String radius)
        {
            String base_url = "https://maps.googleapis.com/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GoogleMapService service = retrofit.create(GoogleMapService.class);
            String ori =  origin.getLocationLatitude() + "," +  origin.getLocationLongitude();
            Call<GoogleMapNearby> googleMapNearbyCall = service.getNearbyByType(ori,radius, type,
                    context.getString(R.string.api_key));
            googleMapNearbyCall.enqueue(new Callback<GoogleMapNearby>() {
                @Override
                public void onResponse(Call<GoogleMapNearby> call, Response<GoogleMapNearby> response) {
                    if(response.body()!=null) {
                        Log.d(" It work ", " It work " + response.body().getResults().size() + " " + response.raw());
                        googleMapsInterface.onNearby(response.body());
                    }
                    else
                        Log.d(" It did not work "," It did not work " + response.message() + " " + response.raw() );
                }

                @Override
                public void onFailure(Call<GoogleMapNearby> call, Throwable t) {

                }
            });
        }

        public void startNearbySearch(final GoogleMapsInterface googleMapsInterface, GoogleMapLocation origin
                , String type, String searchName , String radius)
        {
            String base_url = "https://maps.googleapis.com/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GoogleMapService service = retrofit.create(GoogleMapService.class);
            String ori =  origin.getLocationLatitude() + "," +  origin.getLocationLongitude();
            Call<GoogleMapNearby> googleMapNearbyCall = service.getNearbyByName(ori,radius, type, searchName,
                    context.getString(R.string.api_key));
            googleMapNearbyCall.enqueue(new Callback<GoogleMapNearby>() {
                @Override
                public void onResponse(Call<GoogleMapNearby> call, Response<GoogleMapNearby> response) {
                    if(response.body()!=null) {
                        Log.d(" It work ", " It work " + response.body().getResults().size() + " " + response.raw());
                        googleMapsInterface.onNearby(response.body());
                    }
                    else
                        Log.d(" It did not work "," It did not work " + response.message() + " " + response.raw() );
                }

                @Override
                public void onFailure(Call<GoogleMapNearby> call, Throwable t) {

                }
            });
        }

    public void startNearbyKeyword(final GoogleMapsInterface googleMapsInterface, GoogleMapLocation origin
            , String type, String keyword , String radius)
    {
        String base_url = "https://maps.googleapis.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleMapService service = retrofit.create(GoogleMapService.class);
        String ori =  origin.getLocationLatitude() + "," +  origin.getLocationLongitude();
        Call<GoogleMapNearby> googleMapNearbyCall = service.getNearbyByKeyword(ori,radius, type, keyword,
                context.getString(R.string.api_key));
        googleMapNearbyCall.enqueue(new Callback<GoogleMapNearby>() {
            @Override
            public void onResponse(Call<GoogleMapNearby> call, Response<GoogleMapNearby> response) {
                if(response.body()!=null) {
                    Log.d(" It work ", " It work " + response.body().getResults().size() + " " + response.raw());
                    googleMapsInterface.onNearby(response.body());
                }
                else
                    Log.d(" It did not work "," It did not work " + response.message() + " " + response.raw() );
            }

            @Override
            public void onFailure(Call<GoogleMapNearby> call, Throwable t) {

            }
        });
    }

        public void startPolyline(final GoogleMap googleMap, final GoogleMapLocation origin, GoogleMapLocation destination)
        {
            String base_url = "http://maps.googleapis.com/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //"14.5839333,121.0500025"
            //"14.3478279,120.9471624"
            GoogleMapService service = retrofit.create(GoogleMapService.class);
            String ori =  origin.getLocationLatitude() + "," +  origin.getLocationLongitude();
            String des =  destination.getLocationLatitude() + "," +  destination.getLocationLongitude();

            Call<GoogleMapDirectionResults> googleMapDirectionResults = service.getDirection(ori,des);
            googleMapDirectionResults.enqueue(new Callback<GoogleMapDirectionResults>() {
                @Override
                public void onResponse(Call<GoogleMapDirectionResults> call, retrofit2.Response<GoogleMapDirectionResults> response) {

                    ArrayList<LatLng> routelist = new ArrayList<LatLng>();
                    if(response.body()!=null && response.body().getRoutes().size()>0){
                        ArrayList<LatLng> decodelist;
                        Route routeA = response.body().getRoutes().get(0);

                        if(routeA.getLegs().size()>0){
                            List<Steps> steps= routeA.getLegs().get(0).getSteps();

                            Steps step;
                            Location location;
                            String polyline;
                            for(int i=0 ; i<steps.size();i++){
                                step = steps.get(i);
                                location =step.getStart_location();
                                routelist.add(new LatLng(location.getLat(), location.getLng()));

                                polyline = step.getPolyline().getPoints();
                                decodelist = GoogleMapPolylineDecode.decodePoly(polyline);
                                routelist.addAll(decodelist);
                                location =step.getEnd_location();
                                routelist.add(new LatLng(location.getLat() ,location.getLng()));

                            }
                        }
                    }
                    else
                    {
                        Log.d( " Response ", response.message() + " " + response.raw());
                    }

                    if(routelist.size()>0){
                        PolylineOptions rectLine = new PolylineOptions().width(10).color(
                                Color.RED);

                        for (int i = 0; i < routelist.size(); i++) {
                            rectLine.add(routelist.get(i));
                        }
                        // Adding route on the map
                        googleMap.addPolyline(rectLine);

//                        markerOptions.position(toPosition);
//                        markerOptions.draggable(true);
                        //googleMap.addMarker(markerOptions);
                    }
                }

                @Override
                public void onFailure(Call<GoogleMapDirectionResults> call, Throwable t) {

                }
            });

        }
    }


