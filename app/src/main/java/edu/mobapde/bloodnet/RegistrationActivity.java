package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.User;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnSignUp, btnCancel;
    private EditText etEmail, etPassword, etConfirmPassword, etName;
    private ProgressBar progressBar;
    private Spinner spBType;
    ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth auth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER);

        TextView tv=(TextView)findViewById(R.id.tv_app_name);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway Thin.ttf");
        tv.setTypeface(face);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        etEmail = (EditText) findViewById(R.id.tv_content_email);
        etPassword = (EditText) findViewById(R.id.tv_content_pw);
        etConfirmPassword = (EditText) findViewById(R.id.tv_content_confirm_pw);
        etName = (EditText) findViewById(R.id.tv_content_name);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        spBType = (Spinner) findViewById(R.id.s_bloodtype);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.bloodtype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBType.setAdapter(adapter);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirm = etConfirmPassword.getText().toString().trim();
                String btype = spBType.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(confirm)){
                    Toast.makeText(getApplicationContext(), "Confirm password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!confirm.contentEquals(password)){
                    Toast.makeText(getApplicationContext(), "Confirm password incorrect, please match it with your password.", Toast.LENGTH_LONG);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Registration failed. " + task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Registration success!", Toast.LENGTH_SHORT).show();
                                    User u = new User();
                                    u.setName(etName.getText().toString());
                                    u.setBloodType(spBType.getSelectedItem().toString());
                                    Log.i("REGISTER","UUID: "+auth.getCurrentUser().getUid());
                                    userRef.child(auth.getCurrentUser().getUid()).setValue(u);
                                    startActivity(new Intent(RegistrationActivity.this, NavigationDrawerActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), LogInActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
