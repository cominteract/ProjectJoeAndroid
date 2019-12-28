package com.andre.projjoe.adapters;



/**
 * Created by Dre on 5/30/2014.
 */
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andre.projjoe.R;
import com.andre.projjoe.models.Branch;
import com.googleMapsAndGPS.GoogleMapLocation;

public class MerchBranchesAdapter extends RecyclerView.Adapter<MerchBranchesAdapter.MerchBranchesViewHolder> {
    private Context mContext;
    private List<GoogleMapLocation> mapLocationList;
    PassDetailsInterface passDetailsInterface;
    List<Branch> mapBranchList;
    boolean fromDiscover = false;
    public class MerchBranchesViewHolder extends RecyclerView.ViewHolder {
        public TextView locationTitle,locationAddress;
        public View holderView;
        public GoogleMapLocation googleMapLocation;
        public Branch branch;
        public MerchBranchesViewHolder(View view) {
            super(view);
            locationTitle = (TextView) view.findViewById(R.id.merchBranchName);
            locationAddress = (TextView) view.findViewById(R.id.merchBranchAddress);

            holderView = view;

        }
    }

    public MerchBranchesAdapter(Context mContext, PassDetailsInterface passDetailsInterface, List<Branch> mapBranchList) {
        this.mContext = mContext;
        this.mapBranchList = mapBranchList;
        this.passDetailsInterface = passDetailsInterface;
    }


    public MerchBranchesAdapter(Context mContext, List<GoogleMapLocation> mapLocationList, PassDetailsInterface passDetailsInterface) {
        this.mContext = mContext;
        this.mapLocationList = mapLocationList;
        this.passDetailsInterface = passDetailsInterface;


    }

    public MerchBranchesAdapter(Context mContext, List<GoogleMapLocation> mapLocationList, PassDetailsInterface passDetailsInterface, boolean fromDiscover) {
        this.mContext = mContext;
        this.mapLocationList = mapLocationList;
        this.passDetailsInterface = passDetailsInterface;
        this.fromDiscover = fromDiscover;


    }

    @Override
    public MerchBranchesAdapter.MerchBranchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.merch_branches_adapter, parent, false);

        return new MerchBranchesAdapter.MerchBranchesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MerchBranchesAdapter.MerchBranchesViewHolder holder, int position) {
        if(mapLocationList!=null && mapLocationList.size() > 0) {
            if(!fromDiscover)
                holder.holderView.setBackgroundColor(Color.parseColor("#f4c6ca"));
            else
                holder.holderView.setBackgroundColor(Color.parseColor("#2981b9"));
            holder.googleMapLocation = mapLocationList.get(position);
            holder.locationTitle.setText(holder.googleMapLocation.getLocationName());
            holder.locationAddress.setText(holder.googleMapLocation.getLocationAddress());
            holder.holderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    passDetailsInterface.onMapShownWith(holder.googleMapLocation);
                    //Toast.makeText(mContext," Clicked " + holder.googleMapLocation.getLocationName(), Toast.LENGTH_LONG).show();
//                showPopupMenu(holder.overflow);
                }
            });
        }
        else if(mapBranchList!=null && mapBranchList.size() > 0)
        {
            holder.branch = mapBranchList.get(position);
            holder.locationTitle.setText(holder.branch.branchName);
            holder.locationAddress.setText(holder.branch.branchAddress);
            holder.holderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    passDetailsInterface.onMapShownWith(holder.branch.googleMapLocation);
                    //Toast.makeText(mContext," Clicked " + holder.googleMapLocation.getLocationName(), Toast.LENGTH_LONG).show();
//                showPopupMenu(holder.overflow);
                }
            });
        }
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }
//
//    /**
//     * Click listener for popup menu items
//     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        if(mapBranchList!=null && mapBranchList.size() > 0)
            return mapBranchList.size();
        else
            return mapLocationList.size();
    }
    public interface PassDetailsInterface{
        public void onMapShownWith(GoogleMapLocation googleMapLocation);
    }
}
