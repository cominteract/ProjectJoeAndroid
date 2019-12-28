package com.andre.projjoe.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.activities.MainNavigation;
import com.andre.projjoe.adapters.DiscoverAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.Pass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.googleMapsAndGPS.GoogleMapAccessor;
import com.googleMapsAndGPS.GoogleMapApiManager;
import com.googleMapsAndGPS.GoogleMapLocation;
import com.googleMapsAndGPS.GoogleMapLocationDataSample;
import com.googleMapsAndGPS.GoogleMapManager;
import com.googleMapsAndGPS.GoogleMapNearby;
import com.googleMapsAndGPS.GoogleMapsInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DiscoverFragment extends Fragment implements OnMapReadyCallback, GoogleMapsInterface {
    // TODO: Rename parameter arguments, choose names that match

    FragmentInteractionInterface fragmentInteractionInterface;
    RecyclerView discoverRecyclerView;
    ListView discoverFilterListView;
    LinearLayout discoverFilterContainer;
    LinearLayout discoverMapContainer;
    LinearLayout discoverShowMapContainer;
    ImageView discoverBackBtn;
    DataPasses dataPasses;
    GoogleMapLocationDataSample googleMapLocationDataSample;
    GoogleMapManager googleMapManager;
    GoogleMapApiManager googleMapApiManager;
    SupportMapFragment discoverMap;
    GoogleMap googleMap;
    DiscoverAdapter discoverAdapter;
    private List<GoogleMapLocation> nearestGoogleMapLocationList = new ArrayList<>();
    private List<GoogleMapLocation> availableGoogleMapLocationList = new ArrayList<>();
    private int currentIndex = 0;
    long startTime;
    long endTime;
    static boolean isAlreadyLoaded = false;
    private static boolean isMapLoaded = false;
    public DiscoverFragment() {
        // Required empty public constructor
    }


    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(" On Resume ", " On Resume");
        if(discoverAdapter!=null)
        discoverAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        Log.d(" On Visible ", " On Visible" );
        if(visible && discoverAdapter !=null)
        {
            discoverAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        discoverRecyclerView = (RecyclerView) view.findViewById(R.id.discoverRecyclerView);
        discoverFilterContainer = (LinearLayout)view.findViewById(R.id.discoverFilterContainer);
        discoverMapContainer = (LinearLayout)view.findViewById(R.id.discoverMapContainer);
        discoverShowMapContainer = (LinearLayout)view.findViewById(R.id.discoverShowMapContainer);
        discoverMap = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.discoverMap);
        discoverBackBtn = (ImageView) view.findViewById(R.id.discoverBackBtn);
        setupFilter(view);
        Log.d(" Available Passes ", " Available Passes " + dataPasses.getAvailablePasses().size());
        discoverAdapter = new DiscoverAdapter(getActivity(),dataPasses.getAvailablePasses(),fragmentInteractionInterface,
                DiscoverFragment.this, PassDetailsFragment.newInstance());
        discoverRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        discoverRecyclerView.setAdapter(discoverAdapter);
        discoverFilterContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(discoverFilterListView.getVisibility() == View.GONE) {
                    discoverFilterListView.setVisibility(View.VISIBLE);
                    discoverRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    discoverFilterListView.setVisibility(View.GONE);
                    discoverRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        discoverBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discoverMapContainer.setVisibility(View.GONE);
                discoverBackBtn.setVisibility(View.GONE);
                discoverShowMapContainer.setVisibility(View.GONE);
                discoverRecyclerView.setVisibility(View.VISIBLE);
                discoverFilterListView.setVisibility(View.GONE);
            }
        });

        discoverMap.getMapAsync(this);
    }
    Location startPoint;
    Location endPoint;
    private void getStartPoint()
    {
        startPoint = new Location("locationA");
        startPoint.setLatitude(14.583771);
        startPoint.setLongitude(121.059675);

    }

    private void getNearestLocations(List<GoogleMapLocation> availableGoogleMapLocationList)
    {
        for(GoogleMapLocation googleMapLocation :  new ArrayList<GoogleMapLocation>(availableGoogleMapLocationList)) {
            endPoint = new Location("locationB");
            endPoint.setLatitude(googleMapLocation.getLocationLatitude());
            endPoint.setLongitude(googleMapLocation.getLocationLongitude());
            if(startPoint.distanceTo(endPoint) < 500) {
                nearestGoogleMapLocationList.add(googleMapLocation);
                checkNearbyPasses(googleMapLocation);
            }
        }
    }

    private void checkNearbyPasses(GoogleMapLocation googleMapLocation)
    {
        for(Pass pass : dataPasses.getAvailablePasses())
        {
            //Log.d(" Is added pass ", googleMapLocation.getLocationName().toLowerCase().contains(pass.passMerchant.merchantGeoName.toLowerCase()) + " pass merchant " + pass.passMerchant.merchantName + " googlemap location " + googleMapLocation.getLocationName());
            if(googleMapLocation.getLocationName().toLowerCase().contains(pass.passMerchant.merchantGeoName.toLowerCase()) && !dataPasses.getNearbyAvailablePasses().contains(pass))
                dataPasses.getNearbyAvailablePasses().add(pass);
        }
    }

    private void setupFilter(View rootView)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("All");
        list.add("Industry");
        list.add("Near Me");
        list.add("Trending");
        list.add("Merchant");
        discoverFilterListView = (ListView) rootView.findViewById(R.id.discoverFilterListView);
        final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
                R.layout.filter_adapter, list);
        discoverFilterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                if(position==0)
                {
                    if(isAlreadyLoaded)
                        setupAll();
                    else
                        Toast.makeText(getActivity()," Still loading ", Toast.LENGTH_LONG).show();
                }
                if(position==1)
                    fragmentInteractionInterface.onSwitchFragment(DiscoverFragment.this, FilterIndustryFragment.newInstance());
                else if(position == 2)
                {
                    if(isAlreadyLoaded)
                        setupNearby();
                    else
                        Toast.makeText(getActivity(),"Still loading ", Toast.LENGTH_LONG).show();
                }
                else if(position == 3)
                {
                    if(isAlreadyLoaded)
                        setupTrending();
                    else
                        Toast.makeText(getActivity(),"Still loading " , Toast.LENGTH_LONG).show();
                }
                else if(position == 4)
                {
                    fragmentInteractionInterface.onSwitchFragment(DiscoverFragment.this, FilterAllMerchantsFragment.newInstance());
                }
            }

        });
        discoverFilterListView.setAdapter(adapter);
        discoverShowMapContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discoverMapContainer.setVisibility(View.VISIBLE);
                discoverBackBtn.setVisibility(View.VISIBLE);
                discoverShowMapContainer.setVisibility(View.GONE);
                discoverRecyclerView.setVisibility(View.GONE);
                discoverFilterListView.setVisibility(View.GONE);
            }
        });
    }

    private void setupNearby()
    {
        discoverShowMapContainer.setVisibility(View.VISIBLE);
        discoverFilterListView.setVisibility(View.GONE);
        discoverRecyclerView.setVisibility(View.VISIBLE);
        discoverAdapter.adjustDiscoverList(dataPasses.getNearbyAvailablePasses());
    }

    private void setupAll()
    {
        discoverShowMapContainer.setVisibility(View.VISIBLE);
        discoverFilterListView.setVisibility(View.GONE);
        discoverRecyclerView.setVisibility(View.VISIBLE);
        discoverAdapter.adjustDiscoverList(dataPasses.getAvailablePasses());
    }

    private void setupTrending()
    {
        discoverShowMapContainer.setVisibility(View.VISIBLE);
        discoverFilterListView.setVisibility(View.GONE);
        discoverRecyclerView.setVisibility(View.VISIBLE);
        discoverAdapter.adjustDiscoverList(dataPasses.getTrendingPasses());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapLocationDataSample = new GoogleMapLocationDataSample(this);
        googleMapLocationDataSample.setCurrentLocation(null);
        googleMapManager = new GoogleMapManager(new GoogleMapAccessor(googleMap,
                false,
                googleMapLocationDataSample)
                , this, this.getActivity());
        this.googleMap = googleMap;
        googleMapApiManager = new GoogleMapApiManager(this.getActivity());

//        if(googleMapManager.googleMapAccessor.getGoogleMapLocations().size()>1)
//            googleMapApiManager.startPolyline(googleMap1,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0)
//                ,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(1));
        //googleMapPolylineManager.startNearbyKeyword(this, googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0),"cafe","star");
        //googleMapPolylineManager.startNearbySearch(this, googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0),"cafe","star");
        //googleMapApiManager.startNearbyType(this,
        //googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0),"food", "5000");
        //googleMapApiManager.startNearbySearch();
        startTime = System.nanoTime();

        if(dataPasses.getAvailablePasses().size() > 0 && dataPasses.getBranchList().size() < 1)
            googleMapApiManager.startNearbySearch(this,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0)
                ,dataPasses.getAvailablePasses().get(currentIndex).passMerchant.merchantGeoType, dataPasses.getAvailablePasses().get(currentIndex).passMerchant.merchantGeoName, "5000");
        else
        {
            if(!isMapLoaded) {
                nearestGoogleMapLocationList = dataPasses.getNearestGoogleMapLocationList();
                availableGoogleMapLocationList = dataPasses.getAvailableGoogleMapLocationList();
                googleMapManager.googleMapAccessor.setGoogleMapLocations(nearestGoogleMapLocationList);
                googleMapManager.mapLocations();
            }
            else
                isMapLoaded = true;
            Log.d(" Map Locations "," Map Locations ");
        }
        //googleMapApiManager.startNearbyType(this,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0),"food","5000");
        //googleMapApiManager.startNearbySearch(this,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0),"food", "Jollibee", "5000");
    }

    @Override
    public void onCameraMove(GoogleMapLocation googleMapLocation) {

    }

    @Override
    public void onMarkerClick(GoogleMapLocation googleMapLocation) {

    }

    @Override
    public void onMapLongClick(GoogleMapLocation googleMapLocation) {

    }

    @Override
    public void onInfoMarkerClick(GoogleMapLocation googleMapLocation) {

    }

    @Override
    public void onInfoMarkerLongClick(GoogleMapLocation googleMapLocation) {

    }

    @Override
    public void onInfoMarkerClose(GoogleMapLocation googleMapLocation) {

    }

    @Override
    public void onMapLocationsLoaded(List<GoogleMapLocation> googleMapLocations) {
        handleLocationList(googleMapLocations);
    }


    private void handleLocationList(List<GoogleMapLocation> googleMapLocations)
    {
        if(googleMapManager!=null && currentIndex == dataPasses.getAvailablePasses().size() -1) {
            dataPasses.setBranchList(googleMapLocations);
            availableGoogleMapLocationList = googleMapLocations;
            getStartPoint();
            getNearestLocations(googleMapLocations);
            endTime = System.nanoTime();

            //dataPasses.setNearbyAvailablePasses();
            googleMapManager.googleMapAccessor.setGoogleMapLocations(nearestGoogleMapLocationList);

            googleMapManager.mapLocations();
            dataPasses.setNearestGoogleMapLocationList(nearestGoogleMapLocationList);
            dataPasses.setAvailableGoogleMapLocationList(availableGoogleMapLocationList);
            isAlreadyLoaded = true;
            Log.d(" Finally completed ", nearestGoogleMapLocationList.size() + " nearby locations vs " + availableGoogleMapLocationList.size() + " all locations with time completed " + (endTime - startTime)/1000000000 + " seconds ");
        }
        else
        {
            currentIndex++;
            googleMapApiManager.startNearbySearch(this,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0)
                    ,dataPasses.getAvailablePasses().get(currentIndex).passMerchant.merchantGeoType, dataPasses.getAvailablePasses().get(currentIndex).passMerchant.merchantGeoName, "5000");
        }
    }

    @Override
    public void onLocationLoaded(Location location) {

    }

    @Override
    public void onNearby(GoogleMapNearby googleMapNearby) {
        googleMapLocationDataSample.addLocationsFromNearby(googleMapNearby);
    }

    @Override
    public void onChangeType(String type) {

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionInterface) {
            fragmentInteractionInterface = (FragmentInteractionInterface) context;
            dataPasses = ((FragmentInteractionInterface) context).dataPassRetrieved();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionInterface = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
