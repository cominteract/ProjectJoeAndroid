package com.andre.projjoe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.andre.projjoe.R;
import com.andre.projjoe.adapters.ActivitiesTransactionAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.JoeUser;

import org.w3c.dom.Text;


public class JoeUserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private FragmentInteractionInterface fragmentInteractionInterface;
    private DataPasses dataPasses;
    public JoeUser selectedJoeUser;
    View view;

    // TODO: Rename and change types and number of parameters
    public static JoeUserProfileFragment newInstance() {
        JoeUserProfileFragment fragment = new JoeUserProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joe_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.view = view;

        if(selectedJoeUser!=null) {
            Button joeUserProfileGiveBtn =  view.findViewById(R.id.joeUserProfileGiveBtn);
            joeUserProfileGiveBtn.setVisibility(View.VISIBLE);
            joeUserProfileGiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), " Give to " + selectedJoeUser.fullName, Toast.LENGTH_LONG).show();
                }
            });
            setupTransactionList(selectedJoeUser);
        }
        else
            setupTransactionList(dataPasses.currentJoeUser());


    }

    public void setupTransactionList(JoeUser joeUser)
    {
        ActivitiesTransactionAdapter activitiesTransactionAdapter = new ActivitiesTransactionAdapter(getActivity()
                ,joeUser.transactionList,fragmentInteractionInterface);
        RecyclerView joeUserProfileRecyclerView = (RecyclerView)view.findViewById(R.id.joeUserProfileRecyclerView);
        joeUserProfileRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        joeUserProfileRecyclerView.setAdapter(activitiesTransactionAdapter);

        TextView joeUserProfileFullNameTxt = view.findViewById(R.id.joeUserProfileFullNameTxt);
        joeUserProfileFullNameTxt.setText(joeUser.fullName);
        TextView joeUserProfileUserNameTxt = view.findViewById(R.id.joeUserProfileUserNameTxt);
        joeUserProfileUserNameTxt.setText(joeUser.userName);
        ImageView joeUserProfileImage = view.findViewById(R.id.joeUserProfileImage);
        Glide.with(getActivity()).load(joeUser.image).into(joeUserProfileImage);

        TextView joeUserProfileBirthdayTxt = view.findViewById(R.id.joeUserProfileBirthdayTxt);
        joeUserProfileBirthdayTxt.setText(joeUserProfileBirthdayTxt.getText().toString() + joeUser.birthday);
        TextView joeUserProfileContactTxt = view.findViewById(R.id.joeUserProfileContactTxt);
        joeUserProfileContactTxt.setText(joeUserProfileContactTxt.getText().toString() + joeUser.phone);
        TextView joeUserProfileEmailTxt = view.findViewById(R.id.joeUserProfileEmailTxt);
        joeUserProfileEmailTxt.setText(joeUserProfileEmailTxt.getText().toString() + joeUser.email);
        TextView joeUserProfileFacebookTxt = view.findViewById(R.id.joeUserProfileFacebookTxt);
        joeUserProfileFacebookTxt.setText(joeUserProfileFacebookTxt.getText().toString() + joeUser.facebook);
        TextView joeUserProfileLocationTxt = view.findViewById(R.id.joeUserProfileLocationTxt);
        joeUserProfileLocationTxt.setText(joeUserProfileLocationTxt.getText().toString() + joeUser.googleMapLocation.getLocationName());
        TextView joeUserProfilePointsTxt = view.findViewById(R.id.joeUserProfilePointsTxt);
        joeUserProfilePointsTxt.setText(joeUser.currentPoints + " \n" + joeUserProfilePointsTxt.getText().toString() );
        TextView joeUserProfilePassesTxt = view.findViewById(R.id.joeUserProfilePassesTxt);
        joeUserProfilePassesTxt.setText(joeUser.passList.size() + " \n" + joeUserProfilePassesTxt.getText().toString());
        TextView joeUserProfileFriendsTxt = view.findViewById(R.id.joeUserProfileFriendsTxt);
        joeUserProfileFriendsTxt.setText(joeUser.friendList.size() + " \n" + joeUserProfileFriendsTxt.getText().toString());
        TextView joeUserProfileTransactionsTxt = view.findViewById(R.id.joeUserProfileTransactionsTxt);
        joeUserProfileTransactionsTxt.setText(joeUser.transactionList.size() + " \n" + joeUserProfileTransactionsTxt.getText().toString());
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
