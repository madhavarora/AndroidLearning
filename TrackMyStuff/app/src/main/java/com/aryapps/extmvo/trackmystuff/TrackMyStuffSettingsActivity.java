package com.aryapps.extmvo.trackmystuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TrackMyStuffSettingsActivity extends TrackMyStuffSuperActivity implements  TrackMyStuffConstants{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		TextView yourName = (TextView) findViewById(R.id.TextView_YourName);
		TextView supEmail = (TextView) findViewById(R.id.TextView_targetEmail);

		SharedPreferences settings = getSharedPreferences(SHIFT_PREFERENCE,
				MODE_PRIVATE);
		String configuredName = settings.getString(SHIFT_PREFERENCE_USERNAME,
				"");
		String configuredEmail = settings.getString(SHIFT_PREFERENCE_EMAIL, "");

		if (null != configuredName && !configuredName.equals("")) {
			yourName.setText("Configured Name is  " + configuredName);

		}

		if (null != configuredEmail && !configuredEmail.equals("")) {
			supEmail.setText("Configured Email is  " + configuredEmail);

		}

	}

	/**
	 * 
	 * @param view
	 */
	public void onSaveClicked(View view) {

        if(LOG)
            Log.d("TrackMyStuffSettingsActivity", "onSaveClicked STARTS");

		EditText yourName = (EditText) findViewById(R.id.EditText_YourName);
		EditText recpEmail = (EditText) findViewById(R.id.EditText_targetEmail);

        if(LOG)
            Log.d("TrackMyStuffSettingsActivity", "yourName : " + yourName + "recpEmail : " + recpEmail);

		SharedPreferences settings = getSharedPreferences(SHIFT_PREFERENCE,
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();
		if(yourName.getText().toString()==null || yourName.getText().toString().equals("") || recpEmail.getText().toString()==null || recpEmail.getText().toString().equals(""))
        {
            Toast.makeText(
                    TrackMyStuffSettingsActivity.this,
                    EMAIL_USER_NAME_MANDATORY,
                    Toast.LENGTH_LONG).show();
        }else{

            prefEditor.putString(SHIFT_PREFERENCE_USERNAME, yourName.getText()
                    .toString());
            prefEditor.putString(SHIFT_PREFERENCE_EMAIL, recpEmail.getText()
                    .toString());

            boolean result = prefEditor.commit();

            if(LOG)
                Log.d("TrackMyStuffSettingsActivity", "result:" + result);

            if(result){
                Toast.makeText(
                        TrackMyStuffSettingsActivity.this,
                        EMAIL_DATA_SAVED_SUCCESS,
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(
                        TrackMyStuffSettingsActivity.this,
                        EMAIL_DATA_SAVED_FAIL,
                        Toast.LENGTH_LONG).show();
            }


            Intent intent = new Intent(view.getContext(),
                    TrackMyStuffMenuActivity.class);
            startActivity(intent);

        }




	}
}
