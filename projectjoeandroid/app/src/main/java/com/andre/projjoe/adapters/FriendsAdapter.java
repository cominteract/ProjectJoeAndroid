package com.andre.projjoe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.fragments.JoeUserFriendsFragment;
import com.andre.projjoe.fragments.JoeUserProfileFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.JoeUser;


import java.util.List;

/**
 * Created by Dre on 3/3/2018.
 */

public class FriendsAdapter  extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private Context mContext;
    private List<JoeUser> friendsList;
    private FragmentInteractionInterface fragmentInteractionInterface;
    JoeUserFriendsFragment joeUserFriendsFragment;

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView friendsNameTxt;
        public ImageView friendsImage;
        public View holderView;
        public JoeUser friend;
        public FriendsViewHolder(View view) {
            super(view);
            friendsNameTxt = (TextView) view.findViewById(R.id.friendsNameTxt);
            friendsImage = (ImageView) view.findViewById(R.id.friendsImage);


            holderView = view;

        }
    }


    public FriendsAdapter(Context mContext, List<JoeUser> friendsList ,
                          FragmentInteractionInterface fragmentInteractionInterface
    , JoeUserFriendsFragment joeUserFriendsFragment) {
        this.mContext = mContext;
        this.friendsList = friendsList;
        this.fragmentInteractionInterface = fragmentInteractionInterface;
        this.joeUserFriendsFragment = joeUserFriendsFragment;

    }

    public void adjustFriendList(List<JoeUser> friendsList)
    {
        this.friendsList = friendsList;
        notifyDataSetChanged();
    }

    @Override
    public FriendsAdapter.FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_adapter, parent, false);

        return new FriendsAdapter.FriendsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FriendsAdapter.FriendsViewHolder holder, int position) {
        holder.friend = friendsList.get(position);
        holder.friendsNameTxt.setText(holder.friend.fullName);


        // loading album cover using Glide library
        Glide.with(mContext).load(holder.friend.image).into(holder.friendsImage);

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoeUserProfileFragment joeUserProfileFragment = JoeUserProfileFragment.newInstance();
                joeUserProfileFragment.selectedJoeUser = holder.friend;
                fragmentInteractionInterface.onSwitchFragment(joeUserFriendsFragment,joeUserProfileFragment);
            }
        });
    }


    @Override
    public int getItemCount() {
        return friendsList.size();
    }


}