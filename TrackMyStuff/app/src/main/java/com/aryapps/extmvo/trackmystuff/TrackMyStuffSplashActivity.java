package com.aryapps.extmvo.trackmystuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

/**
 * Created by maro12 on 8/15/2015.
 */
public class TrackMyStuffSplashActivity extends TrackMyStuffSuperActivity implements TrackMyStuffConstants{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.splash_layout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                finish();

                SharedPreferences settings = getSharedPreferences(SHIFT_PREFERENCE,
                        MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putInt(SCREEN_HEIGHT,relativeLayout.getMeasuredHeight() );
                prefEditor.putInt(SCREEN_WIDTH,relativeLayout.getMeasuredWidth() );
                prefEditor.commit();

                Intent menu = new Intent(getBaseContext(), TrackMyStuffMenuActivity.class);
                /*menu.putExtra("width", relativeLayout.getMeasuredWidth());
                menu.putExtra("height", relativeLayout.getMeasuredHeight());*/
                startActivity(menu);
            }
        }, 3000);
    }


}
