package edu.mobapde.bloodnet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.mobapde.bloodnet.models.Pledge;

public class MyPledgeActivity extends AppCompatActivity {

    Button btnStartDonation, btnCancel;
    Toolbar tbEdit;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate;
    Pledge pledge;


    public static final int REQUEST_CODE_DONATE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);
        btnStartDonation = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");

        int id = getIntent().getIntExtra(Pledge.PLEDGE_EXTRA, -1);
        if(id != -1){
            pledge = new Pledge(1,2, true);
        }else{
            pledge = new Pledge(1,2, true);
        }


        if(pledge.getDonated()){
            btnStartDonation.setText("Start Donation");
            btnCancel.setText("Cancel");

            btnStartDonation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(getBaseContext(), RequirementsActivity.class);
                    startActivity(i);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(getBaseContext(), ViewPostActivity.class);
                    startActivity(i);
                }
            });


        }else{
            btnCancel.setText("Donated");
            btnCancel.setTextColor(Color.parseColor("#F44336"));
            btnCancel.setEnabled(false);
            btnStartDonation.setVisibility(View.GONE);

        } // whatever's in the db
        tvName.setText("Winnie The Pooh");
        tvName.setTypeface(face);
        tvHospital.setText("Chinese General Hospital");
        tvAddress.setText("286 Blumentritt Rd, Sampaloc,Manila, Metro Manila");
        tvContactNum.setText("09178075984");
        tvBloodType.setText("B+");
        tvQuantity.setText("2 Bags");
        tvDate.setText("Posted on " + "February 10, 2017");



        btnStartDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), RequirementsActivity.class);
                startActivityForResult(i, REQUEST_CODE_DONATE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), ViewPostActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_DONATE:
                if(resultCode == RESULT_OK){
                    pledge.setDonated(false);
                    btnCancel.setText("Donated");
                    btnCancel.setTextColor(Color.parseColor("#F44336"));
                    btnCancel.setEnabled(false);
                    btnStartDonation.setVisibility(View.GONE);

                }
                break;
            default:
        }

    }
}
