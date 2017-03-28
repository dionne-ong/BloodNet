package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class ViewPostActivity extends AppCompatActivity {

    Button btnPledged, btnCancel;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
        btnPledged = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);

        btnPledged.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        //whatever is in the db
        tvName.setText("Winnie The Pooh");
        tvName.setTypeface(face);
        tvHospital.setText("Chinese General Hospital");
        tvAddress.setText("286 Blumentritt Rd, Sampaloc,Manila, Metro Manila");
        tvContactNum.setText("09178075984");
        tvBloodType.setText("B+");
        tvQuantity.setText("2 Bags");
        tvDate.setText("Posted on " + "February 10, 2017");


        ((SlideButton) findViewById(R.id.unlockButton)).setSlideButtonListener(new SlideButtonListener() {
            @Override
            public void handleSlide() {
                Intent i = new Intent();
                i.setClass(getBaseContext(), MyPledgeActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }
}
