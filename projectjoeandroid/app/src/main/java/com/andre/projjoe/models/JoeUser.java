package com.andre.projjoe.models;

import com.googleMapsAndGPS.GoogleMapLocation;

import java.util.List;

/**
 * Created by Dre on 2/24/2018.
 */

public class JoeUser {
    public String userName;
    public String fullName;
    public String email;
    public String image;
    public String birthday;
    public String phone;
    public String facebook;
    public GoogleMapLocation googleMapLocation;
    public int currentPoints;
    public int givenPoints;
    public int earnedPoints;
    public int usedPoints;
    public int receivedPoints;
    public List<JoeUser> friendList;
    public List<Pass> passList;
    public List<Post> postList;
    public List<Points> pointsList;
    public List<Transaction> transactionList;
    public int merchantPoints;
}
