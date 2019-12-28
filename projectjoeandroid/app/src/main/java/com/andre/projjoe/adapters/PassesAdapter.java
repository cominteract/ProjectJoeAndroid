package com.andre.projjoe.adapters;

/**
 * Created by Dre on 5/30/2014.
 */
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.fragments.PassDetailsFragment;
import com.andre.projjoe.fragments.PassesFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Pass;

public class PassesAdapter extends RecyclerView.Adapter<PassesAdapter.PassesViewHolder> {
    private Context mContext;
    private List<Pass> passesList;
    private FragmentInteractionInterface fragmentInteractionInterface;
    PassesFragment passesFragment;
    PassDetailsFragment passDetailsFragment;
    public class PassesViewHolder extends RecyclerView.ViewHolder {
        public TextView passTitle, passDescription, passClaims, passDuration,passType;
        public ImageView passIcon, passNotification;
        public View holderView;
        public Pass pass;
        public PassesViewHolder(View view) {
            super(view);
            passTitle = (TextView) view.findViewById(R.id.passTitleTxt);
            passDescription = (TextView) view.findViewById(R.id.passDescTxt);
            passIcon = (ImageView) view.findViewById(R.id.passImage);
            passNotification = (ImageView) view.findViewById(R.id.passNotificationImage);
            passClaims = (TextView) view.findViewById(R.id.passClaimsTxt);
            passDuration  = (TextView) view.findViewById(R.id.passDeadlineTxt);
            passType = (TextView)view.findViewById(R.id.passTypeTxt);
            holderView = view;

        }
    }


    public PassesAdapter(Context mContext, List<Pass> passesList, FragmentInteractionInterface fragmentInteractionInterface,
                         PassesFragment currentFragment, PassDetailsFragment destinationFragment) {
        this.mContext = mContext;
        this.passesList = passesList;
        this.fragmentInteractionInterface = fragmentInteractionInterface;
        this.passesFragment = currentFragment;
        this.passDetailsFragment = destinationFragment;

    }

    @Override
    public PassesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passesfeed_adapter, parent, false);

        return new PassesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PassesViewHolder holder, int position) {
        holder.pass = passesList.get(position);
        holder.passTitle.setText(holder.pass.passMerchant.merchantName);
        holder.passClaims.setText(holder.pass.passClaims);
        holder.passDuration.setText(holder.pass.passDuration);
        holder.passDescription.setText(holder.pass.passDescription);
        holder.passType.setText(holder.pass.passType);
        // loading album cover using Glide library
        Glide.with(mContext).load(holder.pass.passMerchant.merchantImage).into(holder.passIcon);

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passDetailsFragment.selectedPass = holder.pass;
                passDetailsFragment.fromDiscover = false;
                fragmentInteractionInterface.onSwitchFragment(passesFragment, passDetailsFragment);
//                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.popup_menu, popup.getMenu());
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
        return passesList.size();
    }

}



