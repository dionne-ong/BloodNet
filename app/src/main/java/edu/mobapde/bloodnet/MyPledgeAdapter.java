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

import edu.mobapde.bloodnet.models.Pledge;

/**
 * Created by Luisa Gilig on 20/03/2017.
 */

public class MyPledgeAdapter extends RecyclerView.Adapter<MyPledgeAdapter.MyPledgeViewHolder> {
    private List<Pledge> MyPledge;

    public MyPledgeAdapter(List<Pledge> myPledge) {
        MyPledge = myPledge;
    }

    @Override
    public MyPledgeAdapter.MyPledgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);

        return new MyPledgeAdapter.MyPledgeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyPledgeAdapter.MyPledgeViewHolder holder, int position) {
        Pledge pledge = MyPledge.get(position);


        holder.tvName.setText(pledge.getPost().getPatientName());
        holder.tvHospital.setText(pledge.getPost().getHospitalName());
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        holder.tvDate.setText("Posted on " + format.format(new Date(pledge.getPost().getDatePosted())));
        holder.tvBloodType.setText(pledge.getPost().getBloodType());

        holder.container.setTag(pledge.getPost().getId());
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
        return MyPledge.size();
    }

    private MyPostAdapter.OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(MyPostAdapter.OnItemClickListener OnItemClickListener) {
        this.OnItemClickListener = OnItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int id);
    }

    public static class MyPledgeViewHolder extends RecyclerView.ViewHolder{
        LinearLayout container;
        TextView tvName, tvHospital, tvDate, tvBloodType;
        public MyPledgeViewHolder(View itemView){
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.container);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_p);
            tvHospital = (TextView) itemView.findViewById(R.id.tv_hospital_p);
            tvDate = (TextView) itemView.findViewById(R.id.tv_datepost_p);
            tvBloodType = (TextView) itemView.findViewById(R.id.tv_btype_p);

        }
    }

}
