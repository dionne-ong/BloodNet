package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.models.pledges.Pledge;
import edu.mobapde.bloodnet.models.posts.Post;

public class MyPledgeActivity extends AppCompatActivity {

    Button btnStartDonation, btnCancel;
    Toolbar tbEdit;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate, tvSliderText;
    Pledge pledge;
    SlideButton sb;
    DatabaseReference pledgeRef;
    Typeface face;

    public static final int REQUEST_CODE_DONATE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);
        sb = (SlideButton) findViewById(R.id.unlockButton);
        tvSliderText = (TextView) findViewById(R.id.slider_text);
        btnStartDonation = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);
        face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
        sb.setVisibility(View.GONE);
        tvSliderText.setVisibility(View.GONE);
        pledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);

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

        Intent i = getIntent();
        String key = i.getStringExtra(DBOPost.EXTRA_POST_ID);

        if(key!=null){
            pledgeRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Post post = dataSnapshot.getValue(Post.class);
                    tvName.setText(post.getPatientName());
                    tvName.setTypeface(face);
                    tvBloodType.setText(post.getBloodType());
                    tvHospital.setText(post.getHospitalName());
                    tvAddress.setText(post.getHospitalAddress());
                    tvContactNum.setText(post.getContactNum());
                    tvQuantity.setText(post.getNeededBags()+"");
                    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                    tvDate.setText("Posted on " + format.format(new Date(post.getDatePosted())));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            tvName.setText("Winnie The Pooh");
            tvName.setTypeface(face);
            tvHospital.setText("Chinese General Hospital");
            tvAddress.setText("286 Blumentritt Rd, Sampaloc,Manila, Metro Manila");
            tvContactNum.setText("09178075984");
            tvBloodType.setText("B+");
            tvQuantity.setText("2 Bags");
            tvDate.setText("Posted on " + "February 10, 2017");

        }

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
