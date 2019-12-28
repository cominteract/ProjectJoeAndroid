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

import com.andre.projjoe.R;
import com.andre.projjoe.adapters.ActivitiesTransactionAdapter;
import com.andre.projjoe.adapters.FriendsAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;

public class JoeUserFriendsFragment extends Fragment {
    DataPasses dataPasses;
    FragmentInteractionInterface fragmentInteractionInterface;
    public JoeUserFriendsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static JoeUserFriendsFragment newInstance() {
        JoeUserFriendsFragment fragment = new JoeUserFriendsFragment();

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
        return inflater.inflate(R.layout.fragment_joe_user_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView joeUserFriendsRecyclerView = view.findViewById(R.id.joeUserFriendsRecyclerView);
        joeUserFriendsRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(),dataPasses.currentJoeUser().
                friendList,
                fragmentInteractionInterface, JoeUserFriendsFragment.this);
        joeUserFriendsRecyclerView.setAdapter(friendsAdapter);
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
