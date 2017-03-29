package edu.mobapde.bloodnet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.mobapde.bloodnet.models.Post;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder>  {

    private List<Post> MyPosts;

    public MyPostAdapter(List<Post> myPosts) {
        MyPosts = myPosts;
    }

    @Override
    public MyPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_posts, parent, false);

        return new MyPostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyPostViewHolder holder, int position) {
        Post post = MyPosts.get(position);


        holder.tvName.setText(post.getPatientName());
        holder.tvHospital.setText(post.getHospitalName());
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        holder.tvDate.setText("Posted on " + format.format(new Date(post.getDatePosted())));
        holder.tvNumOfPledges.setText(post.getPledgedBags() + "");

        holder.container.setTag(post.getId());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnItemClickListener!=null){
                    int id = (int) v.getTag();
                    OnItemClickListener.onItemClick(id);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return MyPosts.size();
    }

    private OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(MyPostAdapter.OnItemClickListener OnItemClickListener) {
        this.OnItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int id);
    }

    public static class MyPostViewHolder extends RecyclerView.ViewHolder{
        LinearLayout container;
        TextView tvName, tvHospital, tvDate, tvNumOfPledges;
        public MyPostViewHolder(View itemView){
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.container_p);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_p);
            tvHospital = (TextView) itemView.findViewById(R.id.tv_hospital_p);
            tvDate = (TextView) itemView.findViewById(R.id.tv_datepost_p);
            tvNumOfPledges = (TextView) itemView.findViewById(R.id.tv_numofpledges_p);

        }
    }
}
