package edu.mobapde.bloodnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class TempNav extends Fragment {
    View MyView;
    Button bViewProfile, bEditProfile, bFilterPost,  bViewHospital;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_temp_navigation, container, false);
        bViewProfile = (Button) MyView.findViewById(R.id.btn_view_profile);
        bEditProfile = (Button) MyView.findViewById(R.id.btn_edit_profile);
        bFilterPost = (Button) MyView.findViewById(R.id.btn_filter_post);

        bViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), CreatePostActivity.class);
                startActivity(i);
            }
        });

        bEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), ViewPostActivity.class);
                startActivity(i);
            }
        });

        bFilterPost = (Button) MyView.findViewById(R.id.btn_filter_post);
        bFilterPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), FilterPostActivity.class);

                startActivity(i);
            }
        });

        bViewHospital = (Button) MyView.findViewById(R.id.btn_view_hospital);
        bViewHospital.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent();
                  i.setClass(getActivity(), ViewHospitalActivity.class);

                  startActivity(i);
              }
          });

        return MyView;
    }
}
