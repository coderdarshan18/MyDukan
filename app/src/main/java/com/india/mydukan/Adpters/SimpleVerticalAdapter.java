package com.india.mydukan.Adpters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.india.mydukan.Models.BannerModel;
import com.india.mydukan.Models.SimpleVerticalModel;
import com.india.mydukan.R;
import com.india.mydukan.Summary;

import java.util.List;

public class SimpleVerticalAdapter extends RecyclerView.Adapter<SimpleVerticalAdapter.PlateViewHolder> {

    private List<SimpleVerticalModel> simpleVerticalModelList;
    private Context context;

    public SimpleVerticalAdapter(List<SimpleVerticalModel> simpleVerticalModelList, Context context) {
        this.simpleVerticalModelList = simpleVerticalModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical,viewGroup,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {

        SimpleVerticalModel simpleVerticalModel = simpleVerticalModelList.get(position);


        Glide.with(context).load(simpleVerticalModel.getPro_img()).placeholder(R.drawable.store).into(holder.pro_img);

        holder.pro_title.setText(simpleVerticalModel.getSimple_title());
        holder.pro_description.setText(simpleVerticalModel.getSimple_description());
        holder.pro_coupon.setText(simpleVerticalModel.getSimple_coupon());
        holder.pro_quantity.setText(simpleVerticalModel.getSimple_quantity());
        holder.pro_status.setText(simpleVerticalModel.getSimple_status());
        holder.pro_rating.setText(simpleVerticalModel.getSimple_rating());


        holder.pro_img.setOnClickListener(view -> {
            
            context.startActivity(new Intent(context, Summary.class)
                    .putExtra("simple_title",simpleVerticalModel.getSimple_title()));

            Toast.makeText(context, "India", Toast.LENGTH_SHORT).show();


        });

    }

    @Override
    public int getItemCount() {
        return simpleVerticalModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {
        private ImageView pro_img;
        private TextView pro_title,pro_description,pro_coupon,pro_quantity,pro_status,pro_rating;

        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            pro_img =(ImageView)itemView.findViewById(R.id.verticalItem);
            pro_title =(TextView)itemView.findViewById(R.id.textView);
            pro_description =(TextView)itemView.findViewById(R.id.textView3);
            pro_coupon =(TextView)itemView.findViewById(R.id.textView5);
            pro_quantity =(TextView)itemView.findViewById(R.id.textView4);
            pro_status =(TextView)itemView.findViewById(R.id.textView6);
            pro_rating =(TextView)itemView.findViewById(R.id.textView7);


        }
    }
}


