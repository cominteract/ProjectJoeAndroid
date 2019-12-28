package com.googleMapsAndGPS;



import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dre on 2/15/2018.
 */

public interface GoogleMapService {

    @GET("/maps/api/place/nearbysearch/json")
    Call<GoogleMapNearby> getNearbyByName(@Query("location") String origin
            , @Query("radius") String radius
            , @Query("types") String types
            , @Query("name") String name
            , @Query("key") String apiKey);
    @GET("/maps/api/place/nearbysearch/json")
    Call<GoogleMapNearby> getNearbyByKeyword(@Query("location") String origin
            , @Query("radius") String radius
            , @Query("types") String types
            , @Query("keyword") String keyword
            , @Query("key") String apiKey);
    @GET("/maps/api/place/nearbysearch/json")
    Call<GoogleMapNearby> getNearbyByType(@Query("location") String origin
            , @Query("radius") String radius
            , @Query("types") String types
            , @Query("key") String apiKey);

    @GET("/maps/api/directions/json")
    Call<GoogleMapDirectionResults> getDirection(@Query("origin") String location, @Query("destination") String destination);

}
