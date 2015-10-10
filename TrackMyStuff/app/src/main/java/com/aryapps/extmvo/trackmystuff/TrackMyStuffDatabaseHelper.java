/***
 Copyright (c) 2008-2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Android Development_
 http://commonsware.com/Android
 */

package com.aryapps.extmvo.trackmystuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackMyStuffDatabaseHelper extends SQLiteOpenHelper implements TrackMyStuffConstants {
    private static final String DATABASE_NAME = "trackInfo";
    static final String ART_NAME = "articleName";
   // static final String IMAGE = "image";
    static final String LOCATION = "location";
    static final String ROOM = "room";
    static final String CONTAINER = "container";
    static final String IMAGE_BYTE_ARRAY = "imageByteArray";
    static final String DATE_STRING="date";

    public TrackMyStuffDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      if(LOG)
          Log.d("TrackMyStuffDatabaseHelper", "onCreate STARTS : creating trackInfo" );

        db.execSQL("CREATE TABLE trackInfo (articleName TEXT PRIMARY KEY, image TEXT, location TEXT, room TEXT, container TEXT, imageByteArray BLOB, date TEXT);");

        if(LOG)
            Log.d("TrackMyStuffDatabaseHelper", "onCreate ENDS : creating trackInfo" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(LOG){
            Log.d("TrackMyStuffDatabaseHelper ","onUpgrade STARTS, db : " + db + "oldVersion : "  + oldVersion + "newVersion : " + newVersion);

            Log.w("TrackMyStuffDatabaseHelper",
                    "Upgrading database, which will destroy all old data");

        }

        db.execSQL("DROP TABLE IF EXISTS trackInfo");
        Log.d("TrackMyStuffDatabaseHelper ","onUpgrade ENDS");

        onCreate(db);
    }



    public long addTrackInfo(TrackInfoBean trackInfo) {

        if (LOG)
            Log.d("TrackMyStuffDatabaseHelper", "addTrackInfo STARTS " + trackInfo);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (null != trackInfo) {

            values.put(ART_NAME, trackInfo.getArticleName());
            // values.put(IMAGE, trackInfo.getImage());
            values.put(LOCATION, trackInfo.getLocation());
            values.put(ROOM, trackInfo.getRoom());
            values.put(CONTAINER, trackInfo.getContainer());
            values.put(IMAGE_BYTE_ARRAY, trackInfo.getImageByteArray());
            values.put(DATE_STRING, trackInfo.getDateString());
        }

        // Inserting Row
        long dbResp = db.insert(DATABASE_NAME, null, values);
        db.close(); // Closing database connection

        if (LOG)
            Log.d("TrackMyStuffDatabaseHelper", "addTrackInfo ENDS " + dbResp);

        return dbResp;
    }


    // update track info
    public int updateTrackInfo(TrackInfoBean trackInfo) {

        if (LOG)
            Log.d("TrackMyStuffDatabaseHelper", "updateTrackInfo STARTS " + trackInfo);

        int dbResp = 0;
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        if (null != trackInfo) {
            values.put(ART_NAME, trackInfo.getArticleName());
            //values.put(IMAGE, trackInfo.getImage());
            values.put(LOCATION, trackInfo.getLocation());
            values.put(ROOM, trackInfo.getRoom());
            values.put(CONTAINER, trackInfo.getContainer());
            values.put(IMAGE_BYTE_ARRAY, trackInfo.getImageByteArray());
            values.put(DATE_STRING, trackInfo.getDateString());
        }


        String whereArgs[] = new String[]{String.valueOf(trackInfo.getDateString())};
        String where = DATE_QUERY;


        db.beginTransaction();
        // deleteTrackInfoByArticleNameAndContainer(trackInfo.getArticleName(), )
        try {
            dbResp = db.update(DATABASE_NAME, values, where, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close(); // Closing database connection
        }

        if (LOG)
            Log.d("TrackMyStuffDatabaseHelper", "updateTrackInfo ENDS " + dbResp);


        return dbResp;
    }


     public List<TrackInfoBean> getAllTrackInfo() {

         if (LOG)
             Log.d("TrackMyStuffDatabaseHelper", "getAllTrackInfo STARTS ");


         List<TrackInfoBean> trackInfoList = new ArrayList<TrackInfoBean>();
        // Select All Query
        String selectQuery =SELECT_QUERY + DATABASE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TrackInfoBean trackInfoBean = new TrackInfoBean();
                DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date date;

                trackInfoBean.setArticleName(cursor.getString(0));


                //trackInfoBean.setImage(cursor.getString(1));
                trackInfoBean.setLocation(cursor.getString(2));
                trackInfoBean.setRoom(cursor.getString(3));
                trackInfoBean.setContainer(cursor.getString(4));
                trackInfoBean.setImageByteArray(cursor.getBlob(5));
                trackInfoBean.setDateString(cursor.getString(6));


                // Adding contact to list
                trackInfoList.add(trackInfoBean);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list

         if (LOG)
             Log.d("TrackMyStuffDatabaseHelper", "getAllTrackInfo ENDS, trackInfoList :: " + trackInfoList );

         return trackInfoList;
    }


   /**
     * This method fetches the data provided a date , it is used to change the data already entered.
     *
     * @param articleName
     * @param container
     * @return
     */
    public TrackInfoBean getAllTrackInfoByArticleName(String articleName, String container) {

        if (LOG)
            Log.d("TrackMyStuffDatabaseHelper", "getAllTrackInfoByArticleName STARTS, articleName : " + articleName + "container : " + container);

        String monthQuery = "SELECT  * FROM " + DATABASE_NAME
                + " where articleName= " + "'" + articleName + "'" + "and  container= " + "'" + container + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(monthQuery, null);
        TrackInfoBean trackInfoBean = new TrackInfoBean();

        if (cursor.moveToFirst()) {
            do {
                trackInfoBean.setArticleName(cursor.getString(0));
                //trackInfoBean.setImage(cursor.getString(1));
                trackInfoBean.setLocation(cursor.getString(2));
                trackInfoBean.setRoom(cursor.getString(3));
                trackInfoBean.setContainer(cursor.getString(4));
                trackInfoBean.setImageByteArray(cursor.getBlob(5));
                trackInfoBean.setDateString(cursor.getString(6));


            } while (cursor.moveToNext());
        }
        db.close();

        if (LOG) {

            if (null != trackInfoBean)
                Log.d("TrackMyStuffDatabaseHelper", "getAllTrackInfoByArticleName ENDS, trackInfoBean : " + trackInfoBean.toString(trackInfoBean));
            else
                Log.d("TrackMyStuffDatabaseHelper", "TrackInfoBean is Null ");
        }


        return trackInfoBean;
    }

   public boolean deleteTrackInfoByArticleNameAndContainer(String articleName, String container) {

       if (LOG)
           Log.d("TrackMyStuffDatabaseHelper", "deleteTrackInfoByArticleNameAndContainer STARTS, articleName : " + articleName + "container : " + container);


       String deleteQuery = "Delete from " + DATABASE_NAME + " where articleName= " + "'" + articleName + "'" + "and  container= " + "'" + container + "'";
        boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(deleteQuery);

        } catch (SQLException e) {

            if(LOG){

                Log.e("Error deleting record", e.toString());
            }


        } finally {
            db.close();
        }

       if (LOG)
           Log.d("TrackMyStuffDatabaseHelper", "deleteTrackInfoByArticleNameAndContainer ENDS, articleName : " + articleName + "container : " + container);

       return success;
    }


}