package com.example.uas_travel_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "TravelApp.db";

    public DatabaseHelper(Context context) {
        super(context, "TravelApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // create user table
        sqLiteDatabase.execSQL("CREATE TABLE Users(UserID INTEGER PRIMARY KEY, UserName TEXT , UserPass TEXT, UserEmail TEXT, UserRegion TEXT, UserPhoneNum TEXT)");

        // create destination table
        sqLiteDatabase.execSQL("CREATE TABLE Destination(DestinationID INTEGER PRIMARY KEY, DestinationName TEXT, DestinationPrice TEXT, DestinationImage TEXT, DestinationImageAlt TEXT, DestinationRating REAL, DestinationDescription TEXT)");

        // create list table
        sqLiteDatabase.execSQL("CREATE TABLE TravelList(TravelListID INTEGER PRIMARY KEY, UserID INTEGER, DestinationID INTEGER, TravelListNote TEXT, FOREIGN KEY(UserID) REFERENCES Users(UserID), FOREIGN KEY(DestinationID) REFERENCES Destination(DestinationID))");

        initData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TravelList");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Destination");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(sqLiteDatabase);
    }

    public void initData(SQLiteDatabase sqLiteDatabase){
        // data user inisial
        sqLiteDatabase.execSQL("INSERT INTO Users(UserName, UserPass, UserEmail, UserRegion, UserPhoneNum) VALUES('admin','12345','admin@gmail.com','Jakarta','08123456789')");
//        sqLiteDatabase.execSQL("INSERT INTO Users(UserName, UserPass, UserEmail, UserRegion, UserPhoneNum) VALUES('user1','12345','user1@gmail.com','Bandung','08987654321')");

    }

    public boolean insertUser(String UserName, String UserPass, String UserEmail, String UserRegion, String UserPhoneNum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", UserName);
        contentValues.put("UserPass", UserPass);
        contentValues.put("UserEmail", UserEmail);
        contentValues.put("UserRegion", UserRegion);
        contentValues.put("UserPhoneNum",UserPhoneNum);
        long res = db.insert("Users", null, contentValues);

        // fail to insert
        if(res == -1){
            return false;
        } else {
            // success
            return true;
        }
    }

    public boolean checkUserNameUnique(String UserName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE UserName = ?", new String[] {UserName});
        if(cursor.getCount() > 0){
            // username tidak unik
            return false;
        } else {
            // username unik
            return true;
        }
    }

    // check if uname and pass match
    public boolean checkLoginMatch(String UserName, String UserPass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE UserName =? AND UserPass = ?", new String[] {UserName, UserPass});
        if(cursor.getCount() > 0){
            // name & pass match
            return true;
        } else {
            // name & pass tidak match
            return false;
        }
    }


}
