package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Luisa Gilig on 20/03/2017.
 */

public class Logout extends Fragment {
    LinearLayout layoutAccount, layoutAbout, layoutLogout;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    View MyView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_logout, container, false);
        auth = FirebaseAuth.getInstance();
        layoutAccount = (LinearLayout) MyView.findViewById(R.id.b_account);
        layoutAbout = (LinearLayout) MyView.findViewById(R.id.b_about);
        layoutLogout = (LinearLayout) MyView.findViewById(R.id.b_logout);
        // this listener will be called when there is change in firebase user session
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LogInActivity.class));
                    getActivity().finish();
                }
            }
        };

        layoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), MyProfile.class);
                startActivity(i);
            }
        });

        layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), AboutBloodNetActivity.class);
                startActivity(i);
            }
        });
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });


        return MyView;
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authListener);
    }
}
