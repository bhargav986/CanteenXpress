package com.example.canteenxpress.Adapters;


import static com.example.canteenxpress.Myapp.db;
import static com.example.canteenxpress.Myapp.dbHelper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenxpress.AddToCartActivity;
import com.example.canteenxpress.Models.FavouriteHorModel;
import com.example.canteenxpress.Models.HomeHorModel;
import com.example.canteenxpress.R;

import java.util.ArrayList;
import java.util.List;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {
    Context context;
    List<HomeHorModel> list;
    private AdapterClickListener listener;
    List<HomeHorModel> selectedItemList;

    public HomeHorAdapter(Context context, List<HomeHorModel> list) {
        this.context = context;
        this.list = list;
        this.selectedItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeHorModel item = list.get(position);
        holder.image.setImageResource(item.getImage());
        holder.name.setText(item.getName());

        if (dbHelper.isUserExists(item.getName())) {
            holder.like.setImageResource(R.drawable.red_like);
        } else {
            holder.like.setImageResource(R.drawable.white_like);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onItemRemoved(String itemName) {
        for (HomeHorModel item : list) {
            if (item.getName().equals(itemName)) {
                item.setLiked(false);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image, like;
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.hor_img);
            name = itemView.findViewById(R.id.hor_name);
            like = itemView.findViewById(R.id.like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        HomeHorModel clickedItem = list.get(position);
                        Intent intent = new Intent(context, AddToCartActivity.class);
                        intent.putExtra("itemName", clickedItem.getName());
                        intent.putExtra("itemImage", clickedItem.getImage());
                        intent.putExtra("itemPrice", clickedItem.getPrice());
                        context.startActivity(intent);
                    }
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeHorModel selectedItem = list.get(getAdapterPosition());

                    if (dbHelper.isUserExists(selectedItem.getName())) {
                        like.setImageResource(R.drawable.white_like);

                        int id = dbHelper.getUserIdByName(selectedItem.getName());
                        dbHelper.deleteUser(db, id);

                    } else {
                        FavouriteHorModel favouriteHorModel = new FavouriteHorModel(selectedItem.getImage(), selectedItem.getName());

                        dbHelper.insertUser(db, favouriteHorModel);

                        like.setImageResource(R.drawable.red_like);
                    }

                    if (listener != null) {
                        listener.onLikeClicked(selectedItem);
                    }
                    Log.d("bhargav", "Error " + list.size());
                }
            });
        }
    }

    public interface AdapterClickListener {
        void onLikeClicked(HomeHorModel selectedItem);
    }
}