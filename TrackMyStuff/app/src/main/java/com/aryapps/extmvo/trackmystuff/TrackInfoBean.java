package com.aryapps.extmvo.trackmystuff;

import android.util.Log;

import java.util.Date;

/**
 * Created by extmvo on 6/7/2015.
 */
public class TrackInfoBean implements TrackMyStuffConstants {


    private String articleName;
    private String location;
    private String room;
    private String container;
    private byte[] imageByteArray;
    private String dateString;


    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }


    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String toString(TrackInfoBean trackInfoBean) {

        if(null!=trackInfoBean){
            String name = trackInfoBean.getArticleName();
            String container = trackInfoBean.getContainer();
            String location = trackInfoBean.getLocation();
            String room = trackInfoBean.getRoom();
            byte[] byteArray = trackInfoBean.getImageByteArray();

            return "name: " + name + ", container : " + container + ", location : " + location + ", room : " + room + ", byteArray : " + byteArray;

        }

        if(LOG)
            Log.e("TrackInfoBean :", "TrackInfoBean is null, TrackInfoBean : "+ trackInfoBean );

        return null;

    }


}
