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
import com.example.pineberry.fragments.ShowAllFragment;
import com.example.pineberry.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder>{
    private Context context;
    private List<NewProductsModel> list;
    private FragmentManager fragmentManager;

    public NewProductsAdapter(Context context,List<NewProductsModel> list,FragmentManager fragmentManager) {
        this.context = context;
        this.list=list;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public NewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImg);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));
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
        ImageView newImg;
        TextView newName,newPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newImg=itemView.findViewById(R.id.new_img);
            newName=itemView.findViewById(R.id.new_product_name);
            newPrice=itemView.findViewById(R.id.new_price);
        }
    }
}
