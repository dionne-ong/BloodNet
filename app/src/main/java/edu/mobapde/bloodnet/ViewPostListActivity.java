package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.models.posts.Post;
import edu.mobapde.bloodnet.models.posts.PostHolder;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class ViewPostListActivity extends Fragment{
    private List<Post> postsList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    FloatingActionButton btnCreate;
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

        btnCreate  = (FloatingActionButton) MyView.findViewById(R.id.fab);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), CreatePostActivity.class);
                startActivity(i);
            }
        });

        //usual adapter

        Post post = new Post("1", "1", "Luisa Gilig", "O+", "09178273678", "Hospital B", "30 IDK St. Who Cares Ave.", 2, 0, new GregorianCalendar().getTimeInMillis());
        postsList.add(post);

        post = new Post("1", "1", "Luisa Gilig", "O+", "09178273678", "Hospital B", "30 IDK St. Who Cares Ave.", 2, 0, new GregorianCalendar().getTimeInMillis());
        postsList.add(post);

        pAdapter = new MyPostAdapter(postsList);

        pAdapter.setOnItemClickListener(new MyPostAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(String id) {
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
