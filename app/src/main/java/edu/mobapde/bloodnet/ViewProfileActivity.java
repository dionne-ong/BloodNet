package edu.mobapde.bloodnet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ViewProfileActivity extends AppCompatActivity {

    ImageView imgBarPicture;
    FloatingActionButton fab;

    public static final int REQUEST_CODE_EDIT_PROFILE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgBarPicture = (ImageView) findViewById(R.id.img_bar_picture);

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
}
