package com.aryapps.extmvo.trackmystuff;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

public class TrackMyStuffSummaryDetailActivity extends TrackMyStuffSuperActivity implements TrackMyStuffConstants {

    private DatePicker dp;
    private String oldDate;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            String articleName = extras.getString("articleName");
            String container = extras.getString("container");


            TrackMyStuffDatabaseHelper db = new TrackMyStuffDatabaseHelper(
                    TrackMyStuffSummaryDetailActivity.this);
            // dp = (DatePicker) findViewById(R.id.datePicker1);


            TrackInfoBean trackInfoBean;
            trackInfoBean = db.getAllTrackInfoByArticleName(articleName, container);

            // this.setTrackInfo(trackInfoBean);

            EditText articleNameTxt = (EditText) findViewById(R.id.EditText_articleName);
            articleNameTxt.setText(trackInfoBean.getArticleName());

			/*EditText articleImage = (EditText) findViewById(R.id.EditText_articleImage);
            articleImage.setText(trackInfoBean.getImage());*/

            EditText location = (EditText) findViewById(R.id.EditText_location);
            location.setText(trackInfoBean.getLocation());

            EditText room = (EditText) findViewById(R.id.EditText_room);
            room.setText(trackInfoBean.getRoom());

            EditText containerTxt = (EditText) findViewById(R.id.EditText_container);
            containerTxt.setText(trackInfoBean.getContainer());

            String oldDate = trackInfoBean.getDateString();

            //Fetch the bye array
            byte[] articleImageByteArray = trackInfoBean.getImageByteArray();
            //Convert Image byte array to bitmap image
            if (articleImageByteArray.length > 0) {
                Bitmap articleImageBitmap = convertByteArrayToBitmap(articleImageByteArray);
                //Render the bitmap to the image view
                ImageView articleImageView = (ImageView) findViewById(R.id.ImageView_articleImage);
                articleImageView.setImageBitmap(articleImageBitmap);
            }

            //sending the old date, becuase we may have to update the record.
            //this.persistEditedShiftInfo();

            //save  button functionality
            super.saveTrackInfoData(ACTION_EDIT, oldDate);

            //Take Image functionality
            super.takeImageButton(REQUEST_IMAGE_CAPTURE);

            //Image View Popup functionality
            super.handleImageViewPopup();

        }
    }


    public Bitmap convertByteArrayToBitmap(byte[] imageByteArray) {

        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }

}
