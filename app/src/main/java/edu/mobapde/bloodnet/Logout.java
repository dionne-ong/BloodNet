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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Luisa Gilig on 20/03/2017.
 */

public class Logout extends Fragment {
    Button btnLogout;
    FirebaseAuth auth;


    View MyView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_logout, container, false);
//        auth = FirebaseAuth.getInstance();
//        btnLogout = (Button) MyView.findViewById(R.id.b_logout);
//        // this listener will be called when there is change in firebase user session
//        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    // user auth state is changed - user is null
//                    // launch login activity
//                    startActivity(new Intent(MyView.getContext(), LogInActivity.class));
//                    getActivity().finish();
//                }
//            }
//        };
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            auth.signOut();
                Toast.makeText(getActivity(), "Logging Out...", Toast.LENGTH_SHORT);
            }
        });


        return MyView;
    }
}
