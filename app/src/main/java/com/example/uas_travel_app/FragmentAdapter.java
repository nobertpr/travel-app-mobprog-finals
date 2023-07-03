package com.example.uas_travel_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            // Destination section
            case 0:
                return new FragmentDestination();
            // Travel List section
            case 1:
                return new FragmentTravelList();
            // Profile section
            case 2:
                return new FragmentProfile();
        }
        // default pos is destination section
        return new FragmentDestination();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
