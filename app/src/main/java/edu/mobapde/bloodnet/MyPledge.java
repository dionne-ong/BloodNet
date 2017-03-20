package edu.mobapde.bloodnet;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class MyPledge extends android.support.v4.app.Fragment{
    Button btnStartDonation, btnCancel;
    TextView tvName, tvHospital, tvAddress, tvContactNum, tvBloodType, tvQuantity, tvDate;

    View MyView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_my_pledge, container, false);

        btnStartDonation = (Button) MyView.findViewById(R.id.b_submit);
        btnCancel = (Button) MyView.findViewById(R.id.b_cancel);
        tvHospital = (TextView) MyView.findViewById(R.id.tv_hospital);
        tvAddress = (TextView) MyView.findViewById(R.id.tv_address);
        tvContactNum = (TextView) MyView.findViewById(R.id.tv_number);
        tvBloodType = (TextView) MyView.findViewById(R.id.tv_bloodtype);
        tvQuantity = (TextView) MyView.findViewById(R.id.tv_bags);
        tvDate = (TextView) MyView.findViewById(R.id.tv_posteddate);
        tvName = (TextView) MyView.findViewById(R.id.tv_name);
        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Raleway-Light.ttf");

        tvName.setText("Winnie The Pooh");
        tvName.setTypeface(face);
        btnStartDonation.setText("Start Donation");
        btnCancel.setText("Cancel");
        // whatever's in the db
        tvHospital.setText("Chinese General Hospital");
        tvAddress.setText("286 Blumentritt Rd, Sampaloc,Manila, Metro Manila");
        tvContactNum.setText("09178075984");
        tvBloodType.setText("B+");
        tvQuantity.setText("2 bags");
        tvDate.setText("Posted on " + "February 10, 2017");

        btnStartDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), RequirementsActivity.class);
                startActivity(i);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), ViewPostActivity.class);
                startActivity(i);
            }
        });

        return MyView;
    }
}
