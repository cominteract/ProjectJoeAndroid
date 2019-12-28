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
import com.andre.projjoe.models.Transaction;


public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private Context mContext;
    private List<Pass> discoverList;
    private FragmentInteractionInterface fragmentInteractionInterface;
    DiscoverFragment discoverFragment;
    PassDetailsFragment passDetailsFragment;
    //discoverDetailsFragment discoverDetailsFragment;
    public class DiscoverViewHolder extends RecyclerView.ViewHolder {
        public TextView discoverTitle, discoverDescription, discoverClaims, discoverDuration;
        public ImageView discoverIcon;
        public TextView discoverNormalPassTypeTxt;

        public TextView discoverLockedTitle, discoverLockedDescription, discoverLockedClaims, discoverLockedDuration;
        public ImageView discoverLockedIcon;
        public TextView discoverLockedTypeTxt;

        public TextView discoverChallengeTitle;
        public ImageView discoverChallengeIcon;
        public TextView discoverChallengeTypeTxt;

        public TextView discoverNewsTitle;
        public ImageView discoverNewsIcon;
        public TextView discoverNewsTypeTxt;

        public View holderView;
        public Pass discover;
        public LinearLayout normalPassContainer,lockedPassContainer,challengePassContainer,newsContainer;

        public DiscoverViewHolder(View view) {
            super(view);
            normalPassContainer = (LinearLayout) view.findViewById(R.id.discoverNormalPassContainer);
            lockedPassContainer = (LinearLayout) view.findViewById(R.id.discoverLockedPassContainer);
            challengePassContainer = (LinearLayout) view.findViewById(R.id.discoverChallengePassContainer);
            newsContainer = (LinearLayout) view.findViewById(R.id.discoverNewsContainer);
            // ======

            discoverTitle = (TextView) view.findViewById(R.id.discoverTitleTxt);
            discoverDescription = (TextView) view.findViewById(R.id.discoverDescTxt);
            discoverIcon = (ImageView) view.findViewById(R.id.discoverImage);
            discoverClaims = (TextView) view.findViewById(R.id.discoverClaimsTxt);
            discoverDuration  = (TextView) view.findViewById(R.id.discoverDeadlineTxt);
            discoverNormalPassTypeTxt = (TextView) view.findViewById(R.id.discoverNormalPassTypeTxt);
            // ======

            discoverLockedTitle = (TextView) view.findViewById(R.id.discoverLockedTitleTxt);
            discoverLockedDescription = (TextView) view.findViewById(R.id.discoverLockedDescTxt);
            discoverLockedIcon = (ImageView) view.findViewById(R.id.discoverLockedImage);
            discoverLockedClaims = (TextView) view.findViewById(R.id.discoverLockedClaimsTxt);
            discoverLockedDuration  = (TextView) view.findViewById(R.id.discoverLockedDeadlineTxt);
            discoverLockedTypeTxt = (TextView) view.findViewById(R.id.discoverLockedTypeTxt);
            // ======

            discoverChallengeIcon = (ImageView) view.findViewById(R.id.discoverChallengeImage);
            discoverChallengeTitle = (TextView) view.findViewById(R.id.discoverChallengeTitleTxt);
            discoverChallengeTypeTxt = (TextView) view.findViewById(R.id.discoverChallengeTypeTxt);
            // ======

            discoverNewsIcon = (ImageView) view.findViewById(R.id.discoverNewsImage);
            discoverNewsTitle = (TextView) view.findViewById(R.id.discoverNewsTitleTxt);
            discoverNewsTypeTxt = (TextView) view.findViewById(R.id.discoverNewsTypeTxt);

            holderView = view;

        }
    }


    public DiscoverAdapter(Context mContext, List<Pass> discoverList,
                             FragmentInteractionInterface fragmentInteractionInterface) {
        this.mContext = mContext;
        this.discoverList = discoverList;
        this.fragmentInteractionInterface = fragmentInteractionInterface;

    }
    public DiscoverAdapter(Context mContext, List<Pass> discoverList,
                           FragmentInteractionInterface fragmentInteractionInterface,
                           DiscoverFragment currentFragment, PassDetailsFragment destinationFragment) {
        this.mContext = mContext;
        this.discoverList = discoverList;
        this.fragmentInteractionInterface = fragmentInteractionInterface;
        this.discoverFragment = currentFragment;
        this.passDetailsFragment = destinationFragment;

    }
    public DiscoverAdapter(Context mContext, List<Pass> discoverList) {
        this.mContext = mContext;
        this.discoverList = discoverList;
    }

    public void adjustDiscoverList(List<Pass> discoverList)
    {
        this.discoverList = discoverList;
        notifyDataSetChanged();
    }

    @Override
    public DiscoverAdapter.DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discoverfeed_adapter, parent, false);

        return new DiscoverAdapter.DiscoverViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DiscoverAdapter.DiscoverViewHolder holder, int position) {

        holder.discover = discoverList.get(position);

        if(holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[4])
                || holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[5])) {
            holder.normalPassContainer.setVisibility(View.GONE);
            holder.newsContainer.setVisibility(View.VISIBLE);
            holder.challengePassContainer.setVisibility(View.GONE);
            holder.lockedPassContainer.setVisibility(View.GONE);
            holder.discoverNewsTypeTxt.setText(holder.discover.passType);
            holder.discoverNewsTitle.setText(holder.discover.passDescription);
            Glide.with(mContext).load(holder.discover.passImage).apply(RequestOptions.centerCropTransform()).into(holder.discoverNewsIcon);
        }
        else if(holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[3]))
        {
            holder.normalPassContainer.setVisibility(View.GONE);
            holder.newsContainer.setVisibility(View.GONE);
            holder.challengePassContainer.setVisibility(View.VISIBLE);
            holder.lockedPassContainer.setVisibility(View.GONE);
            holder.discoverChallengeTypeTxt.setText(holder.discover.passType);
            holder.discoverChallengeTitle.setText(holder.discover.passDescription);
        }
        else
        {
            if(!holder.discover.isLocked) {
                holder.normalPassContainer.setVisibility(View.VISIBLE);
                holder.newsContainer.setVisibility(View.GONE);
                holder.challengePassContainer.setVisibility(View.GONE);
                holder.lockedPassContainer.setVisibility(View.GONE);
                holder.discoverTitle.setText(holder.discover.passMerchant.merchantName);
                holder.discoverClaims.setText(holder.discover.passClaims);
                holder.discoverDuration.setText(holder.discover.passDuration);
                holder.discoverDescription.setText(holder.discover.passDescription);
                holder.discoverNormalPassTypeTxt.setText(holder.discover.passType);
                Glide.with(mContext).load(holder.discover.passMerchant.merchantImage).into(holder.discoverIcon);
            }
            else
            {
                holder.normalPassContainer.setVisibility(View.GONE);
                holder.newsContainer.setVisibility(View.GONE);
                holder.challengePassContainer.setVisibility(View.GONE);
                holder.lockedPassContainer.setVisibility(View.VISIBLE);
                holder.discoverLockedTitle.setText(holder.discover.passMerchant.merchantName);
                holder.discoverLockedClaims.setText(holder.discover.passClaims);
                holder.discoverLockedDuration.setText(holder.discover.passDuration);
                holder.discoverLockedDescription.setText(holder.discover.passDescription);
                holder.discoverLockedTypeTxt.setText(holder.discover.passType);
                Glide.with(mContext).load(holder.discover.passMerchant.merchantImage).into(holder.discoverLockedIcon);
            }
        }
        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(passDetailsFragment!=null ) {

                    if((holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[4])
                            || holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[5])))
                    {
                        Transaction transaction = new Transaction(fragmentInteractionInterface.dataPassRetrieved().currentJoeUser()
                                ,fragmentInteractionInterface.dataPassRetrieved().randomDate(),holder.discover.passPoints.point,
                                Transaction.TransactionType.TransactionEarningPoints);

                        fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().currentPoints += holder.discover.passPoints.point;
                        fragmentInteractionInterface.dataPassRetrieved().currentJoeUser().earnedPoints += holder.discover.passPoints.point;


                        fragmentInteractionInterface.dataPassRetrieved().addTransaction(transaction);
                        Toast.makeText(mContext,"Congratulations! You earned " + holder.discover.passPoints.point,Toast.LENGTH_LONG).show();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.discover.passLink));
                        discoverFragment.getActivity().startActivity(browserIntent);
                    }
                    else if(holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[2]))
                    {
                        passDetailsFragment.selectedPass = holder.discover;
                        passDetailsFragment.fromDiscover = true;
                        fragmentInteractionInterface.onSwitchFragment(discoverFragment, passDetailsFragment);
                    }
                    else if(holder.discover.passType.equals(fragmentInteractionInterface.dataPassRetrieved().passType[3]))
                    {
                        ChallengeDetailsFragment challengeDetailsFragment = ChallengeDetailsFragment.newInstance();
                        challengeDetailsFragment.selectedPass = holder.discover;
                        fragmentInteractionInterface.onSwitchFragment(discoverFragment, challengeDetailsFragment);

                    }
                    else
                    {
                        passDetailsFragment.selectedPass = holder.discover;
                        passDetailsFragment.fromDiscover = true;
                        fragmentInteractionInterface.onSwitchFragment(discoverFragment, passDetailsFragment);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return discoverList.size();
    }

    	
}
