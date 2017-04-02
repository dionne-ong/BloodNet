package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.posts.Post;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class MyPostActivity extends AppCompatActivity {
    Button btnEdit, btnDelete;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvPledged,tvSliderText;
    SlideButton sb;
    FirebaseAuth auth;
    DatabaseReference postRef, ref;
    Typeface face;
    Post post;
    Map<String, Boolean> userMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pledge);

        ref = FirebaseDatabase.getInstance().getReference();

        face= Typeface.createFromAsset(getAssets(),"fonts/Raleway-Light.ttf");
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

        Intent i = getIntent();
        String key = i.getStringExtra(DBOPost.EXTRA_POST_ID);
        if(key != null){
            postRef.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    post = dataSnapshot.getValue(Post.class);
                    if(post != null) {
                        tvAddress.setText(post.getHospitalAddress());
                        tvName.setText(post.getPatientName());
                        tvName.setTypeface(face);
                        tvBloodType.setText(post.getBloodType());
                        tvHospital.setText(post.getHospitalName());
                        tvContactNum.setText(post.getContactNum());
                        tvQuantity.setText(post.getNeededBags() + "");
                        tvPledged.setText(post.getPledgedBags() + " Pledges");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(DBOPost.EXTRA_POST_ID, getIntent().getStringExtra(DBOPost.EXTRA_POST_ID));
                i.setClass(getBaseContext(), EditPostActivity.class);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post != null) {
                    ref.child(post.getBloodType()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                            map.remove(post.getId());
                            ref.child(post.getBloodType()).setValue(map);

                            ref.child(DBOUser.REF_USER_POST)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                    map.remove(post.getId());
                                    ref.child(DBOUser.REF_USER_POST)
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(map);

                                    //TODO: Pledge Deletion
                                    ref.child(DBOUser.REF_PLEDGE_USER).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userMap = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                            if(userMap!=null) {
                                                Set keys = userMap.keySet();
                                                while (keys.iterator().hasNext()) {
                                                    String key = (String) keys.iterator().next();

                                                    ref.child(DBOUser.REF_USER_PLEDGE).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            Map<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                                            map.remove(post.getId());
                                                            ref.child(DBOUser.REF_USER_POST)
                                                                    .child(dataSnapshot.getKey())
                                                                    .setValue(map);
                                                            userMap.put(dataSnapshot.getKey(), false);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            }
                                            ref.child(DBOPost.POST_REF).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if(userMap!=null) {
                                                        while (userMap.containsValue(true)) {
                                                            Log.i("DB", "Still Deleting...");
                                                        }
                                                    }

                                                    postRef.child(getIntent().getStringExtra(DBOPost.EXTRA_POST_ID)).removeValue();
                                                    Intent i = new Intent();
                                                    i.putExtra(NavigationDrawerActivity.EXTRA_VIEW_ID, R.id.nav_posts);
                                                    i.setClass(getBaseContext(), NavigationDrawerActivity.class);
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
                }
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
