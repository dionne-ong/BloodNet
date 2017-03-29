package edu.mobapde.bloodnet;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.User;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class MyProfile extends Fragment {
    View MyView;
    ImageView imgBarPicture;
    FloatingActionButton fab;
    FirebaseAuth auth;
    DatabaseReference userRef;
    TextView tvName, tvEmail, tvContact, tvBirthdate, tvGender, tvBType;
    public static final int REQUEST_CODE_EDIT_PROFILE = 201;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_view_profile, container, false);
        imgBarPicture = (ImageView) MyView.findViewById(R.id.img_bar_picture);
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER);

        tvName = (TextView) MyView.findViewById(R.id.tv_content_name);
        tvEmail = (TextView) MyView.findViewById(R.id.tv_content_email);
        tvContact = (TextView) MyView.findViewById(R.id.tv_content_num);
        tvBirthdate = (TextView) MyView.findViewById(R.id.tv_content_bday);
        tvGender = (TextView) MyView.findViewById(R.id.tv_content_gender);
        tvBType = (TextView) MyView.findViewById(R.id.tv_content_btype);

        userRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                tvName.setText(u.getName());
                tvEmail.setText(auth.getCurrentUser().getEmail());
                tvContact.setText(u.getContactNum());
                SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
                String date = "";
                if(u.getBirthdate()!=-1){
                    GregorianCalendar a = new GregorianCalendar();
                    a.setTimeInMillis(u.getBirthdate());
                    date = format.format(a.getTime());
                }
                tvBirthdate.setText(date);
                tvGender.setText(u.getGender());
                tvBType.setText(u.getBloodType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab = (FloatingActionButton) MyView.findViewById(R.id.fab_view_profile);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "WHAT", 200);
                Intent i = new Intent();
                i.setClass(getActivity(), EditProfileFABActivity.class);

                startActivityForResult(i, REQUEST_CODE_EDIT_PROFILE);
            }
        });

        
        return MyView;
    }



}
