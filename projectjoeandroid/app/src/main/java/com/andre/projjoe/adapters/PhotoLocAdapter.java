package com.andre.projjoe.adapters;



/**
 * Created by Dre on 5/30/2014.
 */
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.fragments.FilterPhotoFragment;
import com.andre.projjoe.fragments.TagPhotoFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Branch;
import com.googleMapsAndGPS.GoogleMapLocation;

public class PhotoLocAdapter extends RecyclerView.Adapter<PhotoLocAdapter.PhotoLocViewHolder> {
	private Context mContext;


	List<Branch> mapBranchList;
	TagPhotoFragment tagPhotoFragment;
	FragmentInteractionInterface fragmentInteractionInterface;
	Bitmap selectedBitmap;
	public class PhotoLocViewHolder extends RecyclerView.ViewHolder {
		public TextView locationTitle,locationAddress,locationDistance;
		public View holderView;
		public Branch branch;
		public PhotoLocViewHolder(View view) {
			super(view);
			locationTitle = (TextView) view.findViewById(R.id.photoLocTitleTxt);
			locationAddress = (TextView) view.findViewById(R.id.photoLocAddressTxt);
			locationDistance = (TextView) view.findViewById(R.id.photoLocDistanceTxt);
			holderView = view;

		}
	}

	public void setSelectedBitmap(Bitmap selectedBitmap) {
		this.selectedBitmap = selectedBitmap;
	}

	public PhotoLocAdapter(Context mContext, List<Branch> mapBranchList,
						   FragmentInteractionInterface fragmentInteractionInterface, TagPhotoFragment tagPhotoFragment) {
		this.mContext = mContext;
		this.mapBranchList = mapBranchList;
		this.fragmentInteractionInterface = fragmentInteractionInterface;
		this.tagPhotoFragment = tagPhotoFragment;
	}






	@Override
	public PhotoLocAdapter.PhotoLocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.photoloc_adapter, parent, false);

		return new PhotoLocAdapter.PhotoLocViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final PhotoLocAdapter.PhotoLocViewHolder holder, int position) {
		if(mapBranchList!=null && mapBranchList.size() > 0)
		{
			holder.branch = mapBranchList.get(position);
			holder.locationTitle.setText(holder.branch.branchName);
			holder.locationAddress.setText(holder.branch.branchAddress);
			holder.holderView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if(holder.branch.branchMerchant!=null) {
						FilterPhotoFragment filterPhotoFragment = FilterPhotoFragment.newInstance();
						if(selectedBitmap!=null)
						filterPhotoFragment.edited = selectedBitmap;
						filterPhotoFragment.selectedBranch = holder.branch;
						fragmentInteractionInterface.onSwitchFragment(tagPhotoFragment, filterPhotoFragment);
					}
					else
					{
						Toast.makeText(mContext, " Selected branch has no merch ", Toast.LENGTH_LONG).show();
					}
					//passDetailsInterface.onMapShownWith(holder.branch.googleMapLocation);
					//Toast.makeText(mContext," Clicked " + holder.googleMapLocation.getLocationName(), Toast.LENGTH_LONG).show();
//                showPopupMenu(holder.overflow);
				}
			});
		}
	}



	@Override
	public int getItemCount() {
			return mapBranchList.size();
	}
	public interface PassDetailsInterface{
		public void onMapShownWith(GoogleMapLocation googleMapLocation);
	}
}
