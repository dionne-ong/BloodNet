package edu.mobapde.bloodnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class MyProfile extends Fragment {
    View MyView;
    ImageView imgBarPicture;
    FloatingActionButton fab;

    public static final int REQUEST_CODE_EDIT_PROFILE = 201;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_view_profile, container, false);
        imgBarPicture = (ImageView) MyView.findViewById(R.id.img_bar_picture);

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
