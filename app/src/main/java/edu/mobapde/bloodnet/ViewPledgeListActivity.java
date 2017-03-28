package edu.mobapde.bloodnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.mobapde.bloodnet.models.Pledge;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class ViewPledgeListActivity extends Fragment{
    FloatingActionButton bCreate;
    private List<Pledge> pledgeList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    private MyPledgeAdapter pAdapter;
    RecyclerView rvPledge;
    View MyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_view_post_list, container, false);
        rvPledge = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPledge.setLayoutManager(pLayoutManager);
        rvPledge.setItemAnimator(new DefaultItemAnimator());
        bCreate = (FloatingActionButton)MyView.findViewById(R.id.fab);
        bCreate.setVisibility(View.GONE);
        Pledge pledge = new Pledge(2,5,false);
        pledgeList.add(pledge);

        pledge = new Pledge(3,4, false);
        pledgeList.add(pledge);

        pledge = new Pledge(1,2,false);
        pledgeList.add(pledge);
        pAdapter = new MyPledgeAdapter(pledgeList);

        pAdapter.setOnItemClickListener(new MyPostAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int id) {
                Intent i = new Intent();
                //go to post
                i.setClass(getActivity(), MyPledgeActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }

        });
        rvPledge.setAdapter(pAdapter);

        return MyView;
    }



}
