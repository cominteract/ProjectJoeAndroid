package com.andre.projjoe.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.activities.MainNavigation;
import com.andre.projjoe.adapters.DashboardAdapter;
import com.andre.projjoe.adapters.MerchBranchesAdapter;
import com.andre.projjoe.adapters.MerchPhotosAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;
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


public class PassDetailsFragment extends Fragment implements OnMapReadyCallback, GoogleMapsInterface, MerchBranchesAdapter.PassDetailsInterface {
	LinearLayout passDetailsPhotosContainer,passDetailsPrintContainer,passDetailsLocationsContainer;
	LinearLayout passDetailsMainContainer,passDetailsMapContainer;
	Button passDetailsPhotoTabBtn,passDetailsPrintTabBtn,passDetailsLocateTabBtn;
	TextView passDetailsDescTxt;
	TextView passDetailsType;
	public Pass selectedPass;
	GoogleMapLocationDataSample googleMapLocationDataSample;
	GoogleMapManager googleMapManager;
	GoogleMapApiManager googleMapApiManager;
	SupportMapFragment passDetailsMap;
	GoogleMap googleMap;
	RecyclerView passLocationsRecyclerView;
	FragmentInteractionInterface fragmentInteractionInterface;
	public boolean fromDiscover = false;
	DataPasses dataPasses;
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		passDetailsMap = (SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.passDetailsMap);
		passDetailsMainContainer = (LinearLayout) view.findViewById(R.id.passDetailsMainContainer);
		passDetailsMapContainer = (LinearLayout) view.findViewById(R.id.passDetailsMapContainer);
		passLocationsRecyclerView = (RecyclerView) view.findViewById(R.id.passDetailsLocationsRecyclerView);
		passLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
		TextView passDetailsPrintTxt = (TextView) view.findViewById(R.id.passDetailsPrintTxt);
		TextView passDetailsTitleTxt = (TextView) view.findViewById(R.id.passDetailsTitleTxt);
		passDetailsDescTxt = (TextView) view.findViewById(R.id.passDetailsDescTxt);
		passDetailsType = (TextView) view.findViewById(R.id.passDetailsTypeTxt);
		TextView passDetailsClaimsTxt = (TextView) view.findViewById(R.id.passDetailsClaimsTxt);
		TextView passDetailsDeadlineTxt = (TextView) view.findViewById(R.id.passDetailsDeadlineTxt);
		ImageView passDetailsImage = (ImageView)view.findViewById(R.id.passDetailsIconImage);
		Button passDetailsLeftBtn = (Button)view.findViewById(R.id.passDetailsLeftBtn);
		Button passDetailsRightBtn = (Button)view.findViewById(R.id.passDetailsRightBtn);
		if(!fromDiscover) {
				passDetailsLeftBtn.setText("USE");
				passDetailsRightBtn.setText("GIVE");
				passDetailsLeftBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						UseInputPasswordFragment useInputPasswordFragment = UseInputPasswordFragment.newInstance();
						useInputPasswordFragment.selectedPass = selectedPass;
						fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this,useInputPasswordFragment);
					}
				});

				passDetailsRightBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						GivePassFragment givePassFragment = GivePassFragment.newInstance();
						givePassFragment.selectedPass = selectedPass;
						fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this,givePassFragment);
					}
				});
		}
		else
		{
			if(selectedPass.isLocked)
			{
				passDetailsLeftBtn.setText("BACK");
				passDetailsRightBtn.setText("UNLOCK");
				passDetailsLeftBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this,DiscoverFragment.newInstance());
					}
				});
				passDetailsRightBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if(selectedPass.lockType.equals(Pass.LockType.LockRedeem))
							showRedeemUnlock();
						else if(selectedPass.lockType.equals(Pass.LockType.LockShare))
							showShareUnlock();
						else
						{
							AnswerSurveyFragment answerSurveyFragment = AnswerSurveyFragment.newInstance();
							answerSurveyFragment.selectedPass = selectedPass;
							fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this, answerSurveyFragment);
						}
					}
				});
			}
			else {
				passDetailsLeftBtn.setText("SHARE");
				passDetailsRightBtn.setText("GRAB");
				passDetailsLeftBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Toast.makeText(getActivity(), "You shared this pass " + selectedPass.passDescription, Toast.LENGTH_LONG).show();
						Transaction transaction = new Transaction(dataPasses.currentJoeUser(), dataPasses.randomDate(), selectedPass, Transaction.TransactionType.TransactionSharingPass);
						dataPasses.addTransaction(transaction);
					}
				});

				passDetailsRightBtn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						showGrabAlert();
					}
				});
			}
		}


		if(selectedPass!=null) {
			Glide.with(getActivity()).load(selectedPass.passMerchant.merchantImage).into(passDetailsImage);
			passDetailsPrintTxt.setText(selectedPass.passMerchant.merchantDetails);
			passDetailsTitleTxt.setText(selectedPass.passMerchant.merchantName);
			passDetailsDescTxt.setText(selectedPass.passDescription);
			passDetailsDeadlineTxt.setText(selectedPass.passDuration);
			passDetailsClaimsTxt.setText(selectedPass.passClaims);
			passDetailsType.setText(selectedPass.passType);
		}

		passDetailsPhotoTabBtn = (Button) view.findViewById(R.id.passDetailsPhotoTabBtn);
		passDetailsLocateTabBtn = (Button) view.findViewById(R.id.passDetailsLocateTabBtn);
		passDetailsPrintTabBtn = (Button) view.findViewById(R.id.passDetailsPrintTabBtn);

		passDetailsPhotosContainer = (LinearLayout) view.findViewById(R.id.passDetailsPhotosContainer);
		passDetailsPrintContainer = (LinearLayout) view.findViewById(R.id.passDetailsPrintContainer);
		passDetailsLocationsContainer = (LinearLayout) view.findViewById(R.id.passDetailsLocationsContainer);
		passDetailsPrintContainer.setVisibility(View.VISIBLE);
		GridView passDetailsPhotosGridView = (GridView) view.findViewById(R.id.passDetailsPhotosGridView);
		passDetailsPhotosGridView.setAdapter(new MerchPhotosAdapter(getActivity(), selectedPass.passMerchant.merchantPostList));

		passDetailsPhotoTabBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				passDetailsPhotoTabBtn.setBackgroundColor(Color.parseColor("#00a652"));
				passDetailsPrintTabBtn.setBackgroundColor(Color.parseColor("#2981b9"));
				passDetailsLocateTabBtn.setBackgroundColor(Color.parseColor("#2981b9"));

				passDetailsPhotosContainer.setVisibility(View.VISIBLE);
				passDetailsPrintContainer.setVisibility(View.GONE);
				passDetailsLocationsContainer.setVisibility(View.GONE);
			}
		});

		passDetailsPrintTabBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				passDetailsPhotoTabBtn.setBackgroundColor(Color.parseColor("#2981b9"));
				passDetailsPrintTabBtn.setBackgroundColor(Color.parseColor("#00a652"));
				passDetailsLocateTabBtn.setBackgroundColor(Color.parseColor("#2981b9"));
				passDetailsPhotosContainer.setVisibility(View.GONE);
				passDetailsPrintContainer.setVisibility(View.VISIBLE);
				passDetailsLocationsContainer.setVisibility(View.GONE);
			}
		});

		passDetailsLocateTabBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				passDetailsPhotoTabBtn.setBackgroundColor(Color.parseColor("#2981b9"));
				passDetailsPrintTabBtn.setBackgroundColor(Color.parseColor("#2981b9"));
				passDetailsLocateTabBtn.setBackgroundColor(Color.parseColor("#00a652"));
				passDetailsPhotosContainer.setVisibility(View.GONE);
				passDetailsPrintContainer.setVisibility(View.GONE);
				passDetailsLocationsContainer.setVisibility(View.VISIBLE);
			}
		});
		passDetailsMap.getMapAsync(this);
	}

	private void shareTransaction()
	{
		Transaction transaction = new Transaction(fragmentInteractionInterface.dataPassRetrieved().currentJoeUser()
				,fragmentInteractionInterface.dataPassRetrieved().randomDate(),selectedPass.passPoints.point,
				Transaction.TransactionType.TransactionUsingPoints);
		selectedPass.isLocked = false;
		fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().passList.add(selectedPass);
		if(fragmentInteractionInterface.dataPassRetrieved().getAvailablePasses().contains(selectedPass))
			fragmentInteractionInterface.dataPassRetrieved().getAvailablePasses().remove(selectedPass);
		fragmentInteractionInterface.dataPassRetrieved().addTransaction(transaction);
		Toast.makeText(getActivity(),"Congratulations! You have shared " + selectedPass.passPoints.point,Toast.LENGTH_LONG).show();
		fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this, PassesFragment.newInstance());
	}

	private void redeemTransaction()
	{
		Transaction transaction = new Transaction(fragmentInteractionInterface.dataPassRetrieved().currentJoeUser()
				,fragmentInteractionInterface.dataPassRetrieved().randomDate(),selectedPass.passPoints.point,
				Transaction.TransactionType.TransactionUsingPoints);
		fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().currentPoints -= selectedPass.passPoints.point;
		fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().usedPoints += selectedPass.passPoints.point;
		selectedPass.isLocked = false;
		fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().passList.add(selectedPass);
		if(fragmentInteractionInterface.dataPassRetrieved().getAvailablePasses().contains(selectedPass))
			fragmentInteractionInterface.dataPassRetrieved().getAvailablePasses().remove(selectedPass);
		fragmentInteractionInterface.dataPassRetrieved().addTransaction(transaction);

		Toast.makeText(getActivity(),"Congratulations! You have used " + selectedPass.passPoints.point,Toast.LENGTH_LONG).show();

	}

	private void showShareUnlock()
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = this.getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_unlockshare, null);
		dialogBuilder.setView(dialogView);
		TextView unlockShareTxt = (TextView) dialogView.findViewById(R.id.unlockShareTxt);
		final AlertDialog alertDialog = dialogBuilder.create();
		ImageView unlockShareFacebookBtn = (ImageView) dialogView.findViewById(R.id.unlockShareFacebookBtn);
		unlockShareFacebookBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				shareTransaction();
				fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this, DiscoverFragment.newInstance());
			}
		});
		ImageView unlockShareEmailBtn = (ImageView) dialogView.findViewById(R.id.unlockShareEmailBtn);
		unlockShareEmailBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				alertDialog.dismiss();
				shareTransaction();
				fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this, DiscoverFragment.newInstance());

			}
		});
		alertDialog.show();
	}

	private void showRedeemUnlock()
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = this.getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_unlockredeem, null);
		dialogBuilder.setView(dialogView);
		TextView unlockRedeemTxt = (TextView) dialogView.findViewById(R.id.unlockRedeemTxt);
		unlockRedeemTxt.setText(unlockRedeemTxt.getText().toString() + selectedPass.passPoints.point + " POINTS");
		Button unlockRedeemBtn = (Button) dialogView.findViewById(R.id.unlockRedeemBtn);
		final AlertDialog alertDialog = dialogBuilder.create();
		unlockRedeemBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				redeemTransaction();
				fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this, DiscoverFragment.newInstance());
			}
		});

		alertDialog.show();
	}

	private void showGrabAlert()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Are you sure you want to Use this pass?");

		String[] arr = {"Note : Grabbing pass does not entitle user for reservation of the pass. Grabbing pass only allows claiming passes even without internet connection"};
		builder.setItems(arr, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				GrabSuccessDetailsFragment grabSuccessDetailsFragment = GrabSuccessDetailsFragment.newInstance();
				grabSuccessDetailsFragment.grabbedPass = selectedPass;
				fragmentInteractionInterface.onSwitchFragment(PassDetailsFragment.this,  grabSuccessDetailsFragment);
		} });

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    		View rootView = inflater.inflate(R.layout.fragment_passdetails, container, false);
    		return rootView;
    }
	public static PassDetailsFragment newInstance() {
		PassDetailsFragment fragment = new PassDetailsFragment();
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
		this.googleMap = googleMap;
		googleMapApiManager = new GoogleMapApiManager(this.getActivity());
		googleMapApiManager.startNearbySearch(this,googleMapManager.googleMapAccessor.getGoogleMapLocations().get(0)
				,selectedPass.passMerchant.merchantGeoType, selectedPass.passMerchant.merchantGeoName, "5000");

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

		if(googleMapManager!=null)
		googleMapManager.mapLocations();

		MerchBranchesAdapter adapter = new MerchBranchesAdapter(getActivity(),googleMapLocations, this,fromDiscover);
		passLocationsRecyclerView.setAdapter(adapter);
	}

	@Override
	public void onLocationLoaded(Location location) {

	}



	@Override
	public void onNearby(GoogleMapNearby googleMapNearby) {
		googleMapLocationDataSample.addLocationsFromNearbyCleared(googleMapNearby);

	}

	@Override
	public void onChangeType(String type) {

	}


	@Override
	public void onMapShownWith(GoogleMapLocation googleMapLocation) {
		googleMap.clear();

		googleMapLocationDataSample.setGoogleMapLocation(googleMapLocation);
		googleMapManager.mapLocations();
		passDetailsMainContainer.setVisibility(View.GONE);
		passDetailsMapContainer.setVisibility(View.VISIBLE);

	}
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof FragmentInteractionInterface) {
			fragmentInteractionInterface = (FragmentInteractionInterface) context;
			dataPasses = fragmentInteractionInterface.dataPassRetrieved();
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
}
