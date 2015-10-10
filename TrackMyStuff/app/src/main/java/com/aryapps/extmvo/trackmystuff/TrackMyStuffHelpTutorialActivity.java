package com.aryapps.extmvo.trackmystuff;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TrackMyStuffHelpTutorialActivity extends TrackMyStuffSuperActivity  implements TrackMyStuffConstants{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_tutorial);

        LinearLayout sv = (LinearLayout) findViewById(R.id.imagesScroller);
        int[] addImageList = getIntent().getExtras().getIntArray("images");

        //for Out of Memory errors
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;
        o.inDither = false;                     //Disable Dithering mode
        o.inPurgeable = true;

        for (int i = 0; i < addImageList.length; i++) {

            ImageView iv = new ImageView(this);
            TextView tv = new TextView(this);

            Bitmap bm = BitmapFactory.decodeResource(getResources(), addImageList[i], o);
            //bm.setDensity(400);

            iv.setImageDrawable(new BitmapDrawable(bm)); // same happens with ScaleDrawable.
            //iv.setScaleType( ImageView.ScaleType.CENTER_INSIDE );
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(700, 900);
            iv.setLayoutParams(layoutParams);
            tv.setText("Step " + (i + 1) + " :");


            sv.addView(tv);
            sv.addView(iv);
        }
    }

}
