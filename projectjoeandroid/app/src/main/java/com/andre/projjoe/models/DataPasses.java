package com.andre.projjoe.models;

import android.util.Log;

import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.googleMapsAndGPS.GoogleMapLocation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Dre on 2/22/2018.
 */

public class DataPasses {
    public final static String kDeal = "DEAL";
    public final static String kCoupon = "COUPON";
    public final static String kFree = "FREE";
    public final static String kChallenge = "CHALLENGE";
    public final static String kNews = "NEWS";
    public final static String kAd = "AD";
    private int maxPass = 30;
    public DataPasses()
    {
        getMerchantList();
        getPassDetails();
        getAvailablePasses();
        getCurrentPassList();
    }
    private FragmentInteractionInterface fragmentInteractionInterface;
    private JoeUser joeUser;
    private List<JoeUser> joeUserList = new ArrayList<>();
    private List<Merchant> merchantList = new ArrayList<>();
    private List<Branch> branchList = new ArrayList<>();
    private List<Pass> availablePasses = new ArrayList<>();
    private List<Pass> nearbyAvailablePasses = new ArrayList<>();
    private List<Pass> trendingPasses = new ArrayList<>();
    private List<Post> availablePostList = new ArrayList<>();
    private List<Pass> currentPassList = new ArrayList<>();
    private List<Post> currentPostList = new ArrayList<>();
    private List<Transaction> allTransactionList = new ArrayList<>();
    private HashMap<String,String[]> passDetails = new HashMap<>();
    private List<GoogleMapLocation> availableGoogleMapLocationList;
    private List<GoogleMapLocation> nearestGoogleMapLocationList;
    private HashMap<String,String[]> passAds = new HashMap<>();
    private HashMap<String,String[]> passNews = new HashMap<>();
    public DataPasses(FragmentInteractionInterface fragmentInteractionInterface) {this.fragmentInteractionInterface = fragmentInteractionInterface;}
    public List<Transaction> getAllTransactionList() {
        return allTransactionList;
    }
    public List<Pass> getTrendingPasses() {
        return trendingPasses;
    }
    public List<GoogleMapLocation> getAvailableGoogleMapLocationList() {return availableGoogleMapLocationList;}
    public List<GoogleMapLocation> getNearestGoogleMapLocationList() {return nearestGoogleMapLocationList;}
    public void setAvailableGoogleMapLocationList(List<GoogleMapLocation> availableGoogleMapLocationList) {this.availableGoogleMapLocationList = availableGoogleMapLocationList;}
    public void setNearestGoogleMapLocationList(List<GoogleMapLocation> nearestGoogleMapLocationList) {this.nearestGoogleMapLocationList = nearestGoogleMapLocationList;}
    public List<JoeUser> getUserListFromMerchant(Merchant merchant)
    {
        List<JoeUser> joeUserList = new ArrayList<>();
        for(JoeUser joeUser: this.joeUserList)
        {
            for(Points points : joeUser.pointsList )
            {
                if(points.pointMerchant.merchantName.toLowerCase().equals(merchant.merchantName.toLowerCase()) && !joeUserList.contains(joeUser)) {
                    joeUser.merchantPoints = points.point;
                    joeUserList.add(joeUser);
                }
            }
        }
        return joeUserList;
    }

    public Transaction.TransactionType getTransactionType(int identifier)
    {
        if(identifier==0)
            return Transaction.TransactionType.TransactionGivingPoints;
        else if(identifier==1)
            return Transaction.TransactionType.TransactionUsingPoints;
        else if(identifier==2)
            return Transaction.TransactionType.TransactionReceivingPoints;
        else if(identifier==3)
            return Transaction.TransactionType.TransactionEarningPoints;
        else if(identifier==4)
            return Transaction.TransactionType.TransactionReceivingPass;
        else if(identifier==5)
            return Transaction.TransactionType.TransactionGivingPass;
        else if(identifier==6)
            return Transaction.TransactionType.TransactionPost;
        else
            return Transaction.TransactionType.TransactionSharingPass;
    }

    public Transaction getTransaction(int identifier,boolean forCurrentUser)
    {
        Transaction.TransactionType transactionType = getTransactionType(identifier);
        Transaction transaction;
        JoeUser joeUser;
        if(forCurrentUser)
            joeUser = currentJoeUser();
        else
            joeUser = joeUserList.get(randInt(0, joeUserList.size() - 1));
        if( transactionType.equals(Transaction.TransactionType.TransactionReceivingPoints)
                && transactionType.equals(Transaction.TransactionType.TransactionReceivingPass)) {
            transaction = new Transaction(joeUserList.get(randInt(0, joeUserList.size() - 1))
                    , randomDate()
                    , randInt(1, 100)
                    , transactionType);
            transaction.setTransactionOtherUser(joeUser);
        }
        else if(transactionType.equals(Transaction.TransactionType.TransactionUsingPoints) &&
                transactionType.equals(Transaction.TransactionType.TransactionGivingPoints) &&
                transactionType.equals(Transaction.TransactionType.TransactionGivingPass)) {
            transaction = new Transaction(joeUser
                    , randomDate()
                    , randInt(1, 100)
                    , transactionType);
            transaction.setTransactionOtherUser(joeUserList.get(randInt(0, joeUserList.size() - 1)));
        }
        else
            transaction = new Transaction(joeUser
                    , randomDate()
                    , availablePostList.get(randInt(0, availablePostList.size()-1))
                    , transactionType);
        return transaction;
    }

    public List<Post> getPostsFromMerchant(Merchant merchant)
    {
        List<Post> posts = new ArrayList<>();
        for(Post post : this.availablePostList)
        {
            if(post.postBranch.branchMerchant!=null && post.postBranch.branchMerchant.merchantName.toLowerCase().equals(merchant.merchantName.toLowerCase()))
                posts.add(post);
        }
        return posts;
    }

    public List<Pass> getPassesFromMerchant(Merchant merchant)
    {
        List<Pass> passes = new ArrayList<>();
        for(Pass pass : this.availablePasses)
        {
            if(pass.passMerchant!=null && pass.passMerchant.merchantName.toLowerCase().equals(merchant.merchantName.toLowerCase()))
                passes.add(pass);
        }
        return passes;
    }

    public List<Branch> getBranchesFromMerchant(Merchant merchant)
    {
        List<Branch> branches = new ArrayList<>();
        for(Branch branch: this.branchList)
        {
           if(branch.branchMerchant!=null && branch.branchMerchant.merchantName.toLowerCase().equals(merchant.merchantName.toLowerCase()))
               branches.add(branch);
        }
        return branches;
    }

    public List<Pass> getNearbyAvailablePasses()
    {
        return nearbyAvailablePasses;
    }
    public List<Branch> getBranchList() {
        return branchList;
    }

