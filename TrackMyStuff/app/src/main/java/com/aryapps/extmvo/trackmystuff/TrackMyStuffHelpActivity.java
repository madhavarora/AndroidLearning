package com.aryapps.extmvo.trackmystuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class TrackMyStuffHelpActivity extends TrackMyStuffSuperActivity  implements TrackMyStuffConstants{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        int width = 0;
        int height = 0;

        SharedPreferences settings = getSharedPreferences(SHIFT_PREFERENCE,
                MODE_PRIVATE);
        height= settings.getInt(SCREEN_HEIGHT, 0);
        width= settings.getInt(SCREEN_WIDTH, 0);

        ListView menuList = (ListView) findViewById(R.id.ListView_Help);

        String[] items = {
                getResources().getString(R.string.addTutorial),
                getResources().getString(R.string.summaryTutorial),
                getResources().getString(R.string.settingTutorial),

        };

        List<String> ItemsList = Arrays.asList(items);
        TrackMyStuffMenuCustomAdapter adapter = new TrackMyStuffMenuCustomAdapter(this, ItemsList, height, HELP_FLOW);

        // Assign adapter to ListView
        menuList.setAdapter(adapter);
        menuList.setMinimumHeight(height);


        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {

                LinearLayout linearLayout = (LinearLayout) itemClicked;
                TextView textView = (TextView) linearLayout.findViewById(R.id.textview_menu_item);
                String strText = textView.getText().toString();
                Intent commonIntent = new Intent(TrackMyStuffHelpActivity.this,  TrackMyStuffHelpTutorialActivity.class);

                if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.addTutorial))) {

                    int[] addImageList = new int[]{R.drawable.image_menu_add, R.drawable.image_add};
                    commonIntent.putExtra("images", addImageList);
                    startActivity(commonIntent);

                } else if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.summaryTutorial))) {

                    int[] summaryImageList = new int[]{R.drawable.image_menu_summary, R.drawable.image_summary, R.drawable.image_summary_detail,R.drawable.image_summary2, R.drawable.image_summary_email, R.drawable.image_summary_email_details};
                    commonIntent.putExtra("images", summaryImageList);

                    startActivity(commonIntent);

                } else if (null != strText
                        && strText.equalsIgnoreCase(getResources().getString(
                        R.string.settingTutorial))) {

                    int[] settingsImageList = new int[]{R.drawable.image_menu_settings, R.drawable.image_settings, R.drawable.image_settings_edit};
                    commonIntent.putExtra("images", settingsImageList);
                    startActivity(commonIntent);
                }


            }
        });

    }

}
