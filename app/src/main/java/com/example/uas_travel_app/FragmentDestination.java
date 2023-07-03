package com.example.uas_travel_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;


public class FragmentDestination extends Fragment {

    private Vector<Destination> destinationList;

    public FragmentDestination() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        RecyclerView destRecycler = view.findViewById(R.id.destination_rv);

        DestinationHelper destinationHelper = new DestinationHelper(getContext());
        destinationHelper.open();

        destinationList = new Vector<>();
        destinationList.addAll(destinationHelper.viewDestination());

        destinationHelper.close();


        DestinationAdapter destinationAdapter = new DestinationAdapter(getContext());
        destinationAdapter.setDestList(destinationList);

        destRecycler.setAdapter(destinationAdapter);
        destRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));



        return view;
    }
}