    public List<Post> getAvailablePostList()
    {
        if(availablePostList.size()<1 && getBranchList().size() > 0)
        {
            for(Branch branch : branchList)
            {
                Post post = new Post();
                post.postBranch = branch;
                post.postCaption = "Here at " + branch.branchName;
                post.postDate = randomDate();
                post.postDetails = "Taken at" + branch + " with " + post.postPoints + " points by " + currentJoeUser().fullName;
                if(branch.branchMerchant!=null) {availablePostList.add(post);}
            }
            getJoeUserList();
            currentJoeUser().friendList = new ArrayList<>();
            for(int n = 0;n<6;n++) {
                JoeUser joeUser = joeUserList.get(randInt(0,joeUserList.size()-1));
                currentJoeUser().friendList.add(joeUser);
            }
            currentJoeUser().transactionList = new ArrayList<>();
            for(int n = 0;n<500;n++)
            {
                Transaction transaction = getTransaction(randInt(0,7),false);
                allTransactionList.add(transaction);
                if(( transaction.transactionJoeUserGiver!=null &&  transaction.transactionJoeUserGiver.equals(currentJoeUser()) )
                        || (transaction.transactionJoeUserReceiver!=null && transaction.transactionJoeUserReceiver.equals(currentJoeUser())))
                    currentJoeUser().transactionList.add(transaction);
            }
            for(JoeUser joeUser : joeUserList)
            {
                for(int n = 0;n<8;n++)
                {
                    joeUser.friendList.add(joeUserList.get(randInt(0,joeUserList.size() - 1)));
                }
                joeUser.transactionList = getTransactionFromUser(joeUser);
            }
        }
        return availablePostList;
    }

    public void setBranchList(List<GoogleMapLocation> googleMapLocationList)
    {
        if(branchList.size()<1) {
            for (GoogleMapLocation googleMapLocation : googleMapLocationList) {
                Branch branch = new Branch();
                branch.branchName = googleMapLocation.getLocationName();
                branch.branchAddress = googleMapLocation.getLocationAddress();
                branch.branchContact = googleMapLocation.getLocationDesc();
                branch.branchDetails = "Tegi boom boom";
                branch.googleMapLocation = googleMapLocation;
                for (Merchant merchant : getMerchantList()) {
                    if (googleMapLocation.getLocationName().toLowerCase().contains(merchant.merchantGeoName.toLowerCase())) {
                        branch.branchMerchant = merchant;
                        branch.branchTaggedPost = new ArrayList<>();
                        branchList.add(branch);
                        break;
                    }
                }

            }
            getAvailablePostList();
            fragmentInteractionInterface.onTransactionsRetrieved();
        }
    }

    public List<Pass> getCurrentPassList()
    {
        List<Pass> passList = new ArrayList<>();
        if(currentPassList.size()<1)
        {
            for(Pass pass : getAvailablePasses())
            {
                if(currentPassList.size()<4 && (pass.passType.equals(kDeal) || pass.passType.equals(kCoupon) || pass.passType.equals(kFree))) {

                    currentPassList.add(pass);
                    passList.add(pass);
                }
                else if(currentPassList.size() == 4)
                    break;
            }
            for(Pass pass : passList)
            {
                if(getAvailablePasses().contains(pass))
                {
                    getAvailablePasses().remove(pass);
                }
            }
        }
        return currentPassList;
    }

    public List<Post> getCurrentPostList()
    {
        return currentPostList;
    }

    public List<Pass>getAvailablePasses()
    {
        if(availablePasses.size()<1)
        {
            for(int n = 0; n<maxPass; n++)
            {
                Pass pass = new Pass();
                pass.passMerchant = getMerchantList().get(randInt(0,merchantNames.length - 1));
                pass.passPrice = randInt(1,500);
                pass.passType = passType[randInt(0,5)];
                pass.passDuration = randInt(1,7) + " days left";
                pass.passClaimCount = randInt(1,99);
                pass.passClaims = pass.passClaimCount + " claims";
                pass.passPoints = new Points();
                pass.passPoints.pointMerchant = pass.passMerchant;
                pass.passPoints.point = randInt(1,100);
                pass.isLocked = false;
                if(pass.passType.equals(passType[5]))
                {
                    pass.passDescription = getPassAds().get(pass.passMerchant.merchantName)[0];
                    pass.passImage = getPassAds().get(pass.passMerchant.merchantName)[1];
                    pass.passLink = getPassAds().get(pass.passMerchant.merchantName)[2];
                }
                else if(pass.passType.equals(passType[4]))
                {
                    pass.passDescription = getPassNews().get(pass.passMerchant.merchantName)[0];
                    pass.passImage = getPassNews().get(pass.passMerchant.merchantName)[1];
                    pass.passLink = getPassNews().get(pass.passMerchant.merchantName)[2];
                }
                else {
                    pass.isLocked = isLocked[randInt(0,isLocked.length-1)];
                    if(pass.isLocked)
                        pass.lockType = passLock[randInt(0,2)];
                    pass.passDescription = getPassDetails().get(pass.passMerchant.merchantName)[randInt(0, getPassDetails().get(pass.passMerchant.merchantName).length - 1)];
                }
                if(randInt(0,5) < 1)
                    trendingPasses.add(pass);
                availablePasses.add(pass);
            }
        }
        return availablePasses;
    }

    public void addTransaction(Transaction transaction)
    {
        if(allTransactionList!=null && currentJoeUser().transactionList!=null)
        {
            allTransactionList.add(transaction);
            currentJoeUser().transactionList.add(transaction);
        }
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public String randomPhone()
    {
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);
        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
        return df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
    }

    private static double randDouble(double min, double max)
    {
        Random rand = new Random();
        double randomNum = min + (max - min) * rand.nextDouble();
        return randomNum;
    }

    public List<Merchant> getMerchantList()
    {
        if(merchantList.size()<1)
        {
            for(int n = 0; n < merchantNames.length; n++)
            {
                Merchant merchant = new Merchant();
                merchant.merchantName = merchantNames[n];
                merchant.merchantImage = merchantLogo[n];
                merchant.merchantCategory = merchantCategory[n];
                merchant.merchantDetails = merchantDetails[n];
                merchant.merchantCaption = merchantCaption[n];
                merchant.merchantImageResource = merchantResource[n];
                merchant.merchantGeoType = merchantGeoTypes[n];
                merchant.merchantGeoName = merchantGeoNames[n];
                merchant.merchantContact = merchantContacts[n];
                merchantList.add(merchant);
            }
        }
        return merchantList;
    }

