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
import com.andre.projjoe.fragments.DiscoverFragment;
import com.andre.projjoe.fragments.PassDetailsFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;

import java.util.List;

/**
 * Created by Dre on 3/3/2018.
 */

public class ActivitiesTransactionAdapter extends RecyclerView.Adapter<ActivitiesTransactionAdapter.TransactionViewHolder> {

    private Context mContext;
    private List<Transaction> transactionList;
    private FragmentInteractionInterface fragmentInteractionInterface;

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView activitiesTransactionTxt,activitiesTransactionDateTxt;
        public ImageView activitiesTransactionImage;
        public View holderView;
        public Transaction transaction;
        public TransactionViewHolder(View view) {
            super(view);
            activitiesTransactionTxt = (TextView) view.findViewById(R.id.activitiesTransactionTxt);
            activitiesTransactionDateTxt = (TextView) view.findViewById(R.id.activitiesTransactionDateTxt);
            activitiesTransactionImage = (ImageView) view.findViewById(R.id.activitiesTransactionImage);


            holderView = view;

        }
    }


    public ActivitiesTransactionAdapter(Context mContext, List<Transaction> transactionList,
                           FragmentInteractionInterface fragmentInteractionInterface) {
        this.mContext = mContext;
        this.transactionList = transactionList;
        this.fragmentInteractionInterface = fragmentInteractionInterface;

    }

    public void adjustTransactionList(List<Transaction> transactionList)
    {
        this.transactionList = transactionList;
        notifyDataSetChanged();
    }

    @Override
    public ActivitiesTransactionAdapter.TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activitiestransaction_adapter, parent, false);

        return new ActivitiesTransactionAdapter.TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ActivitiesTransactionAdapter.TransactionViewHolder holder, int position) {
        holder.transaction = transactionList.get(position);
        holder.activitiesTransactionTxt.setText(holder.transaction.transactionDetails);
        holder.activitiesTransactionDateTxt.setText(holder.transaction.transactionDate);

        // loading album cover using Glide library
        Glide.with(mContext).load(holder.transaction.transactionImage).into(holder.activitiesTransactionImage);

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }


}