package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static android.R.attr.onClick;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class ViewPostListActivity extends Fragment{
    private List<Post> postsList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    private MyPostAdapter pAdapter;
    RecyclerView rvPosts;
    View MyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_view_post_list, container, false);
        rvPosts = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());
        rvPosts.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        Post post = new Post(1, 1, "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar());
        postsList.add(post);

        post = new Post(1, 1, "Luisa Gilig", "O+", "09178273678", "Hospital B", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar());
        postsList.add(post);

        post = new Post(1, 1, "Luisa Gilig", "O+", "09178273678", "Hospital C", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar());
        postsList.add(post);
        pAdapter = new MyPostAdapter(postsList);

        pAdapter.setOnItemClickListener(new MyPostAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int id) {
                Intent i = new Intent();
                //go to post
                i.setClass(getActivity(), MyPostActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }

        });
        rvPosts.setAdapter(pAdapter);

        return MyView;
    }



}
