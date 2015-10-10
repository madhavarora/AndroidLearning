package com.aryapps.extmvo.trackmystuff;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by extmvo on 6/9/2015.
 */
public class TrackMyStuffMenuCustomAdapter extends ArrayAdapter<String> implements TrackMyStuffConstants {
    private Context context;
    private int numberOfItems;
    private int height;


    private String flowType;


    public TrackMyStuffMenuCustomAdapter(Context context, List<String> menuItems, int displayHeight, String flowType) {
        super(context, 0, menuItems);
        setHeight(displayHeight);

        if (null != menuItems)
            setNumberOfItems(menuItems.size());

        if (null != flowType)
            setFlowType(flowType);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String menuItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_custom_row, parent, false);
        }

        TextView menu_text_view = (TextView) convertView.findViewById(R.id.textview_menu_item);

        if (null != getFlowType())
            menu_text_view.setTextSize(22);

        if(LOG){

            Log.v("TrackMyStuffMenuCustomAdapter  : ", "" + getHeight());
        }

        if (getHeight() != 0) {

            if(numberOfItems>0)
            convertView.setMinimumHeight(getHeight() / numberOfItems);
        }

        menu_text_view.setText(menuItem);

        return convertView;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }


    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getFlowType() {
        return flowType;
    }


}
