package com.aryapps.extmvo.trackmystuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.poi.util.StringUtil;

import java.util.List;

public class TrackMyStuffSummaryActivity extends TrackMyStuffSuperActivity implements TrackMyStuffConstants {


    List<TrackInfoBean> trackInfo;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		
		ListView listView = (ListView) findViewById(R.id.mylist);

        TrackMyStuffDatabaseHelper db = new TrackMyStuffDatabaseHelper(TrackMyStuffSummaryActivity.this);

        List<TrackInfoBean> trackInfoBeanList = db
                .getAllTrackInfo();


        this.setTrackInfo(trackInfoBeanList);
        // First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

        TrackMyStuffSummaryCustomAdapter adapter = new TrackMyStuffSummaryCustomAdapter(this,trackInfoBeanList, db);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(LOG){
                    Log.d("rowData",  position + " " + id);
                }

				Toast.makeText(TrackMyStuffSummaryActivity.this ," " +  position, Toast.LENGTH_LONG).show();

                TrackInfoBean entry = (TrackInfoBean)parent.getAdapter().getItem(position);
                //Toast.makeText(TrackMyStuffSummaryActivity.this ," " +  entry.getArticleName(), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(view.getContext(),TrackMyStuffSummaryDetailActivity.class);
				intent.putExtra(ARTICLE_NAME, entry.getArticleName());
                intent.putExtra(CONTAINER, entry.getContainer());
				startActivity(intent);
				
			}
		});


	}

    /**
     * This method will send all the Track Info Data to the recipient.
     * @param view
     */
    public void onSendEmail(View view) {
        if (LOG)
            Log.d("TrackMyStuffSummaryActivity ", "onSendEmail START : ");

        // Log.v("reachedSendingEmail", "yuppie reached!!");

        TrackMyStuffWriteExcelFile poiExcel = new TrackMyStuffWriteExcelFile();
        poiExcel.writeExcel(trackInfo);

        SharedPreferences settings = getSharedPreferences(SHIFT_PREFERENCE,
                MODE_PRIVATE);
        // String yourname = settings.getString(SHIFT_PREFERENCE_USERNAME,"" );
        String email = settings.getString(SHIFT_PREFERENCE_EMAIL, "");

        if (email == null || email.equals("")) {

            if (LOG)
                Log.d("TrackMyStuffSummaryActivity ", "Email is null or blank");

            Toast.makeText(TrackMyStuffSummaryActivity.this, TOAST_NO_EMAIL_CONFIGURED, Toast.LENGTH_LONG).show();

        } else {
            TrackMyStuffEmailHelper emailHelper = new TrackMyStuffEmailHelper();
            emailHelper.sendEmailWithAttachment(this, email,
                    EMAIL_SUBJECT, EMAIL_CONTENT,
                    FILE_LOCATION);
        }

        if (LOG)
            Log.d("TrackMyStuffSummaryActivity ", "onSendEmail ENDS : ");

    }


    public List<TrackInfoBean> getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(List<TrackInfoBean> trackInfo) {
        this.trackInfo = trackInfo;
    }
}
