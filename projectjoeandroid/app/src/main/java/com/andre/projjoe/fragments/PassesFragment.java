package com.andre.projjoe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre.projjoe.R;
import com.andre.projjoe.activities.MainNavigation;
import com.andre.projjoe.adapters.PassesAdapter;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.DataPasses;
import com.andre.projjoe.models.Pass;

import java.util.ArrayList;
import java.util.List;


public class PassesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    private FragmentInteractionInterface fragmentInteractionInterface;
    private DataPasses dataPasses;
    PassesAdapter passesAdapter;

    // TODO: Rename and change types and number of parameters
    public static PassesFragment newInstance() {
        PassesFragment fragment = new PassesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(" On Resume ", " On Resume");
        if(passesAdapter!=null)
            passesAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        Log.d(" On Visible ", " On Visible" );
        if(visible && passesAdapter!=null)
        {
            passesAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_passes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView passesRecyclerview = (RecyclerView) view.findViewById(R.id.passesRecyclerView);



        passesAdapter = new PassesAdapter(getActivity(), dataPasses.getCurrentPassList(), fragmentInteractionInterface,this, PassDetailsFragment.newInstance());
        passesRecyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        passesRecyclerview.setAdapter(passesAdapter);

    }


    public void updateCurrentPasses()
    {
        passesAdapter.notifyDataSetChanged();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onSwitch() {

        if (fragmentInteractionInterface != null) {
            fragmentInteractionInterface.onSwitchFragment(this, PassDetailsFragment.newInstance());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionInterface) {
            fragmentInteractionInterface = (FragmentInteractionInterface) context;
            dataPasses = ((FragmentInteractionInterface) context).dataPassRetrieved();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
