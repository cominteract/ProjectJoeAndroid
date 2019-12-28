package com.andre.projjoe.adapters;

/**
 * Created by Dre on 5/30/2014.
 */
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.andre.projjoe.R;
import com.andre.projjoe.fragments.ChallengeDetailsFragment;
import com.andre.projjoe.fragments.DiscoverFragment;

import com.andre.projjoe.fragments.PassDetailsFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Post;
import com.andre.projjoe.models.Transaction;


public class PhotoCheckInAdapter extends RecyclerView.Adapter<PhotoCheckInAdapter.PhotoCheckInViewHolder> {

    private Context mContext;
    private List<Post> postList;
    FragmentInteractionInterface fragmentInteractionInterface;

    public class PhotoCheckInViewHolder extends RecyclerView.ViewHolder {
        public TextView photoCheckInUserNameTxt,photoCheckInBranchTxt;
        public ImageView photoCheckInUserImage,photoCheckInPostImage;
        Post post;
        public View holderView;


        public PhotoCheckInViewHolder(View view) {
            super(view);

            holderView = view;
            photoCheckInBranchTxt = (TextView)view.findViewById(R.id.photoCheckInBranchTxt);
            photoCheckInUserNameTxt = (TextView) view.findViewById(R.id.photoCheckInUserNameTxt);
            photoCheckInPostImage = (ImageView) view.findViewById(R.id.photoCheckInPostImage);
            photoCheckInUserImage = (ImageView) view.findViewById(R.id.photoCheckInUserImage);

        }
    }

    public PhotoCheckInAdapter(Context mContext, List<Post> postList,
                           FragmentInteractionInterface fragmentInteractionInterface) {
        this.mContext = mContext;
        this.postList = postList;
        this.fragmentInteractionInterface = fragmentInteractionInterface;
    }


    @Override
    public PhotoCheckInAdapter.PhotoCheckInViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photocheckin_adapter, parent, false);
        return new PhotoCheckInAdapter.PhotoCheckInViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoCheckInAdapter.PhotoCheckInViewHolder holder, int position) {
        holder.post = postList.get(position);
        Glide.with(mContext).load(holder.post.postImage).into(holder.photoCheckInPostImage);
        if(holder.post.postUser!=null&& holder.post.postUser.image!=null)
        Glide.with(mContext).load(holder.post.postUser.image).into(holder.photoCheckInUserImage);
        holder.photoCheckInBranchTxt.setText(holder.post.postBranch.branchName);
        holder.photoCheckInUserNameTxt.setText(holder.post.postUser.fullName);
        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }
}