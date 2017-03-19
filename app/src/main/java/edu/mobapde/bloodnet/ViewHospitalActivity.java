package edu.mobapde.bloodnet;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewHospitalActivity extends AppCompatActivity {

    private List<Post> postsList = new ArrayList<Post>();
    private PostsAdapter pAdapter;
    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hospital);

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());
        rvPosts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//        Post post = new Post(1, "Dionne Ong", "O+", "02/27/2017");
//        postsList.add(post);
//
//        Post post2 = new Post(2,"Rachel de Jesus", "O+", "02/27/2017");
//        postsList.add(post2);
//
//        Post post3 = new Post(3,"Luisa Gilig", "B+", "02/27/2017");
//        postsList.add(post3);

        pAdapter = new PostsAdapter(postsList);
        pAdapter.setOnItemClickListener(new PostsAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(int id) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), ViewPostActivity.class);
                i.putExtra("id", id);

                startActivity(i);
            }
        });


        rvPosts.setAdapter(pAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL, false));
    }
}
