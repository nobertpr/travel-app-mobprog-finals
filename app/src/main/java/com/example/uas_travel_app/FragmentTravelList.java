package com.example.uas_travel_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;


public class FragmentTravelList extends Fragment {



    private Vector<TravelList> tvList;
    private RecyclerView tlRecycler;
    private TravelListAdapter tlAdapter;


    private TravelListHelper tlHelper;

    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel_list, container, false);

        sp = getContext().getSharedPreferences("LoggedInSession", Context.MODE_PRIVATE);
//        int userID = sp.getInt("userID", 0);
        String uname = sp.getString("username","");
//        Log.d("USERTAG", "Value " + Integer.toString(userID));
//        Log.d("USERNAME", "Value " + uname);

        tlHelper = new TravelListHelper(getContext());

        tlRecycler = view.findViewById((R.id.tl_rv));

        TravelListHelper tlHelper = new TravelListHelper(getContext());
        tlHelper.open();

        tvList = new Vector<>();
        tvList = tlHelper.viewTravelList(uname);

        tlHelper.close();

        tlAdapter = new TravelListAdapter(getContext());
        tlAdapter.setTravelList(tvList);


        tlRecycler.setAdapter(tlAdapter);

        tlRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        tlHelper.open();
        tvList.clear();

        tvList.addAll(tlHelper.viewTravelList(sp.getString("username","")));
        tlHelper.close();
        tlAdapter.notifyDataSetChanged();
    }
}