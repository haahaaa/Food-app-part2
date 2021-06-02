package com.example.foodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewholder> {

    private final ArrayList<Item> itemlist;

public static class itemViewholder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView textView1;
    public TextView textView2;

    public itemViewholder(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.cartimage);
        textView1 = itemView.findViewById(R.id.carttitle);
        textView2 = itemView.findViewById(R.id.cartquan);

    }
}
    public itemAdapter(ArrayList<Item> list){itemlist = list;}
    @Override
    public itemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent,false);
        itemViewholder EVH = new itemViewholder(v);
        return EVH;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewholder holder, int position) {
        Item cur = itemlist.get(position);
        holder.imageView.setImageBitmap(cur.getImage());
        holder.textView1.setText(cur.getTitle());
        holder.textView2.setText(cur.getQuantity());

    }


    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}
