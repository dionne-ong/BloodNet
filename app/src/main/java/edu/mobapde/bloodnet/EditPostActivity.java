package edu.mobapde.bloodnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class EditPostActivity extends AppCompatActivity{

    Button btnSave, btnCancel;
    TextView tvName, tvLocation, tvContactNumber, tvBloodType, tvQuantity, tvPhoto, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        btnCancel = (Button) findViewById(R.id.b_cancel);
        btnSave = (Button) findViewById(R.id.b_submit);
        tvName = (TextView) findViewById(R.id.tv_content_name);
        tvLocation = (TextView) findViewById(R.id.tv_content_location);
        tvContactNumber = (TextView) findViewById(R.id.tv_content_num);
        tvBloodType = (TextView) findViewById(R.id.tv_content_btype);
        tvQuantity = (TextView) findViewById(R.id.tv_content_quantity);
        tvAddress = (TextView) findViewById(R.id.tv_content_address);

        //whatever is in the database
        tvName.setHint("Someone Else");
        tvLocation.setHint("At Some Hospital");
        tvAddress.setHint("At Some Street");
        tvContactNumber.setHint("090000000");
        tvBloodType.setHint("O+");
        tvQuantity.setHint("2");
        btnCancel.setText("Cancel");
        btnSave.setText("Save");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), MyPostActivity.class);
                startActivity(i);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), MyPostActivity.class);
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
