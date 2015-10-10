package com.aryapps.extmvo.trackmystuff;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrackMyStuffSuperActivity extends Activity  implements TrackMyStuffConstants{

    public static final String SHIFT_PREFERENCE = "shiftpref";
    //For storing settings data
    public static final String SHIFT_PREFERENCE_USERNAME = "username";
    public static final String SHIFT_PREFERENCE_EMAIL = "email";
    public static final String SHIFT_PREFERENCE_PASSWORD = "password";
    public static final String SHIFT_PREFERENCE_EMPID = "empId";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String SCREEN_HEIGHT="screenHeight";
    public static final String SCREEN_WIDTH="screenWidth";
    public static final String HELP_FLOW = "helpFlow";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Called from Add as well as edit activities, action type =add or edit.
     *
     * @param actionType
     */
    public void saveTrackInfoData(final String actionType, final String oldDate) {

        if(LOG)
        Log.d("TrackMyStuffSuperActivity", "saveTrackInfo :: Starts, actionType :: "+actionType +" oldDate :: " + oldDate);

        //Save button functionality
        Button save = (Button) findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               // for(int i=0;i<500;i++){
                    if(LOG)
                        Log.d("TrackMyStuffSuperActivity :: ", "saveButtonClicked");


                    EditText articleName = (EditText) findViewById(R.id.EditText_articleName);
                    ImageView imageView = (ImageView) findViewById(R.id.ImageView_articleImage);
                    EditText location = (EditText) findViewById(R.id.EditText_location);
                    EditText room = (EditText) findViewById(R.id.EditText_room);
                    EditText container = (EditText) findViewById(R.id.EditText_container);

                    String articleNameStr = articleName.getText().toString();
                    String locationStr = location.getText().toString();
                    String roomStr = room.getText().toString();
                    String containerStr = container.getText().toString();
                    Bitmap articleImageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    //Convert Bitmap into byte array
                    byte[] articleImageByteArray = TrackMyStuffSuperActivity.this.getBytesFromBitmap(articleImageBitmap);

                    if(LOG){

                        Log.d("articleNameStr ", articleNameStr);
                        Log.d("locationStr ", locationStr);
                        Log.d("roomStr ", roomStr);
                        Log.d("containerStr ", containerStr);
                        Log.d("articleImageByteArray", ": " + articleImageByteArray);
                    }



                    //TrackInfo bean population
                    TrackInfoBean trackInfoBean = TrackMyStuffSuperActivity.this.populateTrackInfoBean(articleNameStr, locationStr, roomStr, containerStr, articleImageByteArray,actionType,oldDate  );

                    TrackMyStuffDatabaseHelper db = new TrackMyStuffDatabaseHelper(TrackMyStuffSuperActivity.this);
                    long dbResp = 0;
                    if (null != trackInfoBean && !trackInfoBean.getArticleName().equals("") && !trackInfoBean.getContainer().equals("")) {
                        if (actionType != null && actionType.equals("add")) {
                            dbResp = db.addTrackInfo(trackInfoBean);
                        } else if (actionType != null && actionType.equals("edit")) {
                            dbResp = db.updateTrackInfo(trackInfoBean);
                        }

                    } else {

                        Toast.makeText(
                                TrackMyStuffSuperActivity.this,
                                TOAST_MANDATORY_FIELDS,
                                Toast.LENGTH_LONG).show();
                    }

                    if(LOG)
                    Log.d("TrackMyStuffSuperActivity"," dbResp ::" + dbResp );

                    if (dbResp > 0) {
                        Toast.makeText(TrackMyStuffSuperActivity.this,
                                DATA_SAVED_SUCCESS, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(v.getContext(),
                                TrackMyStuffMenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(
                                TrackMyStuffSuperActivity.this,
                                DUPLICATE_DATA,
                                Toast.LENGTH_LONG).show();
                    }



                }

         //   }
        });

        if(LOG)
            Log.d("TrackMyStuffSuperActivity", "saveTrackInfo :: ENDS, actionType :: "+actionType +" oldDate :: " + oldDate);

    }

    /**
     * This method will populate The track info bean.
     * @param articleName
     * @param location
     * @param room
     * @param container
     * @param imageByteArray
     * @param action
     * @param oldDate
     * @return
     */
    public TrackInfoBean populateTrackInfoBean(String articleName,  String location, String room, String container, byte[] imageByteArray, String action, String oldDate) {

        if (LOG)
            Log.d("TrackMyStuffSuperActivity", "populateTrackInfoBean :: STARTS, articleName :: " + articleName + " location :: " + location + "room ::" + room + " container :: " + container + "imageByteArray :: " + imageByteArray + "action ::" + action + " oldDate ::" + oldDate);

        String dateString = null;

        if(action!=null && action.equals(ACTION_ADD)){
            Date date = new Date();
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yy HH:mm:ss.SSS");
            dateString = formatter.format(date);

        }else if(action!=null && action.equals(ACTION_EDIT)) {
            dateString = oldDate;

        }

        if(LOG){
            Log.d("TrackMyStuffSuperActivity : populateTrackInfoBean : dateString : ", dateString);
        }


        TrackInfoBean trackInfoBean = new TrackInfoBean();
        trackInfoBean.setArticleName(articleName);
       // trackInfoBean.setImage(image);
        trackInfoBean.setLocation(location);
        trackInfoBean.setRoom(room);
        trackInfoBean.setContainer(container);
        trackInfoBean.setImageByteArray(imageByteArray);
        trackInfoBean.setDateString(dateString);

        if (LOG)
            Log.d("TrackMyStuffSuperActivity", "populateTrackInfoBean :: ENDS, articleName :: " + articleName + " location :: " + location + "room ::" + room + " container :: " + container + "imageByteArray :: " + imageByteArray + "action ::" + action + " oldDate ::" + oldDate);



        return trackInfoBean;

    }


    public byte[] getBytesFromBitmap(Bitmap bitmap) {

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","getBytesFromBitmap STARTS bitmap :: "+ bitmap);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","getBytesFromBitmap ENDS");

        return stream.toByteArray();
    }

    public void takeImageButton(final int action) {

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","takeImageButton STARTS");

        Button buttonImage = (Button) findViewById(R.id.button_image);
        buttonImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, action);
                }

            }


        });

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","takeImageButton ENDS");


    }

    public void handleImageViewPopup() {

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","handleImageViewPopup STARTS");


        final ImageView imageView = (ImageView) findViewById(R.id.ImageView_articleImage);
        imageView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                Bitmap articleImageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.article_image_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(false);


                ImageView imageView = (ImageView) popupView.findViewById(R.id.image_view_inflated);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(articleImageBitmap, 420, 420, false));

                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                //popupWindow.showAsDropDown(imageView, 50, -30);

                popupWindow.showAtLocation(imageView, Gravity.CENTER, 0, 0);

            }
        });

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","handleImageViewPopup ENDS");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","onActivityResult STARTS");


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            if(LOG)
                Log.d("TrackMyStuffSuperActivity","ImageCaptured");

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView articleImgView = (ImageView) findViewById(R.id.ImageView_articleImage);
            articleImgView.setImageBitmap(imageBitmap);
           // getBaseContext().getContentResolver().delete(data.getData(), null, null);


        }

        if(LOG)
            Log.d("TrackMyStuffSuperActivity","onActivityResult ENDS");
    }


}
