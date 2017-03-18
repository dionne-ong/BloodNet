package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CreatePostActivity extends AppCompatActivity {

    Button btnCreate, btnCancel;
    TextView tvName, tvLocation, tvContactNumber, tvBloodType, tvQuantity, tvPhoto, tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        btnCancel = (Button) findViewById(R.id.b_cancel);
        btnCreate = (Button) findViewById(R.id.b_submit);
        tvName = (TextView) findViewById(R.id.tv_content_name);
        tvLocation = (TextView) findViewById(R.id.tv_content_location);
        tvContactNumber = (TextView) findViewById(R.id.tv_content_num);
        tvBloodType = (TextView) findViewById(R.id.tv_content_btype);
        tvQuantity = (TextView) findViewById(R.id.tv_content_quantity);
        tvAddress = (TextView) findViewById(R.id.tv_content_address);

        tvName.setHint("Name");
        tvLocation.setHint("Name of Hospital");
        tvContactNumber.setHint("Contact Number");
        tvBloodType.setHint("Blood Type");
        tvQuantity.setHint("Number of Bags Needed");
        tvAddress.setHint("Address of the Hospital");
        btnCancel.setText("Cancel");
        btnCreate.setText("Create");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), MyPostActivity.class);
                startActivity(i);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
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