    public List<JoeUser> getJoeUserList() {
        if(joeUserList.size() < 1)
        {
            for(int n = 0;n<userNames.length;n++)
            {
                JoeUser joeUser = new JoeUser();
                joeUser.fullName = userFullnames[n];
                joeUser.userName = userNames[n];
                joeUser.birthday = randomDate();
                joeUser.facebook = "https://www.facebook.com/dre.noctis";
                joeUser.phone = randomPhone();
                GoogleMapLocation googleMapLocation = new GoogleMapLocation().locationAddress("Random Address")
                        .locationDesc(joeUser.phone).locationName("House")
                        .locationLatitude(randDouble(14.7,15.7)).locationLongitude(randDouble(120.0,121.5))
                        .locationIcon(R.drawable.photo_marker_button);
                joeUser.googleMapLocation = googleMapLocation;
                joeUser.image = userPhotos[n];
                joeUser.currentPoints = randInt(100,5000);
                joeUser.postList = new ArrayList<>();
                joeUser.passList = new ArrayList<>();
                joeUser.pointsList = new ArrayList<>();
                joeUser.friendList = new ArrayList<>();
                joeUser.transactionList = new ArrayList<>();
                joeUser.earnedPoints = randInt(0,500);
                joeUser.givenPoints = randInt(0,500);
                joeUser.receivedPoints = randInt(0,500);
                joeUser.usedPoints = randInt(0,500);
                for(int y = 0;y< joeUser.currentPoints/100;y++)
                {
                    Pass joePass = getAvailablePasses().get(randInt(0,getAvailablePasses().size() - 1));
                    joeUser.passList.add(joePass);
                    joeUser.pointsList.add(joePass.passPoints);
                    int rand = randInt(0,availablePostList.size()-1);
                    availablePostList.get(rand).postUser = joeUser;
                    availablePostList.get(rand).postImage = joeUser.image;
                    joeUser.postList.add(availablePostList.get(rand));
                }
                joeUserList.add(joeUser);
            }
            for(Post post : availablePostList)
            {

                if(post.postUser==null) {
                    int rand = randInt(0,joeUserList.size()-1);
                    post.postUser = joeUserList.get(rand);
                    joeUserList.get(rand).postList.add(post);
                }
            }
            currentJoeUser().passList = getCurrentPassList();
            currentJoeUser().pointsList = new ArrayList<>();
            currentJoeUser().postList = new ArrayList<>();
            for(int y = 0;y< currentJoeUser().currentPoints/100;y++)
            {
                Pass joePass = getAvailablePasses().get(randInt(0,getAvailablePasses().size() - 1));
                currentJoeUser().pointsList.add(joePass.passPoints);
                int rand = randInt(0,availablePostList.size()-1);
                availablePostList.get(rand).postUser =  currentJoeUser();
                currentJoeUser().postList.add(availablePostList.get(rand));
            }
            joeUserList.add(currentJoeUser());
            for(Merchant merchant : getMerchantList())
            {
                merchant.merchantJoePassList = new ArrayList<>();
                merchant.merchantPostList = new ArrayList<>();
                merchant.merchantJoeUserList = new ArrayList<>();
                merchant.merchantBranchesList = new ArrayList<>();
                for(Pass pass : getAvailablePasses())
                {
                    if(pass.passMerchant.equals(merchant))
                        merchant.merchantJoePassList.add(pass);
                }
                for(Post post : availablePostList)
                {
                    if(post.postBranch.branchMerchant.equals(merchant)) {
                        merchant.merchantPostList.add(post);
                        if(!merchant.merchantJoeUserList.contains(post.postUser)) {merchant.merchantJoeUserList.add(post.postUser);}
                    }
                }
                for(Branch branch : branchList)
                {
                    if(branch.branchMerchant!=null && branch.branchMerchant.equals(merchant)) merchant.merchantBranchesList.add(branch);
                }
            }
        }
        return joeUserList;
    }

