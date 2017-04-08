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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.posts.Post;
import edu.mobapde.bloodnet.models.posts.PostHolder;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class ViewPostListActivity extends Fragment{
    FloatingActionButton btnCreate;
    RecyclerView rvPosts;
    View MyView;
    TextView tvError;
    ProgressBar progressBar;
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference  keyRef, dataRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_view_post_list, container, false);
        rvPosts = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());

        tvError = (TextView) MyView.findViewById(R.id.tv_error);
        progressBar = (ProgressBar) MyView.findViewById(R.id.progressBar);

        btnCreate  = (FloatingActionButton) MyView.findViewById(R.id.fab);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), CreatePostActivity.class);
                startActivity(i);
            }
        });
        //firebase adapter
        keyRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_POST).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);
        mAdapter = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class,
                R.layout.list_item_my_posts,
                PostHolder.class,
                keyRef, // The Firebase location containing the list of keys to be found in dataRef.
                dataRef)//The Firebase location to watch for data changes. Each key key found at keyRef's location represents a list item in the RecyclerView.
                {
                    @Override
                    protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                        viewHolder.setPost(model, true);
                    }

                    @Override
                    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String key = mAdapter.getRef(position).getKey();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);


                                i.setClass(getActivity(), MyPostActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                        return viewHolder;
                    }

                    @Override
                    protected void onDataChanged() {
                        super.onDataChanged();
                        progressBar.setVisibility(View.GONE);
                        tvError.setVisibility(View.GONE);
                        if(mAdapter.getItemCount() == 0){
                            tvError.setText(getString(R.string.no_entries_found));
                            tvError.setVisibility(View.VISIBLE);
                        }else{
                            tvError.setText("");
                            tvError.setVisibility(View.GONE);
                        }
                    }

                } ;


        rvPosts.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                if(mAdapter.getItemCount() == 0){
                    tvError.setText(getString(R.string.no_entries_found));
                    tvError.setVisibility(View.VISIBLE);
                }else{
                    tvError.setText("");
                    tvError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                if(mAdapter.getItemCount() == 0){
                    tvError.setText(getString(R.string.no_entries_found));
                    tvError.setVisibility(View.VISIBLE);
                }else{
                    tvError.setText("");
                    tvError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                if(mAdapter.getItemCount() == 0){
                    tvError.setText(getString(R.string.no_entries_found));
                    tvError.setVisibility(View.VISIBLE);
                }else{
                    tvError.setText("");
                    tvError.setVisibility(View.GONE);
                }
            }
        });
        return MyView;
    }



}
