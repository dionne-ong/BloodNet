package edu.mobapde.bloodnet;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;

public class AfterRequirements extends AppCompatActivity {
    Button button;
    ImageView ivPoster;
    String key, userId;
    DatabaseReference pledgeUserRef, userPledgeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_requirements);
        button = (Button) findViewById(R.id.b_ok);
        ivPoster = (ImageView) findViewById(R.id.iv_poster);

        Intent in= getIntent();
        final Bundle b = in.getExtras();

        if(b.getBoolean("legible")){
            ivPoster.setImageResource(R.drawable.thankyou);
        }else{
            ivPoster.setImageResource(R.drawable.nottoday);
        }

        pledgeUserRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_PLEDGE_USER);
        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE);

        Intent i = getIntent();
        key = i.getStringExtra(DBOPost.EXTRA_POST_ID);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        pledgeUserRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("LEGIBLE", "It is " + b.getBoolean("legible")+"");
                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                map.put(userId, b.getBoolean("legible"));
                Log.i("PLEDGING", "Setting value in "+dataSnapshot.getRef().getKey()+" to "+(new Gson()).toJson(map));
                dataSnapshot.getRef().setValue(map);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userPledgeRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("LEGIBLE", "It is " + b.getBoolean("legible")+"");
                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                map.put(key, b.getBoolean("legible"));
                Log.i("PLEDGING", "Setting value in "+dataSnapshot.getRef().getKey()+" to "+(new Gson()).toJson(map));

                dataSnapshot.getRef().setValue(map);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(DBOPost.EXTRA_POST_ID, key);

                    i.setClass(getBaseContext(), MyPledgeActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
