package com.aryapps.extmvo.trackmystuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class TrackMyStuffMenuActivity extends TrackMyStuffSuperActivity implements TrackMyStuffConstants {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        int width = 0;
        int height = 0;

        SharedPreferences settings = getSharedPreferences(SHIFT_PREFERENCE,
                MODE_PRIVATE);
        height= settings.getInt(SCREEN_HEIGHT, 0);
        width= settings.getInt(SCREEN_WIDTH, 0);


        ListView menuList = (ListView) findViewById(R.id.ListView_Menu);


        String[] items = {
                getResources().getString(R.string.addTrackInfo),
                getResources().getString(R.string.summary),
                getResources().getString(R.string.settings),
                getResources().getString(R.string.help),

        };

        List<String> ItemsList = Arrays.asList(items);
        TrackMyStuffMenuCustomAdapter adapter = new TrackMyStuffMenuCustomAdapter(this, ItemsList, height, null);

        // Assign adapter to ListView
        menuList.setAdapter(adapter);
        menuList.setMinimumHeight(height);


        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {
                // TODO Auto-generated method stub

                LinearLayout linearLayout = (LinearLayout) itemClicked;
                TextView textView = (TextView) linearLayout.findViewById(R.id.textview_menu_item);
                String strText = textView.getText().toString();
                if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.addTrackInfo))) {

                    startActivity(new Intent(TrackMyStuffMenuActivity.this,
                            TrackMyStuffAddActivity.class));

                } else if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.summary))) {

                    startActivity(new Intent(TrackMyStuffMenuActivity.this, TrackMyStuffSummaryActivity.class));

                } else if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.settings))) {
                    startActivity(new Intent(TrackMyStuffMenuActivity.this, TrackMyStuffSettingsActivity.class));
                } else if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.help))) {

                    Intent menu = new Intent(getBaseContext(), TrackMyStuffMenuActivity.class);
                    startActivity(new Intent(TrackMyStuffMenuActivity.this, TrackMyStuffHelpActivity.class));

                }


            }
        });

    }

   /* public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public int getTitleBarHeight() {
        int viewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return (viewTop - getStatusBarHeight());
    }*/

}
