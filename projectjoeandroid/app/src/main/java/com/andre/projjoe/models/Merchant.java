package com.andre.projjoe.models;

import java.util.List;

/**
 * Created by Dre on 2/24/2018.
 */

public class Merchant {

    public String merchantName;
    public String merchantCaption;
    public String merchantDetails;
    public String merchantImage;
    public String merchantCategory;
    public String merchantGeoType;
    public String merchantGeoName;
    public String merchantContact;
    public int merchantImageResource;
    public List<JoeUser> merchantJoeUserList;
    public List<Pass> merchantJoePassList;
    public List<Branch> merchantBranchesList;
    public List<Post> merchantPostList;

}
