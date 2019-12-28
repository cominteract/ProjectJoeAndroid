package com.andre.projjoe.models;

/**
 * Created by Dre on 3/3/2018.
 */

public class Transaction {



    public Transaction(JoeUser transactionJoeUser, String transactionDate, int transactionPoints,
                       TransactionType transactionType)
    {

        this.transactionImage = transactionJoeUser.image;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        if(transactionType.equals(TransactionType.TransactionReceivingPoints)) {
            this.transactionImage = transactionJoeUser.image;
            this.transactionPointsReceived = transactionPoints;
            this.transactionJoeUserGiver = transactionJoeUser;
            this.transactionTitle = "Points Received Successfully";
            this.transactionDetails = "Claimed " + transactionPoints + " points from " + transactionJoeUser.fullName + " at " + transactionDate;
        }
        else if(transactionType.equals(TransactionType.TransactionGivingPoints))
        {

            this.transactionPointsGiven = transactionPoints;
            this.transactionJoeUserReceiver = transactionJoeUser;
            this.transactionTitle = "Points Given Successfully";
            this.transactionDetails = "Given " + transactionPoints + " points to " + transactionJoeUser.fullName + " at " + transactionDate;
        }
        else if(transactionType.equals(TransactionType.TransactionUsingPoints))
        {

            this.transactionPointsGiven = transactionPoints;
            this.transactionJoeUserGiver = transactionJoeUser;
            this.transactionTitle = "Points Used Successfully";
            this.transactionDetails = "Used " + transactionPoints + " points by "  + transactionJoeUser.fullName + " at " + transactionDate;
        }
        else
        {

            this.transactionPointsGiven = transactionPoints;
            this.transactionJoeUserGiver = transactionJoeUser;
            this.transactionTitle = "Points Earned Successfully";
            this.transactionDetails = " Earned " + transactionPoints + " points by "  + transactionJoeUser.fullName + " at " + transactionDate;
        }

    }

    public void setTransactionOtherUser(JoeUser joeUser)
    {
        if(transactionJoeUserGiver!=null)
            transactionJoeUserReceiver = joeUser;
        else
            transactionJoeUserGiver = joeUser;
        if( this.transactionType.equals(TransactionType.TransactionGivingPass) || this.transactionType.equals(TransactionType.TransactionGivingPoints))
        {

        }
        else
            this.transactionImage = joeUser.image;
    }

    public Transaction(JoeUser transactionJoeUser, String transactionDate, Pass transactionPass,
                       TransactionType transactionType)
    {

        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionImage = transactionJoeUser.image;
        if(transactionType.equals(TransactionType.TransactionReceivingPass)) {

            this.transactionPass = transactionPass;
            this.transactionJoeUserGiver = transactionJoeUser;
            this.transactionTitle = "Pass Received Successfully";
            this.transactionDetails = "Claimed " + transactionPass.passDescription + " from " + transactionJoeUser.fullName + " at " + transactionDate;
        }
        else if(transactionType.equals(TransactionType.TransactionGivingPass))
        {

            this.transactionPass = transactionPass;
            this.transactionJoeUserReceiver = transactionJoeUser;
            this.transactionTitle = "Pass Given Successfully";
            this.transactionDetails = "Given " + transactionPass.passDescription + " to " + transactionJoeUser.fullName + " at " + transactionDate;
        }
        else if(transactionType.equals(TransactionType.TransactionSharingPass))
        {

            this.transactionPass = transactionPass;
            this.transactionJoeUserReceiver = transactionJoeUser;
            this.transactionTitle = "Pass Shared Successfully";
            this.transactionDetails = "Shared " + transactionPass.passDescription + " by " + transactionJoeUser.fullName + " at " + transactionDate;
        }
    }

    public Transaction(JoeUser transactionJoeUser, String transactionDate, Post transactionPost,TransactionType transactionType)
    {
        this.transactionImage = transactionJoeUser.image;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionJoeUserGiver = transactionJoeUser;
        this.transactionTitle = "Posted Successfully";
        this.transactionDetails = "Posted at " + transactionPost.postBranch.branchName + " by " + transactionJoeUser.fullName + " at " + transactionDate;
    }


    public TransactionType transactionType;
    public String transactionTitle;
    public String transactionDetails;
    public String transactionDate;
    public String transactionImage;
    public int transactionPointsGiven;
    public int transactionPointsReceived;
    public JoeUser transactionJoeUserGiver;
    public JoeUser transactionJoeUserReceiver;
    public Post transactionPost;
    public Pass transactionPass;
    public enum TransactionType {
        TransactionGivingPoints,
        TransactionReceivingPoints,
        TransactionReceivingPass,
        TransactionGivingPass,
        TransactionPost,
        TransactionUsingPoints,
        TransactionSharingPass,
        TransactionEarningPoints
    }
}
