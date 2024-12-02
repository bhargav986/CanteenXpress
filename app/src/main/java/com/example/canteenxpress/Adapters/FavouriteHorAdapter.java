package com.example.canteenxpress.Adapters;


import static com.example.canteenxpress.Myapp.db;
import static com.example.canteenxpress.Myapp.dbHelper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenxpress.Models.FavouriteHorModel;
import com.example.canteenxpress.Models.HomeHorModel;
import com.example.canteenxpress.R;

import java.util.List;

public class FavouriteHorAdapter extends RecyclerView.Adapter<FavouriteHorAdapter.ViewHolder> {
    Context context;
    List<FavouriteHorModel> list;

    public FavouriteHorAdapter(Context context, List<FavouriteHorModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FavouriteHorAdapter.ViewHolder holder, int position) {
        FavouriteHorModel selectedItem = list.get(position);
        holder.image.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = selectedItem.getId();
                dbHelper.deleteUser(db, id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, remove;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.hor_image);
            name = itemView.findViewById(R.id.hor_name);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
