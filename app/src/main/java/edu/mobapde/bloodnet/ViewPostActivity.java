package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
 * Created by Luisa Gilig on 18/03/2017.
 */

public class ViewPostActivity extends AppCompatActivity {

    Button btnPledged, btnCancel;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate;
    DatabaseReference postRef;
    DatabaseReference userPledgeRef;
    DatabaseReference pledgeUserRef;
    HashMap<String, Boolean> map;
    Post post;
    Typeface face;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);
        face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
        btnPledged = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHospital = (TextView) findViewById(R.id.tv_hospital);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvContactNum = (TextView) findViewById(R.id.tv_number);
        tvBloodType = (TextView) findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) findViewById(R.id.tv_bags);
        tvDate = (TextView) findViewById(R.id.tv_posteddate);

        postRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);

        btnPledged.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);

        Intent i = getIntent();
        String key = i.getStringExtra(DBOPost.EXTRA_POST_ID);
        if(key != null){
            postRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     post = dataSnapshot.getValue(Post.class);
                    tvAddress.setText(post.getHospitalAddress());
                    tvName.setText(post.getPatientName());
                    tvName.setTypeface(face);
                    tvBloodType.setText(post.getBloodType());
                    tvHospital.setText(post.getHospitalName());
                    tvContactNum.setText(post.getContactNum());
                    tvQuantity.setText(post.getNeededBags()+"");
                    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                    tvDate.setText("Posted on " + format.format(new Date(post.getDatePosted())));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            //whatever is in the db
            tvName.setText("Default Name");
            tvName.setTypeface(face);
            tvHospital.setText("Chinese General Hospital");
            tvAddress.setText("286 Blumentritt Rd, Sampaloc,Manila, Metro Manila");
            tvContactNum.setText("09178075984");
            tvBloodType.setText("B+");
            tvQuantity.setText("2 Bags");
            tvDate.setText("Posted on " + "February 10, 2017");
        }


        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE);
        pledgeUserRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_PLEDGE_USER);
        ((SlideButton) findViewById(R.id.unlockButton)).setSlideButtonListener(new SlideButtonListener() {
            @Override
            public void handleSlide() {

                postRef.child(post.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        post.setPledgedBags(post.getPledgedBags()+1);
                        postRef.child(post.getId()).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    userPledgeRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                            if (map == null) {
                                                map = new HashMap<String, Boolean>();
                                            }

                                            map.put(post.getId(), false);
                                            userPledgeRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);

                                            pledgeUserRef.child(post.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                                    if (map == null) {
                                                        map = new HashMap<String, Boolean>();
                                                    }

                                                    map.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), false);
                                                    pledgeUserRef.child(post.getId()).setValue(map);


                                                    Intent i = new Intent();
                                                    i.putExtra(DBOPost.EXTRA_POST_ID, post.getId());
                                                    i.setClass(getBaseContext(), MyPledgeActivity.class);
                                                    startActivity(i);
                                                    finish();

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }else{
                                    Toast.makeText(getBaseContext(), "Error pledging blood: "+task.getException().getLocalizedMessage(), Toast.LENGTH_LONG);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
