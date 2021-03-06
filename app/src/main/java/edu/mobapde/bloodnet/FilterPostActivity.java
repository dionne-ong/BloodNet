package edu.mobapde.bloodnet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.HashMap;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.User;
import edu.mobapde.bloodnet.models.posts.Post;
import edu.mobapde.bloodnet.models.posts.PostHolder;

public class FilterPostActivity extends Fragment {

    View MyView;
    RecyclerView rvPosts;
    CarouselView carouselView;
    ExpandingList expandingList;
    ExpandingItem item;
    RecyclerView.AdapterDataObserver observer;
    TextView tvFilter;

    TextView tvError;
    ProgressBar progressBar;

    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference userPledgeRef;
    String key;
    private HashMap<String, FirebaseIndexRecyclerAdapter> filterAdapter;
    FirebaseAuth auth;
    DatabaseReference userRef;
    int[] sampleImages = {R.drawable.image3, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image4};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_filter_post, container, false);
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER);

        tvError = (TextView) MyView.findViewById(R.id.tv_error);
        progressBar = (ProgressBar) MyView.findViewById(R.id.progressBar);

        carouselView = (CarouselView) MyView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        expandingList = (ExpandingList) MyView.findViewById(R.id.expanding_list_main);
        item = expandingList.createNewItem(R.layout.expanding_layout);

        filterAdapter = new HashMap<String, FirebaseIndexRecyclerAdapter>();
        createMap();
        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Raleway-Light.ttf");
        tvFilter = (TextView) item.findViewById(R.id.tv_filtertype);
        tvFilter.setTypeface(face);

        item.createSubItems(1);
        View subItem = item.getSubItemView(0);
        subItem = subItem.findViewById(R.id.sub_item);

        final RadioGroup rgType= (RadioGroup) MyView.findViewById(R.id.rg_type);
        final RadioGroup rgSign= (RadioGroup) MyView.findViewById(R.id.rg_sign);
        final String[] sign = new String[1];
        final String[] type = new String[1];
        observer = new RecyclerView.AdapterDataObserver() {
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
        };

        userRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String btype = null;
                User u = dataSnapshot.getValue(User.class);
                if(u != null) {
                    btype = u.getBloodType();
                    switch(btype){
                        case "A+":
                            mAdapter = (filterAdapter.get(DBOPost.A_POSITIVE));
                            rgType.check(R.id.rb_A);
                            rgSign.check(R.id.rb_pos);
                            break;
                        case "A-":
                            mAdapter = (filterAdapter.get(DBOPost.A_NEGATIVE));
                            rgType.check(R.id.rb_A);
                            rgSign.check(R.id.rb_neg);
                            break;
                        case "B+":
                            mAdapter = (filterAdapter.get(DBOPost.B_POSITIVE));
                            rgType.check(R.id.rb_B);
                            rgSign.check(R.id.rb_pos);
                            break;
                        case "B-":
                            mAdapter = (filterAdapter.get(DBOPost.B_NEGATIVE));
                            rgType.check(R.id.rb_B);
                            rgSign.check(R.id.rb_neg);
                            break;
                        case "AB+":
                            mAdapter = (filterAdapter.get(DBOPost.AB_POSITIVE));
                            rgType.check(R.id.rb_AB);
                            rgSign.check(R.id.rb_pos);
                            break;
                        case "AB-":
                            mAdapter = (filterAdapter.get(DBOPost.AB_NEGATIVE));
                            rgType.check(R.id.rb_AB);
                            rgSign.check(R.id.rb_neg);
                            break;
                        case "O+":
                            mAdapter = (filterAdapter.get(DBOPost.O_POSITIVE));
                            rgType.check(R.id.rb_O);
                            rgSign.check(R.id.rb_pos);
                            break;
                        case "O-":
                            mAdapter = (filterAdapter.get(DBOPost.O_NEGATIVE));
                            rgType.check(R.id.rb_O);
                            rgSign.check(R.id.rb_neg);
                            break;
                        default:
                            mAdapter = (filterAdapter.get(DBOPost.A_POSITIVE));
                            rgType.check(R.id.rb_A);
                            rgSign.check(R.id.rb_pos);
                            break;

                    }
                    rvPosts.setAdapter(mAdapter);
                    mAdapter.registerAdapterDataObserver(observer);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rgSign.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                progressBar.setVisibility(View.VISIBLE);
                if(mAdapter != null){
                    mAdapter.unregisterAdapterDataObserver(observer);
                }
                switch(checkedId){
                    case R.id.rb_pos:
                        sign[0] = "+";

                        switch(rgType.getCheckedRadioButtonId()){
                            case R.id.rb_A:
                                mAdapter = (filterAdapter.get(DBOPost.A_POSITIVE));
                                break;
                            case R.id.rb_B:
                                mAdapter = (filterAdapter.get(DBOPost.B_POSITIVE));
                            case R.id.rb_AB:
                                mAdapter = (filterAdapter.get(DBOPost.AB_POSITIVE));
                                break;
                            case R.id.rb_O:
                                mAdapter = (filterAdapter.get(DBOPost.O_POSITIVE));
                                break;
                        }

                        break;
                    case R.id.rb_neg:
                        sign[0] = "-";
                        switch(rgType.getCheckedRadioButtonId()){
                            case R.id.rb_A:
                                mAdapter = (filterAdapter.get(DBOPost.A_NEGATIVE));
                                break;
                            case R.id.rb_B:
                                mAdapter = (filterAdapter.get(DBOPost.B_NEGATIVE));
                            case R.id.rb_AB:
                                mAdapter = (filterAdapter.get(DBOPost.AB_NEGATIVE));
                                break;
                            case R.id.rb_O:
                                mAdapter = (filterAdapter.get(DBOPost.O_NEGATIVE));
                                break;
                        }
                        break;
                }
                rvPosts.setAdapter(mAdapter);
                mAdapter.registerAdapterDataObserver(observer);
            }
        });





        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                progressBar.setVisibility(View.VISIBLE);
                if(mAdapter != null){
                    mAdapter.unregisterAdapterDataObserver(observer);
                }
                switch(checkedId) {
                    case R.id.rb_A:
                        switch(rgSign.getCheckedRadioButtonId()){
                            case R.id.rb_pos:
                                mAdapter = (filterAdapter.get(DBOPost.A_POSITIVE));
                                break;
                            case R.id.rb_neg:
                                mAdapter = (filterAdapter.get(DBOPost.A_NEGATIVE));
                                break;
                        }
                        break;
                    case R.id.rb_B:
                        switch(rgSign.getCheckedRadioButtonId()){
                            case R.id.rb_pos:
                                mAdapter = (filterAdapter.get(DBOPost.B_POSITIVE));
                                break;
                            case R.id.rb_neg:
                                mAdapter = (filterAdapter.get(DBOPost.B_NEGATIVE));
                                break;
                        }
                        break;
                    case R.id.rb_AB:
                        switch(rgSign.getCheckedRadioButtonId()){
                            case R.id.rb_pos:
                                mAdapter = (filterAdapter.get(DBOPost.AB_POSITIVE));
                                break;
                            case R.id.rb_neg:
                                mAdapter = (filterAdapter.get(DBOPost.AB_NEGATIVE));
                                break;
                        }
                        break;

                    case R.id.rb_O:
                        switch(rgSign.getCheckedRadioButtonId()) {
                            case R.id.rb_pos:
                                mAdapter = (filterAdapter.get(DBOPost.O_POSITIVE));
                                break;
                            case R.id.rb_neg:
                                mAdapter = (filterAdapter.get(DBOPost.O_NEGATIVE));
                                break;
                        }
                        break;
                }
                rvPosts.setAdapter(mAdapter);
                mAdapter.registerAdapterDataObserver(observer);
            }
        });


        //firebase adapter

        rvPosts = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());

        /*
        mAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, mRef) {
            @Override
            public void populateViewHolder(PostHolder postViewHolder, Post post, int position) {
                postViewHolder.setPost(post, false);
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
*/

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
        filterAdapter.get(DBOPost.A_POSITIVE).cleanup();
        filterAdapter.get(DBOPost.B_POSITIVE).cleanup();
        filterAdapter.get(DBOPost.AB_POSITIVE).cleanup();
        filterAdapter.get(DBOPost.O_POSITIVE).cleanup();

        filterAdapter.get(DBOPost.A_NEGATIVE).cleanup();
        filterAdapter.get(DBOPost.B_NEGATIVE).cleanup();
        filterAdapter.get(DBOPost.AB_NEGATIVE).cleanup();
        filterAdapter.get(DBOPost.O_NEGATIVE).cleanup();
    }


    public void createMap(){
        // get default posts
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);

        DatabaseReference keyRefAP = FirebaseDatabase.getInstance().getReference().child(DBOPost.A_POSITIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterAP = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefAP, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.A_POSITIVE, adapterAP);


        DatabaseReference keyRefAN = FirebaseDatabase.getInstance().getReference().child(DBOPost.A_NEGATIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterAN = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefAN, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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

            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.A_NEGATIVE, adapterAN);

        DatabaseReference keyRefBP = FirebaseDatabase.getInstance().getReference().child(DBOPost.B_POSITIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterBP = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefBP, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                 if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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

            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.B_POSITIVE, adapterBP);

        DatabaseReference keyRefBN = FirebaseDatabase.getInstance().getReference().child(DBOPost.B_NEGATIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterBN = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefBN, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.B_NEGATIVE, adapterBN);


        DatabaseReference keyRefABP = FirebaseDatabase.getInstance().getReference().child(DBOPost.AB_POSITIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterABP = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefABP, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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

        };
        filterAdapter.put(DBOPost.AB_POSITIVE, adapterABP);


        DatabaseReference keyRefABN = FirebaseDatabase.getInstance().getReference().child(DBOPost.AB_NEGATIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterABN = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefABN, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.AB_NEGATIVE, adapterABN);


        DatabaseReference keyRefOP = FirebaseDatabase.getInstance().getReference().child(DBOPost.O_POSITIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterOP = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefOP, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.O_POSITIVE, adapterOP);


        DatabaseReference keyRefON = FirebaseDatabase.getInstance().getReference().child(DBOPost.O_NEGATIVE);
        FirebaseIndexRecyclerAdapter<Post, PostHolder> adapterON = new FirebaseIndexRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.list_item_post, PostHolder.class, keyRefON, dataRef) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Post model, int position) {
                viewHolder.setPost(model, false);
            }

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                PostHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new PostHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        key = mAdapter.getRef(position).getKey();
                        userPledgeRef = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_PLEDGE).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        Log.i("TEST", "TESTING LOG CLICK");
                        userPledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                Intent i = new Intent();
                                i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                if(map!= null && map.containsKey(key)){
                                    i.setClass(getActivity(), MyPledgeActivity.class);

                                }else{
                                    i.setClass(getActivity(), ViewPostActivity.class);

                                }
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }

            @Override
            public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
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
        };
        filterAdapter.put(DBOPost.O_NEGATIVE, adapterON);
    }

}
