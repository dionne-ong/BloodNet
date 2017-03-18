package edu.mobapde.bloodnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

        btnPledged = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);

        btnPledged.setText("Pledge");
        btnCancel.setVisibility(View.INVISIBLE);
        //whatever is in the db
        tvName.setText("Someone Else");
        tvHospital.setText("At Some Hospital");
        tvAddress.setText("At Some Street");
        tvContactNum.setText("090000000");
        tvBloodType.setText("O+");
        tvQuantity.setText("2");
        tvDate.setText("Posted on " + "February 18, 2017");


        btnPledged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
