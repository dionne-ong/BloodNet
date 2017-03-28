package edu.mobapde.bloodnet;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class FinishedPledgeActivity extends AppCompatActivity {
    Button btnDone, btnCancel;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
        btnDone = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);

        btnCancel.setText("Donated");
        btnCancel.setTextColor(Color.parseColor("#F44336"));
        btnCancel.setEnabled(false);
       // btnCancel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
               // LinearLayout.LayoutParams.WRAP_CONTENT));
       // btnCancel.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check_black_24px, 0,0,0);
        btnDone.setVisibility(View.GONE);
        //whatever is in the db
        tvName.setText("Someone Else");
        tvName.setTypeface(face);
        tvHospital.setText("At Some Hospital");
        tvAddress.setText("At Some Street");
        tvContactNum.setText("090000000");
        tvBloodType.setText("O+");
        tvQuantity.setText("2 Bags");
        tvDate.setText("Posted on " + "February 18, 2017");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

}
