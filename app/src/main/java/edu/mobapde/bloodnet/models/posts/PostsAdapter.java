package edu.mobapde.bloodnet.models.posts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.mobapde.bloodnet.R;

/**
 * Created by cdejesus on 3/16/2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private List<Post> postsList;

    public PostsAdapter(List<Post> postsList){
        this.postsList = postsList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        Post post = postsList.get(position);
        holder.tvName.setText(post.getPatientName());
        holder.tvBtype.setText(post.getBloodType());
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        holder.tvDatePost.setText("Posted on " + format.format(new Date(post.getDatePosted())));
        holder.tvHospital.setText(post.getHospitalName());

        holder.container.setTag(post.getId());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnItemClickListener!=null){
                    String id = (String) v.getTag();
                    OnItemClickListener.onItemClick(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    private OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener OnItemClickListener) {
        this.OnItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(String id);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvBtype, tvDatePost, tvHospital;
        LinearLayout container;
        public PostViewHolder(View itemView){
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.container);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_p);
            tvBtype = (TextView) itemView.findViewById(R.id.tv_data);
            tvDatePost = (TextView) itemView.findViewById(R.id.tv_datepost_p);
            tvHospital = (TextView) itemView.findViewById(R.id.tv_hospital_p);
        }
    }
}
