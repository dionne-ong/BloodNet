package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.models.posts.Post;
import edu.mobapde.bloodnet.models.posts.PostHolder;
import edu.mobapde.bloodnet.models.posts.PostsAdapter;

public class FilterPostActivity extends Fragment {

    View MyView;
    private List<Post> postsList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    private PostsAdapter pAdapter;
    RecyclerView rvPosts;
    CarouselView carouselView;
    ExpandingList expandingList;
    ExpandingItem item;
    TextView tvFilter;

    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;

    int[] sampleImages = {R.drawable.image3, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image4};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_filter_post, container, false);

        carouselView = (CarouselView) MyView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        expandingList = (ExpandingList) MyView.findViewById(R.id.expanding_list_main);
        item = expandingList.createNewItem(R.layout.expanding_layout);

        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Raleway-Light.ttf");
        tvFilter = (TextView) item.findViewById(R.id.tv_filtertype);
        tvFilter.setTypeface(face);
        item.createSubItems(1);
        View subItem = item.getSubItemView(0);
        subItem = subItem.findViewById(R.id.sub_item);

        RadioGroup rgType= (RadioGroup) MyView.findViewById(R.id.rg_type);
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.rb_A:
                        //   Case 1
                        break;
                    case R.id.rb_B:
                        // Case 2
                        break;
                    case R.id.rb_AB:
                        // Case 3
                        break;

                    case R.id.rb_O:
                        // Case 4
                        break;
                }
            }
        });

        RadioGroup rgSign= (RadioGroup) MyView.findViewById(R.id.rg_sign);
        rgSign.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.rb_pos:
                        //   Case 1
                        break;
                    case R.id.rb_neg:
                        // Case 2
                        break;
                }
            }
        });


        //firebase adapter

        rvPosts = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());

        mRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);
        mAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, mRef) {
            @Override
            public void populateViewHolder(PostHolder postViewHolder, Post post, int position) {
                postViewHolder.setPost(post);
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
                        i.setClass(getActivity(), ViewPostActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };

        rvPosts.setAdapter(mAdapter);


//
//        Post post = new Post("1", "1", "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar().getTimeInMillis());
//        postsList.add(post);
//
//        Post post1 = new Post("1", "1", "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar().getTimeInMillis());
//        postsList.add(post1);
//
//        Post post2 = new Post("1", "1", "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar().getTimeInMillis());
//        postsList.add(post2);
//        pAdapter = new PostsAdapter(postsList);
//
//        pAdapter.setOnItemClickListener(new PostsAdapter.OnItemClickListener(){
//            @Override
//            public void onItemClick(String id) {
//                Intent i = new Intent();
//                //go to post
//                i.setClass(getActivity(), ViewPostActivity.class);
//                i.putExtra("id", id);
//                startActivity(i);
//            }
//
//        });
//        rvPosts.setAdapter(pAdapter);


        return MyView;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }


}
