package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.mobapde.bloodnet.DBObjects.DBOPost;

import static android.R.attr.key;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class MyPostActivity extends AppCompatActivity {
    Button btnEdit, btnDelete;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvPledged,tvSliderText;
    SlideButton sb;
    FirebaseAuth auth;
    DatabaseReference postRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);

        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
        btnEdit = (Button) findViewById(R.id.b_submit);
        btnDelete = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvPledged = (TextView) findViewById(R.id.tv_posteddate);
        sb = (SlideButton) findViewById(R.id.unlockButton);
        tvSliderText = (TextView) findViewById(R.id.slider_text);
        sb.setVisibility(View.GONE);
        tvSliderText.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        postRef = FirebaseDatabase.getInstance().getReference()
                .child(DBOPost.POST_REF);

        btnEdit.setText("Edit");
        btnDelete.setText("Delete");
        //whatever is in the db
        tvName.setText("Luisa Gilig");
        tvName.setTypeface(face);
        tvHospital.setText("Hospital A");
        tvAddress.setText("2191 Something Street, Manila City");
        tvContactNum.setText("09172134385");
        tvBloodType.setText("O+");
        tvQuantity.setText("2 Bags");
        tvPledged.setText("0 " + "have pledged to donate");
        tvPledged.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), EditPostActivity.class);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRef.child(getIntent().getStringExtra(DBOPost.EXTRA_POST_ID)).removeValue();
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
