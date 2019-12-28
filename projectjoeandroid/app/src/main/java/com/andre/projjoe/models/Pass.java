package com.andre.projjoe.models;

/**
 * Created by Dre on 2/21/2018.
 */

public class Pass {


    public String passDescription;
    public String passClaims;
    public String passType;
    public String passDuration;

    public String passLink;
    public String passImage;

    public int passClaimCount;
    public double passPrice;
    public Merchant passMerchant;
    public Points passPoints;
    public boolean isNotified;
    public boolean isLocked;
    public LockType lockType;
    public enum LockType
    {
        LockShare,
        LockRedeem,
        LockSurvey
    }
}
