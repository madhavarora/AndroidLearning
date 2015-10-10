package com.aryapps.extmvo.trackmystuff;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by maro12 on 6/10/2015.
 */
public class TrackMyStuffEmailHelper extends TrackMyStuffSuperActivity implements TrackMyStuffConstants {


    public void sendEmailWithAttachment(Context ctx, String to, String subject, String message, String fileAndLocation) {

        if (LOG)
            Log.d("TrackMyStuffEmailHelper", "sendEmailWithAttachment STARTS :" + "ctx : " + ctx + "to : " + to + "subject : " + subject + "message :" + message + "fileAndLocatoin : " + fileAndLocation);


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/excel");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);


        File file = new File(fileAndLocation);
        //  File file = getFileStreamPath();
        if (file.exists()) {

            Log.v("TrackMyStuffEmailHelper :: ", "Email file_exists!");

        } else {

            Log.v("TrackMyStuffEmailHelper ::", "Email file does not exist!");


        }


        Log.v("TrackMyStuffEmailHelper", "SEND EMAIL FileUri=" + Uri.parse("file://" + fileAndLocation));

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + fileAndLocation));

        ctx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        if (LOG)
            Log.d("TrackMyStuffEmailHelper", "sendEmailWithAttachment ENDS :" );

    }//end method

}
