package com.example.uas_travel_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        SharedPreferences sp = getContext().getSharedPreferences("LoggedInSession", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");

        ProfileHelper profileHelper = new ProfileHelper(getContext());

        profileHelper.open();
        Profile profile = profileHelper.viewProfile(username);
        profileHelper.close();

        TextView nameTV = view.findViewById(R.id.profileName);
        TextView emailTV = view.findViewById(R.id.profileEmail);
        TextView regionTV = view.findViewById(R.id.profileRegion);
        TextView phoneNumTV = view.findViewById(R.id.profilePhoneNum);


        nameTV.setText("Username : " + profile.getUsername());
        emailTV.setText("Email : " + profile.getEmail());
        regionTV.setText("Region : " + profile.getRegion());
        phoneNumTV.setText("Phone Number : " + profile.getPhoneNum());

        SwitchMaterial switchBtn = view.findViewById(R.id.modeSwitch);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    compoundButton.setText("Dark Mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    compoundButton.setText("Light Mode");
                }
            }
        });

        boolean isDarkModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        switchBtn.setChecked(isDarkModeOn);
        if (isDarkModeOn){
            switchBtn.setText("Dark Mode");
        } else {
            switchBtn.setText("Light Mode");
        }


        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sp.edit().remove("username");
                                sp.edit().remove("userID");
                                sp.edit().apply();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();


            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}