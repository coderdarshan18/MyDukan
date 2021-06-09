package com.india.mydukan.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.india.mydukan.Models.CategoryModel;

import com.india.mydukan.R;

import java.util.List;

public class CatAdpter extends RecyclerView.Adapter<CatAdpter.PlateViewHolder> {

    private List<CategoryModel> categoryModelList;
    private Context context;

    public CatAdpter(List<CategoryModel> categoryModelList, Context context) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category,viewGroup,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {

        CategoryModel categoryModel = categoryModelList.get(position);
        holder.cat_title.setText(categoryModel.getCat_title());

        Glide.with(context).load(categoryModel.getCat_image()).placeholder(R.drawable.veg).into(holder.catImg);



    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {
        private ImageView catImg;
        private TextView cat_title;
        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            catImg =(ImageView)itemView.findViewById(R.id.catImg);
            cat_title = (TextView)itemView.findViewById(R.id.catTitle);
        }
    }
}

