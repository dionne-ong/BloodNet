package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TempNavigationActivity extends AppCompatActivity {

    Button
        btnViewProfile,
        btnEditProfile,
        btnFilterPost,
        btnViewPost,
        btnViewHospital,
        btnViewPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_navigation);

        btnViewProfile = (Button) findViewById(R.id.btn_view_profile);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), ViewProfileActivity.class);

                startActivity(i);
            }
        });

        btnEditProfile = (Button) findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), EditProfileFABActivity.class);

                startActivity(i);
            }
        });

        btnFilterPost = (Button) findViewById(R.id.btn_filter_post);
        btnFilterPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), FilterPostActivity.class);

                startActivity(i);
            }
        });

//        btnViewPatient = (Button) findViewById(R.id.btn_view_patient);
//        btnViewPatient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i.setClass(getBaseContext(), ViewPatientActivity.class);
//
//                startActivity(i);
//            }
//        });
//
//
//        btnViewHospital = (Button) findViewById(R.id.btn_view_hospital);
//        btnViewHospital.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i.setClass(getBaseContext(), ViewHospitalActivity.class);
//
//                startActivity(i);
//            }
//        });
    }
}
