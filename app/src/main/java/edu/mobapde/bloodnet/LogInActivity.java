package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogIn, btnRegister;
    ImageButton btnLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        TextView tv=(TextView)findViewById(R.id.tv_app_name);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway Thin.ttf");
        tv.setTypeface(face);
        
        btnLogIn = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_reg);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), TempNavigationActivity.class);

                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), RegistrationActivity.class);

                startActivity(i);
            }
        });


        btnLogo = (ImageButton) findViewById(R.id.btn_logo);
        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), AboutBloodNetActivity.class);

                startActivity(i);

            }
        });

    }
}
