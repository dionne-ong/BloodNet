package edu.mobapde.bloodnet;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.GregorianCalendar;

import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.User;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class MyProfile extends AppCompatActivity{

    ImageView imgBarPicture;
    FloatingActionButton fab;
    FirebaseAuth auth;
    DatabaseReference userRef;
    StorageReference profileRef;
    TextView tvName, tvEmail, tvContact, tvBirthdate, tvGender, tvBType;
    public static final int REQUEST_CODE_EDIT_PROFILE = 201;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        imgBarPicture = (ImageView) findViewById(R.id.img_bar_picture);
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER);

        tvName = (TextView) findViewById(R.id.tv_content_name);
        tvEmail = (TextView) findViewById(R.id.tv_content_email);
        tvContact = (TextView) findViewById(R.id.tv_content_num);
        tvBirthdate = (TextView) findViewById(R.id.tv_content_bday);
        tvGender = (TextView) findViewById(R.id.tv_content_gender);
        tvBType = (TextView) findViewById(R.id.tv_content_btype);
        profileRef = FirebaseStorage.getInstance().getReference().child(DBOUser.REF_USER_PROFILE_PIC).child(auth.getCurrentUser().getUid());

        userRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);

                if(u.isHasPic()){
                    Glide.with(getBaseContext())
                            .using(new FirebaseImageLoader())
                            .load(profileRef)
                            .placeholder(getDrawable(R.drawable.imageerror1))
                            .error(getDrawable(R.drawable.imageerror2))
                            .into(imgBarPicture);
                }


                tvEmail.setText(auth.getCurrentUser().getEmail());
                if(u != null) {
                    tvName.setText(u.getName());
                    tvContact.setText(u.getContactNum());
                    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                    String date = "";
                    if (u.getBirthdate() != -1) {
                        GregorianCalendar a = new GregorianCalendar();
                        a.setTimeInMillis(u.getBirthdate());
                        date = format.format(a.getTime());
                    }
                    tvBirthdate.setText(date);
                    tvGender.setText(u.getGender());
                    tvBType.setText(u.getBloodType());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_view_profile);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "WHAT", 200);
                Intent i = new Intent();
                i.setClass(getBaseContext(), EditProfileFABActivity.class);

                startActivityForResult(i, REQUEST_CODE_EDIT_PROFILE);
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
