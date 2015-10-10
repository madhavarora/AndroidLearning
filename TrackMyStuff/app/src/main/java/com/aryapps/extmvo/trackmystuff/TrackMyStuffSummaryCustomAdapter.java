package com.aryapps.extmvo.trackmystuff;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maro12 on 6/9/2015.
 */
public class TrackMyStuffSummaryCustomAdapter extends ArrayAdapter<TrackInfoBean> implements TrackMyStuffConstants {
    private Context context;
    private List<String> strings;
    private List<String> strings2;
    private List<TrackInfoBean> mTrackInfoBeanList;


    private TrackMyStuffDatabaseHelper mDb;

    public TrackMyStuffSummaryCustomAdapter(Context context, List<TrackInfoBean> trackInfoBeanList, TrackMyStuffDatabaseHelper db) {
        super(context, 0, trackInfoBeanList);
        setTrackInfoBeanList(trackInfoBeanList);
        context = context;
        mDb = db;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrackInfoBean trackInfoBean = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }

        TextView article_text_view = (TextView) convertView.findViewById(R.id.listview_articleName);
        TextView container_text_view = (TextView) convertView.findViewById(R.id.listview_container);
        TextView location_text_view = (TextView) convertView.findViewById(R.id.listview_articleLocation);
        TextView room_text_view = (TextView) convertView.findViewById(R.id.listview_articleRoom);

        //get the Delete button reference
        Button btn = (Button) convertView.findViewById(R.id.button_delete);
        btn.setTag(position);

        //set on click listener on the button
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                int index = (Integer) v.getTag();

                if(LOG){
                    Log.v("TrackMyStuffSummaryCustomAdapter", ": " + index);
                }


                //Deletion stuff will go here
                //Delete from DB
                TrackInfoBean trackInfoBeanInner = TrackMyStuffSummaryCustomAdapter.this.getItem(index);


                //TrackMyStuffDatabaseHelper db = new TrackMyStuffDatabaseHelper(context);
                Boolean dbResp = null;
                if (null != trackInfoBeanInner && !trackInfoBeanInner.getArticleName().equals("") && !trackInfoBeanInner.getContainer().equals("") && mDb != null) {
                    dbResp = mDb.deleteTrackInfoByArticleNameAndContainer(trackInfoBeanInner.getArticleName(), trackInfoBeanInner.getContainer());
                } else {

                    Toast.makeText(
                            context,
                            "Article Name and Container are empty",
                            Toast.LENGTH_LONG).show();
                }

                if (dbResp) {

                    if(LOG){
                        Log.v("TrackMyStuffSummaryCustomAdapter : dbresp", ": " + mTrackInfoBeanList);
                    }

                    //Delete from the list depending on deletion in db was a success
                    if (mTrackInfoBeanList != null)
                        mTrackInfoBeanList.remove(index);
                    //Deletion complete

                    //Refresh view after Deletion

                    //RunOnUiThread(() => TrackMyStuffSummaryCustomAdapter.this.notifyDataSetChanged());
                    TrackMyStuffSummaryCustomAdapter.this.clear();

                    // List<TrackInfoBean> trackInfoBeanList = new ArrayList<TrackInfoBean>(mTrackInfoBeanList);
                    if(LOG){
                        Log.v("first element ", ": " + mTrackInfoBeanList.get(0).getArticleName() + "count : " + mTrackInfoBeanList.size());
                    }

                    TrackMyStuffSummaryCustomAdapter.this.addAll(mTrackInfoBeanList);
                    TrackMyStuffSummaryCustomAdapter.this.notifyDataSetChanged();


                }


            }

        });

        article_text_view.setText(trackInfoBean.getArticleName());
        container_text_view.setText(trackInfoBean.getContainer());
        location_text_view.setText(trackInfoBean.getLocation());
        room_text_view.setText(trackInfoBean.getRoom());

        return convertView;
    }


    public List<TrackInfoBean> getTrackInfoBeanList() {
        return mTrackInfoBeanList;
    }

    public void setTrackInfoBeanList(List<TrackInfoBean> mTrackInfoBeanList) {

        this.mTrackInfoBeanList = new ArrayList<TrackInfoBean>(mTrackInfoBeanList);
    }

    public TrackMyStuffDatabaseHelper getmDb() {
        return mDb;
    }

    public void setmDb(TrackMyStuffDatabaseHelper mDb) {
        this.mDb = mDb;
    }


}
