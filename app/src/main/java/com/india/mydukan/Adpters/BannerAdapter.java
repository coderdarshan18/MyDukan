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
import com.india.mydukan.EmailLoginRegister.EmailLoginActivity;
import com.india.mydukan.HomeActivity;
import com.india.mydukan.Models.BannerModel;
import com.india.mydukan.Models.CategoryModel;
import com.india.mydukan.R;
import com.india.mydukan.Summary;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.PlateViewHolder> {

    private List<BannerModel> bannerModelList;
    private Context context;

    public BannerAdapter(List<BannerModel> bannerModelList, Context context) {
        this.bannerModelList = bannerModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_banner,viewGroup,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {

        BannerModel bannerModel = bannerModelList.get(position);


        Glide.with(context).load(bannerModel.getBanner_image()).placeholder(R.drawable.items1).into(holder.banner_img);

        holder.banner_img.setOnClickListener(view -> {
            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();

            context.startActivity(new Intent(context, Summary.class)
                    .putExtra("banner_id",bannerModel.getBanner_id())
                    .putExtra("banner_image",bannerModel.getBanner_image()));
            Toast.makeText(context, "India", Toast.LENGTH_SHORT).show();


        });



    }

    @Override
    public int getItemCount() {
        return bannerModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {
        private ImageView banner_img;

        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            banner_img =(ImageView)itemView.findViewById(R.id.banner_img);

        }
    }
}


