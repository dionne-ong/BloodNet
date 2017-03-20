package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.mobapde.bloodnet.models.Post;

public class FilterPostActivity extends Fragment {

    View MyView;
    CheckBox cbA, cbA1, cbA2,
            cbB, cbB1, cbB2,
            cbAB, cbAB1, cbAB2,
            cbO, cbO1, cbO2;
    private List<Post> postsList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    private PostsAdapter pAdapter;
    RecyclerView rvPosts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_filter_post, container, false);

        cbA = (CheckBox) MyView.findViewById(R.id.cb_a);
        cbA2 = (CheckBox) MyView.findViewById(R.id.cb_a2);

        cbB = (CheckBox) MyView.findViewById(R.id.cb_b);
        cbB2 = (CheckBox) MyView.findViewById(R.id.cb_b2);

        if(cbB.isChecked()){
            cbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Check changed", Toast.LENGTH_SHORT);
                }
            });
        }

        if(cbB2.isChecked()){
            cbB2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Check changed", Toast.LENGTH_SHORT);
                }
            });
        }
        cbAB = (CheckBox) MyView.findViewById(R.id.cb_ab);
        cbAB2 = (CheckBox) MyView.findViewById(R.id.cb_ab2);

        if(cbAB.isChecked()){
            cbAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Check changed", Toast.LENGTH_SHORT);
                }
            });
        }
        if(cbAB2.isChecked()){
            cbAB2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Check changed", Toast.LENGTH_SHORT);
                }
            });
        }
        cbO = (CheckBox) MyView.findViewById(R.id.cb_o);
        cbO2 = (CheckBox) MyView.findViewById(R.id.cb_o2);

        if(cbO.isChecked()){
            cbO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Check changed", Toast.LENGTH_SHORT);
                }
            });
        }
        if(cbO2.isChecked()){
            cbO2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(getActivity(), "Check changed", Toast.LENGTH_SHORT);
                }
            });
        }
        rvPosts = (RecyclerView) MyView.findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());
        rvPosts.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

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

    /**
    public void selectItem(View v){
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.cb_a: if(checked){
                selection.add("A");
            }else{
                selection.remove("A");
            } break;
            case R.id.cb_a2: if(checked){
                selection.add("A-");
            }else{
                selection.remove("A-");
            } break;

            case R.id.cb_b: if(checked){
                selection.add("B");
            }else{
                selection.remove("B");
            } break;
            case R.id.cb_b2: if(checked){
                selection.add("B-");
            }else{
                selection.remove("B-");
            } break;
            case R.id.cb_ab: if(checked){
                selection.add("AB");
            }else{
                selection.remove("AB");
            } break;
            case R.id.cb_ab2: if(checked){
                selection.add("AB-");
            }else{
                selection.remove("AB-");
            } break;

            case R.id.cb_o: if(checked){
                selection.add("O");
            }else{
                selection.remove("O");
            } break;
            case R.id.cb_o2: if(checked){
                selection.add("O-");
            }else{
                selection.remove("O-");
            } break;
        }
    }
     */
}
