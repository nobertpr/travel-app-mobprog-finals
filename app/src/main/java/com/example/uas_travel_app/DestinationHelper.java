package com.example.uas_travel_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class DestinationHelper {

    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    public DestinationHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException{
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public void close() throws SQLException{
        dbHelper.close();
    }

    public Vector<Destination> viewDestination() {
        Vector<Destination> destList = new Vector<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Destination",null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            do {
                String tempName = cursor.getString(cursor.getColumnIndexOrThrow("DestinationName"));
                String tempPrice = cursor.getString(cursor.getColumnIndexOrThrow("DestinationPrice"));
                String tempImage = cursor.getString(cursor.getColumnIndexOrThrow("DestinationImage"));
                double tempRating = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow("DestinationRating")));
                int tempID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("DestinationID")));

                Destination dest = new Destination(tempName,tempPrice,tempImage,tempRating,tempID);
                destList.add(dest);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }

        cursor.close();

        return destList;
    }

    public Destination getSingleDestination(int destID) {
        Destination dest = new Destination();

        Cursor cursor = db.rawQuery("SELECT * FROM Destination WHERE DestinationID = " + String.valueOf(destID), null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            String tempName = cursor.getString(cursor.getColumnIndexOrThrow("DestinationName"));
            String tempPrice = cursor.getString(cursor.getColumnIndexOrThrow("DestinationPrice"));
            String tempImage = cursor.getString(cursor.getColumnIndexOrThrow("DestinationImage"));
            String tempImageAlt = cursor.getString(cursor.getColumnIndexOrThrow("DestinationImageAlt"));
            Double tempRating = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow("DestinationRating")));
            String tempDescription = cursor.getString(cursor.getColumnIndexOrThrow("DestinationDescription"));

            dest.setName(tempName);
            dest.setPrice(tempPrice);
            dest.setImage(tempImage);
            dest.setImageAlt(tempImageAlt);
            dest.setRating(tempRating);
            dest.setDescription(tempDescription);

            cursor.close();
        }

        return dest;
    }

    public String getDestinationImageAlt_from_TravelListID (int TravelListID) {

        Cursor cursor = db.rawQuery("SELECT Destination.DestinationImageAlt AS DestinationImageAlt FROM Destination JOIN TravelList ON Destination.DestinationID = TravelList.DestinationID WHERE TravelList.TravelListID = " + String.valueOf(TravelListID), null);

        cursor.moveToFirst();
        String tempImage = null;
        if (cursor.getCount() > 0) {
            tempImage = cursor.getString(cursor.getColumnIndexOrThrow("DestinationImageAlt"));
        }

        cursor.close();

        return tempImage;
    }

    public boolean isTableExists(String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[] { tableName });
        boolean tableExists = cursor.getCount() > 0;
        cursor.close();
        return tableExists;
    }

    public boolean isDestinationEmpty(){
        open();
        if(!isTableExists("Destination")){
            close();
            return true;
        } else {
            String query = "Select * from Destination";

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            if(cursor.getCount() > 0){
                cursor.close();
                close();
                return false;
            } else {
                cursor.close();
                close();
                return true;
            }
        }
    }

    public void initDestinationJSON(Vector<Destination> destinationVector){

        for (int i = 0; i < destinationVector.size(); i++){
            String destinationName = destinationVector.get(i).getName();
            Double destinationRating = destinationVector.get(i).getRating();
            String destinationPrice = destinationVector.get(i).getPrice();
            String destinationImage = destinationVector.get(i).getImage();
            String destinationImageAlt = destinationVector.get(i).getImageAlt();
            String destinationDescription = destinationVector.get(i).getDescription();

            ContentValues contentValues = new ContentValues();

            contentValues.put("DestinationName", destinationName);
            contentValues.put("DestinationRating", destinationRating);
            contentValues.put("DestinationPrice", destinationPrice);
            contentValues.put("DestinationImage", destinationImage);
            contentValues.put("DestinationImageAlt", destinationImageAlt);
            contentValues.put("DestinationDescription", destinationDescription);

            db.insert("Destination", null, contentValues);
        }
    }


}
