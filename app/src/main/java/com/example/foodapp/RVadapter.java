package com.example.foodapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVadapter extends RecyclerView.Adapter<RVadapter.ViewHolder>{
    ArrayList<Model> modelslist;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onShareClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public RVadapter(ArrayList<Model> modelslist) {
        this.modelslist = modelslist;
    }


    @NonNull
    @Override
    public RVadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVadapter.ViewHolder holder, int position) {
        Model model = modelslist.get(position);
        holder.imageTitle.setText(model.getImagetitle());
        holder.imageDes.setText(model.getImagedes());
        holder.image.setImageBitmap(model.getImage());
        holder.location.setText("Location: " + model.getLocation());
        holder.date.setText("Pick up date: "+ model.getDate());

    }

    @Override
    public int getItemCount() {
        return modelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView imageTitle,imageDes,date,location;
        ImageView image,share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.row_title);
            imageDes = itemView.findViewById(R.id.row_des);
            image = itemView.findViewById(R.id.row_image);
            date = itemView.findViewById(R.id.row_date);
            location = itemView.findViewById(R.id.row_loca);
            share = itemView.findViewById(R.id.share);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onShareClick(position);
                        }
                    }
                }
            });

        }
    }
}
