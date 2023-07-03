package com.example.uas_travel_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class ProfileHelper {


    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    public ProfileHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public void close() throws SQLException{
        dbHelper.close();
    }

    public Profile viewProfile(String username) {

        Profile profile = null;

        SharedPreferences sp = context.getSharedPreferences("LoggedInSession",Context.MODE_PRIVATE);
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE UserName = '" + username + "'",null);


        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            String tempName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"));
            String tempEmail = cursor.getString(cursor.getColumnIndexOrThrow("UserEmail"));
            String tempRegion = cursor.getString(cursor.getColumnIndexOrThrow("UserRegion"));
            String tempPhoneNum = cursor.getString(cursor.getColumnIndexOrThrow("UserPhoneNum"));
            int tempID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("UserID")));

            profile = new Profile(tempName,tempEmail,tempRegion,tempPhoneNum, tempID);

            cursor.close();

//            sp.edit().putInt("userID",tempID);
//            sp.edit().apply();

        }
        return profile;
    }




}
