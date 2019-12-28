package com.googleMapsAndGPS;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dre on 2/15/2018.
 */

public class GoogleMapNearby {
    @SerializedName("results")
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }
}
class Results {

    public Geometry geometry;
    public String icon;
    public String place_id;
    public String name;
    public Opening_Hours opening_hours;
    public String vicinity;
    public ArrayList<String> types;

}

class Opening_Hours
{
    boolean open_now;
}



class Geometry {
    public Location location;
}