    public List<Transaction> getTransactionFromUser(JoeUser joeUser)
    {
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction transaction : allTransactionList)
        {
            if((transaction.transactionJoeUserGiver!= null && transaction.transactionJoeUserGiver.equals(joeUser))
                    || (transaction.transactionJoeUserReceiver!=null && transaction.transactionJoeUserReceiver.equals(joeUser)))
                transactions.add(transaction);
        }
        return transactions;
    }

    public JoeUser currentJoeUser()
    {
        if(joeUser==null) {
            joeUser = new JoeUser();
            joeUser.email = "cominteract@gmail.com";
            joeUser.fullName = "Andre Insigne";
            joeUser.userName = "cominteract@gmail.com";
            joeUser.birthday = randomDate();
            joeUser.facebook = "https://www.facebook.com/dre.noctis";
            joeUser.phone = randomPhone();
            GoogleMapLocation googleMapLocation = new GoogleMapLocation().locationAddress("Random Address")
                    .locationDesc(joeUser.phone).locationName("House")
                    .locationLatitude(randDouble(14.7,15.7)).locationLongitude(randDouble(120.0,121.5))
                    .locationIcon(R.drawable.photo_marker_button);
            joeUser.googleMapLocation = googleMapLocation;
            joeUser.currentPoints = randInt(100,5000);
            joeUser.earnedPoints = randInt(0,500);
            joeUser.givenPoints = randInt(0,500);
            joeUser.receivedPoints = randInt(0,500);
            joeUser.usedPoints = randInt(0,500);
            joeUser.image = "https://yt3.ggpht.com/-jMRGITTAJ20/AAAAAAAAAAI/AAAAAAAAAAA/F21oQNPPOE4/s108-c-k-no-mo-rj-c0xffffff/photo.jpg";
        }
        return joeUser;
    }

    public String randomDate()
    {
        SimpleDateFormat dfDateTime  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        int year = randInt(2017, 2018);// Here you can set Range of years you need
        int month = randInt(0, 11);
        int hour = randInt(9, 22); //Hours will be displayed in between 9 to 22
        int min = randInt(0, 59);
        int sec = randInt(0, 59);
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = randInt(1, gc.getActualMaximum(gc.DAY_OF_MONTH));
        gc.set(year, month, day, hour, min,sec);
        return dfDateTime.format(gc.getTime());
    }

    public String[] userEmail = new String[]{
            "ariane.scott@example.com"
            ,"jen.dunn@example.com"
            ,"caroline.hansen@example.com"
            ,"mason.price@example.com"
            ,"same.may@example.com"
            ,"eloïse.clement@example.com"
            ,"aymeric.sanchez@example.com"
            ,"maria.madsen@example.com"
            ,"ayhan.vlemmix@example.com"
            ,"claude.riley@example.com"
            ,"borre.tolenaars@example.com"
            ,"carmen.byrd@example.com"
            ,"juliette.wilson@example.com"
            ,"julia.lord@example.com"
            ,"bilge.denboef@example.com"
            ,"leonaura.aragão@example.com"
            ,"mustafa.akgül@example.com"
            ,"carlo.leeflang@example.com"
            ,"batur.bolatlı@example.com"
            ,"morgane.michel@example.com"
            ,"vicki.franklin@example.com"
            ,"pinja.sakala@example.com"
            ,"mia.thomas@example.com"
            ,"nicolas.martin@example.com"
            ,"wilma.schmitz@example.com"
            ,"topias.peura@example.com"
            ,"andreas.nielsen@example.com"
            ,"aitor.diez@example.com"
            ,"stella.arnaud@example.com"
            ,"oğuzhan.yeşilkaya@example.com"
            ,"becky.george@example.com"
            ,"cameron.day@example.com"
            ,"ewen.lemoine@example.com"
            ,"ronald.kelley@example.com"
            ,"gregorio.marin@example.com"
            ,"ernest.hall@example.com"
            ,"gilbert.rodriquez@example.com"
            ,"victor.gauthier@example.com"
            ,"coline.denis@example.com"
            ,"holly.kumar@example.com"
            ,"kuzey.karaer@example.com"
            ,"eva.lynch@example.com"
            ,"jaime.cortes@example.com"
            ,"taoufik.rosenberg@example.com"
            ,"russell.banks@example.com"
            ,"micah.geurten@example.com"
    };

    public String[] userFullnames = new String[]{
            "mrs ariane scott"
            ,"ms jen dunn"
            ,"ms caroline hansen"
            ,"mr mason price"
            ,"mr same may"
            ,"ms eloise clement"
            ,"mr aymeric sanchez"
            ,"mrs maria madsen"
            ,"mr ayhan vlemmix"
            ,"mr claude riley"
            ,"mr borre tolenaars"
            ,"ms carmen byrd"
            ,"ms juliette wilson"
            ,"mrs julia lord"
            ,"mrs bilge den boef"
            ,"ms leonaura aragao"
            ,"mr mustafa akgul"
            ,"mr carlo leeflang"
            ,"mr batur bolatli"
            ,"ms morgane michel"
            ,"ms vicki franklin"
            ,"ms pinja sakala"
            ,"mrs mia thomas"
            ,"mr nicolas martin"
            ,"ms wilma schmitz"
            ,"mr topias peura"
            ,"mr andreas nielsen"
            ,"mr aitor diez"
            ,"mrs stella arnaud"
            ,"mr oguzhan yeşilkaya"
            ,"mrs becky george"
            ,"mr cameron day"
            ,"mr ewen lemoine"
            ,"mr ronald kelley"
            ,"mr gregorio marin"
            ,"mr ernest hall"
            ,"mr gilbert rodriguez"
            ,"mr victor gauthier"
            ,"ms coline denis"
            ,"mrs holly kumar"
            ,"mr kuzey karaer"
            ,"ms eva lynch"
            ,"mr jaime cortes"
            ,"mr taoufik rosenberg"
            ,"mr russell banks"
            ,"mr micah geurten"
    };

    public String[] userNames = new String[]{
            "biglion848"
            ,"purplebutterfly355"
            ,"organictiger653"
            ,"ticklishrabbit682"
            ,"yellowfish547"
            ,"redbear998"
            ,"ticklishdog395"
            ,"yellowrabbit911"
            ,"blueelephant326"
            ,"tinykoala433"
            ,"yellowpanda168"
            ,"whitepanda924"
            ,"blackbear723"
            ,"smallbear637"
            ,"bigduck549"
            ,"redrabbit491"
            ,"greenbear131"
            ,"goldenpanda177"
            ,"greenbird755"
            ,"greenfish500"
            ,"yellowelephant335"
            ,"biglion771"
            ,"bluepanda322"
            ,"blackbear464"
            ,"tinymeercat470"
            ,"whitepanda158"
            ,"lazyfrog151"
            ,"crazyfish646"
            ,"blackkoala749"
            ,"ticklishostrich356"
            ,"goldenswan883"
            ,"greenladybug550"
            ,"smallsnake385"
            ,"organiclion784"
            ,"greenbutterfly641"
            ,"ticklishgorilla690"
            ,"reddog435"
            ,"organicrabbit704"
            ,"greendog921"
            ,"blackfish168"
            ,"purpledog586"
            ,"tinybear524"
            ,"blackdog563"
            ,"brownmeercat206"
            ,"silverbutterfly420"
    };

    public String[] userPhotos = new String[]{
            "https://randomuser.me/api/portraits/med/women/6.jpg"
            ,"https://randomuser.me/api/portraits/med/women/22.jpg"
            ,"https://randomuser.me/api/portraits/med/women/25.jpg"
            ,"https://randomuser.me/api/portraits/med/men/47.jpg"
            ,"https://randomuser.me/api/portraits/med/men/95.jpg"
            ,"https://randomuser.me/api/portraits/med/women/27.jpg"
            ,"https://randomuser.me/api/portraits/med/men/14.jpg"
            ,"https://randomuser.me/api/portraits/med/women/87.jpg"
            ,"https://randomuser.me/api/portraits/med/men/89.jpg"
            ,"https://randomuser.me/api/portraits/med/men/72.jpg"
            ,"https://randomuser.me/api/portraits/med/men/53.jpg"
            ,"https://randomuser.me/api/portraits/med/women/68.jpg"
            ,"https://randomuser.me/api/portraits/med/women/53.jpg"
            ,"https://randomuser.me/api/portraits/med/women/42.jpg"
            ,"https://randomuser.me/api/portraits/med/women/30.jpg"
            ,"https://randomuser.me/api/portraits/med/women/46.jpg"
            ,"https://randomuser.me/api/portraits/med/men/70.jpg"
            ,"https://randomuser.me/api/portraits/med/men/39.jpg"
            ,"https://randomuser.me/api/portraits/med/men/33.jpg"
            ,"https://randomuser.me/api/portraits/med/women/29.jpg"
            ,"https://randomuser.me/api/portraits/med/women/40.jpg"
            ,"https://randomuser.me/api/portraits/med/women/26.jpg"
            ,"https://randomuser.me/api/portraits/med/women/86.jpg"
            ,"https://randomuser.me/api/portraits/med/men/32.jpg"
            ,"https://randomuser.me/api/portraits/med/women/69.jpg"
            ,"https://randomuser.me/api/portraits/med/men/27.jpg"
            ,"https://randomuser.me/api/portraits/med/men/50.jpg"
            ,"https://randomuser.me/api/portraits/med/men/93.jpg"
            ,"https://randomuser.me/api/portraits/med/women/70.jpg"
            ,"https://randomuser.me/api/portraits/med/men/36.jpg"
            ,"https://randomuser.me/api/portraits/med/women/30.jpg"
            ,"https://randomuser.me/api/portraits/med/men/49.jpg"
            ,"https://randomuser.me/api/portraits/med/men/62.jpg"
            ,"https://randomuser.me/api/portraits/med/men/57.jpg"
            ,"https://randomuser.me/api/portraits/med/men/85.jpg"
            ,"https://randomuser.me/api/portraits/med/men/34.jpg"
            ,"https://randomuser.me/api/portraits/med/men/47.jpg"
            ,"https://randomuser.me/api/portraits/med/men/37.jpg"
            ,"https://randomuser.me/api/portraits/med/women/92.jpg"
            ,"https://randomuser.me/api/portraits/med/women/76.jpg"
            ,"https://randomuser.me/api/portraits/med/men/49.jpg"
            ,"https://randomuser.me/api/portraits/med/women/91.jpg"
            ,"https://randomuser.me/api/portraits/med/men/24.jpg"
            ,"https://randomuser.me/api/portraits/med/men/24.jpg"
            ,"https://randomuser.me/api/portraits/med/men/51.jpg"
            ,"https://randomuser.me/api/portraits/med/men/10.jpg"
    };
    public Pass.LockType[] passLock = new Pass.LockType[]{Pass.LockType.LockRedeem,Pass.LockType.LockSurvey,Pass.LockType.LockShare};
    public boolean[] isLocked = new boolean[]{false,true,false};
    public String[] merchantContacts = new String[]{"09175874654", "87-000" , "86-236","55-555", "77-777","1800-1888-6453","1800-1888-6453", "+6320844","0203-0141818","857-7240","+632-7020-888","+632-637-2879", "+632-374-2442"};
    public int[] merchantResource = new int[] {R.drawable.guess,R.drawable.jollibee,R.drawable.mcdo,R.drawable.greenwich,R.drawable.shakeys,R.drawable.nike,R.drawable.jordan,R.drawable.etude,R.drawable.easports,R.drawable.honda,R.drawable.cebupacific,R.drawable.mercurydrug,R.drawable.papemelroti};
    public String[] merchantNames = new String[] { "GUESS", "JOLLIBEE", "MCDO", "GREENWICH","SHAKEYS", "NIKE", "JORDAN","ETUDE","EA", "HONDA","CEBU PACIFIC", "MERCURY DRUG", "PAPEMELROTI" };
    public String[] merchantGeoNames = new String[] { "GUESS", "JOLLIBEE", "MCDO", "GREENWICH","SHAKEYS", "NIKE", "JORDAN","ETUDE","DATA BLITZ,GAMEONE", "HONDA","CEBU PACIFIC", "MERCURY DRUG", "PAPEMELROTI" };
    public String[] merchantGeoTypes = new String[] { "clothing_store", "food", "food", "food","food", "shoe_store", "shoe_store","store","store", "car_dealer","airport", "pharmacy", "store" };
    public String[] merchantCaption = new String[] { "Uncompromising Quality", "Bida ang sarap", "Love ko to!", "Beyond Every Pizza Box","Fun, Family, Pizza", "Just do it", "Do whatever it takes","Love is etude, Love is pink","It's in the game", "The power of dreams","Change is in the air", "Nakasisiguro gamot ay laging bago", "hands to work, hearts to God" };
    public String[] merchantLogo = new String[]{ "https://botw-pd.s3.amazonaws.com/styles/logo-original-577x577/s3/062015/guess.png?itok=wVNOgHKI"
             , "https://vignette.wikia.nocookie.net/logopedia/images/0/03/Jollibee_ph_logo.jpeg/revision/latest?cb=20130821014824"
             , "https://vignette.wikia.nocookie.net/logopedia/images/a/a9/Mcdonalds-90s-logo.svg/revision/latest/scale-to-width-down/200?cb=20100717060808"
             , "https://vignette.wikia.nocookie.net/logopedia/images/1/17/Home_logo2.gif/revision/latest?cb=20130902103629"
             , "data:image/png;base64,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"
             , "https://www.soundgrovemusic.sg/wp-content/uploads/2017/02/branding-nike-logo.png"
             , "data:image/png;base64,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"
             , "https://yt3.ggpht.com/-qREE3vjYwd8/AAAAAAAAAAI/AAAAAAAAAAA/sJT6qWvLdj4/s88-c-k-no-mo-rj-c0xffffff"
             , "https://vignette.wikia.nocookie.net/starwars/images/2/22/EA_logo.png/revision/latest/scale-to-width-down/480?cb=20151117033701"
             , "data:image/png;base64,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"
             , "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AAtp0RU.img?h=370&w=624&m=6&q=60&o=f&l=f"
             , "https://iliganjobs.com/wp-content/uploads/job-manager-uploads/company_logo/2017/02/mercury-drug.jpg"
             , "http://3.bp.blogspot.com/-PjzrumPMgPU/WNxZxWaHvCI/AAAAAAAAH8c/M092SwagHPsd6zmuxm-kYNmDbrLRwA2nwCK4B/s1600/header.jpg"};
    public String[] merchantCategory = new String[]{"Lifestyle" , "Food", "Food", "Food", "Food" , "Lifestyle", "Lifestyle","Fashion","Entertainment","Cars","Travel","Health","Arts"};
    public String passNewsShakeys = "You read that right! The P2018 Meal Deal is EXTENDED! Enjoy until March 31, 2018. #Lucky2018\n" + "What are you waiting for? Order now at www.shakeyspizza.ph or dial 77-777!\n" + "*Available for dine-in, carry-out and delivery\n" + "**Dine-in & carry-out price is P2,018.00 and Delivery price is P2,118.00\n" + "DTI FTEB PERMIT NO. 17510 SERIES OF 2017";
    public String passNewsShakeysImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28279497_1850632808312076_7481219612953047337_n.jpg?oh=161e62246e420c4046c5889a0ac4c79b&oe=5B0ADC79";
    public String passNewsShakeysLink = "https://www.facebook.com/ShakeysPH/photos/a.168836723138454.34115.148370235185103/1773326716022772/?type=3&theater";
    public String passAdShakeys = "Try the NEW Louisiana Shrimp Pizza today! Fresh and succulent shrimps marinated in Cajun spice blend topped with fresh basil leaves spread over our famous thin crust pizza with special herbed-garlic sauce";
    public String passAdShakeysImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/27973489_1764107353611375_5739802757888151949_n.jpg?oh=753d64c3afacb1d18100dcf26f4e2495&oe=5B487CEE";
    public String passAdShakeysLink = "https://www.facebook.com/ShakeysPH/videos/1764033930285384/";

    public String passNewsGuess = "THIS JUST IN! Today is the re-opening of GUESS Baby and Kids boutique in Ayala Cebu! Be sure to drop by ";
    public String passNewsGuessImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/27657459_1766048993690354_685913358039780692_n.jpg?oh=7b77cd9539e073723cbf1bda40b287d9&oe=5B4DF124";
    public String passNewsGuessLink = "https://www.facebook.com/GUESSPhil/posts/1766049177023669";
    public String passAdGuess = "Big thanks to our Cebu family who joined us last February 3 for #LoveGUESSCebu! See you on our next event!";
    public String passAdGuessImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/27657573_1766388733656380_3987200793822438350_n.jpg?oh=4344c2f5c9befb53c3fcac44e20e99ff&oe=5B0D4692";
    public String passAdGuessLink = "https://www.facebook.com/GUESSPhil/posts/1766388776989709";

    public String passNewsMcdo = "McDonald’s continues to grow with more new stores this 2018. Visit our latest branch at Madison, Greenhills featuring a new and contemporary design!";
    public String passNewsMcdoImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28166248_1685461124836474_5047436972351650339_n.jpg?oh=b46541005af153a7b53ef8ee65c98774&oe=5B4A61B0";
    public String passNewsMcdoLink = "https://www.facebook.com/McDo.ph/posts/1685462488169671";
    public String passAdMcdo = "Bagong desal para sa bagong umaga! Try our NEW Longgadesal today for only P42! Price varies.";
    public String passAdMcdoImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/27972430_1691886510860602_5672213395465519901_n.jpg?oh=036961cc2c1099b16cfdb2c4f77aaaa0&oe=5B0696C1";
    public String passAdMcdoLink = "https://www.facebook.com/McDo.ph/posts/1691886907527229";
    //
    public String passNewsGreenwich = "SPOTTED: The newest members of the Greenwich Barkada (insert Yassi Pressman) here at #GWorldPizzaDay! #GWithTheBarkada";
    public String passNewsGreenwichImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/27544793_2003745482976040_7900943225789429898_n.jpg?oh=c0d78dcf8b7296b37af6daf2792ae59d&oe=5B49978E";
    public String passNewsGreenwichLink = "https://www.facebook.com/GreenwichPizza/posts/2003745836309338";
    public String passAdGreenwich = "Greenwich Overload Pizza, Loaded sa toppings, Over sa sarap. Starts at Presyong Kaibigan of only P219!";
    public String passAdGreenwichImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/27540062_1995708410446414_8261048103677544787_n.jpg?oh=bacaea5397676d14f7ea31f7af206bbd&oe=5B01BC5B";
    public String passAdGreenwichLink = "https://www.facebook.com/GreenwichPizza/posts/1995708590446396";

    public String passNewsJollibee = "Looking for a fun summer experience for your kids? Enroll them to Jollibee Mini Managers Camp where they can learn the values of service and leadership while having fun! Inquire now at participating Jollibee stores.\n" + "\n" + "For details, please see http://bit.ly/2oHBztt";
    public String passNewsJollibeeImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28279091_1747986201914265_7850783130145934258_n.jpg?oh=781d55e5bd65b74ab1c34d2d6911d970&oe=5B4D04D6";
    public String passNewsJollibeeLink = "https://www.facebook.com/JollibeePhilippines/photos/a.177020915677476.36028.170055766373991/1747986201914265/?type=3&theater";
    public String passAdJollibee = "Free yourself from the guilt. Enjoy a bite of Tuna Pie after a good run 🏃";
    public String passAdJollibeeImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28167226_1747879441924941_1540256596170843114_n.jpg?oh=616ab55c5be2ed0d9a91bf59c06929e8&oe=5B46CCB7";
    public String passAdJollibeeLink = "https://www.facebook.com/JollibeePhilippines/posts/1747879611924924";

    public String passNewsEA = "Save the Date: EA PLAY is coming this June!";
    public String passNewsEAImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28279552_10156173479219190_2085511535561902209_n.jpg?oh=c5f40eccf8c81872e264284693ab2215&oe=5B07118A";
    public String passNewsEALink = "https://www.facebook.com/EA/photos/a.464072989189.252660.68678914189/10156173479219190/?type=3";
    public String passAdEA = "Jump into a classic shooter with EA Access.\n" + "See all the games in the Vault: x.ea.com/44638";
    public String passAdEAImage = "https://media.contentapi.ea.com/content/dam/ea-access-refresh-prototype/news/eax-news-hero-16-9.jpg.adapt.1456w.jpg";
    public String passAdEALink = "https://www.facebook.com/EA/posts/10156072547094190";

    public String passNewsEtude = "Visit us at SM City Baguio (official) this weekend for their 3 Day Sale! Celebrate Panagbenga Festival with Etude House for free gifts and more! ";
    public String passNewsEtudeImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28055682_1753465424676084_1623208960457042331_n.jpg?oh=462991c563bea1e24e1af94329e3bc92&oe=5B4181BB";
    public String passNewsEtudeLink = "https://www.facebook.com/etudehousephilippines/photos/a.156310611058248.31367.156132414409401/1753465424676084/?type=3";
    public String passAdEtude = "When you had to give yourself a break for long lectures and breathe in for a while. Retouch! It's therapeutic. Take your Dear My Lips Talk with you! #DearMyLipsTalk #FromPrincesstoGirlBoss #EtudeHousePH\n" + "\n" + "P578";
    public String passAdEtudeImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28576361_1762024713820155_7531129289865953280_n.jpg?oh=4bfcf3e6ec7fef245c85ebfbb3ad304f&oe=5B457E04";
    public String passAdEtudeLink = "https://www.facebook.com/etudehousephilippines/photos/a.156310611058248.31367.156132414409401/1762024710486822/?type=3";

    public String passNewsNike = "Strong and empowered. Celebrate Women's Week with Nike Factory Store and avail discounts on women's footwear and apparel starting March 2.\n" + "\n" + "Get the Nike Women's Air Zoom Fearless Flyknit at 30% off, available at NFS SLEX Mamplasan, NFS NLEX Pampanga, NFS Paseo de Sta. Rosa, and NFS Mactan Cebu. #NikeFactoryStorePH";
    public String passNewsNikeImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28685553_1647397595306103_1432071791508119449_n.jpg?oh=63449ea7c04fb852f0c1c12df79cd7da&oe=5B01A621";
    public String passNewsNikeLink = "https://www.facebook.com/nikefactorystoreph/photos/a.434831996562675.96615.255304357848774/1647397595306103/?type=3";
    public String passAdNike = "Unmatched aesthetic.\n" + "The Air Jordan 13 Retro \"Altitude\" is now in store. Cop your pair today and visit Nike Factory Store. #Jordan13 #NikeFactoryStorePH";
    public String passAdNikeImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28279461_1642474889131707_7204610419812052834_n.jpg?oh=5b6d0f9527cd5b9640b4fc8fa22960b0&oe=5B139E70";
    public String passAdNikeLink = "https://www.facebook.com/nikefactorystoreph/photos/a.434831996562675.96615.255304357848774/1642474889131707/?type=3";

    public String passNewsJordan = "Recognizing greatness.\n" + "Legendary Jordan designer Tinker Hatfield comes together with shoe artist Joshua Vides to talk about their creative processes. #JUMPMAN";
    public String passNewsJordanImage = "https://air.jordan.com/wp-content/uploads/2018/02/desktop_IMG_1427-1920x900.jpg";
    public String passNewsJordanLink = "https://www.facebook.com/jumpman23/posts/1831994906853066";
    public String passAdJordan = "\uD83D\uDD11 to royalty.\n" + "The #AirJordan XIII 'White / Royal' is available now: https://swoo.sh/2HOWbsA.";
    public String passAdJordanImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28378074_1845528128833077_1101709761159455960_n.jpg?oh=e4692d8dd644ab85a50a4c793d293796&oe=5B42262A";
    public String passAdJordanLink = "https://www.facebook.com/jumpman23/photos/a.117452338307340.20970.113589525360288/1845528128833077/?type=3";

    public String passNewsCebuPacific = "CEBU PACIFIC TRAVEL ADVISORY #2\n" + "As of February 13, 2018\n" + "In light of unfavorable and unsafe weather conditions caused by TS Basyang,";
    public String passNewsCebuPacificImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/11846655_993814750664800_1330115123810384916_n.png?oh=2247cce4347ae4487100789d69d26c1d&oe=5B4BE08B";
    public String passNewsCebuPacificLink = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/11846655_993814750664800_1330115123810384916_n.png?oh=2247cce4347ae4487100789d69d26c1d&oe=5B4BE08B";
    public String passAdCebuPacific = "Now you can fly direct to Melbourne, Australia from Manila! Book now from Feb. 27 to Mar. 3 for as low as P 2,199! Travel from Aug. 14 to Oct. 31, 2018. bit.ly/fb02272018";
    public String passAdCebuPacificImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28168336_1807787169267550_2377213592123721713_n.jpg?oh=8e6b5fe14e934ed8a40446cff4090378&oe=5B4665F3";
    public String passAdCebuPacificLink = "https://www.facebook.com/cebupacificairphilippines/posts/1791706940875573";

    public String passNewsPapemelroti = "Sign up for my art camp this summer! I will have an art camp in Quezon City, Makati and Alabang - take your pick!\n" + "\n" + "sign up here:\n" + "ishine.ph";
    public String passNewsPapemelrotiImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28279799_10156817965820190_5157093754948900511_n.jpg?oh=4837820488443d74d7602f01429de185&oe=5B4BD88F";
    public String passNewsPapemelrotiLink = "https://www.facebook.com/photo.php?fbid=10156817965820190&set=a.74805210189.108812.748755189&type=3&theater";
    public String passAdPapemelroti = "Say yes to adventure and take your #papemelroti essentials with you, or decorate your space with items to inspire you to plan that next trip! ✈️\n" + "\uD83D\uDED2 Get these items in store / on shop.papemelroti.com";
    public String passAdPapemelrotiImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28377480_1814600295230406_3004564803218672336_n.jpg?oh=b3f82cc73b939451342811f45ee9f401&oe=5B0A7559";
    public String passAdPapemelrotiLink = "https://www.facebook.com/papemelroti/photos/a.1513285022028603.1073741872.113492892007830/1814600295230406/?type=3&theater";

    public String passNewsMercuryDrug = "The newest Mercury Drug store at Kawit V Central Mall is now open! #ParaSaYoSuki";
    public String passNewsMercuryDrugImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28468126_1299536503523274_5207979738628597962_n.png?oh=daa7ab499aefe10cf9bfbac23436bfd9&oe=5B0A4A1A";
    public String passNewsMercuryDrugLink = "https://www.facebook.com/mercurydrugph/photos/a.207951849348417.64679.153615111448758/1299536503523274/?type=3&theater";
    public String passAdMercuryDrug = "Make those special moments with your baby even more special with these Johnson's Baby products. #SukiSavings #ParaSaYoSuki";
    public String passAdMercuryDrugImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/28379726_1300235833453341_9135376550129900642_n.png?oh=f02ba79132ef99d56300bc0ce1d70a9b&oe=5B049CA3";
    public String passAdMercuryDrugLink = "https://www.facebook.com/mercurydrugph/photos/a.207951849348417.64679.153615111448758/1300235833453341/?type=3&theater";

    public String passNewsHonda = "We’re live from CES 2018, checking out our latest robot innovations. Join us to see our new companion bot, autonomous off-road workhorse vehicle concept and more.";
    public String passNewsHondaImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/10696307_10152706850109275_1708986217665329844_n.jpg?oh=4ff4467dcdbe28357007682958af4581&oe=5B0BABE1";
    public String passNewsHondaLink = "https://www.facebook.com/Honda/videos/10156066036639275/";
    public String passAdHonda = "Sophisticated style and performance are yours with the 2018 Honda Accord. Learn more at http://honda.us/2qoXLgb";
    public String passAdHondaImage = "https://scontent.fmnl7-1.fna.fbcdn.net/v/t1.0-9/26219148_10156052248004275_8423799297107271897_n.jpg?oh=b5c5faa5fc0f7475c91d6e75b02979cd&oe=5B098B5F";
    public String passAdHondaLink = "https://www.facebook.com/Honda/photos/a.10150704829214275.430187.107926874274/10156052248004275/?type=3&theater";
    public String[] passType = new String[] { kDeal, kCoupon, kFree, kChallenge, kNews, kAd };
    public String[] passCategory = new String[] { "Arts", "Beauty", "Food", "Entertainment", "Lifestyle" , "Fashion", "Health", "Travel","Entertainment","Cars"};
    public String[] merchantDetails = new String[] {"(styled as GUESS or Guess?) is an American clothing brand and retailer. In addition to clothing for both men and women, Guess markets other fashion accessories such as watches, jewelry, perfumes, and shoes."
            , "Jollibee is the largest fast food chain in the Philippines, operating a nationwide network of over 750 stores. A dominant market leader in the Philippines, Jollibee enjoys the lion's share of the local market that is more than all the other multinational brands combined."
            , "is a fast food company that was founded in 1940 as a restaurant operated by Richard and Maurice McDonald, in San Bernardino, California, United States. They rechristened their business as a hamburger stand. The first time a McDonald's franchise used the Golden Arches logo was in 1953 at a location in Phoenix, Arizona. In 1955, Ray Kroc, a businessman, joined the company as a franchise agent and proceeded to purchase the chain from the McDonald brothers. McDonald's had its original headquarters in Oak Brook, Illinois, but has approved plans to move its global headquarters to Chicago by 2018"
            , "founded in 1971 by Cresida Tueres, is a pizza and pasta chain in the Philippines.\n" +
            "In 1994, Jollibee Foods Corporation acquired 80% of Greenwich shares.[1] Then, in 2006, Jollibee bought out the remaining shares of its partners in Greenwich for PHP384 million, giving rise to a new company known as the Greenwich Pizza Corporation. The franchise experienced rapid expansion from the original 50 stores in 1994 to over 240 stores with an annual revenue of over PHP4 billion in 2005. Expanding further through 2011, the corporation had opened over 330 stores.[2]\n" +
            "Greenwich Pizza later became a brand managed by Fresh N Famous Foods, a subsidiary of Jollibee Foods Corporation"
            , "is a pizza restaurant chain based in the United States.[1] Founded in 1954, it was the first franchise pizza chain in the United States.[2][3] In 1968, the chain had 342 locations.[4] The chain currently has about 500 stores globally, and about 60 in the United States."
            , "Nike, Inc. is an American multinational corporation that is engaged in the design, development, manufacturing, and worldwide marketing and sales of footwear, apparel, equipment, accessories, and services"
            , "Air Jordan is a brand of basketball footwear and athletic clothing produced by Nike. It was created for former professional basketball player Michael Jordan"
            , "Etude House is a South Korean cosmetics brand owned by Amore Pacific. The brand name 'Etude' comes from Frederic Chopin's studies of the piano. Etude simply means study in French"
            , "Electronic Arts Inc. is an American video game company headquartered in Redwood City, California. Founded and incorporated on May 28, 1982 by Trip Hawkins"
            , "Honda Motor Company, Ltd. is a Japanese public multinational conglomerate corporation primarily known as a manufacturer of automobiles, aircraft, motorcycles, and power equipmen"
            , "Cebu Air, Inc., operating as Cebu Pacific, is a Philippine low-cost airline based on the grounds of Ninoy Aquino International Airport, Pasay City, Metro Manila, in the Philippines"
            , "Mercury Drug is the leading trusted and caring drugstore in the Philippines, whose founder has been hailed as the 'Father of Philippine Health and Wellness Retailing.'"
            , "We are a family-run company that has been producing original, eco-friendly gifts, stationery, and home décor since 1967"
    };
    public String[] passDescsGuess = new String[] { "GUESS 30 % OFF APPAREL", "GUESS 15% OFF WATCHES", "GUESS 30% OFF BAGS"};
    public String[] passDescsJollibee = new String[] { "JOLLIBEE BUY 1 TAKE 1 BURGER", "JOLLIBEE BUY SPAGETTI VALUE MEAL FREE HAMBURGER",
             "JOLLIBEE FREE YUM CHEESEBURGER FOR EVERY 6-PC CHICKENJOY BUCKET", "JOLLIBEE GRAB AN 8-PC BUCKET GET 2 PCS FREE","JOLLIBEE CHAMP 50 % OFF" };
    public String[] passDescsMcdo = new String[] { "MCDO FREE MEDIUM FRIES WHEN YOU BUY MEDIUM COCA COLA/SOFT DRINKS", "MCDO 2 16OZ GOLDEN ROOTBEER MCFLOAT", "MCDO 2 PC CHICKEN MCDO FOR 99PHP", "MCDO GET FREE 1 PC CHICKEN MCDO FOR EVERY HAPPY MEAL" };
    public String[] passDescsShakeys = new String[] { "SHAKEYS ENJOY A BASKET OF MOJOS FOR 40 PHP", "SHAKEYS 40% OFF CHICKEN 'N' MOJOS", "SHAKEYS SCALLOP PRIMO 30% DISCOUNT" };
    public String[] passDescsGreenwich = new String[] { "GREENWICH 50% OFF MIN ORDER 600 MAXDISCOUNT 800","GREENWICH BUY 1 GET 1 FREE BARKADA HAWAIIAN SPECIAL"};
    public String[] passDescsNike = new String[] { "NIKE 60 % OFF "," NIKE SALE 30-50% ON SELECTED ITEMS " };
    public String[] passDescsJordan = new String[] { "JORDAN 30 % OFF CLEARANCE "," JORDAN SALE 30-50% ON SELECTED ITEMS " };
    public String[] passDescsEtude = new String[] { "ETUDE 30 % OFF APPAREL" , "ETUDE 30 % DISCOUNT ALL ITEMS", "ETUDE SALE UP TO 75% ON SELECTED ITEMS", "ETUDE 30% DISCOUNT FOR ALL + 20% OFF FOR MEMBERS"};
    public String[] passDescsEA = new String[] { "EA 75% XMAS SALE ", "EA FREE EA GAMES FOR LIMITED TIME" };
    public String[] passDescsHonda = new String[] { "HONDA ZERO DOWNPAYMENT PRE BUDGET PRICE BENEFITES ", "HONDA DOUBLE BONUSES AND EXCLUSIVE GIFT" };
    public String[] passDescsCebuPacific = new String[] { "CEBU PACIFIC 350PHP FOR SELECT PHILIPPINE DESTINATIONS ", "CEBU PACIFIC 1,575PHP MANILA TO HONGKONG","CEBU PACIFIC 50% OFF ON ALL INTERNATIONAL DESTINATIONS", "CEBU PACIFIC 88PHP ON ALL DOMESTIC DESTINATIONS", "CEBU PACIFIC 50%OFF PAYDAY SEAT SALE","CEBU PACIFIC 1PHP TO ALL PHILIPPINE DESTINATIONS"};
    public String[] passDescsMercuryDrug = new String[] { "MERCURY DRUG VISINE OPTHALMIC DROPS P12.00 OFF", "MERCURY DRUG AMBROLEX OD CAPSULE P5.00 OFF", "MERCURY DRUG AMBROLEX ORAL DROPS P9.00 OFF","MERCURY DRUG IBUPROFEN DOLAN P5.00 OFF" };
    public String[] passDescsPapemelroti = new String[] { "PAPEMELROTI DISCOUNTED JEWELRY AND PAPER PRODUCTS 10 TO 15% ", "PAPEMELROTI UP TO 50% OFF" };
    private HashMap<String,String[]> getPassDetails()
    {
        if(passDetails.keySet().size()<1) {
            passDetails.put("GUESS", passDescsGuess);
            passDetails.put("JOLLIBEE", passDescsJollibee);
            passDetails.put("MCDO", passDescsMcdo);
            passDetails.put("SHAKEYS", passDescsShakeys);
            passDetails.put("GREENWICH", passDescsGreenwich);
            passDetails.put("NIKE", passDescsNike);
            passDetails.put("JORDAN", passDescsJordan);
            passDetails.put("ETUDE", passDescsEtude);
            passDetails.put("EA", passDescsEA);
            passDetails.put("HONDA", passDescsHonda);
            passDetails.put("CEBU PACIFIC", passDescsCebuPacific);
            passDetails.put("MERCURY DRUG", passDescsMercuryDrug);
            passDetails.put("PAPEMELROTI", passDescsPapemelroti);
        }
        return passDetails;
    }

    private HashMap<String,String[]> getPassAds()
    {
        if(passAds.keySet().size()<1) {
            passAds.put("GUESS", new String[]{passAdGuess,passAdGuessImage,passAdGuessLink});
            passAds.put("JOLLIBEE", new String[]{passAdJollibee,passAdJollibeeImage,passAdJollibeeLink});
            passAds.put("MCDO", new String[]{passAdMcdo,passAdMcdoImage,passAdMcdoLink});
            passAds.put("SHAKEYS", new String[]{passAdShakeys,passAdShakeysImage,passAdShakeysLink});
            passAds.put("GREENWICH", new String[]{passAdGreenwich,passAdGreenwichImage,passAdGreenwichLink});
            passAds.put("NIKE", new String[]{passAdNike,passAdNikeImage,passAdNikeLink});
            passAds.put("JORDAN", new String[]{passAdJordan,passAdJordanImage,passAdJordanLink});
            passAds.put("ETUDE", new String[]{passAdEtude,passAdEtudeImage,passAdEtudeLink});
            passAds.put("EA", new String[]{passAdEA,passAdEAImage,passAdEALink});
            passAds.put("HONDA", new String[]{passAdHonda,passAdHondaImage,passAdHondaLink});
            passAds.put("CEBU PACIFIC", new String[]{passAdCebuPacific,passAdCebuPacificImage,passAdCebuPacificLink});
            passAds.put("MERCURY DRUG", new String[]{passAdMercuryDrug,passAdMercuryDrugImage,passAdMercuryDrugLink});
            passAds.put("PAPEMELROTI", new String[]{passAdPapemelroti,passAdPapemelrotiImage,passAdPapemelrotiLink});
        }
        return passAds;
    }
    private HashMap<String,String[]> getPassNews()
    {
        if(passNews.keySet().size()<1) {
            passNews.put("GUESS", new String[]{passNewsGuess,passNewsGuessImage,passNewsGuessLink});
            passNews.put("JOLLIBEE", new String[]{passNewsJollibee,passNewsJollibeeImage,passNewsJollibeeLink});
            passNews.put("MCDO", new String[]{passNewsMcdo,passNewsMcdoImage,passNewsMcdoLink});
            passNews.put("SHAKEYS", new String[]{passNewsShakeys,passNewsShakeysImage,passNewsShakeysLink});
            passNews.put("GREENWICH", new String[]{passNewsGreenwich,passNewsGreenwichImage,passNewsGreenwichLink});
            passNews.put("NIKE", new String[]{passNewsNike,passNewsNikeImage,passNewsNikeLink});
            passNews.put("JORDAN", new String[]{passNewsJordan,passNewsJordanImage,passNewsJordanLink});
            passNews.put("ETUDE", new String[]{passNewsEtude,passNewsEtudeImage,passNewsEtudeLink});
            passNews.put("EA", new String[]{passNewsEA,passNewsEAImage,passNewsEALink});
            passNews.put("HONDA", new String[]{passNewsHonda,passNewsHondaImage,passNewsHondaLink});
            passNews.put("CEBU PACIFIC", new String[]{passNewsCebuPacific,passNewsCebuPacificImage,passNewsCebuPacificLink});
            passNews.put("MERCURY DRUG", new String[]{passNewsMercuryDrug,passNewsMercuryDrugImage,passNewsMercuryDrugLink});
            passNews.put("PAPEMELROTI", new String[]{passNewsPapemelroti,passNewsPapemelrotiImage,passNewsPapemelrotiLink});
        }
        return passNews;
    }
}
