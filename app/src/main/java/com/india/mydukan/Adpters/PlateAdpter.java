package com.india.mydukan.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.india.mydukan.Models.PlateModel;
import com.india.mydukan.R;

import java.util.List;

public class PlateAdpter extends RecyclerView.Adapter<PlateAdpter.PlateViewHolder> {

    private List<PlateModel> plateModelList;
    private Context context;

    public PlateAdpter(List<PlateModel> plateModelList, Context context) {
        this.plateModelList = plateModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_plates,viewGroup,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {

        PlateModel plateModel = plateModelList.get(position);

        Glide.with(context).load(plateModel.getPlateImg()).into(holder.plateImg);



    }

    @Override
    public int getItemCount() {
        return plateModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {
        private ImageView plateImg;
        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            plateImg =(ImageView)itemView.findViewById(R.id.imageView2);
        }
    }
}
