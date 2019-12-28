package com.andre.projjoe.models;

import com.googleMapsAndGPS.GoogleMapLocation;

import java.util.List;

/**
 * Created by Dre on 2/21/2018.
 */

public class Branch {

    public String branchDetails;
    public String branchName;
    public String branchAddress;
    public String branchContact;
    public Merchant branchMerchant;
    public GoogleMapLocation googleMapLocation;
    public List<Post> branchTaggedPost;
}
