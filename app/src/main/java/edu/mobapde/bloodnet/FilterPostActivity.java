package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.mobapde.bloodnet.models.Post;

public class FilterPostActivity extends Fragment {

    View MyView;
    private List<Post> postsList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    private PostsAdapter pAdapter;
    RecyclerView rvPosts;
    CarouselView carouselView;
    ExpandingList expandingList;
    ExpandingItem item;
    TextView tvTitle;

int[] sampleImages = {R.drawable.image3, R.drawable.image9, R.drawable.image7, R.drawable.image10, R.drawable.image4};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_filter_post, container, false);

        carouselView = (CarouselView) MyView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        expandingList = (ExpandingList) MyView.findViewById(R.id.expanding_list_main);
        item = expandingList.createNewItem(R.layout.expanding_layout);
        tvTitle = (TextView) item.findViewById(R.id.title);
        item.createSubItems(1);
        View subItem = item.getSubItemView(0);
        subItem = subItem.findViewById(R.id.sub_item);


        rvPosts = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());

        Post post = new Post(1, 1, "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar());
        postsList.add(post);

        post = new Post(1, 1, "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar());
        postsList.add(post);

        post = new Post(1, 1, "Luisa Gilig", "O+", "09178273678", "Hospital A", "30 IDK St. Who Cares Ave.", 5, 0, new GregorianCalendar());
        postsList.add(post);
        pAdapter = new PostsAdapter(postsList);

        pAdapter.setOnItemClickListener(new PostsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int id) {
                Intent i = new Intent();
                //go to post
                i.setClass(getActivity(), ViewPostActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }

        });
        rvPosts.setAdapter(pAdapter);


        return MyView;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}
