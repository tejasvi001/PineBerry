package com.example.pineberry.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pineberry.R;
import com.example.pineberry.activities.DetailedActivity;
import com.example.pineberry.fragments.DetailedFragment;
import com.example.pineberry.models.PopularProductsModel;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {
    private Context context;
    private List<PopularProductsModel> list;
    FragmentManager fragmentManager;
    public PopularProductsAdapter(Context context,List<PopularProductsModel> list,FragmentManager fragmentManager) {
        this.context = context;
        this.list=list;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public PopularProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedFragment fragment = new DetailedFragment();
                Bundle args = new Bundle();
                args.putSerializable("detailed", list.get(position));
                fragment.setArguments(args);
                FragmentManager fragmentManager1 = fragmentManager;
                FragmentTransaction transaction = fragmentManager1.beginTransaction();
                transaction.replace(R.id.home_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.all_img);
            name=itemView.findViewById(R.id.all_product_name);
            price=itemView.findViewById(R.id.all_price);
        }
    }
}
