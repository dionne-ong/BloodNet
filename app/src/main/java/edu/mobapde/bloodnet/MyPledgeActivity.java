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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.pledges.Pledge;
import edu.mobapde.bloodnet.models.posts.Post;

public class MyPledgeActivity extends AppCompatActivity {

    Button btnStartDonation, btnCancel;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate, tvSliderText;
    SlideButton sb;
    DatabaseReference pledgeRef, postRef;
    Typeface face;
    String key, id;
    ImageView imgBarPicture;
    StorageReference pictureRef;

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
        pledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_PLEDGE_USER);
        postRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);
        id = getIntent().getStringExtra(DBOPost.EXTRA_POST_ID);
        btnStartDonation.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        imgBarPicture = (ImageView) findViewById(R.id.img_bar_picture);

        pledgeRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Boolean> hasDonated = (HashMap<String, Boolean>)dataSnapshot.getValue();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(hasDonated!=null && hasDonated.containsKey(userId)){

                    if(!hasDonated.get(userId)){
                        btnStartDonation.setText("Start Donation");
                        btnCancel.setText("Cancel");
                        btnStartDonation.setVisibility(View.VISIBLE);
                        btnCancel.setVisibility(View.VISIBLE);

                        btnStartDonation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, id);
                                i.setClass(getBaseContext(), RequirementsActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });


                    }else{
                        btnCancel.setText("Donated");
                        btnCancel.setTextColor(Color.parseColor("#F44336"));
                        btnCancel.setEnabled(false);
                        btnStartDonation.setVisibility(View.GONE);
                        btnCancel.setVisibility(View.VISIBLE);

                    } // whatever's in the db

                }else{
                    Toast.makeText(getBaseContext(), "Not Pledged Yet?", Toast.LENGTH_SHORT);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent i = getIntent();
        key = i.getStringExtra(DBOPost.EXTRA_POST_ID);

        if(key!=null){
            pictureRef = FirebaseStorage.getInstance().getReference().child(DBOPost.REF_POST_PATIENT_PIC).child(key);
            postRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Post post = dataSnapshot.getValue(Post.class);

                    if(post.isHasPic()){
                        Glide.with(getBaseContext())
                                .using(new FirebaseImageLoader())
                                .load(pictureRef)
                                .placeholder(getDrawable(R.drawable.imageerror1))
                                .error(getDrawable(R.drawable.imageerror2))
                                .into(imgBarPicture);
                    }

                    
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
                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                i.setClass(getBaseContext(), RequirementsActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
