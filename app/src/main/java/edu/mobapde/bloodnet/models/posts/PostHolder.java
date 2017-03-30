package edu.mobapde.bloodnet.models.posts;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.mobapde.bloodnet.R;

/**
 * Created by psion on 3/30/2017.
 */

public class PostHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvBtype, tvDatePost, tvHospital;
        Post post;
        LinearLayout container;

        public PostHolder(View itemView){
                super(itemView);
                container = (LinearLayout) itemView.findViewById(R.id.container);
                tvName = (TextView) itemView.findViewById(R.id.tv_name_p);
                tvBtype = (TextView) itemView.findViewById(R.id.tv_btype_p);
                tvDatePost = (TextView) itemView.findViewById(R.id.tv_datepost_p);
                tvHospital = (TextView) itemView.findViewById(R.id.tv_hospital_p);
        }

        public ClickListener getmClickListener(){
            return mClickListener;
        }

        public LinearLayout getContainer(){
            return container;
        }

        public void setPost(Post post){
            this.post = post;

            setName(post.getPatientName());
            setDate(post.getDatePosted());
            setTvHospital(post.getHospitalName());
            setType(post.getBloodType());


            //listener set on ENTIRE ROW, you may set on individual components within a row.
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mClickListener.onItemClick(v, getAdapterPosition());

                            }
                        });
                        itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                mClickListener.onItemLongClick(v, getAdapterPosition());
                                return true;
                            }
                        });
        }

        public void setName(String name){
            tvName.setText(name);
        }

        public void setType(String type){
            tvBtype.setText(type);
        }

        public void setDate(long time){
            SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
            Date date = new Date(time);
            tvDatePost.setText(format.format(date));
        }

        public void setTvHospital(String hospital){
            tvHospital.setText(hospital);
        }

    private PostHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(PostHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }


}