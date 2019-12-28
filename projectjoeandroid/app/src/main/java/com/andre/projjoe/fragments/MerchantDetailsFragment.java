package com.andre.projjoe.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.adapters.DiscoverAdapter;
import com.andre.projjoe.adapters.LeaderBoardAdapter;
import com.andre.projjoe.adapters.MerchBranchesAdapter;
import com.andre.projjoe.adapters.MerchPhotosAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.Merchant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.googleMapsAndGPS.GoogleMapAccessor;
import com.googleMapsAndGPS.GoogleMapApiManager;
import com.googleMapsAndGPS.GoogleMapLocation;
import com.googleMapsAndGPS.GoogleMapLocationDataSample;
import com.googleMapsAndGPS.GoogleMapManager;
import com.googleMapsAndGPS.GoogleMapNearby;
import com.googleMapsAndGPS.GoogleMapsInterface;

public class MerchantDetailsFragment extends Fragment implements OnMapReadyCallback, MerchBranchesAdapter.PassDetailsInterface, GoogleMapsInterface{
		FragmentInteractionInterface fragmentInteractionInterface;
		DataPasses dataPasses;
		public Merchant selectedMerchant;
		GoogleMapLocationDataSample googleMapLocationDataSample;
		GoogleMapManager googleMapManager;
		GoogleMapApiManager googleMapApiManager;
		SupportMapFragment merchantDetailsMap;
	    GoogleMap googleMap;
		ListView merchantDetailsListView;
		RelativeLayout merchantDetailsContainer;
		RelativeLayout merchantDetailsMapContainer;
		RecyclerView merchantDetailsRecyclerView;
		LeaderBoardAdapter leaderBoardAdapter;
		MerchBranchesAdapter branchesAdapter;
		MerchPhotosAdapter photosAdapter;
		DiscoverAdapter passesAdapter;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	
	        View rootView = inflater.inflate(R.layout.fragment_merchantdetails, container, false);
	        merchantDetailsMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.merchantDetailsMap);
	        merchantDetailsContainer = (RelativeLayout) rootView.findViewById(R.id.merchantDetailsContainer);
			merchantDetailsMapContainer = (RelativeLayout) rootView.findViewById(R.id.merchantDetailsMapContainer);
			merchantDetailsListView = (ListView) rootView.findViewById(R.id.merchantDetailsListView);
			merchantDetailsRecyclerView = (RecyclerView) rootView.findViewById(R.id.merchantDetailsRecyclerView);
			merchantDetailsRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
	        TextView merchantDetailsPromotedDiscountTxt= (TextView) rootView.findViewById(R.id.merchantDetailsPromotedDiscountTxt);
	        TextView merchantDetailsCaptionTxt= (TextView) rootView.findViewById(R.id.merchantDetailsCaptionTxt);
	        ImageView merchantDetailsImage= (ImageView) rootView.findViewById(R.id.merchantDetailsImage);
			if(selectedMerchant!=null)
			{
				merchantDetailsCaptionTxt.setText(selectedMerchant.merchantCaption);
				merchantDetailsPromotedDiscountTxt.setText(selectedMerchant.merchantName);
				merchantDetailsImage.setBackgroundResource(selectedMerchant.merchantImageResource);
			}
	        Button merchantDetailsDescriptionBtn = (Button) rootView.findViewById(R.id.merchantDetailsDescriptionBtn);
	        Button merchantDetailsPassesBtn = (Button) rootView.findViewById(R.id.merchantDetailsPassesBtn);
	        Button merchantDetailsPhotosBtn = (Button) rootView.findViewById(R.id.merchantDetailsPhotosBtn);
	        Button merchantDetailsBranchesBtn = (Button) rootView.findViewById(R.id.merchantDetailsBranchesBtn);
		    leaderBoardAdapter = new LeaderBoardAdapter(getActivity(),dataPasses.getUserListFromMerchant(selectedMerchant));
		    merchantDetailsListView.setAdapter(leaderBoardAdapter);
			branchesAdapter = new MerchBranchesAdapter(getActivity(),this,dataPasses.getBranchesFromMerchant(selectedMerchant));
			merchantDetailsRecyclerView.setAdapter(branchesAdapter);
			passesAdapter = new DiscoverAdapter(getActivity(),dataPasses.getPassesFromMerchant(selectedMerchant),fragmentInteractionInterface);
			photosAdapter = new MerchPhotosAdapter(getActivity(),dataPasses.getPostsFromMerchant(selectedMerchant));
			merchantDetailsBranchesBtn.setOnClickListener(new OnClickListener()
	        {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					merchantDetailsRecyclerView.setAdapter(branchesAdapter);
					merchantDetailsListView.setVisibility(View.GONE);
					merchantDetailsRecyclerView.setVisibility(View.VISIBLE);
				}
	  		      });
			merchantDetailsPassesBtn.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					merchantDetailsRecyclerView.setAdapter(passesAdapter);
					merchantDetailsListView.setVisibility(View.GONE);
					merchantDetailsRecyclerView.setVisibility(View.VISIBLE);

//					   DiscoverAdapter adapter = new DiscoverAdapter(getActivity(),feedList);
//					   listview.setAdapter(adapter);
				}
			});
	        rootView.findViewById(R.id.merchantDetailsDirectionsBtn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					googleMapApiManager.startPolyline(googleMap,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0)
							,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(1));
				}
			});
	        rootView.findViewById(R.id.merchantDetailsContactBtn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity()," Calling " + selectedMerchant.merchantContact,Toast.LENGTH_LONG).show();
				}
			});
	        merchantDetailsDescriptionBtn.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					merchantDetailsListView.setAdapter(leaderBoardAdapter);
					merchantDetailsListView.setVisibility(View.VISIBLE);
					merchantDetailsRecyclerView.setVisibility(View.GONE);
				}
	  		      });

			merchantDetailsPhotosBtn.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				    merchantDetailsListView.setAdapter(photosAdapter);
					merchantDetailsListView.setVisibility(View.VISIBLE);
					merchantDetailsRecyclerView.setVisibility(View.GONE);
			}
	  	      });
		    
		    merchantDetailsMap.getMapAsync(this);
	        return rootView;
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


	public static MerchantDetailsFragment newInstance() {
		MerchantDetailsFragment fragment = new MerchantDetailsFragment();

		return fragment;
	}


	@Override
	public void onMapReady(GoogleMap googleMap) {
		googleMapLocationDataSample = new GoogleMapLocationDataSample(this);
		googleMapLocationDataSample.setCurrentLocation(null);
		googleMapManager = new GoogleMapManager(new GoogleMapAccessor(googleMap,
				false,
				googleMapLocationDataSample)
				, this, this.getActivity());
		googleMapApiManager = new GoogleMapApiManager(this.getActivity());
		this.googleMap = googleMap;
	}

	@Override
	public void onMapShownWith(GoogleMapLocation googleMapLocation) {
		googleMap.clear();

		googleMapLocationDataSample.setGoogleMapLocationWithOwn(googleMapLocation);
		googleMapManager.mapLocations();

		merchantDetailsContainer.setVisibility(View.GONE);
		merchantDetailsMapContainer.setVisibility(View.VISIBLE);
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

	}

	@Override
	public void onLocationLoaded(Location location) {

	}

	@Override
	public void onNearby(GoogleMapNearby googleMapNearby) {

	}

	@Override
	public void onChangeType(String type) {

	}
}

