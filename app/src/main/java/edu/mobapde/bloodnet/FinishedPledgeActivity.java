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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.posts.Post;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class FinishedPledgeActivity extends AppCompatActivity {
    Button btnDone, btnCancel;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate, tvSliderText;
    SlideButton sb;
    Typeface face;
    String key;
    DatabaseReference donatedRef;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);
        face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
        btnDone = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);
        sb = (SlideButton) findViewById(R.id.unlockButton);
        tvSliderText = (TextView) findViewById(R.id.slider_text);

        donatedRef = FirebaseDatabase.getInstance().getReference();

        btnCancel.setText("Donated");
        btnCancel.setTextColor(Color.parseColor("#F44336"));
        btnCancel.setEnabled(false);
        sb.setVisibility(View.GONE);
        tvSliderText.setVisibility(View.GONE);
        btnDone.setVisibility(View.GONE);

        Intent i = getIntent();
        key = i.getStringExtra(DBOPost.EXTRA_POST_ID);

        if(key!=null){
            donatedRef
                    .child(DBOUser.REF_USER_PLEDGE)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                    map.put(key, true);
                    dataSnapshot.getRef().setValue(map);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            donatedRef
                    .child(DBOUser.REF_PLEDGE_USER)
                    .child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                    map.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                    dataSnapshot.getRef().setValue(map);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //set Text
        }else{
            tvName.setText("Someone Else");
            tvName.setTypeface(face);
            tvHospital.setText("At Some Hospital");
            tvAddress.setText("At Some Street");
            tvContactNum.setText("090000000");
            tvBloodType.setText("O+");
            tvQuantity.setText("2 Bags");
            tvDate.setText("Posted on " + "February 18, 2017");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

}
