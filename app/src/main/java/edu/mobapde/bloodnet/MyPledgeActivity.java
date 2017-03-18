package edu.mobapde.bloodnet;

import android.content.Context;
import android.content.Intent;
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

public class MyPledgeActivity extends AppCompatActivity {

    Button btnStartDonation, btnCancel;
    Toolbar tbEdit;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate;

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

        btnStartDonation.setText("Start Donation");
        btnCancel.setText("Cancel");
        // whatever's in the db
        tvName.setText("Winnie The Pooh");
        tvHospital.setText("Chinese General Hospital");
        tvAddress.setText("286 Blumentritt Rd, Sampaloc,Manila, Metro Manila");
        tvContactNum.setText("09178075984");
        tvBloodType.setText("B+");
        tvQuantity.setText("2");
        tvDate.setText("Posted on " + "February 10, 2017");


        tbEdit = (Toolbar) findViewById(R.id.tb_edit);
        setSupportActionBar(tbEdit);
        getSupportActionBar().setTitle("My Pledge");


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
