package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    Button btnSignUp, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView tv=(TextView)findViewById(R.id.tv_app_name);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway Thin.ttf");
        tv.setTypeface(face);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), EditProfileActivity.class);

                startActivity(i);
            }
        });

    }
}
