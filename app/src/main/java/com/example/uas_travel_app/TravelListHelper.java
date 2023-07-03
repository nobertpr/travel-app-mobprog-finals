package com.example.uas_travel_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class TravelListHelper {
    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    public TravelListHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException{
        dbHelper.close();
    }

    public Vector<TravelList> viewTravelList(String username) {
        Vector<TravelList> travelList = new Vector<>();

        Cursor cursor = db.rawQuery("SELECT Users.UserName AS UserName, Destination.DestinationName AS DestinationName, Destination.DestinationImage AS DestinationImage, Destination.DestinationImageAlt AS DestinationImageAlt, TravelList.TravelListNote AS TravelListNote, TravelList.TravelListID AS TravelListID FROM TravelList JOIN Users ON TravelList.UserID = Users.UserID JOIN Destination ON TravelList.DestinationID = Destination.DestinationID WHERE UserName = '" + username + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            do {
                String tempUserName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"));
                String tempDestName = cursor.getString(cursor.getColumnIndexOrThrow("DestinationName"));
                String tempDestImage = cursor.getString(cursor.getColumnIndexOrThrow("DestinationImage"));
                String tempDestImageAlt = cursor.getString(cursor.getColumnIndexOrThrow("DestinationImageAlt"));
                String tempTlComment = cursor.getString(cursor.getColumnIndexOrThrow("TravelListNote"));
                int tempTlID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("TravelListID")));

                TravelList tl = new TravelList(tempUserName,tempDestName,tempDestImage, tempDestImageAlt,tempTlComment,tempTlID);

                travelList.add(tl);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();

        return travelList;
    }

    // update travel list
    public void updateTravelList(int tlID, String comment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TravelListNote",comment);

        db.update("TravelList", contentValues,"TravelListID = ?", new String[] {Integer.toString(tlID)});
    }


    // delete travel list based on id
    public void deleteTravelList(int tlID) {
        db.delete("TravelList","TravelListID = ?", new String[] {Integer.toString(tlID)});
    }

    // add travel list details
    public void addTravelList(String note, int userID, int destinationID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DestinationID",destinationID);
        contentValues.put("UserID",userID);
        contentValues.put("TravelListNote",note);
        db.insert("TravelList",null,contentValues);
    }

    public boolean isDestinationExistInList(int UserID, int DestinationID) {
//        Cursor cursor = db.rawQuery("SELECT Destination.DestinationImage AS DestinationImage FROM Destination JOIN TravelList ON Destination.DestinationID = TravelList.DestinationID WHERE TravelList.TravelListID = " + String.valueOf(TravelListID) + " AND TravelList.UserID = " + String.valueOf(UserID), null);
        Cursor cursor = db.rawQuery("SELECT Users.UserName AS UserName, Destination.DestinationName AS DestinationName, Destination.DestinationImage AS DestinationImage, TravelList.TravelListNote AS TravelListNote, TravelList.TravelListID AS TravelListID FROM TravelList JOIN Users ON TravelList.UserID = Users.UserID JOIN Destination ON TravelList.DestinationID = Destination.DestinationID WHERE TravelList.UserID = " + Integer.toString(UserID) + " AND TravelList.DestinationID = " + Integer.toString(DestinationID) , null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}
