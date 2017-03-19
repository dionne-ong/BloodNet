package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static android.R.attr.onClick;

public class FilterPostActivity extends AppCompatActivity {

    CheckBox cbA, cbA1, cbA2,
            cbB, cbB1, cbB2,
            cbAB, cbAB1, cbAB2,
            cbO, cbO1, cbO2;
    private List<Post> postsList = new ArrayList<>();
    private ArrayList<String> selection = new ArrayList<String>();
    private PostsAdapter pAdapter;
    RecyclerView rvPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_post);
        cbA = (CheckBox) findViewById(R.id.cb_a);
        cbA1 = (CheckBox) findViewById(R.id.cb_a1);
        cbA2 = (CheckBox) findViewById(R.id.cb_a2);

        cbB = (CheckBox) findViewById(R.id.cb_b);
        cbB1 = (CheckBox) findViewById(R.id.cb_b1);
        cbB2 = (CheckBox) findViewById(R.id.cb_b2);

        cbAB = (CheckBox) findViewById(R.id.cb_ab);
        cbAB1 = (CheckBox) findViewById(R.id.cb_ab1);
        cbAB2 = (CheckBox) findViewById(R.id.cb_ab2);

        cbO = (CheckBox) findViewById(R.id.cb_o);
        cbO1 = (CheckBox) findViewById(R.id.cb_o1);
        cbO2 = (CheckBox) findViewById(R.id.cb_o2);

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPosts.setLayoutManager(pLayoutManager);
        rvPosts.setItemAnimator(new DefaultItemAnimator());
        rvPosts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

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
                i.setClass(getBaseContext(), ViewPostActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }

        });
        rvPosts.setAdapter(pAdapter);

    }

    public void selectItem(View v){
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.cb_a: if(checked){
                selection.add("A");
            }else{
                selection.remove("A");
            } break;
            case R.id.cb_a1: if(checked){
                selection.add("A+");
            }else{
                selection.remove("A+");
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
            case R.id.cb_b1: if(checked){
                selection.add("B+");
            }else{
                selection.remove("B+");
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
            case R.id.cb_ab1: if(checked){
                selection.add("AB+");
            }else{
                selection.remove("AB+");
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
            case R.id.cb_o1: if(checked){
                selection.add("O+");
            }else{
                selection.remove("O+");
            } break;
            case R.id.cb_o2: if(checked){
                selection.add("O-");
            }else{
                selection.remove("O-");
            } break;
        }
    }
}
