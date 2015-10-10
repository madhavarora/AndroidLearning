package com.aryapps.extmvo.trackmystuff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TrackMyStuffAddActivity extends TrackMyStuffSuperActivity implements TrackMyStuffConstants{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        //save  button functionality
        super.saveTrackInfoData(ACTION_ADD, null);


       //Take Image functionality
        super.takeImageButton(REQUEST_IMAGE_CAPTURE);

        //Image View Popup functionality
        super.handleImageViewPopup();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(LOG)
            Log.v("TrackMyStuffAddActivity", "onActivityResult STARTS: "+ "requestCode : " + requestCode + "resultCode : " + resultCode + " data : " +  data );

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView articleImgView = (ImageView) findViewById(R.id.ImageView_articleImage);
            articleImgView.setImageBitmap(imageBitmap);
         //  TrackMyStuffAddActivity.this.getContentResolver().delete(data.getData(), null, null);


        }

        if(LOG)
            Log.v("TrackMyStuffAddActivity", "onActivityResult ENDS: ");



    }


}
